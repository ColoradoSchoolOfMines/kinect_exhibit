package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Coordinate;
import edu.mines.csci598.recycler.frontend.graphics.Line;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.motion.Movable;
import org.apache.log4j.Logger;

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

    private static final Logger logger = Logger.getLogger(StrikeBar.class);

    private static final int MAX_STRIKES = 10;

    private static final int LEFT_STRIKE_BAR_X = 845;
    private static final int RIGHT_STRIKE_BAR_X = 975;
    private static final int STRIKE_BAR_Y_START = 260;
    private static final int STRIKE_BOX_Y_OFFSET = 100;

    private final double TRANSITION_SPEED = 1;

    private GameOver gameOver;

    private int strikes;
    private List<Movable> movables;
    private List<Coordinate> strikeBoxes;
    private GameStatusDisplay gameStatusDisplay;

    /**
     * Creates a new StrikeBar
     * @param gameStatusDisplay
     */
    public StrikeBar(GameStatusDisplay gameStatusDisplay) {
        this.gameStatusDisplay = gameStatusDisplay;
        gameOver = new GameOver(gameStatusDisplay.getSide());
        movables = new ArrayList<Movable>();
        strikes = 0;
        strikeBoxes = new ArrayList<Coordinate>();

        int xStart;
        if(gameStatusDisplay.getSide() == Side.LEFT) {
            xStart = LEFT_STRIKE_BAR_X;
        }
        else {
            xStart = RIGHT_STRIKE_BAR_X;
        }
        for (int i = MAX_STRIKES; i > 0; i--) {
            if(i%2 == 0){
                strikeBoxes.add(new Coordinate(xStart, STRIKE_BAR_Y_START + (i/2) * STRIKE_BOX_Y_OFFSET));
            }else{
                strikeBoxes.add(new Coordinate(xStart, STRIKE_BAR_Y_START +((i+1)/2) * STRIKE_BOX_Y_OFFSET));
            }
        }
    }

    /**
     * Adds a strike to the strike bar.
     * If it fills the strike bar, ends the game for the current player.
     * @param images
     * @return
     */
    public boolean addStrike(Movable[] images) {

        if (strikes >= MAX_STRIKES) { //Still playing when game over, so don't add more strikes
            for(int i=0; i<images.length; i++){
                Path p = images[i].getPath();
                p.addLine(new Line(images[i].getPosition(), images[i].getPosition(), 0.8));
                images[i].setPath(p);
                images[i].setRemovable(true);
            }
            return true;
        }
        else if (strikes < MAX_STRIKES) {
            for(int i=0; i<images.length; i++){
                movables.add(strikes, images[i]);
                Path p = movables.get(strikes).getPath();
                p.addLine(new Line(movables.get(strikes).getPosition(), strikeBoxes.get(strikes), TRANSITION_SPEED));
                movables.get(strikes).setPath(p);
                strikes++;
                if (strikes == MAX_STRIKES) {
                    gameOver.setGameOver(gameStatusDisplay);
                    return true;
                }

            }

        }

        return false;
    }

    /**
     * Removes strike from strike bar, generally used for powerups.
     */
    public void removeStrike() {
        logger.debug("Remove length: " + strikes);
        if((strikes > 0) && (strikes < MAX_STRIKES)){
            for(int i=0; i<2; i++){
                strikes--;
                movables.get(strikes).setRemovable(true);
                movables.remove(strikes);
            }

        }
    }

}
