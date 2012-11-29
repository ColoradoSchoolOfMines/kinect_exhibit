package edu.mines.csci598.recycler.frontend;

public enum MotionState {
    CHUTE(TouchState.UNTOUCHABLE), 
    CONVEYOR(TouchState.TOUCHABLE), 
    FALL_LEFT(TouchState.UNTOUCHABLE), 
    FALL_RIGHT(TouchState.UNTOUCHABLE), 
    FALL_TRASH(TouchState.UNTOUCHABLE),
    IS_TRASH(TouchState.UNTOUCHABLE),
    ABOVE_BIN(TouchState.UNTOUCHABLE),
    STRIKE(TouchState.UNTOUCHABLE);
    
    public enum TouchState {TOUCHABLE, UNTOUCHABLE};
    private TouchState touchable;
    
    MotionState(TouchState touchable){
    	this.touchable = touchable;
    }
    
    public boolean isTouchable(){
    	return touchable==TouchState.TOUCHABLE;
    }
}
