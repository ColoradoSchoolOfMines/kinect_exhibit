package edu.mines.csci598.recycler.frontend.graphics;

import javax.swing.*;

/**
 * This class is the JFrame or the Window for the game. The actual JPanel we draw to is in GameScreen.
 * This holds that GameScreen.
 *
 * Created with IntelliJ IDEA.
 * User: jrramey11
 * Date: 10/27/12
 * Time: 11:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class Game extends JFrame implements GraphicsConstants{
    private GameScreen gameScreen;

    public Game() {
        gameScreen = new GameScreen();
        add(gameScreen);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        setTitle("Recycler");
        setResizable(false);
        setVisible(true);
    }

    /**
     * This main function is used by those working on the graphics to test things with the graphics.
     * @param args
     */
    public static void main( String[] args ){
        Game game = new Game();
        game.gameScreen.start();
    }

    public GameScreen getGameScreen(){
        return gameScreen;
    }

}
