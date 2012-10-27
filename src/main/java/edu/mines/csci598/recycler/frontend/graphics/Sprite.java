package edu.mines.csci598.recycler.frontend.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * This class keeps track of the sprites location on disk, transforms, position velocity etc.
 * TODO: Should we pull in Kyle's Sprite class and replace this one?
 *
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/20/12
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */



public class Sprite implements GraphicsConstants{

    private int dx;
    private int dy;
    private int x;
    private int y;
    private Image image;
    private String fileName;
    private int width;
    private int height;
    private double scaleFactor;
    private Path path;
    private double startTime;

    public Sprite(String fileName, int x, int y, double scaleFactor) {
        this.x = x;
        this.y = y;
        this.fileName = fileName;
        this.scaleFactor = scaleFactor;
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
        Image img = null;
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            throw new RuntimeException("File error: "+fileName);
        }
        image =(Image)img;
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

    /*
      * Gets the x position.
      *
      * return {int}
      */
    public int getX() {
        return (int) Math.round(x);
    }

    /*
      * Gets the y position.
      *
      * return {int}
      */
    public int getY() {
        return (int) Math.round(y);
    }

    /*
      * Gets the icon image.
      *
      * return {Image}
      */
    public Image getImage() {
        return image.getScaledInstance((int)(screenWidth * scaleFactor), (int)(screenHeight * scaleFactor), Image.SCALE_SMOOTH);
    }

    public void setPath(Path p){
        path = p;
    };
    public void setStartTime(double time){
        startTime = time;
    }
    public void updateLocation(double time){
        if(path != null){
            Coordinate c = path.getLocation(startTime,time);
            x=(int)c.x;
            y=(int)c.y;
        }
    }

}