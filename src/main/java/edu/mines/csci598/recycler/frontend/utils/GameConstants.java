package edu.mines.csci598.recycler.frontend.utils;

import java.awt.geom.Point2D;

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
    public static final boolean DEBUG_COLLISIONS = true;
    /**
     * Set to true when debugging the computer player
     */
    public static final boolean DEBUG_COMPUTER_PLAYER = false;
    /**
     * The number of item types which are generated at the start of the game
     */
    public static final int INITIAL_NUMBER_OF_ITEM_TYPES = 1;
    /**
     * The total number of item types available in the game
     */
    public static final int TOTAL_NUMBER_OF_ITEM_TYPES = 4; // TODO this needs to be 5 eventually
    /**
     * The minimum amount of time between object generations.  Even when the game is running at full speed,
     * items will never be kicked out faster than this.  It should be set high enough to prevent, at the
     * minimum, items from coliding with each other during creation.
     */
    public static final double MIN_TIME_BETWEEN_GENERATIONS = 2;

    public static final double INITIAL_MEAN_TIME_BETWEEN_GENERATIONS = 3;
    
    

    /**
     * If an item is not generated, this is the minimum delay before it can be attempted again.  As item
     * generation possibly happens many times per second, this keeps a non-generation from still showing
     * up immediately as far as the users are concerned.
     */
    public static final double ITEM_GENERATION_DELAY = 0.25;
    /**
     * The probability, at the start of the game, that an item will be generated
     */
    public static final double START_ITEM_GENERATION_PROB = 0.5;

    public static final int BOTTOM_PATH_START_X = 0;
    public static final int BOTTOM_PATH_START_Y = 880;
    public static final int BOTTOM_PATH_END_X = 445;
    public static final int BOTTOM_PATH_END_Y = BOTTOM_PATH_START_Y;
    
    public static final int VERTICAL_PATH_START_X = BOTTOM_PATH_END_X;
    public static final int VERTICAL_PATH_START_Y = BOTTOM_PATH_END_Y;
    public static final int VERTICAL_PATH_END_X = BOTTOM_PATH_END_X;
    public static final int VERTICAL_PATH_END_Y = 40;

    public static final int TOP_PATH_START_X = VERTICAL_PATH_END_X;
    public static final int TOP_PATH_START_Y = VERTICAL_PATH_END_Y;
    public static final int TOP_PATH_END_X = 750;
    public static final int TOP_PATH_END_Y = VERTICAL_PATH_END_Y;

	public static final Point2D END_POSITION = new Point2D.Double(TOP_PATH_END_X, TOP_PATH_END_Y);
    public static final Point2D BECOMES_TOUCHABLE_POSITION = new Point2D.Double(VERTICAL_PATH_START_X,VERTICAL_PATH_START_Y);
    public static final Point2D BECOMES_UNTOUCHABLE_POSITION = new Point2D.Double(VERTICAL_PATH_END_X,VERTICAL_PATH_END_Y);

    public static final int ITEM_PATH_END = 250;

    public static final int SPRITE_BECOMES_TOUCHABLE = BOTTOM_PATH_END_Y - 50;
    public static final int SPRITE_BECOMES_UNTOUCHABLE = TOP_PATH_START_Y + 20;

    public static final int MIN_HAND_VELOCITY = 10;
    public static final int SPRITE_X_OFFSET = 50;
    public static final int SPRITE_Y_OFFSET = 50;

    public static final int IN_BIN_OFFSET = 150;

    public static final RecyclableType BIN_1_TYPE = RecyclableType.GLASS;
    public static final RecycleBin.ConveyorSide BIN_1_SIDE = RecycleBin.ConveyorSide.LEFT;
    public static final double BIN_1_MIN_Y = 438;
    public static final double BIN_1_MAX_Y = 838;

    public static final RecyclableType BIN_2_TYPE = RecyclableType.PAPER;
    public static final RecycleBin.ConveyorSide BIN_2_SIDE = RecycleBin.ConveyorSide.LEFT;
    public static final double BIN_2_MIN_Y = 40;
    public static final double BIN_2_MAX_Y = 440;

    public static final RecyclableType BIN_3_TYPE = RecyclableType.PLASTIC;
    public static final RecycleBin.ConveyorSide BIN_3_SIDE = RecycleBin.ConveyorSide.RIGHT;
    public static final double BIN_3_MIN_Y = 140;
    public static final double BIN_3_MAX_Y = 540;

    public static final RecyclableType BIN_4_TYPE = RecyclableType.PLASTIC;
    public static final RecycleBin.ConveyorSide BIN_4_SIDE = RecycleBin.ConveyorSide.RIGHT;
    public static final double BIN_4_MIN_Y = 538;
    public static final double BIN_4_MAX_Y = 938;

    public static final RecyclableType BIN_5_TYPE = RecyclableType.TRASH;
    public static final RecycleBin.ConveyorSide BIN_5_SIDE = RecycleBin.ConveyorSide.RIGHT;
    public static final double BIN_5_MIN_Y = 0;
    public static final double BIN_5_MAX_Y = 140;


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
	public static final int MAX_ITEM_COUNT = 3;
	/**
	 * The initial speed of the conveyor belt, measured in pixels per second
	 */
	public static final double INITIAL_SPEED_IN_PIXELS_PER_SECOND = 50;
	/**
	 * The speed of the conveyor belt at maximum difficulty, measured in pixels per second
	 */
	public static final double FINAL_SPEED_IN_PIXELS_PER_SECOND = 75;
	/**
	 * The speed that an item flies off the conveyor into a bin when hit with a hand, measured in pixels per second
	 */
	public static final double HAND_COLLISION_PATH_SPEED_IN_PIXELS_PER_SECOND = 50;

}

