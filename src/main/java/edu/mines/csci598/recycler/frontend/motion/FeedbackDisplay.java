package edu.mines.csci598.recycler.frontend.motion;

import edu.mines.csci598.recycler.frontend.graphics.Coordinate;
import edu.mines.csci598.recycler.frontend.graphics.GameScreen;
import edu.mines.csci598.recycler.frontend.graphics.Line;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.items.BinFeedback;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * This class is an easy way to show a checkmark or x just pass in the
 * User: jzeimen
 * Date: 11/23/12
 * Time: 11:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedbackDisplay extends ItemMover {
    private static final Logger logger = Logger.getLogger(FeedbackDisplay.class);

    private GameScreen gameScreen;
    private static final String INCORRECT_SPRITE = "src/main/resources/SpriteImages/incorrect.png";
    private static final String CORRECT_SPRITE = "src/main/resources/SpriteImages/correct.png";


    public FeedbackDisplay(double initialSpeed) {
        super(initialSpeed);
        gameScreen = GameScreen.getInstance();
    }

    public Movable makeDisplay(Coordinate c, double currentTimeSec, boolean isCorrect){
        Path p = new Path(currentTimeSec);
        p.addLine(new Line(c,c,0.5));

        BinFeedback feedback;
        if (isCorrect) {
            feedback = new BinFeedback(CORRECT_SPRITE, p);
            feedback.setRemovable(true);
        }
        else {
            feedback = new BinFeedback(INCORRECT_SPRITE, p);
            feedback.setRemovable(false);
        }
        movables.add(feedback);
        gameScreen.addSprite(feedback.getSprite());
        return feedback;
    }


    @Override
    public void moveItems(double currentTimeSec){
        super.moveItems(currentTimeSec);
        List<Movable> toRemove = releaseControlOfMovablesAtEndOfPath(currentTimeSec);
        for(Movable m : toRemove){
            if (m.isRemovable()){
                gameScreen.removeSprite(m.getSprite());
            }
        }
    }


}