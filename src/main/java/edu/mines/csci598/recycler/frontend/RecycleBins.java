package edu.mines.csci598.recycler.frontend;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Marshall
 * Date: 11/14/12
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecycleBins {
    private List<RecycleBin> recycleBins = new LinkedList<RecycleBin>();

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
        return getLast();
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
               PLASTIC_LEFT_SIDE,  PLASTIC_MIN_Y,
                PLASTIC_MAX_Y, RecyclableType.PLASTIC,
                PLASTIC_IMAGE);
        RecycleBin bin2 = new RecycleBin(
                PAPER_LEFT_SIDE, PAPER_MIN_Y,
                PAPER_MAX_Y, RecyclableType.PAPER,
                PAPER_IMAGE);
        RecycleBin bin3 = new RecycleBin(
                HAZARD_LEFT_SIDE, HAZARD_MIN_Y,
                HAZARD_MAX_Y, RecyclableType.HAZARD,
                HAZARD_IMAGE);
        RecycleBin bin4 = new RecycleBin(
                GLASS_LEFT_SIDE, GLASS_MIN_Y,
                GLASS_MAX_Y, RecyclableType.GLASS,
                GLASS_IMAGE);

        recycleBins.add(bin1);
        recycleBins.add(bin2);
        recycleBins.add(bin3);
        recycleBins.add(bin4);
        recycleBins.add(RecycleBin.TRASH_BIN);
    }

    /**
     *  sets up the location of each of the bins with trash last since it is the last accessed bin
     */
    //TODO: Get images for right side bins and apply those
    private void setUpRightBins() {
        RecycleBin bin1 = new RecycleBin(
                PLASTIC_RIGHT_SIDE,  PLASTIC_MIN_Y,
                PLASTIC_MAX_Y, RecyclableType.PLASTIC,
                PLASTIC_IMAGE);
        RecycleBin bin2 = new RecycleBin(
                PAPER_RIGHT_SIDE, PAPER_MIN_Y,
                PAPER_MAX_Y, RecyclableType.PAPER,
                PAPER_IMAGE);
        RecycleBin bin3 = new RecycleBin(
                HAZARD_RIGHT_SIDE, HAZARD_MIN_Y,
                HAZARD_MAX_Y, RecyclableType.HAZARD,
                HAZARD_IMAGE);
        RecycleBin bin4 = new RecycleBin(
                GLASS_RIGHT_SIDE, GLASS_MIN_Y,
                GLASS_MAX_Y, RecyclableType.GLASS,
                GLASS_IMAGE);

        recycleBins.add(bin1);
        recycleBins.add(bin2);
        recycleBins.add(bin3);
        recycleBins.add(bin4);
        recycleBins.add(RecycleBin.TRASH_BIN);
    }
    
    public RecycleBin getLast(){
        return recycleBins.get(recycleBins.size() - 1);
    }

    public List<RecycleBin> getRecycleBins() {
        return recycleBins;
    }

    //Bin constants
    public static final RecycleBin.ConveyorSide PLASTIC_LEFT_SIDE = RecycleBin.ConveyorSide.LEFT;
    public static final RecycleBin.ConveyorSide PLASTIC_RIGHT_SIDE = RecycleBin.ConveyorSide.RIGHT;
    public static final double PLASTIC_MIN_Y = 40;
    public static final double PLASTIC_MAX_Y = 300;
    public static final String PLASTIC_IMAGE = "src/main/resources/SpriteImages/FinalSpriteImages/bin_plastic_empty.png";

    public static final RecycleBin.ConveyorSide PAPER_LEFT_SIDE = RecycleBin.ConveyorSide.LEFT;
    public static final RecycleBin.ConveyorSide PAPER_RIGHT_SIDE = RecycleBin.ConveyorSide.RIGHT;
    public static final double PAPER_MIN_Y = 301;
    public static final double PAPER_MAX_Y = 838;
    public static final String PAPER_IMAGE = "src/main/resources/SpriteImages/FinalSpriteImages/bin_paper_empty.png";

    public static final RecycleBin.ConveyorSide HAZARD_LEFT_SIDE = RecycleBin.ConveyorSide.RIGHT;
    public static final RecycleBin.ConveyorSide HAZARD_RIGHT_SIDE = RecycleBin.ConveyorSide.LEFT;
    public static final double HAZARD_MIN_Y = 140;
    public static final double HAZARD_MAX_Y = 440;
    public static final String HAZARD_IMAGE = "src/main/resources/SpriteImages/FinalSpriteImages/bin_hazard_empty.png";

    public static final RecycleBin.ConveyorSide GLASS_LEFT_SIDE = RecycleBin.ConveyorSide.RIGHT;
    public static final RecycleBin.ConveyorSide GLASS_RIGHT_SIDE = RecycleBin.ConveyorSide.LEFT;
    public static final double GLASS_MIN_Y = 441;
    public static final double GLASS_MAX_Y = 975;
    public static final String GLASS_IMAGE = "src/main/resources/SpriteImages/FinalSpriteImages/bin_glass_empty.png";

}
