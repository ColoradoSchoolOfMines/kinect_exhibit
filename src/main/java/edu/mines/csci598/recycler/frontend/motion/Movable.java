package edu.mines.csci598.recycler.frontend.motion;

import edu.mines.csci598.recycler.frontend.Hand;
import edu.mines.csci598.recycler.frontend.MotionState;
import edu.mines.csci598.recycler.frontend.graphics.Coordinate;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: Lauren
 * Date: 12/2/12
 * Time: 1:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Movable {
   public Path getPath();
   public void setPath(Path p);

   public Coordinate getPosition();
   public void setPosition(Coordinate position);

   public boolean isRemovable();
   public void setRemovable(boolean removable);

   public boolean isTouchable();

   public Sprite getSprite();
   public boolean collidesWithPoint(Coordinate point);

   public void setMotionState(MotionState state);
   public MotionState getMotionState();

   public void reactToCollision(Hand hand, double currentTimeSec);

}
