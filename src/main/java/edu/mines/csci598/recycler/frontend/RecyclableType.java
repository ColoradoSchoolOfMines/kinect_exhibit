package edu.mines.csci598.recycler.frontend;

public enum RecyclableType {
   
    PLASTIC(new String[]{"src/main/resources/SpriteImages/finalSpriteImages/plastic_01.png", "src/main/resources/SpriteImages/finalSpriteImages/plastic_02.png", "src/main/resources/SpriteImages/finalSpriteImages/plastic_03.png"}),
    PAPER(new String[]{"src/main/resources/SpriteImages/finalSpriteImages/paper_01.png", "src/main/resources/SpriteImages/finalSpriteImages/paper_02.png", "src/main/resources/SpriteImages/finalSpriteImages/paper_03.png", "src/main/resources/SpriteImages/finalSpriteImages/paper_04.png"}),
    GLASS(new String[]{"src/main/resources/SpriteImages/finalSpriteImages/glass_01.png", "src/main/resources/SpriteImages/finalSpriteImages/glass_02.png", "src/main/resources/SpriteImages/finalSpriteImages/glass_03.png", "src/main/resources/SpriteImages/finalSpriteImages/glass_04.png"}),
    TRASH(new String[]{"src/main/resources/SpriteImages/finalSpriteImages/trash_01.png", "src/main/resources/SpriteImages/finalSpriteImages/trash_02.png", "src/main/resources/SpriteImages/finalSpriteImages/trash_03.png"}),
    HAZARD(new String[]{"src/main/resources/SpriteImages/finalSpriteImages/hazard_01.png", "src/main/resources/SpriteImages/finalSpriteImages/hazard_02.png","src/main/resources/SpriteImages/finalSpriteImages/hazard_03.png"}),
    ANVIL(new String[]{"src/main/resources/SpriteImages/anvil.jpg"}),
    TURTLE(new String[]{"src/main/resources/SpriteImages/turtle.png"}),
    RABBIT(new String[]{"src/main/resources/SpriteImages/rabbit.png"}),
    WRONG(new String[]{"src/main/resources/SpriteImages/incorrect.png"}),
    RIGHT(new String[]{"src/main/resources/SpriteImages/correct.png"});

    private String[] imagePaths;
    
    private RecyclableType(String[] imagePaths){
    	this.imagePaths = imagePaths;
    }

	public String[] getImagePaths() {
		return imagePaths;
	}
}
