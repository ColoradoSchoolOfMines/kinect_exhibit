package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.backend.GameManager;
import edu.mines.csci598.recycler.backend.GameState;
import edu.mines.csci598.recycler.backend.ModalMouseMotionInputDriver;
import edu.mines.csci598.recycler.frontend.Recyclable.CollisionState;
import edu.mines.csci598.recycler.frontend.graphics.GameScreen;
import edu.mines.csci598.recycler.frontend.graphics.GraphicsConstants;
import edu.mines.csci598.recycler.frontend.graphics.Line;
import edu.mines.csci598.recycler.frontend.graphics.Path;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;
import org.apache.log4j.Logger;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
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
public class GameLogic extends GameState {
    private static final Logger logger = Logger.getLogger(GameLogic.class);
    
    private static GameLogic INSTANCE;
    private Player player1, player2;
    private ComputerPlayer computerPlayer;
    private GameScreen gameScreen;
    private GameManager gameManager;
    private RecycleBins recycleBins;
    private ConveyorBelt conveyor;
    private List<Recyclable> fallingItems;
    private double currentTimeSec;
    private long startTime;
    private double nextItemTypeGenerationTime;
    private int numItemTypesInUse;
    private int score;
    private int strikes;
    //TODO I (Joe) am adding this game over notfied stuff because it was causing game over to be displayed
    //over and over again too many times ont otp of each other
    private boolean gameOverNotified = false;


    private GameLogic() {
        gameManager = new GameManager("Recycler", false);
        gameScreen = GameScreen.getInstance();
        recycleBins = new RecycleBins();
        fallingItems = new ArrayList<Recyclable>();

        numItemTypesInUse = GameConstants.INITIAL_NUMBER_OF_ITEM_TYPES;
        currentTimeSec = 0;
        nextItemTypeGenerationTime = GameConstants.TIME_TO_ADD_NEW_ITEM_TYPE;

        conveyor = new ConveyorBelt(this);
        startTime = System.currentTimeMillis();

        // sets up the first player and adds its primary hand to the gameScreen
        // so that it can be displayed
        if (!GameConstants.DEBUG_COMPUTER_PLAYER) {
            player1 = new Player(gameManager);
            gameScreen.addHandSprite(player1.primary.getSprite());
        } else {
            computerPlayer = new ComputerPlayer(recycleBins);
            gameScreen.addHandSprite(computerPlayer.primary.getSprite());
        }
        if(GameConstants.DEBUG_COLLISIONS){
            Recyclable r = new Recyclable(RecyclableType.PLASTIC, ConveyorBelt.CONVEYOR_BELT_PATH);
              conveyor.addRecyclable(r);
        }
    }

    public static final GameLogic getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameLogic();
        }
        return INSTANCE;
    }

    public void addRecyclable(Recyclable r) {
        try {
            gameScreen.addSprite(r.getSprite());
        } catch (ExceptionInInitializerError e) {
            logger.error("ExceptionInInitializerError adding Recyclable with time " + currentTimeSec);
        }
    }

    private void potentiallyHandleCollision(Recyclable r) {
        if (!GameConstants.DEBUG_COMPUTER_PLAYER) {
        	Hand hand = player1.primary;
            //logger.info("rms="+r.getMotionState()+",msc="+MotionState.CONVEYOR);
            if(r.getMotionState()== MotionState.CONVEYOR) {
                // Find out what kind of collision happened, if any
                CollisionState collisionState = r.hasCollisionWithHand(hand, currentTimeSec);
                if(collisionState == CollisionState.NONE){
                    //logger.info("Item is untouchable");
                    return;
                }
                else{
                    Point2D position = r.getPosition();
                    Path path = new Path();
                    Line collideLine;
                    if (collisionState == CollisionState.HIT_RIGHT) {
                        //logger.debug("Pushed Right");
                        collideLine = new Line(position.getX(), position.getY(),
                                position.getX() + GameConstants.ITEM_PATH_END, position.getY());
                        //TODO: Items still touchable when falling
                        r.setMotionState(MotionState.FALL_RIGHT);
                    }
                    else if (collisionState == CollisionState.HIT_LEFT) {
                        //logger.debug("Pushed Left");
                        collideLine = new Line(position.getX(), position.getY(),
                                position.getX() - GameConstants.ITEM_PATH_END, position.getY());
                        //TODO: Items still touchable when falling
                        r.setMotionState(MotionState.FALL_LEFT);
                    }
                    else{
                        throw new IllegalStateException("Collision handling can't handle collision states other than right and left");
                    }
                    path.addLine(collideLine);
                    r.setPath(path);

                    fallingItems.add(r);

                    //Retrieves bin
                    RecycleBin bin = recycleBins.findBinForFallingRecyclable(r);
                    handleScore(r, bin);
                }
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
    public void handleScore(Recyclable r, RecycleBin bin) {
        if (!GameConstants.DEBUG_COMPUTER_PLAYER) {
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
        logger.debug("Item generation probability not increasing...");
        //itemGenerationProb = startProbability + (1 - startProbability) * GameConstants.TIME_TO_MAX_DIFFICULTY;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

	public GameScreen getGameScreen() {
		return gameScreen;
	}

    protected GameState updateThis(float elapsedTime) {
    	logger.debug("Entering main update loop");
    	
        //in seconds
        currentTimeSec = (System.currentTimeMillis() - startTime) / 1000.0;

        if (!GameConstants.DEBUG_COMPUTER_PLAYER) {
            // display the hand
            player1.primary.updateLocation();
        } else {
            //call update to computer hand on next recyclable that is touchable
            if (conveyor.getNumRecyclablesOnConveyor() > 0) {
                computerPlayer.updateAI(conveyor.getNextRecyclableThatIsTouchable(), currentTimeSec);
            }
            handleAIScore();
        }

        // Move conveyor and generate items
        conveyor.update(currentTimeSec);

        // Handle existing item collisions
        for (Recyclable r : conveyor.getRecyclables()) {
            potentiallyHandleCollision(r);
        }
        
        for(Recyclable r : fallingItems){
			Point2D newPosition = r.getPath().getLocation(r.getPosition(), GameConstants.HAND_COLLISION_PATH_SPEED_IN_PIXELS_PER_SECOND, elapsedTime); 
			r.setPosition(newPosition);
        }
        
        increaseDifficulty();
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