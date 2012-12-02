package edu.mines.csci598.recycler.frontend.motion;

import edu.mines.csci598.recycler.BinFeedback;
import edu.mines.csci598.recycler.frontend.Recyclable;
import edu.mines.csci598.recycler.frontend.StrikeBar;
import edu.mines.csci598.recycler.frontend.graphics.*;

import java.util.List;

/**
 * This class is an easy way to show a checkmark or x just pass in the
 * User: jzeimen
 * Date: 11/23/12
 * Time: 11:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeedbackDisplay extends ItemMover {

    private GameScreen gameScreen;
    private static Sprite incorrectSprite;
    private static Sprite correctSprite;


    public FeedbackDisplay(double initialSpeed) {
        super(initialSpeed);
        incorrectSprite = new Sprite("src/main/resources/SpriteImages/FinalSpriteImages/incorrect.png", 0, 0);
        correctSprite = new Sprite("src/main/resources/SpriteImages/FinalSpriteImages/correct.png", 0, 0);
        gameScreen = GameScreen.getInstance();
    }

    public Movable makeDisplay(Coordinate c, double currentTimeSec, boolean isCorrect){
        Path p = new Path(currentTimeSec);
        p.addLine(new Line(c,c,0.5));

        BinFeedback feedback;
        if (isCorrect) {
            feedback = new BinFeedback(correctSprite, p);
        }
        else {
            feedback = new BinFeedback(incorrectSprite, p);
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