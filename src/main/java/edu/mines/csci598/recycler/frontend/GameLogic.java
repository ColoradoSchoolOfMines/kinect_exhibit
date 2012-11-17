package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.backend.GameManager;
import edu.mines.csci598.recycler.backend.GameState;
import edu.mines.csci598.recycler.backend.ModalMouseMotionInputDriver;
import edu.mines.csci598.recycler.frontend.graphics.GameScreen;
import edu.mines.csci598.recycler.frontend.graphics.GraphicsConstants;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


/**
 * The GameLogic is where the game play logic is. The main game update loop will go here.
 * <p/>
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/20/12
 * Time: 9:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameLogic extends GameState {
    private static final Logger logger = Logger.getLogger(GameLogic.class);

    private static GameLogic INSTANCE;
    private Player player1, player2;
    private ComputerPlayer computerPlayer;
    private boolean debugComputerPlayer;
    private GameScreen gameScreen;
    private GameManager gameManager;
    private RecycleBins recycleBins;
    private ConveyorBelt conveyor;
    private Random random;
    private double currentTimeSec;
    private long startTime;
    private double minTimeBetweenGenerations;
    private double meanTimeBetweenItems;
    private double nextItemTypeGenerationTime;
    private double lastGenerationTime;
    private double nextItemGenerationTime;
    private boolean debugCollision;
    private int numItemTypesInUse;
    private int score;
    private int strikes;
    private int gameOverStrikes;
    //TODO I (Joe) am adding this game over notfied stuff because it was causing game over to be displayed
    //over and over again too many times ont otp of each other
    private boolean gameOverNotified = false;


    private GameLogic() {
        debugComputerPlayer = false;

        gameManager = new GameManager("Recycler", false);
        gameScreen = GameScreen.getInstance(debugComputerPlayer);
        recycleBins = new RecycleBins();

        random = new Random(System.currentTimeMillis());
        numItemTypesInUse = GameConstants.INITIAL_NUMBER_OF_ITEM_TYPES;
        lastGenerationTime = 0;
        currentTimeSec = 0;
        nextItemTypeGenerationTime = GameConstants.TIME_TO_ADD_NEW_ITEM_TYPE;
        meanTimeBetweenItems = GameConstants.INITIAL_MEAN_TIME_BETWEEN_GENERATIONS;
        nextItemGenerationTime = GameConstants.INITIAL_MEAN_TIME_BETWEEN_GENERATIONS;
        nextItemGenerationTime = GameConstants.TIME_TO_ADD_NEW_ITEM_TYPE;
        gameOverStrikes = 3;

        conveyor = new ConveyorBelt(this);
        startTime = System.currentTimeMillis();


        // sets up the first player and adds its primary hand to the gameScreen
        // so that it can be displayed
        if (!debugComputerPlayer) {
            player1 = new Player(gameManager);
            gameScreen.addHandSprite(player1.primary.getSprite());
        } else {
            computerPlayer = new ComputerPlayer(recycleBins);
            gameScreen.addHandSprite(computerPlayer.primary.getSprite());
        }
    }

    public static final GameLogic getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameLogic();
        }
        return INSTANCE;
    }

    private void updateRecyclables() {
        ArrayList<Recyclable> recyclablesToRemove = new ArrayList<Recyclable>();

        try {
            for (Recyclable recyclable : conveyor.getRecyclables()) {
                Sprite sprite = recyclable.getSprite();

                sprite.updateLocation(currentTimeSec);

                //If the item has reached its final point on the path.
                //Remove it and update the score.
                if (sprite.hasFinishedPath(currentTimeSec)) {
                    recyclablesToRemove.add(recyclable);
                    handleScore(recyclable, recycleBins.getLast());
                }


                // make sure the item is still on the conveyor before changing it's touch status
                if (recyclable.getCurrentMotion() != Recyclable.MotionState.FALL_RIGHT &&
                        recyclable.getCurrentMotion() != Recyclable.MotionState.FALL_LEFT) {
                    if (sprite.getY() <= GameConstants.SPRITE_BECOMES_UNTOUCHABLE) {
                        sprite.setState(Sprite.TouchState.UNTOUCHABLE);
                    } else if (sprite.getY() <= GameConstants.SPRITE_BECOMES_TOUCHABLE) {
                        sprite.setState(Sprite.TouchState.TOUCHABLE);
                    }
                }
                checkCollision(recyclable);


            }
        } catch (ExceptionInInitializerError e) {
            logger.error("ExceptionInInitializerError updating sprites with time " + currentTimeSec);
        }

        //Can't modify in previous loop because it is iterating need this to avoid
        //a concurrent modification exception.
        for (Recyclable recyclable : recyclablesToRemove) {
            removeRecyclable(recyclable);
        }
    }

    public void addRecyclable(Recyclable r) {
        try {
            gameScreen.addSprite(r.getSprite());
        } catch (ExceptionInInitializerError e) {
            logger.error("ExceptionInInitializerError adding Recyclable with time " + currentTimeSec);
        }
    }

    public void removeRecyclable(Recyclable r) {
        conveyor.removeRecyclable(r);
        gameScreen.removeSprite(r.getSprite());
    }

    private void checkCollision(Recyclable r) {
        if (!debugComputerPlayer) {
            if (r.hasCollisionWithHand(player1.primary, currentTimeSec)) {
                r.getSprite().setState(Sprite.TouchState.UNTOUCHABLE);
                //Retrieves bin
                RecycleBin bin = recycleBins.findBinForFallingRecyclable(r);
                handleScore(r, bin);
            }
        } else {
            //Computer collision detection
        }
    }


    /**
     * Given the recyclable and the bin it went into this function either increments the score or adds a strike
     *
     * @param r
     * @param bin
     */
    private void handleScore(Recyclable r, RecycleBin bin) {
        if (!debugComputerPlayer) {
            if (bin.isCorrectRecyclableType(r)) {
                score++;
            } else {
                strikes++;
            }
        } else {
            handleAIScore();
        }
//
//        if (strikes >= gameOverStrikes) {
//            gameOver();
//        }
    }

    public void handleAIScore() {
        score = computerPlayer.getAIScore();
        strikes = computerPlayer.getAIStrikes();
    }

    public String getScoreString() {
        return Integer.toString(score);
    }

    public String getStrikesString() {
        return Integer.toString(strikes);
    }

	public int getNumItemTypesInUse() {
		return numItemTypesInUse;
	}

    private void gameOver() {
        if (gameOverNotified) return;
        Sprite sprite = new Sprite("src/main/resources/SpriteImages/GameOverText.png", (GraphicsConstants.GAME_SCREEN_WIDTH / 2) - 220, (GraphicsConstants.GAME_SCREEN_HEIGHT / 2) - 200);
        gameScreen.addSprite(sprite);
        gameOverNotified = true;
        //If We want it to exit
        //gameManager.destroy();
    }

    private void increaseDifficulty() {
        possiblyIncreaseItemCount();
        increaseSpeed();
        increaseItemGenerationProbability();
    }

    private void possiblyIncreaseItemCount() {
        if (numItemTypesInUse < GameConstants.MAX_ITEM_COUNT && Math.round(currentTimeSec) > nextItemTypeGenerationTime) {
            numItemTypesInUse++;
            logger.info("Increasing item types to " + numItemTypesInUse + "!");
            nextItemTypeGenerationTime += GameConstants.TIME_TO_ADD_NEW_ITEM_TYPE;
        }
    }

    private void increaseSpeed() {
        double pctToMaxDifficulty = Math.min(1, currentTimeSec / GameConstants.TIME_TO_MAX_DIFFICULTY);
        conveyor.setSpeed(pctToMaxDifficulty);
    }

    private void increaseItemGenerationProbability() {
        double startProbability = GameConstants.START_ITEM_GENERATION_PROB;
        //itemGenerationProb = startProbability + (1 - startProbability) * GameConstants.TIME_TO_MAX_DIFFICULTY;
    }


    public GameManager getGameManager() {
        return this.gameManager;
    }

    protected GameState updateThis(float elapsedTime) {

        //in seconds
        currentTimeSec = (System.currentTimeMillis() - startTime) / 1000.0;

        increaseDifficulty();

        conveyor.update(currentTimeSec);

        if (!debugComputerPlayer) {
            // display the hand
            player1.primary.updateLocation();
        } else {
            //call update to computer hand on next recyclable that is touchable
            if (conveyor.getNumRecyclables() > 0) {
                computerPlayer.updateAI(conveyor.getNextRecyclableThatIsTouchable(), currentTimeSec);
            }
            handleAIScore();
        }

        updateRecyclables();

        //check for winning condition.
        return this;
    }

    protected void drawThis(Graphics2D g2d) {
        gameScreen.paint(g2d, gameManager.getCanvas());
    }

    public static void main(String[] args) {
        GameLogic gm = GameLogic.getInstance();
        ModalMouseMotionInputDriver mouse = new ModalMouseMotionInputDriver();
        gm.getGameManager().installInputDriver(mouse);
        gm.getGameManager().setState(gm);
        gm.getGameManager().run();
        gm.getGameManager().destroy();
    }

}