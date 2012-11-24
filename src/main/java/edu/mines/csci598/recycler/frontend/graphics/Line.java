package edu.mines.csci598.recycler.frontend.graphics;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * A line is a part of a path. It has the starting location, the ending location, and the total time it takes
 * the line to be traversed.
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/27/12
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class Line extends Line2D{

	private final Coordinate startPoint, endPoint;
    private double velocity;
	private double timeToComplete;
    /**
     * Begining coordinates, ending coordinates and how long the line takes to be traversed. This way based on a time
     * later we can calculate where along the path it is.
     *
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param timeToComplete
     */



    public Line(double startX, double startY, double endX, double endY, double timeToComplete ) {
        startPoint = new Coordinate(startX, startY);
        endPoint = new Coordinate(endX, endY);
        double length = startPoint.distance(endPoint);
        this.timeToComplete =timeToComplete;
    }
    
    /**
     * Checks whether the given point, with integer coordinates, is on this line.
     * @param point - the point we're wondering about
     * @return true if the given point is on the line, false otherwise
     */
    public boolean intersectsPoint(Coordinate point){
    	// This is a weird function that needs some explanation.  We're given a point with
    	// pixel coordinates, in ints.  Java meanwhile is treating the line as being
    	// continuous.  So we can think of a point on the line as being "rounded" to the
    	// nearest integer grid coordinate.  Visualize a 1x1 square.  Anything in the upper
    	// left quadrant will get "rounded" to the upper left point, anything in the upper
    	// right to the upper right point, etc.  The *worst case* is that the center point
    	// gets rounded ... somewhere, any of the four I suppose.  So if we look at a given
    	// grid coordinate point, the furthest point that could have been rounded to it is
    	// the center of the 1x1 square, which is (via basic trig, triangle sides 1/2, 1/2)
    	// 1/(sqrt2) away from the grid point.
    	return ptLineDist(point) <= Math.sqrt(1.0 / Math.sqrt(2.0));
    }


	@Override
	public Rectangle2D getBounds2D() {
		return new Rectangle2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX()-startPoint.getX(), endPoint.getY()-startPoint.getY());
	}

	@Override
	public double getX1() {
		return startPoint.getX();
	}

	@Override
	public double getY1() {
		return startPoint.getY();
	}

	@Override
	public Coordinate getP1() {
		return startPoint;
	}

	@Override
	public double getX2() {
		return endPoint.getX();
	}

	@Override
	public double getY2() {
		return endPoint.getY();
	}

	@Override
	public Coordinate getP2() {
		return endPoint;
	}

	@Override
	public void setLine(double x1, double y1, double x2, double y2) {
		throw new IllegalArgumentException("Use the coordinate line constructor instead.");
	}

    public double getTimeToComplete(){
        return timeToComplete;
    }


    /**
     * Pass in the elapsed time since this line has started to get the coordinates at that time
     * @param time
     * @return Coordinate of where it is at this relative time
     */
    public Coordinate getCoordinateAtTime(double time){
        double x, y;
        if (time <= 0) {
            return startPoint;
        } else if (time >= timeToComplete) {
            return endPoint;
        } else { //we are somewhere in the middle of the line
            double fraction = time / timeToComplete;
            x = (endPoint.getX()-startPoint.getX()) * fraction + startPoint.getX();
            y = (endPoint.getY()-startPoint.getY()) * fraction + startPoint.getY();
            return new Coordinate(x,y);
        }

    }
}
