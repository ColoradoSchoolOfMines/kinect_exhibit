package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Displayable;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

/**
 * The "hand" represents a user's hand. It can be displayed on the screen and its motion will
 * dictate whether or not it has hit an item on the conveyor belt.
 *
 *
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/20/12
 * Time: 9:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Hand implements MouseMotionListener, Displayable {

	private Sprite sprite;
	
    public Hand() {

    }
    
    public Sprite getSprite()
    {
    	return sprite;
    }

    /*
    * Gets the x position.
    *
    * return {int}
    */
    public int getX() {
        return 1;
    }

    /*
      * Gets the y position.
      *
      * return {int}
      */
    public int getY() {
        return 1;
    }

    public void mouseMoved(MouseEvent e) {
        saySomething("Mouse moved", e);
    }

    public void mouseDragged(MouseEvent e) {
        saySomething("Mouse dragged", e);
    }

    void saySomething(String eventDescription, MouseEvent e) {
        System.out.println(eventDescription
                + " (" + e.getX() + "," + e.getY() + ")"
                + " detected on "
                + e.getComponent().getClass().getName()
                + "\n");
    }

}
