package edu.mines.csci598.recycler.frontend.hands;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import edu.mines.csci598.recycler.frontend.ai.ComputerConstants;
import edu.mines.csci598.recycler.frontend.graphics.Coordinate;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.motion.ConveyorBelt;

/**
 * Created with IntelliJ IDEA.
 * User: Marshall
 * Date: 11/13/12
 * Time: 6:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class ComputerHand extends Hand {

    private static final Logger logger = Logger.getLogger(ComputerHand.class);
    private Path path;
    private int goalX;
    private int goalY;
    /*
     * followingPath
     * Boolean used to determine if the hand should be following a recyclable or a path
     */
    private boolean followingPath;
    /*
     * onCorrectSide
     * Keeps track of whether or not the hand is on the opposite side of the bin for striking
     */
    private boolean onCorrectSide;
    /**
     * DEFAULT_POSITION
     * The position the computer player will take by default
     */
    private static final Coordinate DEFAULT_POSITION = new Coordinate(ComputerConstants.INITIAL_HAND_X, ComputerConstants.INITIAL_HAND_Y);
    /**
     * intendedPosition
     * The position the AI currently wants to be at
     */
    private Coordinate intendedPosition;

    public ComputerHand() {
        logger.setLevel(Level.INFO);
        goalX = 0;
        goalY = 0;
        followingPath = false;
        onCorrectSide = false;
        intendedPosition = DEFAULT_POSITION; // starting location        
    }

    public void setPosition(Coordinate position){
    	this.intendedPosition = position;
    }

	@Override
	protected Coordinate getNextPosition() {
		return intendedPosition;
	}

    public void resetHandToInitialPosition(){
        setPosition(DEFAULT_POSITION);
    }

    public int getGoalX() {
        return goalX;
    }

    public int getGoalY() {
        return goalY;
    }

    public void setGoal(int goalX,int goalY) {
        this.goalX = goalX;
        this.goalY = goalY;
    }

    public void setPath(Path p) {
        logger.debug("ComputerHand following path");
        path = p;
        followingPath = true;
    }

    public Path getPath() {
        return path;
    }

    public void resetFollowingPath() {
        followingPath = false;
    }

    public boolean isFollowingPath() {
        return followingPath;
    }

    public boolean isOnCorrectSide() {
        return onCorrectSide;
    }

    public void setOnCorrectSide(boolean onCorrectSide) {
        this.onCorrectSide = onCorrectSide;
    }

    public boolean isHandOnLeftSide() {
        boolean ret = false;
        logger.debug("sx=" + getX() + ",px=" + ConveyorBelt.RIGHT_VERTICAL_PATH_START_X);
        if(getX() < ConveyorBelt.RIGHT_VERTICAL_PATH_START_X)ret = true;
        return ret;
    }
}