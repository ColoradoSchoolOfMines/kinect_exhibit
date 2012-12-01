package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.backend.GameManager;
import edu.mines.csci598.recycler.frontend.graphics.*;
import edu.mines.csci598.recycler.frontend.motion.ConveyorBelt;
import edu.mines.csci598.recycler.frontend.motion.FeedbackDisplay;
import edu.mines.csci598.recycler.frontend.motion.TheForce;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;
import edu.mines.csci598.recycler.splashscreen.highscores.SavePlayer;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * The GameLogic is where the game play logic is. The main game update loop will go here.
 * <p/>
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/20/12
 * Time: 9:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameLogic {
    private static final Logger logger = Logger.getLogger(GameLogic.class);

    private GameLogic otherScreen;
    private List<Hand> hands;
    private ComputerPlayer computerPlayer;
    private GameScreen gameScreen;
    private RecyclableFactory factory;
    private RecycleBins recycleBins;
    private ConveyorBelt conveyorBelt;
    private TheForce theForce;
    private double currentTimeSec;
    private double wallTimeSec;
    private FeedbackDisplay feedbackDisplay;
    private double lastWallTimeSec;
    private double timeSpeedFactor;
    private double powerUpSpeedFactor;
    private long startTime;
    private double nextItemTypeGenerationTime;
    private int numItemTypesInUse;
    private int strikes;
    private boolean gameOverNotified = false;
    private boolean playerIsAComputer;
    private boolean debuggingCollisions;
    private GameStatusDisplay gameStatusDisplay;
    //TODO game manager should be removed from this class when the idea of players having hands is dissolved
    private GameManager gameManager;
    private double timeToRemovePowerUp;


    public GameLogic(RecycleBins recycleBins, Path conveyorPath, GameManager gameManager, GameStatusDisplay gameStatusDisplay,
                     boolean playerIsAComputer, boolean debuggingCollision) {
        this.gameManager = gameManager;
        gameScreen = GameScreen.getInstance();
        factory = new RecyclableFactory();
        this.recycleBins = recycleBins;
        numItemTypesInUse = GameConstants.INITIAL_NUMBER_OF_ITEM_TYPES;
        currentTimeSec = 0;
        lastWallTimeSec = 0;
        timeSpeedFactor = 1;
        powerUpSpeedFactor = 1;
        timeToRemovePowerUp = 0.0;
        feedbackDisplay = new FeedbackDisplay(0);
        nextItemTypeGenerationTime = GameConstants.TIME_TO_ADD_NEW_ITEM_TYPE;
        this.gameStatusDisplay = gameStatusDisplay;
        conveyorBelt = new ConveyorBelt(this, gameScreen, conveyorPath);
        theForce = new TheForce();
        startTime = System.currentTimeMillis();

        this.playerIsAComputer = playerIsAComputer;
        this.debuggingCollisions = debuggingCollision;

        hands = new ArrayList<Hand>();

        // sets up the first player and adds its primary hand to the gameScreen
        // so that it can be displayed
        if (!this.playerIsAComputer) {
            //   player1 = new Player(gameManager);
            // creates the max number of hands that can be displayed which is 4
            //TODO: This is going to be a problem - each player might have 2 hands, but how do we know whose hands are whose?
            for (int i = 0; i < 2; i++) {
                hands.add(new PlayerHand(gameManager, i));
                gameScreen.addHandSprite(hands.get(hands.size() - 1).getSprite());
            }
        } else {
            computerPlayer = new ComputerPlayer(recycleBins);
            gameScreen.addHandSprite(computerPlayer.primary.getSprite());
            hands.add(computerPlayer.getHand());
        }
        //Add recycle bins to game screen to be drawn
        for (RecycleBin bin : recycleBins.getRecycleBins()) {
            if (bin.hasSprite()) {
                gameScreen.addRecycleBinSprite(bin.getSprite());
            }
        }
        //Add single item to conveyer for debugging collisions.
        if (this.debuggingCollisions) {
            logger.debug("Adding recyclable for collision detection");
            Recyclable r = factory.generateItemForDebugging(conveyorBelt.getNewPath());
            conveyorBelt.takeControlOfRecyclable(r);
            gameScreen.addSprite(r.getSprite());
        }
    }

    private void lookForAndHandleCollisions() {

        // checks to see if there is a collision with any one of the hands
        for (Hand hand : hands) {

            //If the hand isn't moving fast enough to swipe, skip it
            if (Math.abs(hand.getVelocityX()) < GameConstants.MIN_HAND_VELOCITY) {
                continue;
            }
            // If we get here, it is causing collisions with anything at this location.

            List<Recyclable> swipedOffConveyor = conveyorBelt.releaseTouchableItemsAtPoint(hand.getPosition());
            logger.debug("swipedSize="+swipedOffConveyor.size());
            // We should really check the theForce also, but we're not allowing things it controls to be touchable, so it would be kind of silly.

            handleCollisions(hand, swipedOffConveyor);

        }
    }

    public void handleCollisions(Hand hand, List<Recyclable> swipedOffConveyor) {
        for (Recyclable r : swipedOffConveyor) {

            //Rough way to make items fall at different speeds
            double travelTime = Math.abs(GameConstants.ITEM_PATH_END / hand.getVelocityX());
            //Don't let it go too fast.
            travelTime = Math.max(travelTime, 0.3);
            //or too slow
            travelTime = Math.min(travelTime, 2);
            //Account for the speedup
            travelTime *= timeSpeedFactor;

            Coordinate position = r.getPosition();
            Line collideLine;
            if (hand.getVelocityX() >= GameConstants.MIN_HAND_VELOCITY) {
                //logger.debug("Pushed Right");
                r.setMotionState(MotionState.FALL_RIGHT);
                RecycleBin destBin = recycleBins.findBinForFallingRecyclable(r);
                collideLine = new Line( position.getX(), position.getY(),
                         position.getX() + GameConstants.ITEM_PATH_END,  destBin.getMidPoint(),
                        travelTime,Math.PI*2);

            } else if (hand.getVelocityX() <= -1 * GameConstants.MIN_HAND_VELOCITY) {
                //logger.debug("Pushed Left");
                r.setMotionState(MotionState.FALL_LEFT);
                RecycleBin destBin = recycleBins.findBinForFallingRecyclable(r);
                collideLine = new Line( position.getX(), position.getY(),
                         position.getX() - GameConstants.ITEM_PATH_END, destBin.getMidPoint(),
                        travelTime,Math.PI*2);
            } else {
                throw new IllegalStateException("It really shouldn't be possible to get here!  The hand wasn't moving fast enough to make the conveyor release control!");
            }

            Path path = new Path(currentTimeSec);
            path.addLine(collideLine);
            r.setPath(path);

            // handle powerups
            if (r.getType() == RecyclableType.ANVIL) {
                strikes--;
            }
            else if (r.getType() == RecyclableType.RABBIT) {
                logger.info("Rabbit Powerup");
                otherScreen.powerUpSpeedFactor = 1.5;
                otherScreen.timeToRemovePowerUp = otherScreen.lastWallTimeSec + 15;
            }
            else if (r.getType() == RecyclableType.TURTLE) {
                logger.info("Turtle Powerup");
                powerUpSpeedFactor = 0.5;
                timeToRemovePowerUp = lastWallTimeSec + 15;
            }
        }

        // We now have a list of items that the conveyor belt has released, and they know where they are going.
        // Handing control to TheForce!
        theForce.takeControlOfRecyclables(swipedOffConveyor);
        conveyorBelt.releaseRecyclables(swipedOffConveyor);
    }

    /**
     * Given the recyclable and the bin it went into this function either increments the score or adds a strike
     *
     * @param r
     * @param bin
     */
    public void handleScore(Recyclable r, RecycleBin bin) {
        if (bin.isCorrectRecyclableType(r)) {
            gameStatusDisplay.incrementScore(10);
            feedbackDisplay.addRight(r.getPosition(), currentTimeSec);
            SoundEffectEnum.CORRECT.playSound();

        } else {
            //TODO Need to make sure power ups are not counted here
            strikes++;
            feedbackDisplay.addWrong(r.getPosition(), currentTimeSec);
            SoundEffectEnum.WRONG.playSound();
        }

//
//        if (strikes >= gameOverStrikes) {
//            gameOver();
//        }
    }


    public void addLinkToOtherScreen(GameLogic otherScreen) {
        this.otherScreen = otherScreen;
    }

    public String getStrikesString() {
        return Integer.toString(strikes);
    }

    private void gameOver() {
        if (gameOverNotified) return;
        Sprite sprite = new Sprite("src/main/resources/SpriteImages/game_over_text.png", (GraphicsConstants.GAME_SCREEN_WIDTH / 2) - 220, (GraphicsConstants.GAME_SCREEN_HEIGHT / 2) - 200);
        gameScreen.addSprite(sprite);
        gameOverNotified = true;

        /*we want to pass this off to splash screen crew to save and store evrything*/
        SavePlayer playerScore = new SavePlayer();
        playerScore.submitPlayerScore(gameStatusDisplay.getScore());

        //If We want it to exit
        //gameManager.destroy();
    }

    private void increaseDifficulty() {
        possiblyIncreaseTypesInUse();
        increaseSpeed();
        increaseItemGenerationProbability();
    }

    private void possiblyIncreaseTypesInUse() {
        if (numItemTypesInUse < GameConstants.MAX_ITEM_COUNT && Math.round(currentTimeSec) > nextItemTypeGenerationTime) {
            numItemTypesInUse++;
            nextItemTypeGenerationTime += GameConstants.TIME_TO_ADD_NEW_ITEM_TYPE;
            factory.setNumItemTypesInUse(numItemTypesInUse);
        }
    }

    private void increaseSpeed() {
        double pctToMaxDifficulty = Math.min(1, wallTimeSec / GameConstants.TIME_TO_MAX_DIFFICULTY);
        timeSpeedFactor = pctToMaxDifficulty * (GameConstants.FINAL_TIME_SPEED_FACTOR - 1) + 1;
    }

    private void increaseItemGenerationProbability() {
        double pctToMaxDifficulty = Math.min(1, wallTimeSec / GameConstants.TIME_TO_MAX_DIFFICULTY);
        factory.increaseGenerationRate(pctToMaxDifficulty);
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    protected void updateThis() {
        updateTime();

        if (wallTimeSec >= timeToRemovePowerUp) {
            powerUpSpeedFactor = 1;
        }
        if(playerIsAComputer){
            computerPlayer.updateAI(conveyorBelt.getNextRecyclableThatIsTouchable(), currentTimeSec);
        }

            // display the hands
        for (Hand hand : hands) {
            hand.updateLocation();
        }


        // Handle existing item collisions
        lookForAndHandleCollisions();

        // Move Items
        conveyorBelt.moveItems(currentTimeSec);
        theForce.moveItems(currentTimeSec);
        feedbackDisplay.moveItems(currentTimeSec);
        // Release items at the end of the path
        List<Recyclable> itemsToRemove = conveyorBelt.releaseControlOfRecyclablesAtEndOfPath(currentTimeSec);
        for (Recyclable r : itemsToRemove) {
            handleScore(r, RecycleBin.TRASH_BIN);
            gameScreen.removeSprite(r.getSprite());
        }
        itemsToRemove = theForce.releaseControlOfRecyclablesAtEndOfPath(currentTimeSec);
        for (Recyclable r : itemsToRemove) {
            handleScore(r, recycleBins.findBinForFallingRecyclable(r));
            gameScreen.removeSprite(r.getSprite());
        }

        // Generate more items, if we feel like it
        if (!debuggingCollisions) {
            Recyclable r = factory.possiblyGenerateItem(conveyorBelt.getNewPath(), currentTimeSec);
            if (r != null) {
                try {
                    conveyorBelt.takeControlOfRecyclable(r);
                    gameScreen.addSprite(r.getSprite());
                } catch (ExceptionInInitializerError e) {
                    logger.error("ExceptionInInitializerError adding Recyclable with time " + currentTimeSec);
                }
            }
        }
        increaseDifficulty();

    }

    private void updateTime() {
        wallTimeSec = (System.currentTimeMillis() - startTime) / 1000.0;
        currentTimeSec += (wallTimeSec - lastWallTimeSec) * timeSpeedFactor * powerUpSpeedFactor;
        lastWallTimeSec = wallTimeSec;

    }
}
