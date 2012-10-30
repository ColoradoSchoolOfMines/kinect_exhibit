package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.*;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;
import edu.mines.csci598.recycler.frontend.utils.Log;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * The GameManager is where the game play logic is. The main game update loop will go here.
 * TODO:*******NEED TO FIX sprite linked list.  Sometimes throws exception*******
 *
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/20/12
 * Time: 9:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameManager {
	private static final GameManager INSTANCE = new GameManager();
    Player player1, player2;
    GameScreen gameScreen;
    ArrayList<RecycleBin> recycleBins;
    ArrayList<Recyclable> recyclables;
    double lastGenerateTime;
    double generateTimeDelay;

   private GameManager(){
       GamePanel game = GamePanel.getInstance();
       gameScreen = game.getGameScreen();
       lastGenerateTime=0;
       generateTimeDelay=GameConstants.INITIAL_ITEM_GENERATION_DELAY;
       gameUpdateLoop();
   }
   
   public static final GameManager getInstance(){
	   return INSTANCE;
   }
   
   private void generateSprite(double currentTime,RecyclableType itemType){
       Sprite s;
       Path p = new Path();
       p.addLine(new Line(0.0,0.0,700.0,00.0,10.0));
       if(itemType==RecyclableType.GLASS){
           s= new Sprite("src/main/resources/SpriteImages/glass.png", 0, 0, 0.1);
           s.setPath(p);
           s.setStartTime(currentTime);
           gameScreen.addSprite(s);
       }else if(itemType==RecyclableType.PLASTIC){
           s= new Sprite("src/main/resources/SpriteImages/jug.png", 0, 0, 0.1);
           s.setPath(p);
           s.setStartTime(currentTime);
           gameScreen.addSprite(s);
       }else if(itemType==RecyclableType.PAPER){
           Log.logError(("Paper not implemented yet"));
       }else if(itemType==RecyclableType.TRASH){
           Log.logError(("Trash not implemented yet"));
       }
   }
   private void generateItems(double currentTime,int numItemTypes){
       //Function will decide on item type to generate
       //Function will create a new sprite of that type
       //Function will add sprite to screen
       int itemType;

       if((currentTime-lastGenerateTime) > generateTimeDelay){
           //System.out.println("Ct:"+currentTime+",IT:"+itemType);
           try{
               generateSprite(currentTime,RecyclableType.getRandom());
               lastGenerateTime=currentTime;
           }catch (ConcurrentModificationException e){}
       }
   }
   private void gameUpdateLoop(){
       long startTime = System.currentTimeMillis(); //in seconds
       int numItemType = GameConstants.INITIAL_NUMBER_OF_ITEM_TYPES;

       while(true){
           double currentTime = (System.currentTimeMillis()-startTime)/1000.0;
           generateItems(currentTime,numItemType);

           //see if hand is going through item and handle it
           // check coordinates here

           //see if hand hits powerup and handle it

           //tell the game screen to update
           gameScreen.update(currentTime);

           //check to for winning condition.
       }
   }

   public static void main(String[] args){
       GameManager gm = new GameManager();
   }

}
