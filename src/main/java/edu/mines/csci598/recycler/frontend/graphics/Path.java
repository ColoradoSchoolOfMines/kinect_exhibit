package edu.mines.csci598.recycler.frontend.graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * A Path is a representation of the path an item is to follow on the screen. It consists
 * of a list of Lines.  Lines on the path should *not* intersect or figuring out which
 * line a point is on will break.
 * <p/>
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/27/12
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class Path {
    List<Line> path = new ArrayList<Line>();

    public Path() {
    	
    }
    
    public Path(List<Line> lines) {
    	for(Line l : lines){
    		path.add(l);
    	}
    }

    public Path(Path existingPath) {
    	for(Line l : existingPath.path){
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
    public Coordinate getLocation(Coordinate currentLocation, double speedPixPerSec, double timeInMotionSec){
	    // Calculate and store how far item will travel at given speed in given time
    	double pixelsToTravel = speedPixPerSec * timeInMotionSec;
		Coordinate endLocation = currentLocation;
		final boolean goingForwards = speedPixPerSec > 0;
		
		int currentLineIndex;
		if(goingForwards){
			currentLineIndex = 0;
		}
		else{
			currentLineIndex = path.size() - 1;
		}
		
		// We now go through each line in our list and follow it.  Probably, you won't need more than one.
		// But if you go around a corner, it'll first go to the end of the first line, and then follow part of the
		// second.  Why use |pixelsToTravel|>0.5?  If it's less than that, we can guarantee motion won't happen again
		// as currentLine.getCoordinateAfterMovingDistance will always round it right back to where it is now.
		// It *is* possible to have the line at such an angle that no motion will occur after rounding and yet
		// pixelsToTravel is greater than 0.5.  For example, think of y=x, and use pixelsToTravel=0.6.  We know from
		// trig that the change in x and y will both be 0.42, and no motion will occur.  This is OK - first it's a 
		// rare case as we don't go around many corners.  Second, getCoordinateAfterMovingDistance will just return
		// the same point if necessary, so the worst case is that we'll iterate briefly over each line.
    	while(currentLineIndex >= 0 && currentLineIndex < path.size() && (pixelsToTravel >= 0.5 || pixelsToTravel <= -0.5)){    		
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

    public Coordinate finalPosition(){
        return (Coordinate) path.get(path.size()-1).getP2();
    }
    
    public Coordinate initialPosition(){
         Line l = path.get(0);
         if(l!=null){
             return (Coordinate) l.getP1();
         } else{
             return null;
         }
    }
}
