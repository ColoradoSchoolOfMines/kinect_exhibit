package edu.mines.csci598.recycler.frontend.ai;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import edu.mines.csci598.recycler.frontend.Hand;
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
public class    ComputerHand extends Hand {

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

    public ComputerHand() {
        logger.setLevel(Level.INFO);
        goalX = 0;
        goalY = 0;
        followingPath = false;
        onCorrectSide = false;
        x = ComputerConstants.INITIAL_HAND_X;
        y = ComputerConstants.INITIAL_HAND_Y;
    }

    private void updateVelocity(){
        //logger.info("Update velocity"+(this.x - oldX));
        velocityX = this.x - oldX;
        velocityY = this.y - oldY;
    }

    public void setPosition(Coordinate position){
        oldX = x;
        oldY = y;
        x = (int)position.getX();
        y = (int)position.getY();
        updateVelocity();
    }

    public void resetHandToInitialPosition(){
        oldX = x;
        //logger.debug("hx="+x+",hy="+y+",ix="+initialX+",iy="+initialY);
        if(y!=ComputerConstants.INITIAL_HAND_Y) {
            logger.debug("Resetting ComputereHand to initial position");
            x = ComputerConstants.INITIAL_HAND_X;
            y = ComputerConstants.INITIAL_HAND_Y;
        }
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
        logger.debug("PlayerHand following path");
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