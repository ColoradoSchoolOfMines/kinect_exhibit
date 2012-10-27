package edu.mines.csci598.recycler.frontend.graphics;

import javax.swing.*;
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
public class GameScreen extends JFrame{
    LinkedList<Displayable> drawableLinkedList;
    public static final int x = 800;
    public static final int y = 600;

    public GameScreen() {

        add(new Board());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(x, y);
        setLocationRelativeTo(null);
        setTitle("R - Type");
        setResizable(false);
        setVisible(true);
    }

}
