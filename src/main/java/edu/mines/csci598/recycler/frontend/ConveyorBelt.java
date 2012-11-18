package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Line;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ConveyorBelt {
	private static final Logger logger = Logger.getLogger(ConveyorBelt.class);
	
    private Random random;
    private List<Recyclable> recyclables;
    private double lastMotionTimeSec;
	private double nextTimeToGenerate;
    private GameLogic game;
    
    private double speedPixPerSecond;
    private final double maxSpeedPixPerSecond;
    
    private static final boolean debugCollisions = GameConstants.DEBUG_COLLISIONS;

    private static final Line bottomLine = new Line(GameConstants.BOTTOM_PATH_START_X, GameConstants.BOTTOM_PATH_START_Y,
            GameConstants.BOTTOM_PATH_END_X, GameConstants.BOTTOM_PATH_END_Y);
    private static final Line verticalLine = new Line(GameConstants.VERTICAL_PATH_START_X, GameConstants.VERTICAL_PATH_START_Y,
            GameConstants.VERTICAL_PATH_END_X, GameConstants.VERTICAL_PATH_END_Y);
    private static final Line topLine = new Line(GameConstants.TOP_PATH_START_X, GameConstants.TOP_PATH_START_Y,
            GameConstants.TOP_PATH_END_X, GameConstants.TOP_PATH_END_Y);
    private static final Path CONVEYOR_BELT_PATH = new Path(Arrays.asList(bottomLine, verticalLine, topLine));


    public ConveyorBelt(GameLogic game) {
        this.game = game;
    	
    	recyclables = new ArrayList<Recyclable>();
        
        speedPixPerSecond = GameConstants.INITIAL_SPEED_IN_PIXELS_PER_SECOND;
        maxSpeedPixPerSecond = GameConstants.FINAL_SPEED_IN_PIXELS_PER_SECOND;
        
        //logger.setLevel(Level.DEBUG);
        random = new Random(System.currentTimeMillis());
    }

    public List<Recyclable> getRecyclables() {
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
        while(!(ret.isTouchable()) && index < recyclables.size()){
            index++;
            if(index<recyclables.size()-1)
                ret = recyclables.get(index);
        }

        return ret;
    }

    public void addRecyclable(Recyclable r) {
        recyclables.add(r);
		game.addRecyclable(r);
    }

    public void removeRecyclable(Recyclable r) {
        recyclables.remove(r);
    }

    public void setSpeed(double pctOfFullSpeed) {
    	if(pctOfFullSpeed >= 1){
    		speedPixPerSecond = maxSpeedPixPerSecond;
    	}
    	
    	speedPixPerSecond = speedPixPerSecond + (maxSpeedPixPerSecond - speedPixPerSecond) * pctOfFullSpeed;
    }

	/**
	 * Generates new item if necessary
	 */
	private void possiblyGenerateItem(double currentTimeSec) {
		if (needsItemGeneration(currentTimeSec)) {
			// Recyclables initially take the path of the conveyor belt
			Recyclable r = new Recyclable(RecyclableType.getRandom(game.getNumItemTypesInUse()), CONVEYOR_BELT_PATH);
			addRecyclable(r);
			logger.debug("Item generated: " + r);
		}

    }
    /**
     * Determines if item should be generated.  If true, sets the time for the next object to be generated
     *
     * @return true if item should be generated, false otherwise
     */
    private boolean needsItemGeneration(double currentTimeSec) {
		if (currentTimeSec > nextTimeToGenerate) {
			double timeToAdd = random.nextGaussian()
					+ GameConstants.MIN_TIME_BETWEEN_GENERATIONS;
			nextTimeToGenerate = currentTimeSec + Math.max(timeToAdd, GameConstants.MIN_TIME_BETWEEN_GENERATIONS);
			
			logger.debug("Current time: " + currentTimeSec);
			logger.debug("Time for next item generation: " + nextTimeToGenerate);
			
			return true;
		}
		return false;
    }

	public void update(double currentTimeSec) {
		// Move the conveyor
        moveConveyorBelt(currentTimeSec);
        
        // Generate more items, if we feel like it
        if (!debugCollisions) {
            possiblyGenerateItem(currentTimeSec);
        }
	}    

	/**
	 * Advances the conveyor belt.  All the items on it will get carried along for the ride!
	 * @param currentTimeMsec
	 */
	private void moveConveyorBelt(double currentTimeSec) {
		ArrayList<Recyclable> recyclablesToRemove = new ArrayList<Recyclable>();
        
		// Figure out how much time has passed since we last moved
		double timePassedSec = currentTimeSec - lastMotionTimeSec;
        
        // Update all the current items
		for(Recyclable recyclable : recyclables){
			Point2D newPosition = recyclable.getPath().getLocation(recyclable.getPosition(), speedPixPerSecond, timePassedSec); 
			
			if(newPosition.equals(GameConstants.END_POSITION)){
                recyclablesToRemove.add(recyclable);
                game.handleScore(recyclable, RecycleBin.TRASH_BIN);
			}
			else{
				recyclable.setPosition(newPosition);		
			}
		}
		
		// Some need to be removed
        // Can't modify in previous loop because it is iterating need this to avoid
        // a concurrent modification exception.
        for (Recyclable recyclable : recyclablesToRemove) {
        	// Removal involves the item in the linked list
            removeRecyclable(recyclable);
            
            // And the image on the screen
            game.getGameScreen().removeSprite(recyclable.getSprite());
        }
		lastMotionTimeSec = currentTimeSec;
	}
}