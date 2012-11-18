package edu.mines.csci598.recycler.frontend.graphics;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * A line is a part of a path. It has the starting location, the ending location, and the total time it takes
 * the line to be traversed.
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/27/12
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class Line extends Line2D.Double{

	private final Coordinate startPoint, endPoint;
	
    /**
     * Begining coordinates, ending coordinates and how long the line takes to be traversed. This way based on a time
     * later we can calculate where along the path it is.
     *
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param totalTime
     */
    public Line(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
                
        startPoint = new Coordinate(startX, startY);
        endPoint = new Coordinate(endX, endY);
        //TODO: Add error detection

    }

    /**
     * Based on the time this function will return the location along the line it is in.
     *
     * @param time
     * @return
     */
    /*public Coordinate getLocation(double time) {
        double x, y;
        if (time <= 0) {
            x = startX;
            y = startY;
        } else if (time >= totalTime) {
            x = endX;
            y = endY;
        } else { //we are somewhere in the middle of the line
            double fraction = time / totalTime;
            x = (endX - startX) * fraction + startX;
            y = (endY - startY) * fraction + startY;
        }
        return new Coordinate(x, y);
    }*/
    
    public boolean intersectsPoint(Point2D point){
    	return ptLineDist(point) == 0.0;
    }

	public Point2D getCoordinateAfterMovingDistance(
			Point2D currentLocation, double pixelsToTravel) {
		Point2D lineLimit;
		if(pixelsToTravel > 0){
			lineLimit = endPoint;
		}
		else if(pixelsToTravel < 0){
			lineLimit = startPoint;			
		}
		else{
			return currentLocation;
		}
		
		double distanceToEnd = lineLimit.distance(currentLocation);
		double fractionOfLineTravelling = Math.abs(pixelsToTravel / distanceToEnd);
		if(fractionOfLineTravelling >= 1){
			return lineLimit;
		}
		
		double x = (lineLimit.getX() - currentLocation.getX()) * fractionOfLineTravelling + currentLocation.getX();
		double y = (lineLimit.getY() - currentLocation.getY()) * fractionOfLineTravelling + currentLocation.getY();
		return new Coordinate(x, y);
	}
    
}
