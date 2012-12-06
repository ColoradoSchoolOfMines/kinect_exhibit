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

    private static final String GAME_OVER_LEFT = "src/main/resources/SpriteImages/game_over_p1.png";
    private static final String GAME_OVER_RIGHT = "src/main/resources/SpriteImages/game_over_p2.png";

    private GameScreen gameScreen;
    private Sprite s;
    private boolean displayed;

    /**
     * Creates a new game over object based on the Side object.
     * @param side
     */
    public GameOver(Side side) {
        displayed = false;
        gameScreen = GameScreen.getInstance();
        if (side == Side.LEFT) {
            s = new Sprite(GAME_OVER_LEFT, 0, 0);
        } else {
            s = new Sprite(GAME_OVER_RIGHT, 0, 0);
        }
    }

    /**
     * Signalas the game over state of the entire game for a particular player.
     * @param gameStatusDisplay
     */
    public void setGameOver(GameStatusDisplay gameStatusDisplay) {
        if (!displayed) {
            gameStatusDisplay.setGameOverState(true);
            gameScreen.addGameOverSprite(s);
            displayed = true;
        }
    }
}
