package edu.mines.csci598.recycler;

import edu.mines.csci598.recycler.frontend.MotionState;
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

    private Sprite sprite;
    private Path path;
    private Coordinate position;

    private boolean removable;
    private boolean touchable;
    //TODO: Touchable vs MotionState - ask motionState if touchable

    private MotionState motionState;

    public BinFeedback(Sprite sprite, Path path) {
        this.sprite = sprite;
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

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
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

}
