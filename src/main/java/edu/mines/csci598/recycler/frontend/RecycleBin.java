package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Displayable;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;
import edu.mines.csci598.recycler.frontend.motion.Movable;

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
public class RecycleBin implements Displayable {

	public static final RecycleBin TRASH_BIN = new RecycleBin(RecyclableType.TRASH);
	
    public enum ConveyorSide {RIGHT, LEFT};
    private int minY;
    private int maxY;
    private int numItems;
    private ConveyorSide side;
    private RecyclableType type;
    private Sprite sprite = null;

    public RecycleBin(RecyclableType trash) {
        this.type = trash;
    }

    public RecycleBin(ConveyorSide side, int minY, int maxY, RecyclableType type, String imagePath) {
        this.side = side;
        this.minY = minY;
        this.maxY = maxY;
        this.type = type;
        numItems = 0;
        sprite = new Sprite(imagePath, 0, 0);
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    public boolean hasSprite() {
        if (sprite == null) {
            return false;
        }
        return true;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public ConveyorSide getSide() {
        return side;
    }

    public RecyclableType getType() {
        return type;
    }

    public int getMidPoint() {
        return (minY + maxY) / 2;
    }

    @Override
    public String toString() {
        return type.toString();
    }

    public void addItem() {
        numItems++;
    }


    // returns true if the incoming recyclable type is the same as the bin type
    public boolean isCorrectRecyclableType(Movable m) {
        if (m instanceof Recyclable) {
            Recyclable r = (Recyclable) m;
            if (r.getType() == type) {
                return true;
            }
        }
        return false;
    }

}
