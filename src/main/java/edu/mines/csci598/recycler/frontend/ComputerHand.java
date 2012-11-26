package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Coordinate;
import edu.mines.csci598.recycler.frontend.graphics.GraphicsConstants;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;
import edu.mines.csci598.recycler.frontend.utils.ComputerConstants;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Marshall
 * Date: 11/13/12
 * Time: 6:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class ComputerHand {
    private static final Logger logger = Logger.getLogger(ComputerHand.class);
    private Sprite sprite;
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
        //resetHandPosition();
        //position.setLocation(GraphicsConstants.GAME_SCREEN_WIDTH*GraphicsConstants.SCALE_FACTOR*3/4,
        //                     GraphicsConstants.GAME_SCREEN_HEIGHT*GraphicsConstants.SCALE_FACTOR*1/4);
        //sprite.setPosition(position);

        goalX=0;
        goalY=0;
        followingPath=false;
        onCorrectSide=false;
        int x = GameConstants.RIGHT_VERTICAL_PATH_END_X + ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER;
        //int x = GameConstants.RIGHT_VERTICAL_PATH_END_X / 2;
        int y = GameConstants.RIGHT_VERTICAL_PATH_START_Y / 2;
        sprite = new Sprite("src/main/resources/SpriteImages/hand.png", x,y);
        logger.info("Added Computer Hand at ("+x+","+y+")");
    }
    public Coordinate getPosition(){
        return sprite.getPosition();
    }
    public void setPosition(Coordinate position){
        sprite.setPosition(position);
    }
    public int getGoalX(){
        return goalX;
    }
    public int getGoalY(){
        return goalY;
    }
    public void setGoal(int goalX,int goalY){
        this.goalX=goalX;
        this.goalY=goalY;
    }
    public void setPath(Path p){
        logger.debug("Hand following path");
        path = p;
        followingPath=true;
    }
    public Path getPath(){
        return path;
    }
    public void resetFollowingPath(){
        followingPath=false;
    }
    public boolean isFollowingPath(){
        return followingPath;
    }
    public boolean isOnCorrectSide(){
        return onCorrectSide;
    }
    public void setOnCorrectSide(boolean onCorrectSide){
        this.onCorrectSide=onCorrectSide;
    }

    public boolean isHandOnLeftSide(){
        boolean ret=false;
        logger.debug("sx=" + sprite.getX() + ",px=" + GameConstants.RIGHT_VERTICAL_PATH_START_X);
        if(sprite.getX()<GameConstants.RIGHT_VERTICAL_PATH_START_X)ret=true;
        return ret;
    }
    /*
     * Get hand image
     */
    public Sprite getSprite() {
        return sprite;
    }
}