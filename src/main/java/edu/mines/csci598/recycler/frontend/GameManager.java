package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.*;

import java.util.ArrayList;

/**
 * The GameManager is where the game play logic is. The main game update loop will go here.
 *
 *
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/20/12
 * Time: 9:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameManager {
    Player player1, player2;
    GameScreen gameScreen;
    ArrayList<RecycleBin> recycleBins;
    ArrayList<Recyclable> recyclables;
   public GameManager(){
       Game game = new Game();
       gameScreen = game.getGameScreen();
       Sprite s= new Sprite("src/main/resources/SpriteImages/glass.png", 0, 0, 0.1);
       Path p = new Path();
       p.addLine(new Line(0.0,0.0,700.0,00.0,10.0));
       s.setPath(p);
       s.setStartTime(0);

       gameScreen.addSprite(s);

       gameUpdateLoop();
   }
   private void gameUpdateLoop(){
       long startTime = System.currentTimeMillis(); //in seconds

       while(true){
           double current_time = (System.currentTimeMillis()-startTime)/1000.0;
           gameScreen.update(current_time);


           //see if hand is going through item and handle it
           //see if hand hits powerup and handle it
           //tell the game screen to update
           //check to for winning condition.
       }


   }

   public static void main(String[] args){
       GameManager gm = new GameManager();

   }

}
