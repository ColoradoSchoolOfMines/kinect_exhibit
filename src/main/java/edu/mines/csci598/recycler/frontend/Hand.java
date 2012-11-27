package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.backend.GameManager;
import edu.mines.csci598.recycler.frontend.graphics.Coordinate;
import edu.mines.csci598.recycler.frontend.graphics.Displayable;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;

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
public class Hand implements Displayable {
    
    private Sprite sprite;
    private int velocityX;
    private int velocityY;
    private int x;
    private int y;
    private int oldX;
    private int oldY;
    private int velocityCount;
    GameManager gameManager;
    int handNum;

    public Hand(GameManager manager, int handNum) {
        x = 0;
        y = 0;
        oldX = 0;
        oldY = 0;
        velocityX = 0;
        velocityY = 0;
        sprite = new Sprite("src/main/resources/SpriteImages/hand.png", x, y);
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

        sprite.setX(x);
        sprite.setY(y);
    }

    public Sprite getSprite() {
        return sprite;
    }

    /*
    * Gets the x position.
    *
    * return {int}
    */
    public int getX() {
        return x;
    }

    /*
      * Gets the y position.
      *
      * return {int}
      */
    public int getY() {
        return y;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityX(int x) {
        velocityX = x;
    }
    
    public Coordinate getPosition(){
    	return new Coordinate(x, y);
    }

}
