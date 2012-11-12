package edu.mines.csci598.recycler.frontend.graphics;

import edu.mines.csci598.recycler.frontend.utils.GameConstants;
import edu.mines.csci598.recycler.frontend.utils.Log;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

    public enum TouchState {TOUCHABLE, UNTOUCHABLE};
    private int dx;
    private int dy;
    private int x;
    private int y;
    private Image image;
    private String fileName;
    private Path path;
    private double startTime;
    private TouchState state;

    public Sprite(String fileName, int x, int y) {
        this.x = x;
        this.y = y;
        this.fileName = fileName;
        setImage(fileName);
        setHorizontalVelocity(0);
        setVerticalVelocity(0);
        state = TouchState.UNTOUCHABLE;
    }

    /*
      * Sets the file path for this sprite's icon image.
      *
      * param {String} fileName
      * 		The name of the file to be loaded.
      */
    public void setImage(String fileName) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            throw new RuntimeException("File error: " + fileName);
        }
        //Calculate the rounded scaled height
        int newHeight = (int) Math.round(img.getHeight() * GraphicsConstants.SCALE_FACTOR);
        int newWidth = (int) Math.round(img.getWidth() * GraphicsConstants.SCALE_FACTOR);
        //scale the image
        image = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);

    }

    public void setState(TouchState newState) {
        state = newState;
    }

    public TouchState getState() {
        return state;
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
        return image;
    }

    public void setPath(Path p) {
        path = p;
    }

    ;

    /**
     * Set the time the sprite is supposed to start moving along its path.
     * This is usually whatever the current time is.
     *
     * @param time
     */
    public void setStartTime(double time) {
        startTime = time;
    }

    /**
     * Based on the time the sprite will update it's x and y location.
     *
     * @param time
     */
    public synchronized void updateLocation(double time) {
        if (path != null) {
            Coordinate c = path.getLocation(startTime, time);
            x = (int) c.x;
            y = (int) c.y;
        }
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

}