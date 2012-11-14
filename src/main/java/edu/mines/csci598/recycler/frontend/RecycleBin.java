package edu.mines.csci598.recycler.frontend;

import java.util.LinkedList;

/**
 * This is a representation of invisible "bins" where you knock the recyclables into.
 * The drawing of bins will be in the background.
 * This class basically keeps track of where the bins are in relation to the conveyor so you can
 * ask if a given item fell into a bin and if it was the correct one.
 * <p/>
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/20/12
 * Time: 9:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecycleBin {

    public enum ConveyorSide {RIGHT, LEFT};
    private double minY;
    private double maxY;
    private ConveyorSide side;
    private RecyclableType type;

    public RecycleBin(RecyclableType type) {
        this.type = type;
    }

    public RecycleBin(ConveyorSide side, double minY, double maxY, RecyclableType type) {
        this.side = side;
        this.minY = minY;
        this.maxY = maxY;
        this.type = type;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public ConveyorSide getSide() {
        return side;
    }

    public RecyclableType getType() {
        return type;
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
    public static RecycleBin findBinForFallingRecyclable(LinkedList<RecycleBin> recycleBins,Recyclable r) {
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

    // returns true if the incoming recyclable type is the same as the bin type
    public boolean isCorrectRecyclableType(Recyclable r) {
        if (r.getType() == type) {
            return true;
        }
        return false;
    }

}
