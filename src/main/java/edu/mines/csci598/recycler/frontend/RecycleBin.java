package edu.mines.csci598.recycler.frontend;

import org.apache.log4j.Logger;

import edu.mines.csci598.recycler.frontend.graphics.Displayable;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;
import edu.mines.csci598.recycler.frontend.items.Recyclable;
import edu.mines.csci598.recycler.frontend.items.RecyclableType;
import edu.mines.csci598.recycler.frontend.motion.Movable;

/**
 * This is a representation of invisible "bins" where you knock the recyclables into.
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

    private static final Logger logger = Logger.getLogger(RecycleBin.class);

    private static final int NUM_ITEMS_BIN_FULL = 6;
    private static final int NUM_ITEMS_BIN_HALF = 3;

	public static final RecycleBin TRASH_BIN = new RecycleBin(RecyclableType.TRASH);

    public enum RecycleBinLevel {
        EMPTY,
        HALF,
        FULL;
    }
	
    public enum ConveyorSide {RIGHT, LEFT};
    private int minY;
    private int maxY;
    private int numItems;
    private RecycleBinLevel level;
    private ConveyorSide side;
    private RecyclableType type;
    private Sprite sprite = null;
    private SoundEffectEnum soundEffect;

    /**
     * Creates the Trash bin.  Not intended to be used for any other type of item.
     * @param type
     */
    public RecycleBin(RecyclableType type) {
    	if(type != RecyclableType.TRASH){
    		throw new IllegalArgumentException("This constructor is intended only for the trash can");
    	}
        this.type = type;
        this.soundEffect = SoundEffectEnum.NONE;
    }

    /**
     * Creates a new RecycleBin
     * @param side - The side of the player's screen the bin appears on
     * @param minY - The lower bound of the bin, in pixels
     * @param maxY - The upper bound of the bin, in pixels
     * @param type - The type of item this bin accepts
     * @param imagePath - The path to the image used for this bin
     * @param itemHitsBinSound - The sound effect used when items are dropped into the bin.  (Correct or incorrect items) 
     */
    public RecycleBin(ConveyorSide side, int minY, int maxY, RecyclableType type, String imagePath,SoundEffectEnum itemHitsBinSound) {
        this.side = side;
        this.minY = minY;
        this.maxY = maxY;
        this.type = type;
        numItems = 0;
        sprite = new Sprite(imagePath, 0, 0);
        soundEffect = itemHitsBinSound;
        level = RecycleBinLevel.EMPTY;
    }

    /**
     * Returns true if the given recyclable type is the same as the bin type
     * @param m
     * @return
     */
    public boolean isCorrectRecyclableType(Movable m) {
        if (m instanceof Recyclable) {
            Recyclable r = (Recyclable) m;
            if (r.getType() == type) {
                return true;
            }
        }
        return false;
    }

    /**
     * Puts an item into the bin
     */
    public void addItem() {
        numItems++;
    }

    /**
     * Deals with graphically filling up bins
     * @param side
     * @return true if combo bonus score is achieved (empty a full bin), false otherwise
     */
    public boolean handleBinLevel(Side side) {
        boolean comboBonus = false; //Get extra points for filling up bin
        if (type != RecyclableType.TRASH) { //Trash can doesn't fill up
            if (level == RecycleBinLevel.FULL) {  //Was full, just added an item - need to empty
                level = RecycleBinLevel.EMPTY;
                numItems = 0;
                comboBonus = true;
            }
            if (numItems >= NUM_ITEMS_BIN_FULL) {
                level = RecycleBinLevel.FULL;
            }
            else if (numItems >= NUM_ITEMS_BIN_HALF) {
                level = RecycleBinLevel.HALF;
            }
            StringBuilder filename = new StringBuilder();
            filename.append("src/main/resources/SpriteImages/Bins/");
            filename.append(side.toString().toLowerCase());
            filename.append("_bin_");
            filename.append(type.toString().toLowerCase());
            filename.append("_");
            filename.append(level.toString().toLowerCase());
            filename.append(".png");
            sprite.setImage(filename.toString());
        }
        return comboBonus;
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

    public int getYMidPoint() {
        return (minY + maxY) / 2;
    }

    public SoundEffectEnum getSoundEffect(){
        return soundEffect;
    }

    @Override
    public String toString() {
        return type.toString() + " " + minY + " to " + maxY;
    }
}
