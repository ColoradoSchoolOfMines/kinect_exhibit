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
	
	protected List<Movable> movables;
	protected double speedPixPerSecond;
	
	public ItemMover(double initialSpeed){
        movables = new ArrayList<Movable>();
		speedPixPerSecond = initialSpeed;
	}
	
	/**
	 * Given the current time, moves the items under the ItemMover's control
	 * @param currentTimeSec The current time in seconds
	 */
	// TODO OJC - rework this to put lastMotionTime into the recyclables themselves
	public void moveItems(double currentTimeSec){
		// Figure out how much time has passed since we last moved
		for(Movable m : movables){
			Coordinate newPosition = m.getPath().getLocation(currentTimeSec);
            m.setPosition(newPosition);
		}
	}

	/**
	 * @return the list of items this ItemMover moves
	 */
    public final List<Movable> getMovables() {
        return movables;
    }
    
    /**
     * Registers the given recyclable with this ItemMover.  It will now be moved with each call to moveItems
     * @param m The recyclable you wish to take controll of
     */
    public final void takeControlOfMovable(Movable m){
        movables.add(m);
    }
    
    /**
     * Registers each of the given recyclables with this ItemMover.
     * They will now be moved with each call to moveItems
     * @param newItems - List of items that you should take control of
     */
    public final void takeControlOfMovables(List<Movable> newItems){
    	for(Movable m : newItems){
            movables.add(m);
    	}
    }
    
    /**
     * Transfers ownership of any items managed by this ItemMover that are at the end of their path.
     * The ItemMover will no longer be aware of these items after calling this method.
     * @return The items no longer owned by this ItemMover
     */
    public final List<Movable> releaseControlOfMovablesAtEndOfPath(double currentTimeSec){
    	List<Movable> atEnd = new ArrayList<Movable>();
    	for(Movable m : movables){
    		if((m.getPath().PathFinished(currentTimeSec)) && (m.isRemovable())){
    			atEnd.add(m);
    		}
    	}
    	for(Movable m : atEnd){
            movables.remove(m);
    	}
    	return atEnd;
    }
    
    /**
     * Transfers ownership of any items managed by this ItemMover that are touchable at the given point.
     * The ItemMover will no longer be aware of these items after calling this method.
     * @param - the point that may have items
     * @return - any items that existed at that point, which will no longer be managed by this ItemMover
     */
    public final List<Movable> releaseTouchableItemsAtPoint(Coordinate point){
    	List<Movable> releasing = new ArrayList<Movable>();
    	for(Movable m : movables){
    		if(!(m.isTouchable())){
    			continue; // can't return this one, go to next
    		}

            logger.debug("point="+point+",r=" + m.getPosition());
    		if(m.collidesWithPoint(point)){
                logger.debug("Release");
    			releasing.add(m);
    		}
    		
    	}
    	for(Movable m : releasing){
            movables.remove(m);
    	}
    	return releasing;
    }


    /**
     * Tells this itemMover to stop paying attention to said recyclables
     * @param movablesToRemove
     * @return
     */
    public final boolean releaseMovables(List<Movable> movablesToRemove){
        return movables.removeAll(movablesToRemove);
    }
}
