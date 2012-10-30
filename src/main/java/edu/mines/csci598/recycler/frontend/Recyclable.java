package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Displayable;
import edu.mines.csci598.recycler.frontend.graphics.Line;
import edu.mines.csci598.recycler.frontend.graphics.Path;
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
    public enum MotionState{SHOOT, CONVEYOR, FALL_LEFT, FALL_RIGHT, FALL_TRASH, STRIKE }
    private Sprite sprite;
    private RecyclableType type;
    private Path p;
    //private Line line = new Line(0.0,0.0,700.0,00.0,10.0);
    private Line bottomLine = new Line(GameConstants.BOTTOM_PATH_START_X,GameConstants.BOTTOM_PATH_START_Y,
                        GameConstants.BOTTOM_PATH_END_X,GameConstants.BOTTOM_PATH_END_Y,GameConstants.BOTTOM_PATH_TIME);
    private Line verticalLine = new Line(GameConstants.VERTICAL_PATH_START_X,GameConstants.VERTICAL_PATH_START_Y,
            GameConstants.VERTICAL_PATH_END_X,GameConstants.VERTICAL_PATH_END_Y,GameConstants.VERTICAL_PATH_TIME);
    private Line topLine = new Line(GameConstants.TOP_PATH_START_X,GameConstants.TOP_PATH_START_Y,
            GameConstants.TOP_PATH_END_X,GameConstants.TOP_PATH_END_Y,GameConstants.TOP_PATH_TIME);
    
    public Recyclable(double currentTime, RecyclableType type){ 
    	this.type = type;
    	
        p = new Path();
        p.addLine(bottomLine);
        p.addLine(verticalLine);
        p.addLine(topLine);
        
        sprite = new Sprite(type.getFilePath(), GameConstants.BOTTOM_PATH_START_X,GameConstants.BOTTOM_PATH_START_Y, 0.1);
        sprite.setPath(p);
        sprite.setStartTime(currentTime);
    	// TODO should recyclables really need to know the time???
    }
    
	@Override
	public Sprite getSprite() {
		return sprite;
	};
}
