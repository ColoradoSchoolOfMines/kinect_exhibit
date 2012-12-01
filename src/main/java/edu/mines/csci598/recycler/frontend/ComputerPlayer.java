package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Coordinate;
import edu.mines.csci598.recycler.frontend.graphics.Line;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.motion.ConveyorBelt;
import edu.mines.csci598.recycler.frontend.motion.Movable;
import edu.mines.csci598.recycler.frontend.utils.ComputerConstants;
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
    /* lastStrikeTime
     * Time of last strike
     * Used for slowing down strike attempts and resetting hand to initial position
     */
    private double lastStrikeTime;
    /* lastStrikeDelay
     * Amount of time needed since lastStrikeTime to be able to strike
     * Accumulates and then is reset with a successful strike
     */
    private double lastStrikeDelay;
    /* justStruckRecyclable
     * Used for continuation of hand movement across conveyer
     */
    private boolean justStruckRecyclable;
    /* lastMoveDelay
     * Used to slowdown AI hand movement
     * Time after a successful strike before being reset to initial location
     */
    private double lastMoveDelay;
    /* recycleBins
     * List of recycleBins
     * Needed to determine when and where to strike
     */
    private RecycleBins recycleBins;
    /* lastMotionTimeSec
     * Used for following the path around a recyclable across the conveyer
     */
    private double lastMotionTimeSec;
    /* goalBinTopY
     * Top of target recyclables goalBin
     */
    private double goalBinTopY;
    /* goalBinBottomY
     * Bottom of target recyclables goalBin
     */
    private double goalBinBottomY;

    public ComputerPlayer(RecycleBins recycleBins) {
        logger.setLevel(Level.INFO);
        logger.debug("Second player is a computer");
        primary = new ComputerHand();
        random = new Random(System.currentTimeMillis());
        justStruckRecyclable = false;
        lastMoveDelay=ComputerConstants.LAST_MOVE_DELAY;
        this.recycleBins = recycleBins;
        lastStrikeTime = 0;
        lastStrikeDelay=ComputerConstants.LAST_STRIKE_DELAY;
        lastMotionTimeSec = 0;
        goalBinBottomY = -1;
        goalBinTopY = -1;
    }
    /* getHand
     * Used in gameLogic to add ComputerHand to hands array
     */
    public Hand getHand() {
        return primary;
    }

    /* updateAI
     * Called from gameLogic.updateThis()
     * Determines AI behavior
     */
    public void updateAI(Movable movable, double currentTimeSec) {
        if(movable == null) {
            //No recyclable to track, reset to initial for next recyclable
            if(currentTimeSec>lastStrikeTime+lastMoveDelay)
                primary.resetHandToInitialPosition();
            return;
        }else {
            if(!primary.isFollowingPath()){
                if(justStruckRecyclable){
                    handleJustStruckRecyclable();
                }
                if (movable instanceof PowerUp) {
                    primary.setOnCorrectSide(true);
                }
                if(movable instanceof Recyclable){
                    handleTrashAndSettingHand(movable,currentTimeSec);
                }
                if(!primary.isFollowingPath()){
                    //Follow target recyclable
                    followRecyclable(movable);
                    //Attempt to Strike target recyclable
                    attemptToStrike(movable,currentTimeSec);
                }

            } else {
                followPath(currentTimeSec);
            }
        }
    }

    /* HandleTrashAndSettingHand
     * Marks trash as Untouchable
     * Tries to set hand to correct side
     */
    private void handleTrashAndSettingHand(Movable movable, double currentTimeSec){
        Recyclable r = (Recyclable) movable;
        if(r.getType()==RecyclableType.TRASH){
            movable.setMotionState(MotionState.IS_TRASH);
        } else if(!primary.isOnCorrectSide()){
            setHandToCorrectSide(r,currentTimeSec);
        }
    }
    /* handleJustStruckRecyclable
     * Moves hand off of the conveyer after striking recyclable
     */
    private void handleJustStruckRecyclable(){
        if(primary.getVelocityX()>0){
            Coordinate position = new Coordinate(
                    ConveyorBelt.RIGHT_VERTICAL_PATH_START_X + ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER,
                    primary.getY());
            primary.setPosition(position);
        } else if(primary.getVelocityX()<0) {
            Coordinate position = new Coordinate(
                    ConveyorBelt.RIGHT_VERTICAL_PATH_START_X - ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER,
                    primary.getY());
            primary.setPosition(position);
        }
        justStruckRecyclable=false;
    }

    private void followRecyclable(Movable m) {
        Coordinate position = new Coordinate(primary.getX(), m.getPosition().getY());
        primary.setPosition(position);
    }

    private void crossConveyer(Recyclable r, double currentTimeSec, int newX){
        //int newX = -1*ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER;
        int rand = random.nextInt(ComputerConstants.MAX_GENERATION_NUMBER) + 1;
        if(rand > ComputerConstants.HAND_SET_THRESHOLD) {
            if(!primary.isFollowingPath()) {
                setUpPath(r, currentTimeSec, newX);
            }
            else {
                followPath(currentTimeSec);
            }
        }
        else {
            primary.setOnCorrectSide(true);
        }
    }

    private void setUpPath(Recyclable r, double currentTimeSec, int newX) {
        primary.setGoal(r.getSprite().getX() + newX, primary.getSprite().getY() - ComputerConstants.HAND_Y_OFFSET);
        logger.debug("rx=" + r.getSprite().getX() + ",ry=" + r.getSprite().getY() +
                ",rsx=" + r.getSprite().getScaledX() + ",rsy=" + r.getSprite().getScaledY() +
                ",hx=" + primary.getSprite().getX() + ",hy=" + primary.getSprite().getY() +
                ",hsx=" + primary.getSprite().getScaledX() + ",hsy=" + primary.getSprite().getScaledY() +
                ",gx=" + primary.getGoalX() + ",gy=" + primary.getGoalY());
        Path p = new Path(currentTimeSec);
        Line moveAboveRecyclable = new Line(primary.getSprite().getX(), primary.getSprite().getY(),
                primary.getSprite().getX(), primary.getGoalY(), ComputerConstants.PATH_TIME_SEC);
        Line moveAcrossRecyclable = new Line(primary.getSprite().getX(), primary.getGoalY(),
                primary.getGoalX(), primary.getGoalY(), ComputerConstants.PATH_TIME_SEC);
        p.addLine(moveAboveRecyclable);
        p.addLine(moveAcrossRecyclable);
        primary.setPath(p);
    }

    private void followPath(double currentTimeSec) {
        double timePassedSec = currentTimeSec-lastMotionTimeSec;
        Coordinate newPosition = primary.getPath().getLocation(timePassedSec);
        if(newPosition.getX() != primary.getGoalX()) {
            primary.setPosition(newPosition);
        }
        else{
            primary.resetFollowingPath();
        }
    }

    public int findBinSide(Recyclable r) {
        int newX = 0;
        RecycleBin bin = recycleBins.findCorrectBin(r);
        RecycleBin.ConveyorSide binSide = bin.getSide();
        goalBinTopY = bin.getMinY();
        goalBinBottomY = bin.getMaxY();
        if(binSide == RecycleBin.ConveyorSide.RIGHT){
            if(!primary.isHandOnLeftSide()) {
                logger.debug("Set hand to left side");
                newX =- 1 * ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER;
            }
            else
                primary.setOnCorrectSide(true);
        }
        else {
            if(primary.isHandOnLeftSide()) {
                logger.debug("Set hand to right side");
                newX = ComputerConstants.HAND_X_OFFSET_FROM_CONVEYER;
            }
            else
                primary.setOnCorrectSide(true);
        }
        return newX;
    }

    public void setHandToCorrectSide(Recyclable r, double currentTimeSec){
        if(!primary.isOnCorrectSide()) {
            int newX = findBinSide(r);
            if(!primary.isOnCorrectSide())
                crossConveyer(r,currentTimeSec,newX);
        }
    }

    private boolean recyclableWillFallInBin(Movable m) {
        boolean ret = false;
        double ry = m.getSprite().getY();
        int rand = random.nextInt(ComputerConstants.MAX_GENERATION_NUMBER) + 1;
        if(rand>ComputerConstants.INCORRECT_STRIKE_THRESHOLD){
            if(ry > goalBinTopY && ry < goalBinBottomY ) {
                ret = true;
                logger.debug("Recyclable will fall in bin");
            }
            else if(ry < goalBinTopY){
                m.setMotionState(MotionState.ABOVE_BIN);
                logger.debug("Recyclable above bin");
            }
        } else {
            logger.debug("Computer has chance to strike recyclable incorrectly");
            ret = true;
        }
        return ret;
    }

    private void attemptToStrike(Movable movable, double currentTimeSec){
        if(!(movable instanceof PowerUp)){
            if(recyclableWillFallInBin(movable)){
                if(currentTimeSec > lastStrikeTime + lastStrikeDelay){
                    if(ICanStrike()){
                        strikeItem(movable);
                        lastStrikeDelay=ComputerConstants.LAST_STRIKE_UPDATE;
                        lastStrikeTime = currentTimeSec;
                    }else {
                        lastStrikeDelay+=ComputerConstants.LAST_STRIKE_UPDATE;
                    }
                }
            }
        }else {
            if(ICanStrike()){
                strikeItem(movable);
                lastStrikeDelay=ComputerConstants.LAST_STRIKE_UPDATE;
                lastStrikeTime = currentTimeSec;
            }else {
                lastStrikeDelay+=ComputerConstants.LAST_STRIKE_UPDATE;
            }
        }
    }

    public boolean ICanStrike(){
        boolean ret = false;
        int rand = random.nextInt(ComputerConstants.MAX_GENERATION_NUMBER) + 1;
        if(rand > ComputerConstants.MIN_GENERATION_THRESHOLD){
            ret = true;
        }
        return ret;
    }

    public void strikeItem(Movable m) {
        primary.setOnCorrectSide(false);
        int newX = m.getSprite().getX();

        justStruckRecyclable = true;
        Coordinate position = new Coordinate(newX,primary.getY());
        logger.debug("Strike - SetPosition");
        primary.setPosition(position);
    }
}
