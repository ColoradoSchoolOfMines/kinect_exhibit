package edu.mines.csci598.recycler.frontend.graphics;

import edu.mines.csci598.recycler.frontend.Hand;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;
import edu.mines.csci598.recycler.frontend.utils.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * The GameScreen class is responsible for drawing the sprites with their updated time.
 *
 *
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/20/12
 * Time: 9:13 PM
 */
public class GameScreen {
	private static GameScreen INSTANCE;
    private LinkedList<Displayable> drawableLinkedList;
    private Sprite s;
    private Sprite background;
    private Sprite player1PrimaryHand;
    private LinkedList<Sprite> sprites = new LinkedList<Sprite>();
    private LinkedList<Sprite> spritesToRemove = new LinkedList<Sprite>();
    private Iterator it = sprites.iterator();

    private GameScreen() {
        background = new Sprite("src/main/resources/SpriteImages/background.png", 0, 0);
        s = new Sprite("src/main/resources/SpriteImages/glass.png", 0, GraphicsConstants.GAME_SCREEN_HEIGHT -200);
    }

    public static final GameScreen getInstance()
    {
    	if(INSTANCE == null){
    		INSTANCE = new GameScreen();
    	}
    	return INSTANCE;
    }


    public synchronized void paint(Graphics2D g2d, Component canvas) {

        g2d.drawImage(background.getImage(), background.getX(), background.getY(), canvas);
        g2d.drawImage(player1PrimaryHand.getImage(), player1PrimaryHand.getX(), player1PrimaryHand.getY(), canvas);

        for(Sprite sprite : sprites){
        	try{
        		g2d.drawImage(sprite.getImage(), sprite.getScaledX(), sprite.getScaledY(), canvas);
        	}
        	catch(ConcurrentModificationException e){
        		Log.logError("Trying to draw sprite: " + s);
        	}
        }
    }
    public synchronized void addSprite(Sprite s){
        try{
         s.setState(GameConstants.UNTOUCHABLE);
         sprites.addLast(s);
        }catch (ConcurrentModificationException e){Log.logError("Trying to add sprite " + s);}
    }

    public synchronized boolean removeSprite(Sprite s){
        return sprites.remove(s);
    }

    /**
     * @param flag
     * Not sure we need now that functions are synchronized.
     * I was attempting to funnel all sprite edits through this function,
     * to keep from having the ConcurrentAccessException occur.
     */
    public synchronized void handleSprite(int flag, Sprite s, double time){
        if(flag == GameConstants.ADD_SPRITE) addSprite(s);
        else if (flag == GameConstants.REMOVE_SPRITE) removeSprite(s);
    }

    public void addHandSprite(Sprite s) {
        player1PrimaryHand = s;
    }

}
