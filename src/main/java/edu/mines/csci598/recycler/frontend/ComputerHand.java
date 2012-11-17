package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.GraphicsConstants;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;
import edu.mines.csci598.recycler.frontend.utils.ComputerConstants;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.awt.*;

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
    public int x;
    public int y;
    private int oldX;
    private int oldY;

    public ComputerHand() {
        //logger.setLevel(Level.INFO);
        resetHandPosition();
        oldX = 0;
        oldY = 0;
        velocityX = 0;
        velocityY = 0;
        sprite = new Sprite("src/main/resources/SpriteImages/hand.png", x, y);
    }
    
    public void resetHandPosition(){
        x = GameConstants.VERTICAL_PATH_END_X + ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER;
        y = (int)(GameConstants.VERTICAL_PATH_START_Y * GraphicsConstants.SCALE_FACTOR);
    }
    
    public boolean isHandOnLeftSide(){
        boolean ret=false;
        logger.info("sx="+sprite.getX()+",px="+GameConstants.VERTICAL_PATH_START_X);
        if(sprite.getX()<GameConstants.VERTICAL_PATH_START_X)ret=true;
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
