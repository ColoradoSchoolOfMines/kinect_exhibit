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
    private boolean justStruckRecyclable;
    private double lastMoveDelay;
    public int targetRecyclable;
    private RecycleBins recycleBins;
    private int score;
    private int strikes;
    private double lastMotionTimeSec;
    private double goalBinTopY;
    private double goalBinBottomY;

    public ComputerPlayer(RecycleBins recycleBins){
        logger.setLevel(Level.INFO);
        logger.debug("Second player is a computer");
        primary = new ComputerHand();
        random = new Random(System.currentTimeMillis());
        lastStrikeTime=0;
        lastStrikeDelay=ComputerConstants.LAST_STRIKE_DELAY;
        justStruckRecyclable = true;
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
    	if(r == null) {
            //logger.debug("untouchable");
            if(currentTimeSec>lastStrikeTime+lastMoveDelay)
                primary.resetHandToInitialPosition();
            return;
        }
	    if(!primary.isFollowingPath()){
            if(r.isNotTrash()){
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
            }else{
                r.setMotionState(MotionState.IS_TRASH);
                score+=10;
            }
        } else {
            followPath(currentTimeSec);
        }
    }
    private void followRecyclable(Recyclable r){
        //logger.debug("FollowRecyclable");
        Coordinate position = new Coordinate(primary.getX(),r.getPosition().getY());
        primary.setPosition(position);
    }
    private void crossConveyer(Recyclable r,double currentTimeSec,int newX){
        //int newX = -1*ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER;
        int rand = random.nextInt(ComputerConstants.MAX_GENERATION_NUMBER)+1;
        if(rand>ComputerConstants.HAND_SET_THRESHOLD){
            if(!primary.isFollowingPath()){
                setUpPath(r,currentTimeSec,newX);
            } else{
                followPath(currentTimeSec);
            }
        } else{
            primary.setOnCorrectSide(true);
        }
    }
    private void setUpPath(Recyclable r,double currentTimeSec,int newX){
        primary.setGoal(r.getSprite().getX()+newX,primary.getSprite().getY()-ComputerConstants.HAND_Y_OFFSET);
        logger.debug("rx="+r.getSprite().getX()+",ry="+r.getSprite().getY()+
                ",rsx="+r.getSprite().getScaledX()+",rsy="+r.getSprite().getScaledY()+
                ",hx="+primary.getSprite().getX()+",hy="+primary.getSprite().getY()+
                ",hsx="+primary.getSprite().getScaledX()+",hsy="+primary.getSprite().getScaledY()+
                ",gx="+primary.getGoalX()+",gy="+primary.getGoalY());
        Path p = new Path(currentTimeSec);
        Line moveAboveRecyclable = new Line(primary.getSprite().getX(),primary.getSprite().getY(),
                primary.getSprite().getX(),primary.getGoalY(), ComputerConstants.PATH_TIME_SEC);
        Line moveAcrossRecyclable = new Line(primary.getSprite().getX(),primary.getGoalY(),
                primary.getGoalX(),primary.getGoalY(), ComputerConstants.PATH_TIME_SEC);
        p.addLine(moveAboveRecyclable);
        p.addLine(moveAcrossRecyclable);
        primary.setPath(p);
    }
    private void followPath(double currentTimeSec){
        double timePassedSec = currentTimeSec-lastMotionTimeSec;
        Coordinate newPosition = primary.getPath().getLocation(timePassedSec);
        if(newPosition.getX()!=primary.getGoalX()){
            primary.setPosition(newPosition);
        }else{
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
                logger.debug("Set hand to left side");
                newX=-1*ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER;
            } else
                primary.setOnCorrectSide(true);
        } else {
            if(primary.isHandOnLeftSide()){
                logger.debug("Set hand to right side");
                newX=ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER;
            } else
                primary.setOnCorrectSide(true);
        }
        return newX;
    }
    public void setHandToCorrectSide(Recyclable r, double currentTimeSec){
        if(!primary.isOnCorrectSide()){
            int newX=findBinSide(r);
            if(!primary.isOnCorrectSide())
                crossConveyer(r,currentTimeSec,newX);
        }
    }
    private boolean recyclableWillFallInBin(Recyclable r){
        boolean ret = false;
        double ry=r.getSprite().getY();
        int rand = random.nextInt(ComputerConstants.MAX_GENERATION_NUMBER)+1;
        //if(rand>ComputerConstants.INCORRECT_STRIKE_THRESHOLD){
            if(ry>goalBinTopY && ry <goalBinBottomY ){
                ret=true;
                logger.debug("Recyclable will fall in bin");
            }else if(ry<goalBinTopY){
                r.setMotionState(MotionState.ABOVE_BIN);
                logger.debug("Recyclable above bin");
            }
        //} else {
        //    logger.debug("Computer has chance to strike recyclable incorrectly");
        //    ret = true;
        //}
        return ret;
    }
    private void attemptToStrike(Recyclable r, double currentTimeSec){
        if(r.isNotAPowerUp()){
            if(recyclableWillFallInBin(r)){
                if(currentTimeSec > lastStrikeTime + lastStrikeDelay){
                    if(ICanStrike()){
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
        justStruckRecyclable = true;
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
            score+=10;
        }else {
            strikes++;
        }
    }
    public int getAIScore(){
        return score;
    }
    public int getAIStrikes() {
        return strikes;
    }
}
