package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.GraphicsConstants;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;
import edu.mines.csci598.recycler.frontend.utils.ComputerConstants;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;
import org.apache.log4j.Logger;

import java.awt.geom.Point2D;

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
    private int velocityX;
    private int velocityY;
    Point2D position;
    private Path path;
    private int goalX;
    private int goalY;

    private boolean followingPath;
    private boolean aboveRecyclable;

    public ComputerHand() {
        //logger.setLevel(Level.INFO);
        //resetHandPosition();
        //position.setLocation(GraphicsConstants.GAME_SCREEN_WIDTH*GraphicsConstants.SCALE_FACTOR*3/4,
        //                     GraphicsConstants.GAME_SCREEN_HEIGHT*GraphicsConstants.SCALE_FACTOR*1/4);
        //sprite.setPosition(position);

        velocityX = 0;
        velocityY = 0;
        goalX=0;
        goalY=0;
        followingPath=false;
        aboveRecyclable=false;
        int x = GameConstants.RIGHT_VERTICAL_PATH_END_X + ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER;
        int y = GameConstants.RIGHT_VERTICAL_PATH_START_Y * (int)GraphicsConstants.SCALE_FACTOR;
        sprite = new Sprite("src/main/resources/SpriteImages/hand.png", x,y);
    }
    public Point2D getPosition(){
        return sprite.getPosition();
    }
    public void setPosition(Point2D position){
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
        logger.info("Hand following path");
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
    public boolean isAboveRecyclable(){
        return aboveRecyclable;
    }
    public void setAboveRecyclable(boolean aboveRecyclable){
        this.aboveRecyclable=aboveRecyclable;
    }
    public void resetAboveRecyclable(){
        aboveRecyclable=false;
    }
    /*public void resetHandPosition(){
        position.setLocation(GameConstants.VERTICAL_PATH_END_X + ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER,
                             GameConstants.VERTICAL_PATH_START_Y * GraphicsConstants.SCALE_FACTOR);
        sprite.setPosition(position);
    } */
    
    public boolean isHandOnLeftSide(){
        boolean ret=false;
        //logger.info("sx="+sprite.getX()+",px="+GameConstants.VERTICAL_PATH_START_X);
        if(sprite.getX()<GameConstants.RIGHT_VERTICAL_PATH_START_X)ret=true;
        return ret;
    }

    /*
     * Get hand image
     */
    public Sprite getSprite() {
        return sprite;
    }
    
    /*
     * Get X velocity for computer player
     */
    public int getVelocityX() {
        return velocityX;
    }
    
    /*
     * Get Y velocity for computer player
     */
    public int getVelocityY() {
        return velocityY;
    }
}
