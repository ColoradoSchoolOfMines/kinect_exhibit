package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.motion.Movable;

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

    public RecycleBins(Side s) {
        if(s.equals(Side.LEFT)) {
            setUpLeftBins();
        }
        else {
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
     * @param m
     * @return
     */
    public RecycleBin findBinForFallingRecyclable(Movable m) {

        if (!(m instanceof Recyclable)) {
            throw new IllegalStateException("Something is falling into a bin that shouldn't be (not a recyclable)!");
        }

        int yCord = m.getSprite().getScaledY();

        // finds the bin that the trash has gone into using the y coordinates since it can only fall to the right or
        // left of the conveyor we only need to check which way it's going and the y coordinates
        for (RecycleBin bin : recycleBins) {
            if ((m.getMotionState() == MotionState.FALL_LEFT && bin.getSide() == RecycleBin.ConveyorSide.LEFT) ||
                    (m.getMotionState() == MotionState.FALL_RIGHT && bin.getSide() == RecycleBin.ConveyorSide.RIGHT)) {

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
    public RecycleBin findCorrectBin(Recyclable r) {
    	RecyclableType recycleType = r.getType();
        for(RecycleBin recycleBin: recycleBins) {
            if(recycleBin.getType() == recycleType) {
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
                RecycleBin.ConveyorSide.LEFT,  PLASTIC_MIN_Y,
                PLASTIC_MAX_Y, RecyclableType.PLASTIC,
                PLASTIC_IMAGE_LEFT);
        RecycleBin bin2 = new RecycleBin(
                RecycleBin.ConveyorSide.LEFT, PAPER_MIN_Y,
                PAPER_MAX_Y, RecyclableType.PAPER,
                PAPER_IMAGE_LEFT);
        RecycleBin bin3 = new RecycleBin(
                RecycleBin.ConveyorSide.RIGHT, HAZARD_MIN_Y,
                HAZARD_MAX_Y, RecyclableType.HAZARD,
                HAZARD_IMAGE_LEFT);
        RecycleBin bin4 = new RecycleBin(
                RecycleBin.ConveyorSide.RIGHT, GLASS_MIN_Y,
                GLASS_MAX_Y, RecyclableType.GLASS,
                GLASS_IMAGE_LEFT);

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
                RecycleBin.ConveyorSide.LEFT,  PLASTIC_MIN_Y,
                PLASTIC_MAX_Y, RecyclableType.PLASTIC,
                PLASTIC_IMAGE_RIGHT);
        RecycleBin bin2 = new RecycleBin(
                RecycleBin.ConveyorSide.LEFT, PAPER_MIN_Y,
                PAPER_MAX_Y, RecyclableType.PAPER,
                PAPER_IMAGE_RIGHT);
        RecycleBin bin3 = new RecycleBin(
                RecycleBin.ConveyorSide.RIGHT, HAZARD_MIN_Y,
                HAZARD_MAX_Y, RecyclableType.HAZARD,
                HAZARD_IMAGE_RIGHT);
        RecycleBin bin4 = new RecycleBin(
                RecycleBin.ConveyorSide.RIGHT, GLASS_MIN_Y,
                GLASS_MAX_Y, RecyclableType.GLASS,
                GLASS_IMAGE_RIGHT);

        recycleBins.add(bin1);
        recycleBins.add(bin2);
        recycleBins.add(bin3);
        recycleBins.add(bin4);
        recycleBins.add(RecycleBin.TRASH_BIN);
    }
    
    public RecycleBin getLast() {
        return recycleBins.get(recycleBins.size() - 1);
    }

    public List<RecycleBin> getRecycleBins() {
        return recycleBins;
    }

    //Bin constants
    public static final int PLASTIC_MIN_Y = 60;
    public static final int PLASTIC_MAX_Y = 300;
    public static final String PLASTIC_IMAGE_LEFT = "src/main/resources/SpriteImages/FinalSpriteImages/left_bin_plastic_empty.png";
    public static final String PLASTIC_IMAGE_RIGHT = "src/main/resources/SpriteImages/FinalSpriteImages/right_bin_plastic_empty.png";

    public static final int PAPER_MIN_Y = 301;
    public static final int PAPER_MAX_Y = 620;
    //TODO: Replace with empty image when Kyle finishes them
    public static final String PAPER_IMAGE_LEFT = "src/main/resources/SpriteImages/FinalSpriteImages/left_bin_paper_little.png";
    public static final String PAPER_IMAGE_RIGHT = "src/main/resources/SpriteImages/FinalSpriteImages/right_bin_paper_little.png";

    public static final int HAZARD_MIN_Y = 130;
    public static final int HAZARD_MAX_Y = 410;
    public static final String HAZARD_IMAGE_LEFT = "src/main/resources/SpriteImages/FinalSpriteImages/left_bin_hazard_empty.png";
    public static final String HAZARD_IMAGE_RIGHT = "src/main/resources/SpriteImages/FinalSpriteImages/right_bin_hazard_empty.png";

    public static final int GLASS_MIN_Y = 411;
    public static final int GLASS_MAX_Y = 775;
    public static final String GLASS_IMAGE_LEFT = "src/main/resources/SpriteImages/FinalSpriteImages/left_bin_glass_empty.png";
    public static final String GLASS_IMAGE_RIGHT = "src/main/resources/SpriteImages/FinalSpriteImages/right_bin_glass_empty.png";

}
