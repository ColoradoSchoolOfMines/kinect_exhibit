package edu.mines.csci598.recycler.frontend.graphics;

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
    LinkedList<Displayable> drawableLinkedList;
    Sprite s;
    Sprite background;
    LinkedList<Sprite> sprites = new LinkedList<Sprite>();
    Iterator it = sprites.iterator();
    public GameScreen() {

        setFocusable(true);
        setBackground(Color.RED);
        setDoubleBuffered(true);
        background = new Sprite("src/main/resources/SpriteImages/background.jpg", 0, 0, 1.0);
        s= new Sprite("src/main/resources/SpriteImages/glass.png", 0, screenHeight -200, 0.1);

    }


    public void paint(Graphics g) {
        super.paint(g);
        Toolkit.getDefaultToolkit().sync();
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(background.getImage(), background.getX(), background.getY(), this);
        g2d.drawImage(s.getImage(), s.getX(), s.getY(), this);
        for(Sprite sprite : sprites){
            g2d.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
        }
        g.dispose();
    }

    /**
     * Function only used to test some graphics features during development. Useless for the game will delete in future
     * when we know we don't need it any more.
     */
    public void start(){
        while(s.getX() < 700){
            s.setHorizontalVelocity(1);
            while(s.getX() <= 500){
                s.move();
                repaint();
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            s.setHorizontalVelocity(0);
            s.setVerticalVelocity(-1);
            while(s.getY() != 200){
                s.move();
                repaint();
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            s.setVerticalVelocity(0);
            s.setHorizontalVelocity(1);
            s.move();
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            System.out.println(s.getX());
        }

    }

    public void addSprite(Sprite s){
        try{
         sprites.addLast(s);
        }catch (ConcurrentModificationException e){}
    }
    public boolean removeSprite(Sprite s){
        return sprites.remove(s);
    }

    /**
     * Updates the game screen. You must pass in the time so it knows how far everything needs to move.
     * @param time
     */
    public void update(double time ){
        for(Sprite sprite: sprites){
            try{
                sprite.updateLocation(time);
                //if(sprite.getX()==700)removeSprite(sprite);
            }catch (ConcurrentModificationException e){

            }
        }
        repaint();

    }
}
