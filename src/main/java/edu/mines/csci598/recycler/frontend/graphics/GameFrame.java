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

//TODO: We might not need this class after merge with backend

public class GameFrame extends JFrame{
    private GameScreen gameScreen;
    private static GameFrame INSTANCE;

    private GameFrame() {
    	gameScreen = GameScreen.getInstance();
        //add(gameScreen);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(GraphicsConstants.GAME_SCREEN_WIDTH, GraphicsConstants.GAME_SCREEN_HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Recycler");
        setResizable(false);
        setVisible(true);
    }

    public static final GameFrame getInstance() {
    	if(INSTANCE == null){
    		INSTANCE = new GameFrame();
    	}
    	return INSTANCE;
    }

    /**
     * This main function is used by those working on the graphics to test things with the graphics.
     * @param args
     */
    public static void main(String[] args) {
        GameFrame game = GameFrame.getInstance();
        //game.gameScreen.start();
    }

    public GameScreen getGameScreen(){
        return gameScreen;
    }

}
