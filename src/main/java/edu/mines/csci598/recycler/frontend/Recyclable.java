package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Displayable;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;

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
    public enum MotionState{SHOOT, CONVEYOR, FALL_LEFT, FALL_RIGHT, FALL_TRASH, STRIKE }

	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return null;
	};
}
