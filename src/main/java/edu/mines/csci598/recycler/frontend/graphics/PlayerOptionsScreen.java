package edu.mines.csci598.recycler.frontend.graphics;

import edu.mines.csci598.recycler.backend.GameManager;
import edu.mines.csci598.recycler.frontend.Hand;
import edu.mines.csci598.recycler.frontend.PlayerHand;
import edu.mines.csci598.recycler.frontend.utils.PlayerMode;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PlayerOptionsScreen {

    private static final Logger logger = Logger.getLogger(GameScreen.class);
    private ArrayList<Sprite> handSprites;
    private java.util.List<Hand> hands;
    private ArrayList<TextSpritesHolder> textSpriteHolders;
    private long previousTime;
    private Sprite background;
    int countdown;

        public PlayerOptionsScreen(GameManager gameManager) {
            background = new Sprite("src/main/resources/SpriteImages/playerSelection.jpg",0,0);
            handSprites = new ArrayList<Sprite>();
            hands = new ArrayList<Hand>();

            Hand tempHand = new PlayerHand(gameManager, 0);
            hands.add(tempHand);
            addHandSprite(tempHand.getSprite());

            textSpriteHolders = new ArrayList<TextSpritesHolder>();

            TextSpritesHolder holder = new TextSpritesHolder() {
                @Override
                public List<TextSprite> getTextSprites() {
                    Font f = new Font("Stencil", Font.BOLD, 100);
                    Color c = Color.green;
                    List<TextSprite> sprites = new ArrayList<TextSprite>();
                    sprites.add(new TextSprite(Integer.toString(countdown), f, c, 70, 200));
                    return sprites;
                }
            };
            textSpriteHolders.add(holder);
            countdown = 6;
            previousTime = 0;
        }

        public void paint(Graphics2D g2d, Component canvas) {
            g2d.drawImage(background.getImage(), background.getX(), background.getY(), canvas);
            drawTextSprites(g2d);
            drawHands(g2d, canvas);
        }

    /**
     * Adds a hand sprite to the hands array
     * @param s
     */
    public void addHandSprite(Sprite s) {
        handSprites.add(s);
    }

    private void drawHands(Graphics2D g2d, Component canvas){
        // draws each hand as long as it's x position is greater than -1. The back end returns -1 when
        // a hand is not available.
        for (Sprite hand: handSprites) {
            if (hand.getX() > -1) {
                // Computer hand uses scaled x and y for use of drawing to align properly with recyclables which
                // are also drawn using scaling
                g2d.drawImage(hand.getImage(), hand.getX(), hand.getY(), canvas);
            }
        }
    }

    /**
     * Draws the text sprites that are held in a TextSpriteHolder
     * @param g
     */
    private void  drawTextSprites(Graphics2D g) {
        for(TextSpritesHolder holder : textSpriteHolders) {
            for(TextSprite textSprite : holder.getTextSprites()){
                g.setColor(textSprite.getColor());
                g.setFont(textSprite.getFont());
                int x = (int)Math.floor(textSprite.getX()*GraphicsConstants.SCALE_FACTOR);
                int y = (int)Math.floor(textSprite.getY()*GraphicsConstants.SCALE_FACTOR);
                g.drawString(textSprite.getMessage(), x, y);
            }
        }
    }

    public void updateThis() {
        for (Hand hand : hands) {
            hand.updateLocation();
        }

        if ((System.currentTimeMillis() / 1000) > previousTime + 1) {
            System.out.println("countdown: " + countdown);
            if (countdown >= 0)   {
                countdown -= 1;
                System.out.println("countdown: " + countdown);
            }
            previousTime = System.currentTimeMillis() / 1000;
        }
    }

    public boolean canGameStart() {
        return (countdown == 0);
    }

    public PlayerMode getPlayerMode() {
        if (hands.get(0).getX() < GraphicsConstants.GAME_SCREEN_WIDTH) {
            return PlayerMode.ONE_PLAYER;
        }
        else {
            return PlayerMode.TWO_PLAYER;
        }
    }

}
