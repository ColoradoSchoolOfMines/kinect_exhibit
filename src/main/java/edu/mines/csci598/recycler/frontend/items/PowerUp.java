package edu.mines.csci598.recycler.frontend.items;


import edu.mines.csci598.recycler.frontend.graphics.*;
import org.apache.log4j.Logger;

import edu.mines.csci598.recycler.frontend.hands.Hand;
import edu.mines.csci598.recycler.frontend.motion.Movable;

/**
 * Created with IntelliJ IDEA.
 * User: laberle
 * Date: 12/2/12
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class PowerUp implements Displayable, Movable {

    private static final Logger logger = Logger.getLogger(PowerUp.class);

    private static final double TIME_ON_CONVEYOR_AFTER_SWIPE = 0.5;

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
        motionState = MotionState.CHUTE;
    }

    /**
     * If swiped, the powerup receives a new path to make it stay on conveyor for a bit before disappearing
     * @param hand
     * @param currentTimeSec
     */
    @Override
    public void reactToCollision(Hand hand, double currentTimeSec) {
        if (!(this instanceof PowerUp)) {
            throw new IllegalStateException("Trying to react to PowerUp collision with a non-PowerUp!");
        }
        this.setMotionState(MotionState.NONE);
        Line collideLine = new Line(this.getPosition(), this.getPosition(), TIME_ON_CONVEYOR_AFTER_SWIPE);
        Path path = new Path(currentTimeSec);
        path.addLine(collideLine);
        this.setPath(path);
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
        return sprite.isPointInside((int) point.getX(), (int) point.getY());
    }

    /**
     * Determines if the power up should be touched by a hand
     * @return true if power up can be touched, false otherwise
     */
    @Override
    public boolean isTouchable() {
        return motionState.isTouchable();
    }

    /**
     * Determines if PowerUp can be removed from screen when its path is done
     *
     * @return true if it's okay to remove, false otherwise
     */
    @Override
    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
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

    public enum PowerUpType {
        DYNAMITE("src/main/resources/SpriteImages/Items/dynamite.png"),
        TURTLE("src/main/resources/SpriteImages/Items/turtle.png"),
        BLASTER("src/main/resources/SpriteImages/Items/blaster.png");

        private String image;

        private PowerUpType(String image) {
            this.image = image;
        }

        public String getImage() {
            return image;
        }

        public static void preLoadImages() {
            ResourceManager resourceManager = ResourceManager.getInstance();
            for(PowerUpType r : PowerUpType.values()){
                String s = r.getImage();
                logger.debug("Trying to preload image: " + s);
                resourceManager.getImage(s);
            }
        }
    }

}
