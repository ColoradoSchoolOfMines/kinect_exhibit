package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Displayable;
import edu.mines.csci598.recycler.frontend.graphics.Line;
import edu.mines.csci598.recycler.frontend.graphics.Path;
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
    private Sprite sprite;
    private RecyclableType type;
    private Path p;
    
    public Recyclable(double currentTime, RecyclableType type){ 
    	this.type = type;
    	
        p = new Path();
        p.addLine(new Line(0.0,0.0,700.0,00.0,10.0));
        
        sprite = new Sprite(type.getFilePath(), 0, 0, 0.1);
        sprite.setPath(p);
        sprite.setStartTime(currentTime);
    	// TODO should recyclables really need to know the time???
    }
    
	@Override
	public Sprite getSprite() {
		return sprite;
	};
}
