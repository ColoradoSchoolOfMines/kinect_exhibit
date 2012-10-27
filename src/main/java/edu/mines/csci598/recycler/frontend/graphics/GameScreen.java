package edu.mines.csci598.recycler.frontend.graphics;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * The GameScreen class is responsible for drawing the displayables with their updated time.
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

    public boolean addSprite(Sprite s){
        return sprites.add(s);
    }
    public boolean removeSprite(Sprite s){
        return sprites.remove(s);
    }
    public void update(double time ){
        for(Sprite sprite: sprites){
            sprite.updateLocation(time);
        }
        repaint();

    }
}
