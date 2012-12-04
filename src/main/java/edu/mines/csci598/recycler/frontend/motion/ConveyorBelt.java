package edu.mines.csci598.recycler.frontend.motion;

import edu.mines.csci598.recycler.frontend.GameConstants;
import edu.mines.csci598.recycler.frontend.GameLogic;
import edu.mines.csci598.recycler.frontend.graphics.Coordinate;
import edu.mines.csci598.recycler.frontend.graphics.GameScreen;
import edu.mines.csci598.recycler.frontend.graphics.Line;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.items.MotionState;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

public class ConveyorBelt extends ItemMover {
    private static final Logger logger = Logger.getLogger(ConveyorBelt.class);
    private final Path PATH;

    public static final int LEFT_BOTTOM_PATH_START_X = 0;
    public static final int LEFT_BOTTOM_PATH_START_Y = 880;
    public static final int LEFT_BOTTOM_PATH_END_X = 390;
    public static final int LEFT_BOTTOM_PATH_END_Y = LEFT_BOTTOM_PATH_START_Y;

    public static final int LEFT_VERTICAL_PATH_START_X = LEFT_BOTTOM_PATH_END_X;
    public static final int LEFT_VERTICAL_PATH_START_Y = LEFT_BOTTOM_PATH_END_Y;
    public static final int LEFT_VERTICAL_PATH_END_X = LEFT_BOTTOM_PATH_END_X;
    public static final int LEFT_VERTICAL_PATH_END_Y = 40;

    public static final int LEFT_TOP_PATH_START_X = LEFT_VERTICAL_PATH_END_X;
    public static final int LEFT_TOP_PATH_START_Y = LEFT_VERTICAL_PATH_END_Y;
    public static final int LEFT_TOP_PATH_END_X = 650;
    public static final int LEFT_TOP_PATH_END_Y = LEFT_VERTICAL_PATH_END_Y;

    public static final int RIGHT_BOTTOM_PATH_START_X = 1920 - LEFT_BOTTOM_PATH_START_X - 100;
    public static final int RIGHT_BOTTOM_PATH_START_Y = 880;
    public static final int RIGHT_BOTTOM_PATH_END_X = 1920 - LEFT_BOTTOM_PATH_END_X - 100;
    public static final int RIGHT_BOTTOM_PATH_END_Y = RIGHT_BOTTOM_PATH_START_Y;

    public static final int RIGHT_VERTICAL_PATH_START_X = RIGHT_BOTTOM_PATH_END_X;
    public static final int RIGHT_VERTICAL_PATH_START_Y = RIGHT_BOTTOM_PATH_END_Y;
    public static final int RIGHT_VERTICAL_PATH_END_X = RIGHT_BOTTOM_PATH_END_X;
    public static final int RIGHT_VERTICAL_PATH_END_Y = 40;

    public static final int RIGHT_TOP_PATH_START_X = RIGHT_VERTICAL_PATH_END_X;
    public static final int RIGHT_TOP_PATH_START_Y = RIGHT_VERTICAL_PATH_END_Y;
    public static final int RIGHT_TOP_PATH_END_X = 1920 - LEFT_TOP_PATH_END_X - 100;
    public static final int RIGHT_TOP_PATH_END_Y = RIGHT_VERTICAL_PATH_END_Y;

    public static final int SPRITE_BECOMES_TOUCHABLE = LEFT_BOTTOM_PATH_END_Y - 50;
    public static final int SPRITE_BECOMES_UNTOUCHABLE = LEFT_TOP_PATH_START_Y + 20;

    public ConveyorBelt(GameLogic game, GameScreen gameScreen, Path path) {
        super();
        movables = new ArrayList<Movable>();
        PATH = path;
    }

    /*
    * Returns the next touchable recyclable
    */
    public Movable getNextMovableThatIsTouchable() {
        int index = 0;
        while(index < movables.size()){
        	Movable r = movables.get(index);
        	if(r.isTouchable()){
        		return r;
        	}
        	index++;
        }
        return null;
    }



    /**
     * Advances the conveyor belt.  All the items on it will get carried along for the ride!
     * In addition to doing the standard motion of items, it also updates their MotionState.
     *
     * @param currentTimeSec in milliseconds
     */
    @Override
    public void moveItems(double currentTimeSec) {
        super.moveItems(currentTimeSec);

        // Update all the current items
        for (Movable movable : movables) {
            Coordinate position = movable.getPosition();
            if (position.getY() < SPRITE_BECOMES_UNTOUCHABLE) {
                movable.setMotionState(MotionState.CHUTE);

            } else if (position.getY() < SPRITE_BECOMES_TOUCHABLE) {
                if (movable.getMotionState() == MotionState.CHUTE) {
                    movable.setMotionState(MotionState.CONVEYOR);
                }
            }
        }
    }

    public Path getConveyorPath() {
        return new Path(PATH);
    }

    public static Path getConveyorBeltPathLeft() {
        //Left Path
        final Line bottomLineLeft = new Line(LEFT_BOTTOM_PATH_START_X, LEFT_BOTTOM_PATH_START_Y,
                LEFT_BOTTOM_PATH_END_X, LEFT_BOTTOM_PATH_END_Y, 5);
        final Line verticalLineLeft = new Line(LEFT_VERTICAL_PATH_START_X, LEFT_VERTICAL_PATH_START_Y,
                LEFT_VERTICAL_PATH_END_X, LEFT_VERTICAL_PATH_END_Y, 10);
        final Line topLineLeft = new Line(LEFT_TOP_PATH_START_X, LEFT_TOP_PATH_START_Y,
                LEFT_TOP_PATH_END_X, LEFT_TOP_PATH_END_Y, 5);
        return new Path(Arrays.asList(bottomLineLeft, verticalLineLeft, topLineLeft));
    }

    public static Path getConveyorBeltPathRight() {
        //Right Path
        final Line bottomLineRight = new Line(RIGHT_BOTTOM_PATH_START_X, RIGHT_BOTTOM_PATH_START_Y,
                RIGHT_BOTTOM_PATH_END_X, RIGHT_BOTTOM_PATH_END_Y, 5);
        final Line verticalLineRight = new Line(RIGHT_VERTICAL_PATH_START_X, RIGHT_VERTICAL_PATH_START_Y,
                RIGHT_VERTICAL_PATH_END_X, RIGHT_VERTICAL_PATH_END_Y, 10);
        final Line topLineRight = new Line(RIGHT_TOP_PATH_START_X, RIGHT_TOP_PATH_START_Y,
                RIGHT_TOP_PATH_END_X, RIGHT_TOP_PATH_END_Y, 5);
        return new Path(Arrays.asList(bottomLineRight, verticalLineRight, topLineRight));
    }
}
