package edu.mines.csci598.recycler.frontend.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: jrramey11
 * Date: 10/27/12
 * Time: 11:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class Game extends JFrame implements GraphicsConstants{

    public Game() {

        add(new GameScreen());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        setTitle("Recycler");
        setResizable(false);
        setVisible(true);
    }
    public static void main( String[] args ){
        new Game();
    }

}
