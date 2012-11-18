package edu.mines.csci598.recycler.frontend.graphics;

import java.awt.geom.Point2D;

/**
 * A simple class to hold 2 doubles representing a point on the screen.
 * <p/>
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/27/12
 * Time: 2:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class Coordinate extends Point2D.Double{
	private double x, y;
	
	// Our coordinates measure pixels.  To avoid loss of precision
    public Coordinate(double x, double y) {
    	super(x, y);
    }
    
}
