package edu.mines.csci598.recycler.frontend.graphics;

import edu.mines.csci598.recycler.frontend.Hand;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;
import edu.mines.csci598.recycler.frontend.utils.Log;

import javax.swing.*;
import java.awt.*;
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
public class GameScreen extends JPanel implements GraphicsConstants{
	private static GameScreen INSTANCE;
    private LinkedList<Displayable> drawableLinkedList;
    private Sprite s;
    private Sprite background;
    private LinkedList<Sprite> sprites = new LinkedList<Sprite>();
    private LinkedList<Sprite> spritesToRemove = new LinkedList<Sprite>();
    private Iterator it = sprites.iterator();
    public Hand hand;

    private GameScreen() {
        setFocusable(true);
        setBackground(Color.RED);
        setDoubleBuffered(true);
        background = new Sprite("src/main/resources/SpriteImages/background.jpg", 0, 0, 1.0);
        s= new Sprite("src/main/resources/SpriteImages/glass.png", 0, screenHeight -200, 0.1);
        
        // TODO I think this should be a temporary hack
        hand = new Hand();
        addMouseMotionListener(hand);
    }
    
    public static final GameScreen getInstance()
    {
    	if(INSTANCE == null){
    		INSTANCE = new GameScreen();
    	}
    	return INSTANCE;
    }


    public synchronized void paint(Graphics g) {
        super.paint(g);
        Toolkit.getDefaultToolkit().sync();
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(background.getImage(), background.getX(), background.getY(), this);
        //g2d.drawImage(s.getImage(), s.getX(), s.getY(), this);
        for(Sprite sprite : sprites){
        	try{
        		g2d.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
        	}
        	catch(ConcurrentModificationException e){
        		Log.logError("Trying to draw sprite: " + s);
        	}
        }
        g.dispose();
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

}
