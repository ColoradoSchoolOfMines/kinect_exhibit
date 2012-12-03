package edu.mines.csci598.recycler.frontend.utils;

import edu.mines.csci598.recycler.frontend.RecyclableType;
import edu.mines.csci598.recycler.frontend.RecycleBin;

/**
 * Created with IntelliJ IDEA.
 * User: Marshall
 * Date: 10/28/12
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
public final class GameConstants {
    /**
     * Set to true to have only one item generated on the screen at a time
     */
    public static final boolean DEBUG_COLLISIONS = false;
    /**
     * Set to true when debugging the computer player
     */
    public static final boolean SECOND_PLAYER_IS_A_COMPUTER = true;
    /**
     * The number of item types which are generated at the start of the game
     */
    public static final int INITIAL_NUMBER_OF_ITEM_TYPES = 1;

    /**
     * The minimum amount of time between object generations.  Even when the game is running at full speed,
     * items will never be kicked out faster than this.  It should be set high enough to prevent, at the
     * minimum, items from colliding with each other during creation.
     */
    public static final double MIN_TIME_BETWEEN_GENERATIONS = 2;

    /**
     * The initial amount of time between object generations.  As the game progresses items will probably
     * be generated more often than this, but never more often than <code>MIN_TIME_BETWEEN_GENERATIONS</code>
     */
    public static final double INITIAL_TIME_BETWEEN_GENERATIONS = 7;

    public static final int MIN_HAND_VELOCITY = 10;
    public static final int SPRITE_X_OFFSET = 50;
    public static final int SPRITE_Y_OFFSET = 50;

    /**
     * The amount of time, in seconds from the game start or last item, that it takes before a new item is added to the game
     */
    public static final int TIME_TO_ADD_NEW_ITEM_TYPE = 15;
    /**
     * The amount of time it takes from the start of the game, in seconds, to reach its maximum difficulty
     */
    public static final int TIME_TO_MAX_DIFFICULTY = 300;
    /**
     * The number of items we can include, not including powerups
     */
	public static final int MAX_ITEM_COUNT = 5;
	/**
	 * The initial speed of the conveyor belt, measured in pixels per second
	 */
	public static final double INITIAL_SPEED_IN_PIXELS_PER_SECOND = 20;
	/**
	 * The max time speedup. When the game starts it is 1.0
	 */
	public static final double FINAL_TIME_SPEED_FACTOR = 3.0;
	/**
	 * The speed that an item flies off the conveyor into a bin when hit with a hand, measured in pixels per second
	 */
	public static final double HAND_COLLISION_PATH_SPEED_IN_PIXELS_PER_SECOND = 50;

    /**
     * The distance of the path items travel (x direction) into a bin after being swiped
     */
    public static final int ITEM_PATH_END = 225;

    /**
     * The time range it takes for the item to travel to a bin after being swiped
     */
    public static final double MIN_ITEM_TRAVEL_TIME = 0.3;
    public static final double MAX_ITEM_TRAVEL_TIME = 2;
}

