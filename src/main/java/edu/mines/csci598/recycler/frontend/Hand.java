package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Coordinate;
import edu.mines.csci598.recycler.frontend.graphics.Displayable;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: Marshall
 * Date: 11/29/12
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class Hand implements Displayable {
    private Sprite sprite;
    protected int velocityX;
    protected int velocityY;
    protected int x;
    protected int y;
    protected int oldX;
    protected int oldY;

    public Hand(){
        x = 0;
        y = 0;
        oldX = 0;
        oldY = 0;
        velocityX = 0;
        velocityY = 0;
        sprite = new Sprite("src/main/resources/SpriteImages/hand.png", x, y);
    }
    public void updateLocation(){
        sprite.setX(x);
        sprite.setY(y);
    }

    public final Sprite getSprite(){
        return sprite;
    }
    /*
    * Gets the x position.
    *
    * return {int}
    */
    public final int getX() {
        return x;
    }

    /*
      * Gets the y position.
      *
      * return {int}
      */
    public final int getY() {
        return y;
    }

    public final int getVelocityX() {
        return velocityX;
    }

    public final int getVelocityY() {
        return velocityY;
    }

    public final void setVelocityX(int x) {
        velocityX = x;
    }

    public final Coordinate getPosition(){
        return new Coordinate(x, y);
    }


}
