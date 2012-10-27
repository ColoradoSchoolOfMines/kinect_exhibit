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

    public GameScreen() {

        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

    }

    public void paint(Graphics g) {
        super.paint(g);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }


}
