package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Coordinate;
import edu.mines.csci598.recycler.frontend.graphics.Line;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.motion.ConveyorBelt;
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
    private double goalBinTopY;
    private double goalBinBottomY;

    public ComputerPlayer(RecycleBins recycleBins){
        logger.setLevel(Level.DEBUG);
        logger.debug("Second player is a computer");
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
        goalBinBottomY = -1;
        goalBinTopY = -1;

    }
    public void updateAI(Recyclable r,double currentTimeSec){
        //logger.debug("rt="+r.isTouchable());
        if(r.isTouchable()){
            if(!primary.isFollowingPath()){
                if(!primary.isOnCorrectSide()){
                    //Set hand to correct side
                    if(r.isNotAPowerUp()){
                        setHandToCorrectSide(r,currentTimeSec);
                    }else {
                        primary.setOnCorrectSide(true);
                    }
                }else {
                    //Follow target recyclable
                    followRecyclable(r);
                    //Strike target recyclable
                    attemptToStrike(r,currentTimeSec);
                }
            } else {
                followPath(currentTimeSec);
            }
        }else {
            //logger.debug("untouchable");
            primary.resetHandToInitialPosition();
        }
    }
    private void followRecyclable(Recyclable r){
        //logger.debug("FollowRecyclable");
        primary.getSprite().setY(r.getSprite().getY());
    }
    private void crossConveyer(Recyclable r,double currentTimeSec,int newX){
        //int newX = -1*ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER;
        if(!primary.isFollowingPath()){
            if(r.isTouchable()){
                setUpPath(r,currentTimeSec,newX);
            }
        } else
            followPath(currentTimeSec);
    }
    private void setUpPath(Recyclable r,double currentTimeSec,int newX){
        logger.debug("SetUpPath");
        primary.setGoal(r.getSprite().getScaledX()+newX,primary.getSprite().getScaledY()-ComputerConstants.HAND_Y_OFFSET);
        logger.debug("rx="+r.getSprite().getX()+",ry="+r.getSprite().getY()+
                ",rsx="+r.getSprite().getScaledX()+",rsy="+r.getSprite().getScaledY()+
                ",hx="+primary.getSprite().getX()+",hy="+primary.getSprite().getY()+
                ",hsx="+primary.getSprite().getScaledX()+",hsy="+primary.getSprite().getScaledY()+
                ",gx="+primary.getGoalX()+",gy="+primary.getGoalY());
        Path p = new Path(currentTimeSec);
        Line moveAboveRecyclable = new Line(primary.getSprite().getScaledX(),primary.getSprite().getScaledY(),
                primary.getSprite().getScaledX(),primary.getGoalY(), ComputerConstants.PATH_TIME_SEC);
        Line moveAcrossRecyclable = new Line(primary.getSprite().getScaledX(),primary.getGoalY(),
                primary.getGoalX(),primary.getGoalY(), ComputerConstants.PATH_TIME_SEC);
        p.addLine(moveAboveRecyclable);
        p.addLine(moveAcrossRecyclable);
        primary.setScaledPath(p);

    }
    private void followPath(double currentTimeSec){
        double timePassedSec = currentTimeSec-lastMotionTimeSec;
        Coordinate newPosition = primary.getScaledPath().getLocation(timePassedSec);
        if(newPosition.getX()!=primary.getGoalX()){
            //logger.debug("px="+newPosition.getX()+",py="+newPosition.getY());
            primary.setScaledPosition(newPosition);
        }else{
            //logger.debug("Finished following path");
            primary.resetFollowingPath();
        }
    }
    public int findBinSide(Recyclable r){
        int newX =0;
        RecycleBin bin = recycleBins.findCorrectBin(r);
        RecycleBin.ConveyorSide binSide = bin.getSide();
        goalBinTopY = bin.getMinY();
        goalBinBottomY = bin.getMaxY();
        if(binSide==RecycleBin.ConveyorSide.RIGHT){
            if(!primary.isHandOnLeftSide()){
                //logger.debug("**SetPath left");
                newX=-1*ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER;
                //primary.getSprite().setX((int)newX);
            }
        } else {
            if(primary.isHandOnLeftSide()){
                //logger.debug("**SetPath right");
                newX=ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER;
                //primary.getSprite().setX((int)newX);
            }
        }
        primary.setOnCorrectSide(true);
        return newX;
    }
    public void setHandToCorrectSide(Recyclable r, double currentTimeSec){
        if(r.isTouchable()){
            //logger.debug("setHandToCorrectSide:"+primary.isOnCorrectSide());
            if(!primary.isOnCorrectSide()){
                //logger.info("  onWrongSide");
                //Check goal x and y
                int newX=findBinSide(r);
                crossConveyer(r,currentTimeSec,newX);
            } else {

            }
        }
    }
    private boolean recyclableWillFallInBin(Recyclable r){
        boolean ret = false;
        double ry=r.getSprite().getY();
        //logger.debug("bTopY="+goalBinTopY +",bBottomY=="+goalBinBottomY +",ry="+r.getSprite().getY()+
        //        ",rsy="+r.getSprite().getScaledY());
        if(ry>goalBinTopY && ry <goalBinBottomY ){
            ret=true;
            logger.debug("Recyclable will fall in bin");
        }else if(ry<goalBinTopY){
            r.setMotionState(MotionState.ABOVE_BIN);
        }
        return ret;
    }
    private void attemptToStrike(Recyclable r, double currentTimeSec){
        if(r.isTouchable()){
            if(r.isNotAPowerUp()){
                if(recyclableWillFallInBin(r)){
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
            }else {
                if(ICanStrike()){
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
    public void strikeRecyclable(Recyclable r,double currentTimeSec){
        primary.setOnCorrectSide(false);
        int rX = r.getSprite().getX();

        if(rX<primary.getSprite().getX()){
            handleCollision(r,currentTimeSec,rX,-1 * ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER);
        } else {
            handleCollision(r, currentTimeSec, rX, ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER);
        }
    }
    private void handleCollision(Recyclable r,double currentTimeSec,int newX, int pathOffset){
        Path path = new Path(currentTimeSec);
        Line collideLine;
        primary.getSprite().setX(newX + pathOffset);
        if(pathOffset>0){
            logger.debug("Pushed Right");
            r.setMotionState(MotionState.FALL_RIGHT);
            collideLine = new Line(r.getSprite().getX(), r.getSprite().getY(),
                    r.getSprite().getX() + GameConstants.ITEM_PATH_END, r.getSprite().getY(), 4);
        } else {
            logger.debug("Pushed Left");
            r.setMotionState(MotionState.FALL_LEFT);
            collideLine = new Line(r.getSprite().getX(), r.getSprite().getY(),
                    r.getSprite().getX() - GameConstants.ITEM_PATH_END, r.getSprite().getY(), 4);
        }
        path.addLine(collideLine);
        r.setPath(path);
        
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
