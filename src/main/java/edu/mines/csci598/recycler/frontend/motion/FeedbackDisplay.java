package edu.mines.csci598.recycler.frontend.motion;

import edu.mines.csci598.recycler.frontend.Recyclable;
import edu.mines.csci598.recycler.frontend.RecyclableType;
import edu.mines.csci598.recycler.frontend.graphics.Coordinate;
import edu.mines.csci598.recycler.frontend.graphics.GameScreen;
import edu.mines.csci598.recycler.frontend.graphics.Line;
import edu.mines.csci598.recycler.frontend.graphics.Path;

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
    public FeedbackDisplay(double initialSpeed) {
        super(initialSpeed);
        gameScreen = GameScreen.getInstance();
    }

    public void addWrong(Coordinate c, double currentTimeSec){
         makeDisplay(c, currentTimeSec, RecyclableType.WRONG);
    }

    public void addRight(Coordinate c, double currentTimeSec){
         makeDisplay(c, currentTimeSec, RecyclableType.RIGHT);
    }

    private void makeDisplay(Coordinate c, double currentTimeSec, RecyclableType recyclableType){
        Path p = new Path(currentTimeSec);

        p.addLine(new Line(c,c,0.5));
        //TODO is there a better way to do this part? Or since it is the only one can we let it slide that this is using recyclables...
        Recyclable r = new Recyclable(recyclableType,p,recyclableType.getImagePaths()[0]);
        recyclables.add(r);
        gameScreen.addSprite(r.getSprite());
    }

    @Override
    public void moveItems(double currentTimeSec){
        super.moveItems(currentTimeSec);
        List<Recyclable> toRemove = releaseControlOfRecyclablesAtEndOfPath(currentTimeSec);
        for(Recyclable r : toRemove){
            gameScreen.removeSprite(r.getSprite());
        }
    }


}
