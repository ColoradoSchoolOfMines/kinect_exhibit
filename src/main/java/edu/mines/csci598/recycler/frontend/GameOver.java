package edu.mines.csci598.recycler.frontend;

import org.apache.log4j.Logger;

import edu.mines.csci598.recycler.frontend.graphics.GameScreen;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: jrramey11
 * Date: 11/30/12
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameOver {
    private static final Logger logger = Logger.getLogger(GameOver.class);

    private boolean signalGameOver;
    private GameScreen gameScreen;
    Sprite s;
    private boolean displayed;

    public GameOver(Side side) {
        displayed = false;
        gameScreen = GameScreen.getInstance();
        signalGameOver = false;
        if (side == Side.LEFT) {
            s = new Sprite("src/main/resources/SpriteImages/game_over_text.png", 100, 300);
        } else {
            s = new Sprite("src/main/resources/SpriteImages/game_over_text.png", 1200, 300);
        }
    }

    public void setGameOver(GameStatusDisplay gameStatusDisplay) {
        if (!displayed) {
            gameStatusDisplay.setGameOverState(true);
            gameScreen.addSprite(s);
            displayed = true;
        }
    }

    private void signalSplashScreen() {
    }

}
