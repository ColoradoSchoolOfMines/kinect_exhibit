package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Coordinate;
import edu.mines.csci598.recycler.frontend.graphics.Line;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.motion.Movable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class keeps track of how many strikes the user has had and keeps a drawing on the screen.
 * <p/>
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/20/12
 * Time: 9:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class StrikeBar {

    private static final int MAX_STRIKES = 5;

    private static final int LEFT_STRIKE_BAR_X = 845;
    private static final int RIGHT_STRIKE_BAR_X = 975;
    private static final int STRIKE_BAR_Y_START = 360;
    private static final int STRIKE_BOX_Y_OFFSET = 100;

    private final double TRANSITION_SPEED = 1;

    private GameOver gameOver;

    private int strikes;
    private static Movable[] movables;
    private List<Coordinate> strikeBoxes;
    private GameStatusDisplay gameStatusDisplay;

    /**
     * The recyclable is the offending strike so it can be displayed in the bar
     *
     * @paramrecyclable
     */

    public StrikeBar(GameStatusDisplay gameStatusDisplay){
        this.gameStatusDisplay = gameStatusDisplay;
        gameOver = new GameOver(gameStatusDisplay.getSide());
        movables = new Recyclable[MAX_STRIKES];
        strikes = -1;
        strikeBoxes = new ArrayList<Coordinate>();

        int xStart;
        if(gameStatusDisplay.getSide() == Side.LEFT){
            xStart = LEFT_STRIKE_BAR_X;
        }
        else {
            xStart = RIGHT_STRIKE_BAR_X;
        }
        for (int i = MAX_STRIKES; i > 0; --i) {
            strikeBoxes.add(new Coordinate(xStart, STRIKE_BAR_Y_START + i * STRIKE_BOX_Y_OFFSET));
        }
    }

    public void addStrike(Movable strike) {

        if (strikes > MAX_STRIKES) { //Still playing when game over, so don't add more strikes
            strike.setRemovable(true);
        }
        else if (strikes < MAX_STRIKES){
            strikes++;
            movables[strikes] = strike;
            Path p = movables[strikes].getPath();
            p.addLine(new Line(movables[strikes].getPosition(), strikeBoxes.get(strikes), TRANSITION_SPEED));
            movables[strikes].setPath(p);
        }
        else {
            strikes++;
            gameOver.setGameOver(gameStatusDisplay);
        }
    }

    /**
     * Removes strike from strike bar, generally used for powerups
     */
    public void removeStrike() {
        System.out.println("Remove length: " + strikes);
        if((strikes > 0) && (strikes < MAX_STRIKES)){

            movables[strikes].setRemovable(true);
            movables[strikes] = null;
            strikes--;

        }

    }

}
