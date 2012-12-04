package edu.mines.csci598.recycler.frontend.motion;

import org.apache.log4j.Logger;

import edu.mines.csci598.recycler.frontend.utils.GameConstants;

/**
 * Magically moves items around!  In practice used to control the items which are falling off the conveyor belt into the bins.
 * @author Oliver
 * @namedBy Oliver's sister
 *
 */
public class TheForce extends ItemMover {
    private static final Logger logger = Logger.getLogger(TheForce.class);
	
	public TheForce(){
		super(GameConstants.HAND_COLLISION_PATH_SPEED_IN_PIXELS_PER_SECOND);
	}

}
