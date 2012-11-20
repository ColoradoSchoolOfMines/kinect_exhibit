package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.backend.GameManager;
import edu.mines.csci598.recycler.backend.GameState;
import edu.mines.csci598.recycler.backend.ModalMouseMotionInputDriver;
import edu.mines.csci598.recycler.frontend.graphics.GameScreen;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 11/19/12
 * Time: 8:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParentGameLogic extends GameState {
        private GameManager gameManager;
        private GameLogic leftGame,rightGame;
    private GameScreen gameScreen;
        public ParentGameLogic (){
            gameManager = new GameManager("Recycler", false);
            gameScreen = GameScreen.getInstance();
            leftGame = new GameLogic(new RecycleBins(RecycleBins.Side.LEFT), ConveyorBelt.CONVEYOR_BELT_PATH_LEFT,gameManager);
            rightGame = new GameLogic(new RecycleBins(RecycleBins.Side.RIGHT), ConveyorBelt.CONVEYOR_BELT_PATH_RIGHT,gameManager);
        }
        protected void drawThis(Graphics2D g2d) {
            gameScreen.paint(g2d, gameManager.getCanvas());
        }
        public GameManager getGameManager(){
              return gameManager;
        }
        public static void main(String[] args) {
            ParentGameLogic gm = new ParentGameLogic();
            ModalMouseMotionInputDriver mouse = new ModalMouseMotionInputDriver();
            gm.getGameManager().installInputDriver(mouse);
            gm.getGameManager().setState(gm);
            gm.getGameManager().run();
            gm.getGameManager().destroy();
        }

        public ParentGameLogic updateThis(float time){
            leftGame.updateThis(time);
            rightGame.updateThis(time);
            return this;
        }

}
