package edu.mines.csci598.recycler.frontend.items;

import edu.mines.csci598.recycler.frontend.SoundEffectEnum;
import org.apache.log4j.Logger;

import edu.mines.csci598.recycler.frontend.graphics.ResourceManager;

public enum RecyclableType {

    PLASTIC(new String[]{"src/main/resources/SpriteImages/Items/plastic_01.png", "src/main/resources/SpriteImages/Items/plastic_02.png", "src/main/resources/SpriteImages/Items/plastic_03.png"},SoundEffectEnum.PLASTIC_OR_PAPER_HIT),
    PAPER(new String[]{"src/main/resources/SpriteImages/Items/paper_01.png", "src/main/resources/SpriteImages/Items/paper_02.png", "src/main/resources/SpriteImages/Items/paper_03.png", "src/main/resources/SpriteImages/Items/paper_04.png"},SoundEffectEnum.PLASTIC_OR_PAPER_HIT),
    GLASS(new String[]{"src/main/resources/SpriteImages/Items/glass_01.png", "src/main/resources/SpriteImages/Items/glass_02.png", "src/main/resources/SpriteImages/Items/glass_03.png", "src/main/resources/SpriteImages/Items/glass_04.png"},SoundEffectEnum.HIT_GLASS),
    HAZARD(new String[]{"src/main/resources/SpriteImages/Items/hazard_01.png", "src/main/resources/SpriteImages/Items/hazard_02.png","src/main/resources/SpriteImages/Items/hazard_03.png"},SoundEffectEnum.OTHER_HIT),
    TRASH(new String[]{"src/main/resources/SpriteImages/Items/trash_01.png", "src/main/resources/SpriteImages/Items/trash_02.png", "src/main/resources/SpriteImages/Items/trash_03.png"},SoundEffectEnum.NONE);

    private String[] imagePaths;
    private static final Logger logger = Logger.getLogger(RecyclableType.class);
    private SoundEffectEnum hitSound;
    private RecyclableType(String[] imagePaths,SoundEffectEnum hitSound) {
    	this.imagePaths = imagePaths;
        this.hitSound = hitSound;
    }

	public String[] getImagePaths() {
		return imagePaths;
	}
    public SoundEffectEnum getHitSound(){
        return hitSound;
    }
    public static void preLoadImages() {
        ResourceManager resourceManager = ResourceManager.getInstance();
        for(RecyclableType r : RecyclableType.values()){
            for(String s : r.getImagePaths()){
                logger.debug("Trying to preload image: " + s);
                resourceManager.getImage(s);

            }
        }
    }

}
