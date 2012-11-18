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
    private double lastMotionTimeSec;
	private double nextTimeToGenerate;
    private GameLogic game;
    
    private double speedPixPerSecond;
    private final double maxSpeedPixPerSecond;
    
    private static final boolean debugCollisions = GameConstants.DEBUG_COLLISIONS;

    private Line bottomLine = new Line(GameConstants.BOTTOM_PATH_START_X, GameConstants.BOTTOM_PATH_START_Y,
            GameConstants.BOTTOM_PATH_END_X, GameConstants.BOTTOM_PATH_END_Y);
    private Line verticalLine = new Line(GameConstants.VERTICAL_PATH_START_X, GameConstants.VERTICAL_PATH_START_Y,
            GameConstants.VERTICAL_PATH_END_X, GameConstants.VERTICAL_PATH_END_Y);
    private Line topLine = new Line(GameConstants.TOP_PATH_START_X, GameConstants.TOP_PATH_START_Y,
            GameConstants.TOP_PATH_END_X, GameConstants.TOP_PATH_END_Y);


    public ConveyorBelt(GameLogic game) {
        this.game = game;
    	
    	recyclables = new ArrayList<Recyclable>();

        p = new Path();
        p.addLine(bottomLine);
        p.addLine(verticalLine);
        p.addLine(topLine);
        
        speedPixPerSecond = GameConstants.INITIAL_SPEED_IN_PIXELS_PER_SECOND;
        maxSpeedPixPerSecond = GameConstants.FINAL_SPEED_IN_PIXELS_PER_SECOND;
        
        //logger.setLevel(Level.DEBUG);
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
    	if(pctOfFullSpeed >= 1){
    		speedPixPerSecond = maxSpeedPixPerSecond;
    	}
    	
    	speedPixPerSecond = speedPixPerSecond + (maxSpeedPixPerSecond - speedPixPerSecond) * pctOfFullSpeed;
    }

	public void update(double currentTimeSec) {
        moveConveyorBelt(currentTimeSec);
        
        if (!debugCollisions) {
            possiblyGenerateItem(currentTimeSec);
        }
        
	}

	/**
	 * Advances the conveyor belt.  All the items on it will get carried along for the ride!
	 * @param currentTimeMsec
	 */
	private void moveConveyorBelt(double currentTimeSec) {
		double timePassedSec = currentTimeSec - lastMotionTimeSec;
		for(Recyclable r : recyclables){
			updateItemPosition(timePassedSec);
		}
		lastMotionTimeSec = currentTimeSec;
	}
	
	/**
	 * Moves a recyclable to wherever it should be after the given amount of time.
	 * @param timePassedMsec
	 */
	private void updateItemPosition(double timePassedSec){
		throw new Exception("Not implemented");
	}

	/**
	 * Generates new item if necessary
	 */
	private void possiblyGenerateItem(double currentTimeSec) {
		if (needsItemGeneration(currentTimeSec)) {
			Recyclable r = new Recyclable(currentTimeSec, RecyclableType.getRandom(game.getNumItemTypesInUse()));
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
}