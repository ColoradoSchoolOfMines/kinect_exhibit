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

    RecycleBins(){
        setUpBins();
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
            if ((r.getCurrentMotion() == Recyclable.MotionState.FALL_LEFT && bin.getSide() == RecycleBin.ConveyorSide.LEFT) ||
                    (r.getCurrentMotion() == Recyclable.MotionState.FALL_RIGHT && bin.getSide() == RecycleBin.ConveyorSide.RIGHT)) {

                if (yCord >= bin.getMinY() && yCord <= bin.getMaxY()) {
                    return bin;
                }
            }
        }
        return recycleBins.getLast();
    }
    // sets up the location of each of the bins with trash last since it is the last accessed bin
    private void setUpBins() {
        RecycleBin bin1 = new RecycleBin(
                GameConstants.BIN_1_SIDE, GameConstants.BIN_1_MIN_Y,
                GameConstants.BIN_1_MAX_Y, GameConstants.BIN_1_TYPE);
        RecycleBin bin2 = new RecycleBin(
                GameConstants.BIN_2_SIDE, GameConstants.BIN_2_MIN_Y,
                GameConstants.BIN_2_MAX_Y, GameConstants.BIN_2_TYPE);
        RecycleBin bin3 = new RecycleBin(
                GameConstants.BIN_3_SIDE, GameConstants.BIN_3_MIN_Y,
                GameConstants.BIN_3_MAX_Y, GameConstants.BIN_3_TYPE);
        RecycleBin bin4 = new RecycleBin(
                GameConstants.BIN_4_SIDE, GameConstants.BIN_4_MIN_Y,
                GameConstants.BIN_4_MAX_Y, GameConstants.BIN_4_TYPE);
        RecycleBin bin5 = new RecycleBin(
                GameConstants.BIN_5_SIDE, GameConstants.BIN_5_MIN_Y,
                GameConstants.BIN_5_MAX_Y, GameConstants.BIN_5_TYPE);
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
}
