package edu.mines.csci598.recycler.backend;

import java.nio.ShortBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.OpenNI.*;

public class HandTracker {
    // the size of the history for locations, for drawing paths
	private static final int HISTORY_SIZE = 2;

    // OpenNI context information
	private OutArg<ScriptNode> scriptNode;
    private Context context;

    // OpenNI depth information
    private DepthGenerator depthGen;
    
    // OpenNI image information
    private ImageGenerator imageGen;

    // OpenNI gesture information
    private GestureGenerator gestureGen;

    // OpenNI hand tracker information
    private HandsGenerator handsGen;

    // a list of historical 3D points for each tracked point; each tracked
    // point has a unique ID, which is the key of the map
    private Map<Integer, List<Point3D>> history;

    // width and height of the depth image
    private int width, height;

    // the XML file to read for configuration information
    private static final String CONFIG_XML_FILE = "openni_config.xml";

    /**
     * A gesture observer to begin tracking a hand when a gesture is observed.
     */
	class MyGestureRecognized implements IObserver<GestureRecognizedEventArgs> {
		@Override
		public void update(IObservable<GestureRecognizedEventArgs> observable,
                           GestureRecognizedEventArgs args) {
			try {
                // start tracking the position of the wave, which is the hand
				handsGen.StartTracking(args.getEndPosition());
			}
            catch (StatusException e) {
				e.printStackTrace();
			}
		}
	}

    /**
     * A gesture observer that is called when a new hand is detected.
     */
	class MyHandCreateEvent implements IObserver<ActiveHandEventArgs> {
		public void update(IObservable<ActiveHandEventArgs> observable,
                           ActiveHandEventArgs args) {
                  System.out.println("create event: " + args);
            // create a new list of historical points for the newly-detected
            // hand and add the current location
			List<Point3D> newList = Collections.synchronizedList(new LinkedList<Point3D>());
			newList.add(args.getPosition());
			history.put(new Integer(args.getId()), newList);
		}
	}

    /**
     * A gesture observer that is called when a hand is moved.
     */
	class MyHandUpdateEvent implements IObserver<ActiveHandEventArgs> {
		public void update(IObservable<ActiveHandEventArgs> observable,
                           ActiveHandEventArgs args) {
                  System.out.println(args.getPosition());
            // add the current location to the history of points
			List<Point3D> historyList = history.get(args.getId());
			historyList.add(args.getPosition());

            // only keep the last HISTORY_SIZE points
			while(historyList.size() > HISTORY_SIZE) {
				historyList.remove(0);
			}
		}
	}

    /**
     * A gesture observer that is called when a hand disappears.
     */
	class MyHandDestroyEvent implements IObserver<InactiveHandEventArgs> {
		public void update(IObservable<InactiveHandEventArgs> observable,
                           InactiveHandEventArgs args) {
            // the hand went away; remove the tracking information for this
            // point
			history.remove(args.getId());
		}
	}

    /**
     * Creates a hand tracker, initializes gestures, and initializes buffers.
     */
    public HandTracker() {
        try {
          System.out.println("constructor");
            // context setup
            scriptNode = new OutArg<ScriptNode>();
            context = Context.createFromXmlFile(CONFIG_XML_FILE, scriptNode);

            // wave to start tracking a hand
            gestureGen = GestureGenerator.create(context);
            gestureGen.addGesture("Wave");
            gestureGen.getGestureRecognizedEvent().addObserver(new MyGestureRecognized());

            // track hands
            handsGen = HandsGenerator.create(context);
            handsGen.getHandCreateEvent().addObserver(new MyHandCreateEvent());
            handsGen.getHandUpdateEvent().addObserver(new MyHandUpdateEvent());
            handsGen.getHandDestroyEvent().addObserver(new MyHandDestroyEvent());

            // depth generator map
            depthGen = DepthGenerator.create(context);
            DepthMetaData depthMD = depthGen.getMetaData();
            
            // Visual spectrum map generator
            imageGen = ImageGenerator.create(context);
            ImageMetaData imageMD = imageGen.getMetaData();

            // start everything
			context.startGeneratingAll();

            // keep a history of points
            history = new HashMap<Integer, List<Point3D>>();

            // width and height of the depth image
            width = depthMD.getFullXRes();
            height = depthMD.getFullYRes();
        }
        catch (GeneralException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Returns the current position information for each tracked ID.
     *
     * @return A map of hand ID to the position of the hand in 2D space.
     */
    public Map<Integer, Point3D> getCurrentPositions() {
        try {
          context.waitAnyUpdateAll();
        } catch (StatusException e) {
          e.printStackTrace();
        }
        Map<Integer, Point3D> positions = new HashMap<Integer, Point3D>();

        for(Integer id : history.keySet()) {
            // get the current point for a given ID and project onto the 2D
            // screen coordinates
            Point3D point = history.get(id).get(history.get(id).size() - 1);
            try {
                Point3D proj = depthGen.convertRealWorldToProjective(point);
                positions.put(id, proj);
            }
            catch (StatusException e) {
			}
        }

        return positions;
    }

    /**
     * Returns the width component of the OpenNI space.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Returns the width component of the OpenNI space.
     */
    public int getHeight() {
        return this.height;
    }
    
    public DepthMetaData getDepthData(){
        return depthGen.getMetaData();
    }
    
    public ImageMetaData getVisualData(){
        return imageGen.getMetaData();
    }
}
