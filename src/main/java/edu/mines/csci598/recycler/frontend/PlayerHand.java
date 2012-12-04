package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.backend.GameManager;
import edu.mines.csci598.recycler.frontend.graphics.GraphicsConstants;

/**
 * The "hand" represents a user's hand. It can be displayed on the screen and its motion will
 * dictate whether or not it has hit an item on the conveyor belt.
 * <p/>
 * <p/>
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/20/12
 * Time: 9:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerHand extends Hand {

    private int velocityCount;
    GameManager gameManager;
    int handNum;

    public PlayerHand(GameManager manager, int handNum) {
        gameManager = manager;
        this.handNum = handNum;
    }

    public void updateLocation() {
        oldX = x;
        oldY = y;

        x = gameManager.vcxtopx(gameManager.getSharedInputStatus().pointers[handNum][0]);
        y = gameManager.vcytopx(gameManager.getSharedInputStatus().pointers[handNum][1]);

        //The input is different for the display so we must account for the scale factor
        //For example if we have a monitor that is half as tall and half as wide as a 1080p monitor
        //we would use a scale factor of 0.5. The mouse would send inputs only in that range so we
        //scale them up to where they would normally be at.
        x = (int) Math.round(x* 1/GraphicsConstants.SCALE_FACTOR);
        y = (int) Math.round(y* 1/GraphicsConstants.SCALE_FACTOR);

        velocityX = x - oldX;
        velocityY = y - oldY;
        super.updateLocation();
    }

}
