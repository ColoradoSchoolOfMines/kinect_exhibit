package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Coordinate;
import edu.mines.csci598.recycler.frontend.graphics.Line;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.utils.ComputerConstants;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Random;


/**
 * Created with IntelliJ IDEA.
 * User: Marshall
 * Date: 11/13/12
 * Time: 6:09 PM
 *
 *
 */
public class ComputerPlayer {
    private static final Logger logger = Logger.getLogger(ComputerPlayer.class);
    public ComputerHand primary;
    public Path p;
    private Random random;
    private double lastStrikeTime;
    private double lastStrikeDelay;
    private double lastMoveTime;
    private double lastMoveDelay;
    public int targetRecyclable;
    private RecycleBins recycleBins;
    private int score;
    private int strikes;
    private double lastMotionTimeSec;

    public ComputerPlayer(RecycleBins recycleBins){
        logger.setLevel(Level.INFO);
        primary = new ComputerHand();
        random = new Random(System.currentTimeMillis());
        lastStrikeTime=0;
        lastStrikeDelay=ComputerConstants.LAST_STRIKE_DELAY;
        lastMoveTime=0;
        lastMoveDelay=ComputerConstants.LAST_MOVE_DELAY;
        targetRecyclable = 0;
        score=0;
        strikes=0;
        this.recycleBins=recycleBins;
        lastMotionTimeSec=0;
    }
    public void updateAI(Recyclable r,double currentTimeSec){
        //Set hand to correct side
        if(!primary.isOnCorrectSide())
            setHandToCorrectSide(r,currentTimeSec);
        //Follow target recyclable
        followRecyclable(r,currentTimeSec);
        //Strike target recyclable
        strike(r,currentTimeSec);
    }
    public void followRecyclable(Recyclable r, double currentTimeSec){
        //logger.info("FollowRecyclable");
        if(!primary.isFollowingPath()){
            //logger.info("following recyclable");
            if(currentTimeSec>lastMoveTime+lastMoveDelay)
                primary.getSprite().setY(r.getSprite().getY());
        }else {
            logger.info("Moving path.gx="+primary.getGoalX()+",gy="+primary.getGoalY()+",hx="+primary.getSprite().getX()+",hy="+primary.getSprite().getY()+",fp="+primary.isFollowingPath()+",cs="+primary.isOnCorrectSide());
            double timePassedSec = currentTimeSec-lastMotionTimeSec;
            Coordinate newPosition = primary.getPath().getLocation(timePassedSec);
            if(!(newPosition.equals(primary.getPosition()))){
                lastMotionTimeSec=currentTimeSec;
                primary.setPosition(newPosition);
            }
            if(primary.getSprite().getY()<primary.getGoalY()){
                primary.resetFollowingPath();
                if(!primary.isOnCorrectSide()){
                    //logger.info("Hand is on wrong side");
                    if(primary.isHandOnLeftSide()){
                        primary.getSprite().setX((int)r.getPosition().getX()+GameConstants.SPRITE_X_OFFSET);
                    }else{
                        primary.getSprite().setX((int)r.getPosition().getX()-GameConstants.SPRITE_X_OFFSET);
                    }
                    lastStrikeTime=currentTimeSec;
                    lastMoveTime = currentTimeSec;
                }else {
                    //logger.info("Hand is on correct side");
                }
            }
        }
    }
    public boolean hasCollisionWithRecyclable(Recyclable r,double currentTime){
        boolean ret = false;
        return ret;
    }
    private void strike(Recyclable r, double currentTimeSec){
        if(r.isTouchable()){
            if(currentTimeSec > lastStrikeTime + lastStrikeDelay){
                //Log.logInfo("hx="+primary.getSprite().getX()+",hy"+primary.getSprite().getY()+
                //            ",hsx="+primary.getSprite().getScaledX()+",hsy"+primary.getSprite().getScaledY()+
                //            ",rx"+r.getSprite().getX()+",ry"+r.getSprite().getY()+
                //            ",rsx="+r.getSprite().getScaledX()+",rsy="+r.getSprite().getScaledY());
                if(ICanStrike()){
                    //Log.logInfo("Strike");
                    strikeRecyclable(r,currentTimeSec);
                    lastStrikeDelay=ComputerConstants.LAST_STRIKE_UPDATE;
                    lastStrikeTime = currentTimeSec;
                }else {
                    lastStrikeDelay+=ComputerConstants.LAST_STRIKE_UPDATE;
                }
            }
        }
    }
    public boolean ICanStrike(){
        boolean ret = false;
        int rand = random.nextInt(ComputerConstants.MAX_GENERATION_NUMBER)+1;
        if(rand > ComputerConstants.MIN_GENERATION_THRESHOLD){
            ret = true;
        }
        return ret;
    }

