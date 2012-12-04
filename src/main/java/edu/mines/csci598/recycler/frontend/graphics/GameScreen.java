package edu.mines.csci598.recycler.frontend.graphics;

import org.apache.log4j.Logger;
import java.awt.*;
import java.awt.Component;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import edu.mines.csci598.recycler.frontend.GameConstants;

/**
 * The GameScreen class is responsible for drawing the sprites with their updated time.
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
    private ArrayList<TextSpritesHolder> textSpriteHolders;
    private LinkedList<Sprite> sprites;
    private ArrayList<Sprite> recycleBinSprites;
    private ArrayList<Sprite> handSprites;

    private GameScreen() {
        background1 = new Sprite("src/main/resources/SpriteImages/Backgrounds/ui_background_1.jpg", 0, 0);
        background2 = new Sprite("src/main/resources/SpriteImages/Backgrounds/ui_background_2.jpg", 0, 0);
        backgroundChutesAndFrame = new Sprite("src/main/resources/SpriteImages/Backgrounds/ui_frame.png", 0, 0);
        backgroundScoreFrame = new Sprite("src/main/resources/SpriteImages/Backgrounds/ui_score_frame.png", 0, 0);

        textSpriteHolders = new ArrayList<TextSpritesHolder>();
        handSprites = new ArrayList<Sprite>();
        sprites = new LinkedList<Sprite>();
        recycleBinSprites = new ArrayList<Sprite>();
    }

    public static final GameScreen getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameScreen();
        }
        return INSTANCE;
    }

    public void paint(Graphics2D g2d, Component canvas) {
        //TODO: Figure out which background to draw
        g2d.drawImage(background1.getImage(), background1.getX(), background1.getY(), canvas);

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

    public boolean removeSprite(Sprite s) {
        return sprites.remove(s);
    }

    public void addRecycleBinSprite(Sprite s) {
        recycleBinSprites.add(s);
    }

    public void removeRecycleBinSprite(Sprite s) {
        recycleBinSprites.remove(s);
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

    private void drawHands(Graphics2D g2d, Component canvas) {

        // draws each hand as long as it's x position is greater than -1. The back end returns -1 when
        // a hand is not available.
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
    }

}
