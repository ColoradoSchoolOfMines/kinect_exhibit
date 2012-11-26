package edu.mines.csci598.recycler.frontend.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Marshall
 * Date: 11/14/12
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class ComputerConstants {
    /*
     * MIN_GENERATION_THRESHOLD
     * Random number must be greater than this number to strike at recyclable.
     * This is only a starting threshold and should change as game progresses.
     */
    public static final int MIN_GENERATION_THRESHOLD = 90;
    /*
     * MAX_GENERATION_NUMBER
     * For random number generator.
     * Generates numbers from 1 to 100 using a +1
     */
    public static final int MAX_GENERATION_NUMBER = 100;
    /*
     * LAST_STRIKE_UPDATE
     * Used to slow hand movement when repositioning to the correct side.
     */
    public static final double LAST_STRIKE_UPDATE = 0.33;
    /*
     * HAND_X_OFFSET_FROM_CONVEYER
     * Place hand at an offset to the recyclable.
     */
    public static final int HAND_X_OFFSET_FROM_CONVEYER = 100;
    /*
     * LAST_STRIKE_DELAY
     * Must wait this long before attempting to strike at a new recyclable
     */
    public static final double LAST_STRIKE_DELAY = 0.65;
    /*
     * LAST_MOVE_DELAY
     * Delay hand movement when following recyclable.
     */
    public static final double LAST_MOVE_DELAY = 0.30;
    /*
     * HAND_GOAL_OFFSET
     * Goal for moving up and around recyclable.
     */
    public static final int HAND_GOAL_OFFSET = 60;
    /*
     * HAND_Y_OFFSET
     * Position for moving up and around recyclable.
     */
    public static final int HAND_Y_OFFSET = 200;
}
