package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.backend.GameManager;
import edu.mines.csci598.recycler.backend.GameState;
import edu.mines.csci598.recycler.backend.ModalMouseMotionInputDriver;
import edu.mines.csci598.recycler.bettyCrocker.Song;
import edu.mines.csci598.recycler.bettyCrocker.Track;
import edu.mines.csci598.recycler.frontend.graphics.GameScreen;
import edu.mines.csci598.recycler.frontend.graphics.InstructionScreen;
import edu.mines.csci598.recycler.frontend.graphics.PlayerOptionsScreen;
import edu.mines.csci598.recycler.frontend.motion.ConveyorBelt;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;
import edu.mines.csci598.recycler.frontend.utils.PlayerMode;

import java.awt.*;

/**
 * This class launches 2 instances of GameLogic which represent the left and right games being played.
 * User: jzeimen
 * Date: 11/19/12
 * Time: 8:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameLauncher extends GameState {
	private GameManager gameManager;
	private GameLogic leftGame, rightGame;
    private GameStatusDisplay leftGameStatusDisplay, rightGameStatusDisplay;
	private GameScreen gameScreen;
    private boolean gameCanStart;
    private boolean gameStarted;
    private long timeInstructionsStarted;

    private InstructionScreen instructionScreen;
    private PlayerOptionsScreen playerOptions;

	public GameLauncher() {
        //Preloading the images will prevent some flickering.
        //TODO: Preload correct/incorrect images too
        RecyclableType.preLoadImages();
        GameScreen.getInstance().preLoadImages();

        // the boolean in gameManager determines if the screen is full screen or not
		gameManager = new GameManager("Recycler",true);

		gameScreen = GameScreen.getInstance();
        leftGameStatusDisplay = new GameStatusDisplay(Side.LEFT);
        rightGameStatusDisplay = new GameStatusDisplay(Side.RIGHT);

        boolean computerPlayer = false;

        leftGame = new GameLogic(
                new RecycleBins(RecycleBins.Side.LEFT),
                ConveyorBelt.getConveyorBeltPathLeft(),
                gameManager,
                leftGameStatusDisplay,
                false,
                false);
        rightGame = new GameLogic(
                new RecycleBins(RecycleBins.Side.RIGHT),
                ConveyorBelt.getConveyorBeltPathRight(),
                gameManager,
                rightGameStatusDisplay,
                true,
                GameConstants.DEBUG_COLLISIONS);
        leftGame.addLinkToOtherScreen(rightGame);
        rightGame.addLinkToOtherScreen(leftGame);

        gameScreen.addTextSpriteHolder(leftGameStatusDisplay);
        gameScreen.addTextSpriteHolder(rightGameStatusDisplay);


        instructionScreen = new InstructionScreen();
        gameCanStart = false;
        timeInstructionsStarted = System.currentTimeMillis() / 1000;

        playerOptions = new PlayerOptionsScreen(gameManager);
	}

    protected void setUpPlayerMode(PlayerMode mode) {
        // if there is a computer player set up the hands for a computer
        if (mode == PlayerMode.ONE_PLAYER)
            rightGame.setUpHands(true);
        else
            rightGame.setUpHands(false);
        leftGame.setUpHands(false);

    }

	protected void drawThis(Graphics2D g2d) {
        if (gameCanStart) {
            if (!playerOptions.canGameStart()) {
                playerOptions.paint(g2d, gameManager.getCanvas());
            }
            else {
                gameScreen.paint(g2d, gameManager.getCanvas());
            }
        }
        else {
            instructionScreen.paint(g2d, gameManager.getCanvas());
        }
	}

	public GameManager getGameManager() {
		return gameManager;
	}

	public GameLauncher updateThis(float time) {
        if (gameCanStart) {
          //  System.out.println("can start; " + playerOptions.canGameStart());
            if (!playerOptions.canGameStart()) {
                playerOptions.updateThis();
            }
            else {
                System.out.println("can start; " + playerOptions.canGameStart());
                if (!gameStarted) {
                    gameStarted = true;
                    setUpPlayerMode(playerOptions.getPlayerMode());
                }
		        leftGame.updateThis();
		        rightGame.updateThis();
            }
        }
        else {
            if ((System.currentTimeMillis() / 1000) > timeInstructionsStarted + 5) {
                gameCanStart = true;
            }
        }
		return this;
	}

    public static void main(String[] args) {
        Song x = new Song();
        x.addTrack(new Track("src/main/resources/Sounds/recyclotron.mp3"));
        x.setLooping(true);
        x.startPlaying();
        GameLauncher gm = new GameLauncher();
        ModalMouseMotionInputDriver mouse = new ModalMouseMotionInputDriver();
        gm.getGameManager().installInputDriver(mouse);
        gm.getGameManager().setState(gm);
        gm.getGameManager().run();
        gm.getGameManager().destroy();
    }

}
