package edu.mines.csci598.recycler.frontend;

/**
 * This is a representation of invisible "bins" where you knock the recyclables into.
 * The drawing of bins will be in the background.
 * This class basically keeps track of where the bins are in relation to the conveyor so you can
 * ask if a given item fell into a bin and if it was the correct one.
 *
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/20/12
 * Time: 9:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecycleBin {

    public enum ConveyorSide { RIGHT, LEFT };
    private double minY;
    private double maxY;
    private ConveyorSide side;
    private RecyclableType type;

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

}
