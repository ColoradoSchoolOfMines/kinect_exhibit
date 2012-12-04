package edu.mines.csci598.recycler.frontend.items;

import org.apache.log4j.Logger;

public enum MotionState {

    CHUTE(TouchState.UNTOUCHABLE), 
    CONVEYOR(TouchState.TOUCHABLE), 
    FALL_LEFT(TouchState.UNTOUCHABLE), 
    FALL_RIGHT(TouchState.UNTOUCHABLE),
    IS_TRASH(TouchState.UNTOUCHABLE),
    ABOVE_BIN(TouchState.UNTOUCHABLE),
    NONE(TouchState.UNTOUCHABLE);
    
    private static final Logger logger = Logger.getLogger(MotionState.class);
    public enum TouchState {TOUCHABLE, UNTOUCHABLE};
    private TouchState touchable;
    
    MotionState(TouchState touchable) {
    	this.touchable = touchable;
    }
    
    public boolean isTouchable() {
    	return touchable == TouchState.TOUCHABLE;
    }

}
