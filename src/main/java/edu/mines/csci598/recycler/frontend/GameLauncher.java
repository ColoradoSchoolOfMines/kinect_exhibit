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
        RecyclableType.preLoadImages();
		gameManager = new GameManager("Recycler");

		gameScreen = GameScreen.getInstance();
        leftGameStatusDisplay = new GameStatusDisplay(Side.LEFT);
        rightGameStatusDisplay = new GameStatusDisplay(Side.RIGHT);

        gameScreen.addTextSpriteHolder(leftGameStatusDisplay);
        gameScreen.addTextSpriteHolder(rightGameStatusDisplay);


        instructionScreen = new InstructionScreen();
        gameCanStart = false;
        timeInstructionsStarted = System.currentTimeMillis() / 1000;

        playerOptions = new PlayerOptionsScreen(gameManager);
	}

    protected void setUpGameSides(PlayerMode mode) {
        boolean computerPlayer = false;
        if (mode == PlayerMode.ONE_PLAYER) {
            computerPlayer = true;
        }
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
                computerPlayer,
                GameConstants.DEBUG_COLLISIONS);
        leftGame.addLinkToOtherScreen(rightGame);
        rightGame.addLinkToOtherScreen(leftGame);
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

	public GameLauncher updateThis(float time) {

        if (gameCanStart) {
            if (!playerOptions.canGameStart()) {
                playerOptions.updateThis();
            }
            else {
                if (!gameStarted) {
                    gameStarted = true;
                    setUpGameSides(playerOptions.getPlayerMode());
                }
		        leftGame.updateThis();
		        rightGame.updateThis();
            }
        }
        else {
           // if ((System.currentTimeMillis() / 1000) > timeInstructionsStarted + 5) {
                gameCanStart = true;
           // }
        }
		return this;
	}

}
