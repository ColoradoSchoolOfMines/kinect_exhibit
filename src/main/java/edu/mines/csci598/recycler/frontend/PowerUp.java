package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Coordinate;
import edu.mines.csci598.recycler.frontend.graphics.Displayable;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;
import edu.mines.csci598.recycler.frontend.motion.Movable;

/**
 * Created with IntelliJ IDEA.
 * User: jrramey11
 * Date: 12/2/12
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class PowerUp implements Displayable, Movable {

    public enum PowerUpType {
        DYNAMITE("src/main/resources/SpriteImages/FinalSpriteImages/dynamite.png"),
        TURTLE("src/main/resources/SpriteImages/FinalSpriteImages/turtle.png"),
        BLASTER("src/main/resources/SpriteImages/FinalSpriteImages/blaster.png");

        private String image;

        private PowerUpType(String image){
            this.image = image;
        }

        public String getImage() {
            return image;
        }
    }
    private Sprite sprite;
    private Path path;
    private PowerUpType type;

    private boolean removable;

    private MotionState motionState;

    public PowerUp(PowerUpType type, Path path, String image) {
        this.sprite = new Sprite(image, (int) path.getInitialPosition().getX(), (int) path.getInitialPosition().getY());
        this.path = path;
        this.type = type;
        removable = true;
        motionState = MotionState.CHUTE; //TODO: ????
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
    public boolean isTouchable(){
        return motionState.isTouchable();
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

    public PowerUpType getType() {
        return type;
    }

    public void setType(PowerUpType type) {
        this.type = type;
    }
}
