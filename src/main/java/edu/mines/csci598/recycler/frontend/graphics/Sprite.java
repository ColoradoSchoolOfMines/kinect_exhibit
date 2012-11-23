package edu.mines.csci598.recycler.frontend.graphics;

import edu.mines.csci598.recycler.frontend.utils.GameConstants;

import java.awt.Image;

/**
 * This class keeps track of the sprites location on disk, transforms, position velocity etc.
 * <p/>
 * <p/>
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/20/12
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Sprite {
    private int dx;
    private int dy;
    private int x;
    private int y;
    private String fileName;

    public Sprite(String fileName, int x, int y) {
        this.x = x;
        this.y = y;
        this.fileName = fileName;
        setImage(fileName);
        setHorizontalVelocity(0);
        setVerticalVelocity(0);
    }

    /*
      * Sets the file path for this sprite's icon image.
      *
      * param {String} fileName
      * 		The name of the file to be loaded.
      */
    public void setImage(String fileName) {
        this.fileName = fileName;
    }

    /*
      * Moves the sprite based on current velocities.
      *
      * param {float} x
      * 		The x position on the board.
      * param {float} y
      * 		The y position on the board.
      */
    public void move() {
        x += dx;
        y += dy;
    }

    /*
      * Moves the sprite to a specific coordinate.
      *
      * param {float} x
      * 		The x position on the board.
      * param {float} y
      * 		The y position on the board.
      */
    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /*
      * Sets the horizontal velocity.
      *
      * param {float} dx
      * 		The positive or negative horizontal velocity.
      */
    public void setHorizontalVelocity(int dx) {
        this.dx = dx;
    }

    /*
      * Sets the vertical velocity.
      *
      * param {float} dy
      * 		The positive or negative vertical velocity.
      */
    public void setVerticalVelocity(int dy) {
        this.dy = dy;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    /*
      * Gets the x position.
      *
      * return {int}
      */
    public int getScaledX() {
        return (int) Math.round(x * GraphicsConstants.SCALE_FACTOR);
    }

    public int getX() {
        return x;
    }

    /*
      * Gets the y position.
      *
      * return {int}
      */
    public int getScaledY() {
        return (int) Math.round(y * GraphicsConstants.SCALE_FACTOR);
    }

    public int getY() {
        return y;
    }

    /*
    * Gets the icon image.
    *
    * return {Image}
    */
    public Image getImage() {
        return ResourceManager.getInstance().getImage(fileName);
    }

    /**
     * Checks to see if a given point is within the bounds of the sprite
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isPointInside(int x, int y) {
        if (x >= getScaledX() - (GameConstants.SPRITE_X_OFFSET) &&
                x <= getScaledX() + (GameConstants.SPRITE_X_OFFSET)) {
            if (y >= getScaledY() - (GameConstants.SPRITE_Y_OFFSET) &&
                    y <= getScaledY() + (GameConstants.SPRITE_Y_OFFSET)) {
                return true;
            }
        }
        return false;
    }
    
    public Coordinate getPosition(){
    	return new Coordinate(x, y);
    }

	public synchronized void setPosition(Coordinate location) {
		setX((int)Math.round(location.getX()));
		setY((int)Math.round(location.getY()));
	}

}