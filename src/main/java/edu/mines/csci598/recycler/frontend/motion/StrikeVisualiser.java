package edu.mines.csci598.recycler.frontend.motion;

import edu.mines.csci598.recycler.frontend.GameLogic;
import edu.mines.csci598.recycler.frontend.graphics.GameScreen;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;

/**
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 11/23/12
 * Time: 9:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class StrikeVisualiser extends ItemMover {
    public StrikeVisualiser(GameLogic game, GameScreen gameScreen, Path path) {
        super(GameConstants.INITIAL_SPEED_IN_PIXELS_PER_SECOND);

    }
}
