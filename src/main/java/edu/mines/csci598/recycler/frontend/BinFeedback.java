package edu.mines.csci598.recycler.frontend;

import org.apache.log4j.Logger;

import edu.mines.csci598.recycler.frontend.graphics.Coordinate;
import edu.mines.csci598.recycler.frontend.graphics.Displayable;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;
import edu.mines.csci598.recycler.frontend.motion.Movable;

/**
 * Created with IntelliJ IDEA.
 * User: Lauren
 * Date: 12/2/12
 * Time: 1:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class BinFeedback implements Displayable, Movable {
    private static final Logger logger = Logger.getLogger(BinFeedback.class);

    private Sprite sprite;
    private Path path;

    private boolean removable;
    private boolean touchable;
    //TODO: Touchable vs MotionState - ask motionState if touchable

    private MotionState motionState;

    public BinFeedback(String image, Path path) {
        this.sprite = new Sprite(image, (int) path.getInitialPosition().getX(), (int) path.getInitialPosition().getY());
        this.path = path;
        removable = true;
        touchable = false;
        motionState = MotionState.NONE;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    public Coordinate getPosition() {
        return sprite.getPosition();
    }

    @Override
    public void setPosition(Coordinate position) {
        sprite.setPosition(position);
    }

    @Override
    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    @Override
    public boolean isTouchable() {
        return touchable;
    }

    /**
     * checks for a collision with the given point.
     * Does *not* check if the item is touchable.
     *
     * @param point
     * @return
     */
    @Override
    public boolean collidesWithPoint(Coordinate point) {
        return sprite.isPointInside((int)point.getX(), (int)point.getY());
    }

    @Override
    public MotionState getMotionState() {
        return motionState;
    }

    @Override
    public void setMotionState(MotionState state) {
        motionState = state;
    }

    @Override
    public void reactToCollision(Hand hand, double travelTime) {
        throw new IllegalStateException("BinFeedback should not be reacting to any collisions!");
    }

}
