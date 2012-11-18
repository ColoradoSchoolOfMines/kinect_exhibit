package edu.mines.csci598.recycler.frontend.graphics;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * A Path is a representation of the path an item is to follow on the screen. It consists
 * of a list of Lines.
 * <p/>
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/27/12
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class Path {
    ArrayList<Line> path = new ArrayList<Line>();

    public Path() {
    	
    }
    
    public Path(List<Line> lines) {
    	for(Line l : lines){
    		path.add(l);
    	}
    }

    /**
     * This function adds the Line to the end of the path.
     *
     * @param l
     * @return
     */
    public boolean addLine(Line l) {
        return path.add(l);
    }
    
    /**
     * This method calculates the new position of an object along this path given the item’s
     * current location, speed, and time in motion.  It handles turning corners.  Though the
     * game is continuously changing the conveyor speed, we neglect calculus, use the item’s
     * speed at the instant the method is called, and assume it is constant until the item
     * arrives at the end destination at the end of the method.
     * @param currentLocation - The coordinate the object is currently located at
     * @param speedPixPerSec - The speed the object is travelling, measured in pixels per second
     * @param timeInMotionSec - The time elapsed at this speed
     * @return - The new coordinate of the object
     */
    public Point2D getLocation(Point2D currentLocation, double speedPixPerSec, double timeInMotionSec){
	    // Calculate and store how far item will travel at given speed in given time
    	double pixelsToTravel = speedPixPerSec * timeInMotionSec;
		Point2D endLocation = currentLocation;
		final boolean goingForwards = speedPixPerSec > 0;
		
		int currentLineIndex;
		if(goingForwards){
			currentLineIndex = 0;
		}
		else{
			currentLineIndex = path.size() - 1;
		}
		
    	while(currentLineIndex >= 0 && currentLineIndex < path.size() && pixelsToTravel != 0){    		
    		Line currentLine = path.get(currentLineIndex);
    		
    		if(currentLine.intersectsPoint(currentLocation)){
   				endLocation = currentLine.getCoordinateAfterMovingDistance(currentLocation, pixelsToTravel);
    			
   				if(goingForwards){
   					pixelsToTravel -= currentLocation.distance(endLocation);
   				}
   				else{
   					pixelsToTravel += currentLocation.distance(endLocation);
   				}
   				currentLocation = endLocation;
    		}
    		
    		if(goingForwards){
    			currentLineIndex++;
    		}
    		else{
    			currentLineIndex--;
    		}
    	}
    	
		return endLocation;
    }
}
