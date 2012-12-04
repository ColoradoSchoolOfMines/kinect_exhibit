package edu.mines.csci598.recycler.frontend.items;

import org.apache.log4j.Logger;

import edu.mines.csci598.recycler.frontend.graphics.ResourceManager;

public enum RecyclableType {

    PLASTIC(new String[]{"src/main/resources/SpriteImages/Items/plastic_01.png", "src/main/resources/SpriteImages/Items/plastic_02.png", "src/main/resources/SpriteImages/Items/plastic_03.png"}),
    PAPER(new String[]{"src/main/resources/SpriteImages/Items/paper_01.png", "src/main/resources/SpriteImages/Items/paper_02.png", "src/main/resources/SpriteImages/Items/paper_03.png", "src/main/resources/SpriteImages/Items/paper_04.png"}),
    GLASS(new String[]{"src/main/resources/SpriteImages/Items/glass_01.png", "src/main/resources/SpriteImages/Items/glass_02.png", "src/main/resources/SpriteImages/Items/glass_03.png", "src/main/resources/SpriteImages/Items/glass_04.png"}),
    HAZARD(new String[]{"src/main/resources/SpriteImages/Items/hazard_01.png", "src/main/resources/SpriteImages/Items/hazard_02.png","src/main/resources/SpriteImages/Items/hazard_03.png"}),
    TRASH(new String[]{"src/main/resources/SpriteImages/Items/trash_01.png", "src/main/resources/SpriteImages/Items/trash_02.png", "src/main/resources/SpriteImages/Items/trash_03.png"});

    private String[] imagePaths;
    private static final Logger logger = Logger.getLogger(RecyclableType.class);

    private RecyclableType(String[] imagePaths) {
    	this.imagePaths = imagePaths;
    }

	public String[] getImagePaths() {
		return imagePaths;
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
