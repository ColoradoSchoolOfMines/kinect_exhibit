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
    public GameScreen() {

        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        background = new Sprite("src/main/resources/SpriteImages/background.jpg");
        s= new Sprite("src/main/resources/SpriteImages/default.jpg");


    }

    public void paint(Graphics g) {
        super.paint(g);
        Toolkit.getDefaultToolkit().sync();
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(background.getImage(), 0, 0, this);
        g2d.drawImage(s.getImage(),5,5, this);
        g.dispose();
    }


}
