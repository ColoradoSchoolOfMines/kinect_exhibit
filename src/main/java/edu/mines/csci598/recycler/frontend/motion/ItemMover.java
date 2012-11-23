package edu.mines.csci598.recycler.frontend.motion;

import java.util.ArrayList;
import java.util.List;

import edu.mines.csci598.recycler.frontend.Recyclable;
import edu.mines.csci598.recycler.frontend.graphics.Coordinate;

/**
 * Manages a list of items and causes them to move on demand.
 * @author Oliver
 *
 */
public abstract class ItemMover {
	
	protected List<Recyclable> recyclables;
	private double lastMotionTimeSec;
	protected double speedPixPerSecond;
	
	public ItemMover(double initialSpeed){
		recyclables = new ArrayList<Recyclable>();
		lastMotionTimeSec = 0;
		speedPixPerSecond = initialSpeed;
	}
	
	/**
	 * Given the current time, moves the items under the ItemMover's control
	 * @param currentTimeSec
	 */
	public void moveItems(double currentTimeSec){
		// Figure out how much time has passed since we last moved
		double timePassedSec = currentTimeSec - lastMotionTimeSec;
		
		for(Recyclable r : recyclables){
			Coordinate newPosition = r.getPath().getLocation(r.getPosition(), speedPixPerSecond, timePassedSec); 
			if(!(newPosition.equals(r.getPosition()))){
				r.setPosition(newPosition);
				lastMotionTimeSec = currentTimeSec;
			}
		}
	}

	/**
	 * @return the list of items this ItemMover moves
	 */
    public final List<Recyclable> getRecyclables() {
        return recyclables;
    }
    
    /**
     * Registers the given recyclable with this ItemMover.  It will now be moved with each call to moveItems
     * @param r
     */
    public final void takeControlOfRecyclable(Recyclable r){
    	recyclables.add(r);
    }
    
    /**
     * Registers each of the given recyclables with this ItemMover.
     * They will now be moved with each call to moveItems
     * @param r
     */
    public final void takeControlOfRecyclables(List<Recyclable> newItems){
    	for(Recyclable r : newItems){
    		recyclables.add(r);
    	}
    }
    
    /**
     * Transfers ownership of any items managed by this ItemMover that are at the end of their path.
     * The ItemMover will no longer be aware of these items after calling this method.
     * @return The items no longer owned by this ItemMover
     */
    public final List<Recyclable> releaseControlOfRecyclablesAtEndOfPath(){
    	List<Recyclable> atEnd = new ArrayList<Recyclable>();
    	for(Recyclable r : recyclables){
    		if(r.getPosition().equals(r.getPath().finalPosition())){
    			atEnd.add(r);
    		}
    	}
    	for(Recyclable r : atEnd){
    		recyclables.remove(r);
    	}
    	return atEnd;
    }
    
    /**
     * Transfers ownership of any items managed by this ItemMover that are touchable at the given point.
     * The ItemMover will no longer be aware of these items after calling this method.
     * @param - the point that may have items
     * @return - any items that existed at that point, which will no longer be managed by this ItemMover
     */
    public final List<Recyclable> releaseTouchableItemsAtPoint(Coordinate point){
    	List<Recyclable> releasing = new ArrayList<Recyclable>();
    	for(Recyclable r : recyclables){
    		if(!(r.isTouchable())){
    			continue; // can't return this one, go to next
    		}
    		
    		if(r.collidesWithPoint(point)){
    			releasing.add(r);
    		}
    		
    	}
    	for(Recyclable r : releasing){
    		recyclables.remove(r);
    	}
    	return releasing;
    }
}
