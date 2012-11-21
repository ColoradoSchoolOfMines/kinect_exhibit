package edu.mines.csci598.recycler.frontend.graphics;

import edu.mines.csci598.recycler.frontend.Hand;
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
    private Sprite backgroundBottom;
    private Sprite backgroundTop;
    //private Sprite backgroundLeft;
    //private Sprite backgroundRight;
    private LinkedList<Sprite> sprites = new LinkedList<Sprite>();
    private ArrayList<Sprite> handSprites;
    private double scaledWidth;
    private double scaledHeight;

    private GameScreen() {
        backgroundBottom = new Sprite("src/main/resources/SpriteImages/FinalSpriteImages/background_doubled.jpg",0,0);
        backgroundTop = new Sprite("src/main/resources/SpriteImages/FinalSpriteImages/ui_Frame.png",0,0);
        //backgroundLeft = new Sprite("src/main/resources/SpriteImages/background_b.png", 0, 0);
        //backgroundRight = new Sprite("src/main/resources/SpriteImages/background_b_R.png", GraphicsConstants.GAME_SCREEN_WIDTH, 0);
        scaledWidth = GraphicsConstants.GAME_SCREEN_WIDTH * GraphicsConstants.SCALE_FACTOR;
        scaledHeight = GraphicsConstants.GAME_SCREEN_HEIGHT * GraphicsConstants.SCALE_FACTOR;

        handSprites = new ArrayList<Sprite>();
    }

    public static final GameScreen getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameScreen();
        }
        return INSTANCE;
    }

    public void paint(Graphics2D g2d, Component canvas) {
        g2d.drawImage(backgroundBottom.getImage(), backgroundBottom.getX(), backgroundBottom.getY(), canvas);
       // g2d.drawImage(backgroundRight.getImage(), backgroundRight.getX(), backgroundRight.getY(), canvas);


        g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g2d.setColor(Color.green);
        g2d.drawString("SCORE: ", 0, 20);
        //g2d.drawString(GameLogic.getInstance().getScoreString(), 100, 20);

        g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g2d.setColor(Color.red);
        g2d.drawString("STRIKES: ", 130, 20);
        //g2d.drawString(GameLogic.getInstance().getStrikesString(), 220, 20);

        for (Sprite sprite : sprites) {
                g2d.drawImage(sprite.getImage(), sprite.getScaledX(), sprite.getScaledY(), canvas);
        }

        g2d.drawImage(backgroundTop.getImage(), backgroundTop.getX(), backgroundTop.getY(), canvas);

        if(GameConstants.SECOND_PLAYER_IS_A_COMPUTER) {
         //   g2d.drawImage(player1PrimaryHand.getImage(), player1PrimaryHand.getScaledX(), player1PrimaryHand.getScaledY(), canvas);
            // draws 2 hands for the first player if hands are available - not sure if this is correct!
            for (int i = 0; i < 2; i++) {
                if (handSprites.get(i).getX() > -1) {
                g2d.drawImage(handSprites.get(i).getImage(), handSprites.get(i).getScaledX(), handSprites.get(i).getScaledY(), canvas);
                }
            }
        }
        else {
            // draws each hand as long as it's x position is greater than -1. The back end returns -1 when
            // a hand is not available.
            for (Sprite hand: handSprites) {
                if (hand.getX() > -1) {
                    g2d.drawImage(hand.getImage(), hand.getX(), hand.getY(), canvas);
                }
            }
        }
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

    /**
     * Adds a hand sprite to the hands array
     * @param s
     */
    public void addHandSprite(Sprite s) {
        handSprites.add(s);
    }

}