    public void setHandToCorrectSide(Recyclable r, double currentTimeSec){
        if(r.isTouchable()){
            logger.info("setHandToCorrectSide");
            if(!primary.isOnCorrectSide()){
                //logger.info("  onWrongSide");
                //Check goal x and y
                double newX=0;
                RecycleBin bin = recycleBins.findCorrectBin(r);
                RecycleBin.ConveyorSide binSide = bin.getSide();
                if(binSide==RecycleBin.ConveyorSide.RIGHT){
                    if(!primary.isHandOnLeftSide()){
                        logger.info("**SetPath left");
                        primary.setOnCorrectSide(false);
                        //newX=r.getPosition().getX()-GameConstants.SPRITE_X_OFFSET;
                        //primary.getSprite().setX((int)newX);
                    } else {
                        primary.setOnCorrectSide(true);
                    }
                } else {
                    if(primary.isHandOnLeftSide()){
                        logger.info("**SetPath right");
                        primary.setOnCorrectSide(false);
                        //newX=r.getPosition().getX()+GameConstants.SPRITE_X_OFFSET;
                        //primary.getSprite().setX((int)newX);
                    } else {
                        primary.setOnCorrectSide(true);
                    }
                }
                if(!primary.isFollowingPath()){
                    //logger.info("Setting path");
                    Path p = new Path(currentTimeSec);
                    Line moveAboveRecyclable = new Line(primary.getSprite().getX(),primary.getSprite().getY(),
                            r.getSprite().getX(),primary.getSprite().getY()-ComputerConstants.HAND_Y_OFFSET, 4);
                    int goalY = r.getSprite().getY()-ComputerConstants.HAND_GOAL_OFFSET;
                    //int goalX = r.getSprite().getX();
                    p.addLine(moveAboveRecyclable);
                    primary.setPath(p);
                    primary.setGoal(primary.getSprite().getX(),goalY);
                }

            } else {
                primary.resetFollowingPath();
            }

            //logger.info("LeftSide="+primary.isHandOnLeftSide()+",px="+primary.getSprite().getX()+",bt="+bin.getType()+",bs="+binSide);
        }else{
            //logger.info("  onCorrectSide");
        }
    }
    public void strikeRecyclable(Recyclable r,double currentTimeSec){
        primary.setOnCorrectSide(false);
        int newX = r.getSprite().getX();
        int newY = r.getSprite().getY();

        if(newX<primary.getSprite().getX()){
            handleCollision(r,currentTimeSec,newX,-1 * ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER);
        } else {
            handleCollision(r, currentTimeSec, newX, ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER);
        }
    }
    private void handleCollision(Recyclable r,double currentTimeSec,int newX, int pathOffset){
        Path path = new Path(currentTimeSec);
        Line collideLine;
        primary.getSprite().setX(newX + pathOffset);
        if(pathOffset>0){
            logger.info("Pushed Right");
            r.setMotionState(MotionState.FALL_RIGHT);
            collideLine = new Line(r.getSprite().getX(), r.getSprite().getY(),
                    r.getSprite().getX() + GameConstants.ITEM_PATH_END, r.getSprite().getY(), 4);
        } else {
            logger.info("Pushed Left");
            r.setMotionState(MotionState.FALL_LEFT);
            collideLine = new Line(r.getSprite().getX(), r.getSprite().getY(),
                    r.getSprite().getX() - GameConstants.ITEM_PATH_END, r.getSprite().getY(), 4);
        }
        path.addLine(collideLine);
        r.setPath(path);
        
        // Marshall: I'm trying to stay out of this code, but you'll need something
        // like this to get it to work again:
        // fallingItems.add(r);
        // elsewhere: 
        //      for(Recyclable r : fallingItems){
		//			Coordinate newPosition = r.getPath().getLocation(r.getPosition(), GameConstants.HAND_COLLISION_PATH_SPEED_IN_PIXELS_PER_SECOND, elapsedTime); 
		//			r.setPosition(newPosition);
        //		}
        
        RecycleBin bin = recycleBins.findBinForFallingRecyclable(r);
        if(bin.isCorrectRecyclableType(r)){
            score++;
        }else {
            strikes++;
        }
        //Log.logInfo("Score="+score+",Strikes="+strikes);
    }
    public int getAIScore(){
        return score;
    }
    public int getAIStrikes() {
        return strikes;
    }
}
