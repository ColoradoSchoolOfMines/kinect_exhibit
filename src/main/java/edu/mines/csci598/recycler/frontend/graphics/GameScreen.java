package edu.mines.csci598.recycler.frontend.graphics;

import org.apache.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * The GameScreen singleton class is responsible for drawing the sprites.
 * Generally to a sprite to be drawn call the addSprite method. To get it to stop
 * drawing the sprites remove the sprite using removeSprite()
 *
 * <p/>
 * <p/>
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/20/12
 * Time: 9:13 PM
 */
public class GameScreen {

    private static final Logger logger = Logger.getLogger(GameScreen.class);

    private static GameScreen INSTANCE;
    private Sprite background1;
    private Sprite background2;
    private Sprite backgroundChutesAndFrame;
    private Sprite backgroundScoreFrame;
    private Sprite backgroundToDraw;
    private ArrayList<TextSpritesHolder> textSpriteHolders;
    private LinkedList<Sprite> sprites;
    private ArrayList<Sprite> recycleBinSprites;
    private ArrayList<Sprite> handSprites;
    private ArrayList<Sprite> gameOverSprites;

    private GameScreen() {

        background1 = new Sprite("src/main/resources/SpriteImages/Backgrounds/ui_background_1.jpg", 0, 0);
        background2 = new Sprite("src/main/resources/SpriteImages/Backgrounds/ui_background_2.jpg", 0, 0);
        backgroundToDraw = background1;
        backgroundChutesAndFrame = new Sprite("src/main/resources/SpriteImages/Backgrounds/ui_frame.png", 0, 0);
        backgroundScoreFrame = new Sprite("src/main/resources/SpriteImages/Backgrounds/ui_score_frame.png", 0, 0);

        textSpriteHolders = new ArrayList<TextSpritesHolder>();
        handSprites = new ArrayList<Sprite>();
        sprites = new LinkedList<Sprite>();
        recycleBinSprites = new ArrayList<Sprite>();
        gameOverSprites = new ArrayList<Sprite>();

        //Just a simple thread that toggles between the background images
        // so it feels more like the bars on the conveyor are turning.
        Thread backgroundSwitcher = new Thread("Background Switcher"){
          public void run(){
              while(true){
                  int sleepTime = 300;
                  synchronized (backgroundToDraw){
                      backgroundToDraw = background1;
                  }
                  try {
                      Thread.sleep(sleepTime);
                  } catch (InterruptedException e) {
                      //ignore
                  }
                  synchronized (backgroundToDraw){
                      backgroundToDraw = background2;
                  }
                  try {
                      Thread.sleep(sleepTime);
                  } catch (InterruptedException e) {
                      //ignore
                  }
              }
          }
        };
        backgroundSwitcher.start();
    }

