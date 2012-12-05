package edu.mines.csci598.recycler.frontend.motion;

import edu.mines.csci598.recycler.frontend.graphics.GameScreen;
import edu.mines.csci598.recycler.frontend.graphics.Line;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.graphics.ResourceManager;
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
    private static final String INCORRECT_SPRITE = "src/main/resources/SpriteImages/incorrect.png";
    private static final String CORRECT_SPRITE = "src/main/resources/SpriteImages/correct.png";


    public FeedbackDisplay(double initialSpeed) {
        gameScreen = GameScreen.getInstance();
    }

    public Movable[] makeDisplay(Movable movableRecyclable, double currentTimeSec, boolean isCorrect){
        Movable[] movableArray = new Movable[2];
        Path p = new Path(currentTimeSec);


        BinFeedback feedback;
        if (isCorrect) {
            p.addLine(new Line(movableRecyclable.getPosition(),movableRecyclable.getPosition(),0.8));
            feedback = new BinFeedback(CORRECT_SPRITE, p);
            feedback.setRemovable(true);
        }
        else {
            p.addLine(new Line(movableRecyclable.getPosition(),movableRecyclable.getPosition(),0.0));
            feedback = new BinFeedback(INCORRECT_SPRITE, p);
            feedback.setRemovable(false);
            movableRecyclable.setRemovable(false);
        }
        movables.add(movableRecyclable);
        movables.add(feedback);

        gameScreen.addSprite(movableRecyclable.getSprite());
        gameScreen.addSprite(feedback.getSprite());

        movableArray[0] = feedback;
        movableArray[1] = movableRecyclable;
        return movableArray;
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

    public static void preLoadImages() {
        ResourceManager resourceManager = ResourceManager.getInstance();
        resourceManager.getImage(INCORRECT_SPRITE);
        resourceManager.getImage(CORRECT_SPRITE);
    }

}