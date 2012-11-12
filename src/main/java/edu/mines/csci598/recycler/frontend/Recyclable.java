package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Displayable;
import edu.mines.csci598.recycler.frontend.graphics.Line;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;
import edu.mines.csci598.recycler.frontend.utils.Log;

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

    public enum MotionState {CHUTE, CONVEYOR, FALL_LEFT, FALL_RIGHT, FALL_TRASH, STRIKE};
    private Sprite sprite;
    private RecyclableType type;
    private MotionState currentMotion = MotionState.CONVEYOR;

    public Recyclable(double currentTime, RecyclableType type) {
        this.type = type;

        sprite = new Sprite(type.getFilePath(), GameConstants.BOTTOM_PATH_START_X, GameConstants.BOTTOM_PATH_START_Y);
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

    /**
     * checks for a collision with the given hand
     *
     * @param hand
     * @param currentTimeSec
     * @return
     */
    public boolean hasCollisionWithHand(Hand hand, double currentTimeSec) {
        if (sprite.getState() == Sprite.TouchState.TOUCHABLE) {
            if(sprite.isPointInside(hand.getX(), hand.getY())) {
                if (hand.getVelocityX() > GameConstants.MIN_HAND_VELOCITY) {
                    Path path = new Path();
                    Log.logInfo("INFO: Pushed Right");
                    Line collideLine = new Line(sprite.getX(), sprite.getY(),
                            sprite.getX() + GameConstants.ITEM_PATH_END, sprite.getY(),
                            GameConstants.ITEM_PATH_TIME);
                    path.addLine(collideLine);
                    sprite.setPath(path);
                    sprite.setStartTime(currentTimeSec);
                    setMotionState(Recyclable.MotionState.FALL_RIGHT);
                    return true;
                }
                else if (hand.getVelocityX() < -1 * GameConstants.MIN_HAND_VELOCITY) {
                    Path path = new Path();
                    Log.logInfo("INFO: Pushed Left");
                    Line collideLine = new Line(sprite.getX(), sprite.getY(),
                            sprite.getX() - GameConstants.ITEM_PATH_END, sprite.getY(),
                            GameConstants.ITEM_PATH_TIME);
                    path.addLine(collideLine);
                    sprite.setPath(path);
                    sprite.setStartTime(currentTimeSec);
                    setMotionState(Recyclable.MotionState.FALL_LEFT);
                    return true;
                }
            }
        }
        return false;
    }

}
