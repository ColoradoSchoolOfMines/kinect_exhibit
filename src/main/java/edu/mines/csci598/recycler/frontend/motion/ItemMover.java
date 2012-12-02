package edu.mines.csci598.recycler.frontend.motion;

import edu.mines.csci598.recycler.frontend.Recyclable;
import edu.mines.csci598.recycler.frontend.graphics.Coordinate;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of items and causes them to move on demand.
 * @author Oliver
 *
 */
public abstract class ItemMover {
    private static final Logger logger = Logger.getLogger(ItemMover.class);
	
	protected List<Recyclable> recyclables;
	protected double speedPixPerSecond;
	
	public ItemMover(double initialSpeed){
		recyclables = new ArrayList<Recyclable>();
		speedPixPerSecond = initialSpeed;
	}
	
	/**
	 * Given the current time, moves the items under the ItemMover's control
	 * @param currentTimeSec The current time in seconds
	 */
	// TODO OJC - rework this to put lastMotionTime into the recyclables themselves
	public void moveItems(double currentTimeSec){
		// Figure out how much time has passed since we last moved
		for(Recyclable r : recyclables){
			Coordinate newPosition = r.getPath().getLocation(currentTimeSec);
            r.setPosition(newPosition);
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
     * @param r The recyclable you wish to take controll of
     */
    public final void takeControlOfRecyclable(Recyclable r){
    	recyclables.add(r);
    }
    
    /**
     * Registers each of the given recyclables with this ItemMover.
     * They will now be moved with each call to moveItems
     * @param newItems - List of items that you should take control of
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
    public final List<Recyclable> releaseControlOfRecyclablesAtEndOfPath(double currentTimeSec){
    	List<Recyclable> atEnd = new ArrayList<Recyclable>();
    	for(Recyclable r : recyclables){
    		if((r.getPath().PathFinished(currentTimeSec)) && (r.removable())){
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

            logger.debug("point="+point+",r="+r.getPosition());
    		if(r.collidesWithPoint(point)){
                logger.debug("Release");
    			releasing.add(r);
    		}
    		
    	}
    	for(Recyclable r : releasing){
    		recyclables.remove(r);
    	}
    	return releasing;
    }


    /**
     * Tells this itemMover to stop paying attention to said recyclables
     * @param recyclablesToRemove
     * @return
     */
    public final boolean releaseRecyclables(List<Recyclable> recyclablesToRemove){
        return recyclables.removeAll(recyclablesToRemove);
    }
}
