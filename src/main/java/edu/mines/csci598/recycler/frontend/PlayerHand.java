package edu.mines.csci598.recycler.frontend;

import org.apache.log4j.Logger;

import edu.mines.csci598.recycler.backend.GameManager;

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

    private static final Logger logger = Logger.getLogger(PlayerHand.class);
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

        velocityX = x - oldX;
        velocityY = y - oldY;
        super.updateLocation();
    }

}
