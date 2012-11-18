package edu.mines.csci598.recycler.frontend;

import java.awt.geom.Point2D;

import org.apache.log4j.Logger;

import edu.mines.csci598.recycler.frontend.graphics.Displayable;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;

/**
 * Recyclables are things like bottles, plastic etc. that you would be swiping at.
 * They need to keep track of what kind of recyclable they are, and where state they are in.
 * <p/>
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/20/12
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Recyclable implements Displayable {
    private static final Logger logger = Logger.getLogger(Recyclable.class);
    public enum CollisionState{HIT_LEFT, HIT_RIGHT, NONE};
    
    private Sprite sprite;
    private RecyclableType type;
    private MotionState currentMotion;
    private Path path;

    public Recyclable(RecyclableType type, Path path) {
        this.type = type;
        this.path = path;
        currentMotion = MotionState.CONVEYOR;
        sprite = new Sprite(type.getFilePath(), GameConstants.BOTTOM_PATH_START_X, GameConstants.BOTTOM_PATH_START_Y);
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

    public MotionState getMotionState() {
        return currentMotion;
    }
    
    public boolean isTouchable(){
        return currentMotion.isTouchable();
    }
    
    public Path getPath(){
    	return path;
    }
    
    public void setPath(Path path){
    	this.path = path;
    }

    /**
     * checks for a collision with the given hand
     *
     * @param hand
     * @param currentTimeSec
     * @return
     */
    public CollisionState hasCollisionWithHand(Hand hand, double currentTimeSec) {
    	if(!isTouchable() || !sprite.isPointInside(hand.getX(), hand.getY())){
    		return CollisionState.NONE;
    	}
    	if(hand.getVelocityX() > GameConstants.MIN_HAND_VELOCITY){
    		return CollisionState.HIT_RIGHT;
    	}
    	if(hand.getVelocityX() < -1 * GameConstants.MIN_HAND_VELOCITY){
    		return CollisionState.HIT_LEFT;
    	}
    	
    	return CollisionState.NONE;
    }

	public Point2D getPosition() {
		return sprite.getPosition();
	}

	public void setPosition(Point2D location) {
		sprite.setPosition(location);
	}

}
