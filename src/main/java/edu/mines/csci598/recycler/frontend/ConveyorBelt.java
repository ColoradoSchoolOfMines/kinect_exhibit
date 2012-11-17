package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Line;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ConveyorBelt {
	private static final Logger logger = Logger.getLogger(ConveyorBelt.class);
	
    private Path p;
    private Random random;
    private ArrayList<Recyclable> recyclables;
    private double lastGenerationTime;
	private double nextTimeToGenerate;
    private GameLogic game;
    
    private double bottomLineStartTime = GameConstants.BOTTOM_PATH_START_TIME;
    private double bottomLineEndTime = GameConstants.BOTTOM_PATH_END_TIME;
    private double verticalLineStartTime = GameConstants.VERTICAL_PATH_START_TIME;
    private double verticalLineEndTime = GameConstants.VERTICAL_PATH_END_TIME;
    private double topLineStartTime = GameConstants.TOP_PATH_START_TIME;
    private double topLineEndTime = GameConstants.TOP_PATH_END_TIME;
    private static final boolean debugCollisions = GameConstants.DEBUG_COLLISIONS;

    private Line bottomLine = new Line(GameConstants.BOTTOM_PATH_START_X, GameConstants.BOTTOM_PATH_START_Y,
            GameConstants.BOTTOM_PATH_END_X, GameConstants.BOTTOM_PATH_END_Y, bottomLineStartTime);
    private Line verticalLine = new Line(GameConstants.VERTICAL_PATH_START_X, GameConstants.VERTICAL_PATH_START_Y,
            GameConstants.VERTICAL_PATH_END_X, GameConstants.VERTICAL_PATH_END_Y, verticalLineStartTime);
    private Line topLine = new Line(GameConstants.TOP_PATH_START_X, GameConstants.TOP_PATH_START_Y,
            GameConstants.TOP_PATH_END_X, GameConstants.TOP_PATH_END_Y, topLineStartTime);


    public ConveyorBelt(GameLogic game) {
        this.game = game;
    	
    	recyclables = new ArrayList<Recyclable>();

        p = new Path();
        p.addLine(bottomLine);
        p.addLine(verticalLine);
        p.addLine(topLine);
        
        lastGenerationTime = 0;
        
        logger.setLevel(Level.DEBUG);
        random = new Random(System.currentTimeMillis());
    }

    public ArrayList<Recyclable> getRecyclables() {
        return recyclables;
    }
    
    public int getNumRecyclablesOnConveyor(){
        return recyclables.size();
    }
    
    /*
     * Returns the next touchable recyclable
     */
    public Recyclable getNextRecyclableThatIsTouchable(){
        Recyclable ret;
        int index=0;
        ret = recyclables.get(index);
        while(ret.getSprite().getState()== Sprite.TouchState.UNTOUCHABLE && index < recyclables.size()){
            index++;
            if(index<recyclables.size()-1)
                ret = recyclables.get(index);
        }

        return ret;
    }

    public void addRecyclable(Recyclable r) {
        recyclables.add(r);
        r.getSprite().setPath(p);
		game.addRecyclable(r);
    }

    public void removeRecyclable(Recyclable r) {
        recyclables.remove(r);
    }

    public void setSpeed(double pctOfFullSpeed) {
        bottomLine.setTotalTime(bottomLineStartTime + pctOfFullSpeed * (bottomLineEndTime - bottomLineStartTime));
        verticalLine.setTotalTime(verticalLineStartTime + pctOfFullSpeed * (verticalLineEndTime - verticalLineStartTime));
        topLine.setTotalTime(topLineStartTime + pctOfFullSpeed * (topLineEndTime - topLineStartTime));
    }

	public void update(double currentTimeSec) {
        if (!debugCollisions) {
            generateItems(currentTimeSec);
        }
	}

	/**
	 * Generates new item if necessary
	 */
	private void generateItems(double currentTimeSec) {
		if (needsItemGeneration(currentTimeSec)) {
			Recyclable r = new Recyclable(currentTimeSec, RecyclableType.getRandom(game.getNumItemTypesInUse()));
			addRecyclable(r);
			lastGenerationTime = currentTimeSec;
			//logger.debug("Item generated: " + r);
		}

    }
    /**
     * Determines if item should be generated.  If true, sets the time for the next object to be generated
     *
     * @return true if item should be generated, false otherwise
     */
    private boolean needsItemGeneration(double currentTimeSec) {
		// TODO: Make it harder - faster generation
		if (currentTimeSec > nextTimeToGenerate) {
			double timeToAdd = random.nextGaussian()
					+ GameConstants.MIN_TIME_BETWEEN_GENERATIONS;
			nextTimeToGenerate = currentTimeSec + Math.max(timeToAdd, GameConstants.MIN_TIME_BETWEEN_GENERATIONS);
			lastGenerationTime = currentTimeSec;
			
			logger.debug("Current time: " + currentTimeSec);
			logger.debug("Next generation: " + nextTimeToGenerate);
			
			return true;
		}
		return false;
    }
}