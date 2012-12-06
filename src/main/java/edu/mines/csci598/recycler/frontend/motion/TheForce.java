package edu.mines.csci598.recycler.frontend.motion;

import org.apache.log4j.Logger;

/**
 * Magically moves items around!  In practice used to control the items which are falling off the conveyor belt into the bins.
 * @author Oliver
 * @namedBy Oliver's sister
 *
 */
public class TheForce extends ItemMover {
    private static final Logger logger = Logger.getLogger(TheForce.class);
	
    /**
     * Creates a new TheForce ItemMover
     */
	public TheForce(){
		super();
	}

}