    public static final GameScreen getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameScreen();
        }
        return INSTANCE;
    }

    public void paint(Graphics2D g2d, Component canvas) {
        //TODO: Figure out which background to draw
        synchronized (backgroundToDraw) {
            g2d.drawImage(backgroundToDraw.getImage(), backgroundToDraw.getX(), backgroundToDraw.getY(), canvas);
        }

        for (Sprite bin : recycleBinSprites) {
            g2d.drawImage(bin.getImage(), bin.getScaledX(), bin.getScaledY(), canvas);
        }

        g2d.drawImage(backgroundScoreFrame.getImage(), backgroundScoreFrame.getX(), backgroundScoreFrame.getY(), canvas);

        for (Sprite sprite : sprites) {
            int offset = (int) (GraphicsConstants.SCALE_FACTOR * 50);
            g2d.rotate(sprite.getPosition().getRotation(), sprite.getScaledX() + offset, sprite.getScaledY() + offset);
            g2d.drawImage(sprite.getImage(), sprite.getScaledX(), sprite.getScaledY(), canvas);
            g2d.rotate(-1.0 * sprite.getPosition().getRotation(), sprite.getScaledX() + offset, sprite.getScaledY() + offset);
        }

        for(Sprite sprites: gameOverSprites){
            g2d.drawImage(sprites.getImage(), sprites.getScaledX(), sprites.getScaledY(), canvas);
        }

        g2d.drawImage(backgroundChutesAndFrame.getImage(), backgroundChutesAndFrame.getX(), backgroundChutesAndFrame.getY(), canvas);

        drawHands(g2d, canvas);
        drawTextSprites(g2d);
    }

    /**
     * Adds a sprite
     *
     * @param s - The sprite to add
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    public void removeHandSprites() {
        handSprites.clear();
    }

    public boolean removeSprite(Sprite s) {
        return sprites.remove(s);
    }

    public void addRecycleBinSprite(Sprite s) {
        recycleBinSprites.add(s);
    }

    public void removeRecycleBinSprite(Sprite s) {
        recycleBinSprites.remove(s);
    }

    public void addGameOverSprite(Sprite s){
        gameOverSprites.add(s);
    }



    /**
     * Adds a hand sprite to the hands array
     *
     * @param s
     */
    public void addHandSprite(Sprite s) {
        handSprites.add(s);
    }

    public boolean addTextSpriteHolder(TextSpritesHolder textSpritesHolder) {
        return textSpriteHolders.add(textSpritesHolder);
    }


    /**
     *
     * draws each hand as long as it's x position is greater than -1. The back end returns -1 when
     * a hand is not available.
     * @param g2d
     * @param canvas
     */
    private void drawHands(Graphics2D g2d, Component canvas) {
        for (Sprite hand : handSprites) {
            //compensate for current scale
            int x = (int) Math.round(hand.getX() * GraphicsConstants.SCALE_FACTOR);
            int y = (int) Math.round(hand.getY() * GraphicsConstants.SCALE_FACTOR);
            //If it is negative one it is a sentinel for it not existing so ignore.
            if (hand.getX() > -1) {
                g2d.drawImage(hand.getImage(), x, y, canvas);
            }
        }


    }

    /**
     * Draws the text sprites that are held in a TextSpriteHolder
     *
     * @param g
     */
    private void drawTextSprites(Graphics2D g) {
        for (TextSpritesHolder holder : textSpriteHolders) {
            for (TextSprite textSprite : holder.getTextSprites()) {
                g.setColor(textSprite.getColor());
                g.setFont(textSprite.getFont());
                int x = (int) Math.floor(textSprite.getX() * GraphicsConstants.SCALE_FACTOR);
                int y = (int) Math.floor(textSprite.getY() * GraphicsConstants.SCALE_FACTOR);
                g.drawString(textSprite.getMessage(), x, y);
            }
        }
    }

    /**
     * Preloads all of the gameScreen images so that they are ready to be used
     */
    public void preLoadImages() {
        background1.getImage();
        background2.getImage();
        backgroundChutesAndFrame.getImage();
        backgroundScoreFrame.getImage();

        for (Sprite bin : recycleBinSprites) {
            bin.getImage();
        }

        //TODO: Preload other recycle bins better

        String[] imagesToLoad =
                {"src/main/resources/SpriteImages/Bins/left_bin_paper_half.png",
                "src/main/resources/SpriteImages/Bins/left_bin_paper_full.png",
                "src/main/resources/SpriteImages/Bins/left_bin_plastic_half.png",
                "src/main/resources/SpriteImages/Bins/left_bin_plastic_full.png",
                "src/main/resources/SpriteImages/Bins/left_bin_hazard_half.png",
                "src/main/resources/SpriteImages/Bins/left_bin_hazard_full.png",
                "src/main/resources/SpriteImages/Bins/left_bin_glass_half.png",
                "src/main/resources/SpriteImages/Bins/left_bin_glass_full.png",

                "src/main/resources/SpriteImages/Bins/right_bin_paper_half.png",
                "src/main/resources/SpriteImages/Bins/right_bin_paper_full.png",
                "src/main/resources/SpriteImages/Bins/right_bin_plastic_half.png",
                "src/main/resources/SpriteImages/Bins/right_bin_plastic_full.png",
                "src/main/resources/SpriteImages/Bins/right_bin_hazard_half.png",
                "src/main/resources/SpriteImages/Bins/right_bin_hazard_full.png",
                "src/main/resources/SpriteImages/Bins/right_bin_glass_half.png",
                "src/main/resources/SpriteImages/Bins/right_bin_glass_full.png"};
        for(String s : imagesToLoad){
            ResourceManager.getInstance().getImage(s);
        }
    }

}
