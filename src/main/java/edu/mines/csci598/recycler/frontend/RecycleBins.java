package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.utils.GameConstants;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Marshall
 * Date: 11/14/12
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecycleBins {
    private LinkedList<RecycleBin> recycleBins = new LinkedList<RecycleBin>();

    public enum Side {LEFT, RIGHT};
    public RecycleBins(Side s){
        if(s.equals(Side.LEFT)){
            setUpLeftBins();
        } else {
            setUpRightBins();
        }
    }

    /**
     * Returns the bin that the recyclable is currently falling into. If it is not falling into any then it is going
     * into the trash.
     *
     * Moved from GameLogic check collision function
     * Need here for AI
     *
     * @param r
     * @return
     */
    public RecycleBin findBinForFallingRecyclable(Recyclable r) {
        int yCord = r.getSprite().getScaledY();

        // finds the bin that the trash has gone into using the y coordinates since it can only fall to the right or
        // left of the conveyor we only need to check which way it's going and the y coordinates
        for (RecycleBin bin : recycleBins) {
            if ((r.getMotionState() == MotionState.FALL_LEFT && bin.getSide() == RecycleBin.ConveyorSide.LEFT) ||
                    (r.getMotionState() == MotionState.FALL_RIGHT && bin.getSide() == RecycleBin.ConveyorSide.RIGHT)) {

                if (yCord >= bin.getMinY() && yCord <= bin.getMaxY()) {
                    return bin;
                }
            }
        }
        return recycleBins.getLast();
    }
    
    /*
     * Find bin type
     */
    public RecycleBin findCorrectBin(Recyclable r){
    	RecyclableType recycleType = r.getType();
        for(RecycleBin recycleBin: recycleBins){
            if(recycleBin.getType()==recycleType){
                return recycleBin;
            }
        }
        
        throw new IllegalStateException("No bin found for recyclable " + r);
    }
    
    /**
     *  sets up the location of each of the bins with trash last since it is the last accessed bin
     */
    private void setUpLeftBins() {
        RecycleBin bin1 = new RecycleBin(
               LEFT_BIN_1_SIDE, LEFT_BIN_1_MIN_Y,
               LEFT_BIN_1_MAX_Y, LEFT_BIN_1_TYPE);
        RecycleBin bin2 = new RecycleBin(
                LEFT_BIN_2_SIDE, LEFT_BIN_2_MIN_Y,
                LEFT_BIN_2_MAX_Y, LEFT_BIN_2_TYPE);
        RecycleBin bin3 = new RecycleBin(
                LEFT_BIN_3_SIDE, LEFT_BIN_3_MIN_Y,
                LEFT_BIN_3_MAX_Y, LEFT_BIN_3_TYPE);
        RecycleBin bin4 = new RecycleBin(
                LEFT_BIN_4_SIDE, LEFT_BIN_4_MIN_Y,
                LEFT_BIN_4_MAX_Y, LEFT_BIN_4_TYPE);
        RecycleBin bin5 = new RecycleBin(
                LEFT_BIN_5_SIDE, LEFT_BIN_5_MIN_Y,
                LEFT_BIN_5_MAX_Y, LEFT_BIN_5_TYPE);
        RecycleBin trash = new RecycleBin(RecyclableType.TRASH);

        recycleBins.add(bin1);
        recycleBins.add(bin2);
        recycleBins.add(bin3);
        recycleBins.add(bin4);
        recycleBins.add(bin5);
        recycleBins.add(trash);
    }

    /**
     *  sets up the location of each of the bins with trash last since it is the last accessed bin
     */
    private void setUpRightBins() {
        RecycleBin bin1 = new RecycleBin(
                RIGHT_BIN_1_SIDE, RIGHT_BIN_1_MIN_Y,
                RIGHT_BIN_1_MAX_Y, RIGHT_BIN_1_TYPE);
        RecycleBin bin2 = new RecycleBin(
                RIGHT_BIN_2_SIDE, RIGHT_BIN_2_MIN_Y,
                RIGHT_BIN_2_MAX_Y, RIGHT_BIN_2_TYPE);
        RecycleBin bin3 = new RecycleBin(
                RIGHT_BIN_3_SIDE, RIGHT_BIN_3_MIN_Y,
                RIGHT_BIN_3_MAX_Y, RIGHT_BIN_3_TYPE);
        RecycleBin bin4 = new RecycleBin(
                RIGHT_BIN_4_SIDE, RIGHT_BIN_4_MIN_Y,
                RIGHT_BIN_4_MAX_Y, RIGHT_BIN_4_TYPE);
        RecycleBin bin5 = new RecycleBin(
                RIGHT_BIN_5_SIDE, RIGHT_BIN_5_MIN_Y,
                RIGHT_BIN_5_MAX_Y, RIGHT_BIN_5_TYPE);
        RecycleBin trash = new RecycleBin(RecyclableType.TRASH);

        recycleBins.add(bin1);
        recycleBins.add(bin2);
        recycleBins.add(bin3);
        recycleBins.add(bin4);
        recycleBins.add(bin5);
        recycleBins.add(trash);
    }
    
    public RecycleBin getLast(){
        return recycleBins.getLast();
    }

    //Left recycle bins
    public static final RecyclableType LEFT_BIN_1_TYPE = RecyclableType.PLASTIC;
    public static final RecycleBin.ConveyorSide LEFT_BIN_1_SIDE = RecycleBin.ConveyorSide.LEFT;
    public static final double LEFT_BIN_1_MIN_Y = 40;
    public static final double LEFT_BIN_1_MAX_Y = 300;

    public static final RecyclableType LEFT_BIN_2_TYPE = RecyclableType.PAPER;
    public static final RecycleBin.ConveyorSide LEFT_BIN_2_SIDE = RecycleBin.ConveyorSide.LEFT;
    public static final double LEFT_BIN_2_MIN_Y = 301;
    public static final double LEFT_BIN_2_MAX_Y = 838;

    public static final RecyclableType LEFT_BIN_3_TYPE = RecyclableType.HAZARD;
    public static final RecycleBin.ConveyorSide LEFT_BIN_3_SIDE = RecycleBin.ConveyorSide.RIGHT;
    public static final double LEFT_BIN_3_MIN_Y = 140;
    public static final double LEFT_BIN_3_MAX_Y = 440;

    public static final RecyclableType LEFT_BIN_4_TYPE = RecyclableType.GLASS;
    public static final RecycleBin.ConveyorSide LEFT_BIN_4_SIDE = RecycleBin.ConveyorSide.RIGHT;
    public static final double LEFT_BIN_4_MIN_Y = 441;
    public static final double LEFT_BIN_4_MAX_Y = 975;

    public static final RecyclableType LEFT_BIN_5_TYPE = RecyclableType.TRASH;
    public static final RecycleBin.ConveyorSide LEFT_BIN_5_SIDE = RecycleBin.ConveyorSide.RIGHT;
    public static final double LEFT_BIN_5_MIN_Y = 0;
    public static final double LEFT_BIN_5_MAX_Y = 140;

    //Right recycle bins
    public static final RecyclableType RIGHT_BIN_1_TYPE = RecyclableType.PLASTIC;
    public static final RecycleBin.ConveyorSide RIGHT_BIN_1_SIDE = RecycleBin.ConveyorSide.RIGHT;
    public static final double RIGHT_BIN_1_MIN_Y = 40;
    public static final double RIGHT_BIN_1_MAX_Y = 360;

    public static final RecyclableType RIGHT_BIN_2_TYPE = RecyclableType.PAPER;
    public static final RecycleBin.ConveyorSide RIGHT_BIN_2_SIDE = RecycleBin.ConveyorSide.RIGHT;
    public static final double RIGHT_BIN_2_MIN_Y = 361;
    public static final double RIGHT_BIN_2_MAX_Y = 818;

    public static final RecyclableType RIGHT_BIN_3_TYPE = RecyclableType.HAZARD;
    public static final RecycleBin.ConveyorSide RIGHT_BIN_3_SIDE = RecycleBin.ConveyorSide.LEFT;
    public static final double RIGHT_BIN_3_MIN_Y = 120;
    public static final double RIGHT_BIN_3_MAX_Y = 480;

    public static final RecyclableType RIGHT_BIN_4_TYPE = RecyclableType.GLASS;
    public static final RecycleBin.ConveyorSide RIGHT_BIN_4_SIDE = RecycleBin.ConveyorSide.LEFT;
    public static final double RIGHT_BIN_4_MIN_Y = 481;
    public static final double RIGHT_BIN_4_MAX_Y = 918;

    public static final RecyclableType RIGHT_BIN_5_TYPE = RecyclableType.TRASH;
    public static final RecycleBin.ConveyorSide RIGHT_BIN_5_SIDE = RecycleBin.ConveyorSide.LEFT;
    public static final double RIGHT_BIN_5_MIN_Y = 0;
    public static final double RIGHT_BIN_5_MAX_Y = 140;
}
