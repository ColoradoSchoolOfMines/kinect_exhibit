package edu.mines.csci598.recycler.frontend.motion;

import edu.mines.csci598.recycler.frontend.graphics.GameScreen;
import edu.mines.csci598.recycler.frontend.graphics.Line;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.items.BinFeedback;
import org.apache.log4j.Logger;

import java.util.List;

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
    private static final String INCORRECT_SPRITE = "src/main/resources/SpriteImages/FinalSpriteImages/incorrect.png";
    private static final String CORRECT_SPRITE = "src/main/resources/SpriteImages/FinalSpriteImages/correct.png";


    public FeedbackDisplay(double initialSpeed) {
        super(initialSpeed);
        gameScreen = GameScreen.getInstance();
    }

    public Movable makeDisplay(Movable m, double currentTimeSec, boolean isCorrect){
        Path p = new Path(currentTimeSec);
        p.addLine(new Line(m.getPosition(),m.getPosition(),0.8));

        BinFeedback feedback;
        if (isCorrect) {
            feedback = new BinFeedback(CORRECT_SPRITE, p);
            feedback.setRemovable(true);
        }
        else {
            feedback = new BinFeedback(INCORRECT_SPRITE, p);
            feedback.setRemovable(true);
            m.setRemovable(false);
        }
        movables.add(feedback);
        movables.add(m);
        gameScreen.addSprite(feedback.getSprite());
        gameScreen.addSprite(m.getSprite());
        return m;
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