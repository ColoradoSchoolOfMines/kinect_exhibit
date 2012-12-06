package edu.mines.csci598.recycler.frontend.ai;

import edu.mines.csci598.recycler.frontend.motion.ConveyorBelt;

/**
 * Created with IntelliJ IDEA.
 * User: Marshall
 * Date: 11/14/12
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public final class ComputerConstants {
    /**
     * MIN_GENERATION_THRESHOLD
     * Random number must be greater than this number to strike at recyclable.
     * This is only a starting threshold and should change as game progresses.
     */
    public static final int MIN_GENERATION_THRESHOLD = 85;
    /**
     * MAX_GENERATION_NUMBER
     * For random number generator.
     * Generates numbers from 1 to 100 using a +1
     */
    public static final int MAX_GENERATION_NUMBER = 100;
    /** SET_HAND_T0_CORRECT_SIDE_THRESHOLD
     * Probability that the hand will be set to the correct side
     */
    public static final int SET_HAND_T0_CORRECT_SIDE_THRESHOLD = 15;
    /**
     * LAST_STRIKE_UPDATE
     * Used to slow hand movement when repositioning to the correct side.
     */
    public static final double LAST_STRIKE_UPDATE = 0.33;
    /**
     * HAND_X_OFFSET_FROM_CONVEYER
     * Place hand at an offset to the recyclable.
     */
    public static final int HAND_X_OFFSET_FROM_CONVEYER = 100;
    /**
     * LAST_STRIKE_DELAY
     * Must wait this long before attempting to strike at a new recyclable
     * Slows down the speed at which the computer can strike
     */
    public static final double LAST_STRIKE_DELAY = 0.65;
    /**
     * LAST_MOVE_DELAY
     * Delay hand movement when following recyclable.
     */
    public static final double LAST_MOVE_DELAY = 0.30;
    /**
     * HAND_GOAL_OFFSET
     * Goal for moving up and around recyclable.
     */
    public static final int HAND_GOAL_OFFSET = 60;
    /**
     * HAND_Y_OFFSET
     * Position for moving up and around recyclable.
     */
    public static final int HAND_Y_OFFSET = 100;
    /**
     * INITIAL_HAND_Y_OFFSET
     * Starting y position
     */
    public static final int INITIAL_HAND_Y_OFFSET = 50;
    /**
     * PATH_TIME_SEC
     * Time needed to move a line
     */
    public  static final double PATH_TIME_SEC = 0.2;
    /** INCORRECT_STRIKE_THRESHOLD
     * Must exceed this number to strike recyclable into correct bin
     */
    public static final int INCORRECT_STRIKE_THRESHOLD = 2;
    /** INITIAL_HAND_X
     * Position of initial x
     */
    public static final int INITIAL_HAND_X = ConveyorBelt.RIGHT_VERTICAL_PATH_END_X +
            ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER;
    /** INITIAL_HAND_Y
     * Position of initial y
     */
    public static final int INITIAL_HAND_Y = ConveyorBelt.RIGHT_VERTICAL_PATH_START_Y -
            ComputerConstants.INITIAL_HAND_Y_OFFSET;
}
