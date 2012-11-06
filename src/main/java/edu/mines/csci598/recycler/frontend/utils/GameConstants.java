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
    public static final int INITIAL_NUMBER_OF_ITEM_TYPES = 2;
    public static final double INITIAL_ITEM_GENERATION_DELAY_SECONDS = 3;

    public static final int ADD_SPRITE = 0;
    public static final int REMOVE_SPRITE = 1;
    public static final int UPDATE_SPRITES = 2;

    public static final int BOTTOM_PATH_START_X = 0;
    public static final int BOTTOM_PATH_START_Y = 880;
    public static final int BOTTOM_PATH_END_X = 480;
    public static final int BOTTOM_PATH_END_Y = BOTTOM_PATH_START_Y;
    public static final int BOTTOM_PATH_TIME = 5;

    public static final int VERTICAL_PATH_START_X = BOTTOM_PATH_END_X;
    public static final int VERTICAL_PATH_START_Y = BOTTOM_PATH_END_Y;
    public static final int VERTICAL_PATH_END_X = BOTTOM_PATH_END_X;
    public static final int VERTICAL_PATH_END_Y = 80;
    public static final int VERTICAL_PATH_TIME = 10;

    public static final int TOP_PATH_START_X = VERTICAL_PATH_END_X;
    public static final int TOP_PATH_START_Y = VERTICAL_PATH_END_Y;
    public static final int TOP_PATH_END_X = 860;
    public static final int TOP_PATH_END_Y = VERTICAL_PATH_END_Y;
    public static final int TOP_PATH_TIME = BOTTOM_PATH_TIME;

    public static final int ITEM_PATH_END = 250;
    public static final int ITEM_PATH_TIME = 5;

    public static final int SPRITE_BECOMES_TOUCHABLE = BOTTOM_PATH_END_Y - 50;
    public static final int SPRITE_BECOMES_UNTOUCHABLE = TOP_PATH_START_Y + 20;
    public static final int HORIZONTAL_VELOCITY = 2;

    public static final int MIN_VELOCITY = 25;
    public static final int SPRITE_X_OFFSET = 50;
    public static final int SPRITE_Y_OFFSET = 50;

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

}

