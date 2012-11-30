package edu.mines.csci598.recycler.frontend.graphics;

import edu.mines.csci598.recycler.frontend.RecycleBin;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

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
    private Sprite background;
    private Sprite backgroundChutes;
    private Sprite backgroundScoreFrame;
    private Sprite backgroundFrame;
    private ArrayList<TextSpritesHolder> textSpriteHolders;
    private LinkedList<Sprite> sprites;
    private ArrayList<Sprite> recycleBinSprites;
    private ArrayList<Sprite> handSprites;
    private double scaledWidth;
    private double scaledHeight;

    private GameScreen() {
        background = new Sprite("src/main/resources/SpriteImages/FinalSpriteImages/ui_background.jpg", 0, 0);
        backgroundChutes = new Sprite("src/main/resources/SpriteImages/FinalSpriteImages/ui_chutes.png", 0, 0);
        backgroundScoreFrame = new Sprite("src/main/resources/SpriteImages/FinalSpriteImages/ui_score_frame.png", 0, 0);
        backgroundFrame = new Sprite("src/main/resources/SpriteImages/FinalSpriteImages/ui_frame.png", 0, 0);

        scaledWidth = GraphicsConstants.GAME_SCREEN_WIDTH * GraphicsConstants.SCALE_FACTOR;
        scaledHeight = GraphicsConstants.GAME_SCREEN_HEIGHT * GraphicsConstants.SCALE_FACTOR;
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
        g2d.drawImage(background.getImage(), background.getX(), background.getY(), canvas);
        g2d.drawImage(backgroundScoreFrame.getImage(), backgroundScoreFrame.getX(), backgroundScoreFrame.getY(), canvas);

        for (Sprite bin : recycleBinSprites) {
            g2d.drawImage(bin.getImage(), bin.getScaledX(), bin.getScaledY(), canvas);
        }

        for (Sprite sprite : sprites) {
            g2d.drawImage(sprite.getImage(), sprite.getScaledX(), sprite.getScaledY(), canvas);
        }

        g2d.drawImage(backgroundChutes.getImage(), backgroundChutes.getX(), backgroundChutes.getY(), canvas);
        g2d.drawImage(backgroundFrame.getImage(), backgroundFrame.getX(), backgroundFrame.getY(), canvas);

        drawHands(g2d, canvas);
        drawTextSprites(g2d);
    }

    /**
     * Adds a sprite
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
     * @param s
     */
    public void addHandSprite(Sprite s) {
        handSprites.add(s);
    }

    public boolean addTextSpriteHolder(TextSpritesHolder textSpritesHolder){
        return textSpriteHolders.add(textSpritesHolder);
    }

    private void drawHands(Graphics2D g2d, Component canvas){

            // draws each hand as long as it's x position is greater than -1. The back end returns -1 when
            // a hand is not available.
            for (Sprite hand: handSprites) {
                if (hand.getX() > -1) {
                    // Computer hand uses scaled x and y for use of drawing to align properly with recyclables which
                    // are also drawn using scaling
                    if(GameConstants.SECOND_PLAYER_IS_A_COMPUTER){
                        if(hand.getX()<1000)
                            g2d.drawImage(hand.getImage(), hand.getX(), hand.getY(), canvas);
                        else
                            g2d.drawImage(hand.getImage(), hand.getScaledX(), hand.getScaledY(), canvas);
                    } else {
                        g2d.drawImage(hand.getImage(), hand.getX(), hand.getY(), canvas);
                    }
                }
            }

    }

    /**
     * Draws the text sprites that are held in a TextSpriteHolder
     * @param g
     */
    private void  drawTextSprites(Graphics2D g){
        for(TextSpritesHolder holder : textSpriteHolders){
            for(TextSprite textSprite : holder.getTextSprites()){
                g.setColor(textSprite.getColor());
                g.setFont(textSprite.getFont());
                int x = (int)Math.floor(textSprite.getX()*GraphicsConstants.SCALE_FACTOR);
                int y = (int)Math.floor(textSprite.getY()*GraphicsConstants.SCALE_FACTOR);
                g.drawString(textSprite.getMessage(), x,y);
            }
        }
    }

}
