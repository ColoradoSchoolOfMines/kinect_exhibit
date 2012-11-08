package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Displayable;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;

/**
 * Recyclables are things like bottles, plastic etc. that you would be swiping at.
 * They need to keep track of what kind of recyclable they are, and where state they are in.
 *
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/20/12
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Recyclable implements Displayable {
    public enum MotionState { CHUTE, CONVEYOR, FALL_LEFT, FALL_RIGHT, FALL_TRASH, STRIKE };
    private Sprite sprite;
    private RecyclableType type;
    private MotionState currentMotion = MotionState.CONVEYOR;

    public Recyclable(double currentTime, RecyclableType type){
    	this.type = type;

        sprite = new Sprite(type.getFilePath(), GameConstants.BOTTOM_PATH_START_X,GameConstants.BOTTOM_PATH_START_Y);
        sprite.setStartTime(currentTime);
    	// TODO should recyclables really need to know the time???
    }

	@Override
	public Sprite getSprite() {
		return sprite;
	}

    public RecyclableType getType() {
        return type;
    }

    public void setMotionState(MotionState motion) {
        this.currentMotion = motion;
    }

    public MotionState getCurrentMotion() {
        return currentMotion;
    }
}
