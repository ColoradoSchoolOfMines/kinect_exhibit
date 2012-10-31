package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.*;
import edu.mines.csci598.recycler.frontend.utils.GameConstants;
import edu.mines.csci598.recycler.frontend.utils.Log;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;

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
	private static GameManager INSTANCE = new GameManager();
    Player player1, player2;
    GameScreen gameScreen;
    LinkedList<RecycleBin> recycleBins= new LinkedList<RecycleBin>();
    LinkedList<Recyclable> recyclables= new LinkedList<Recyclable>();
    LinkedList<Recyclable> recyclablesToRemove= new LinkedList<Recyclable>();
    double lastGenerateTime;
    double currentTime;
    double generateTimeDelay;
    boolean generateMultiple;
    int numItemTypes;

   private GameManager(){
       GameFrame gamePanel = GameFrame.getInstance();
       gameScreen = gamePanel.getGameScreen();
       numItemTypes = GameConstants.INITIAL_NUMBER_OF_ITEM_TYPES;
       lastGenerateTime=0;
       currentTime = 0;
       generateTimeDelay=GameConstants.INITIAL_ITEM_GENERATION_DELAY_SECONDS;
       generateMultiple=true;
       if(!generateMultiple){
           Recyclable r = new Recyclable(0, RecyclableType.getRandom(2));
           handleRecyclables(GameConstants.ADD_SPRITE, r);
       }
       gameUpdateLoop();
   }
   
   public static final GameManager getInstance(){
	   if(INSTANCE == null){
		   INSTANCE = new GameManager();
	   }
	   return INSTANCE;
   }
   
   private void generateItems(double currentTime){
       //Function will decide on item type to generate
       //Function will create a new sprite of that type
       //Function will add sprite to screen
	   
       if((currentTime-lastGenerateTime) > generateTimeDelay){
           //System.out.println("Ct:"+currentTime+",IT:"+itemType);
           try{
               Recyclable r = new Recyclable(currentTime, RecyclableType.getRandom(numItemTypes));
               handleRecyclables(GameConstants.ADD_SPRITE, r);
               
               lastGenerateTime=currentTime;
           }catch (ConcurrentModificationException e){
               Log.logError("Trying to generate a new sprite");}
       }
   }
   private synchronized void updateRecyclables(){
       Sprite sprite;

       try{
           for(Recyclable recyclable:recyclables){
               sprite = recyclable.getSprite();
               try{
                   sprite.updateLocation(currentTime);
                   //if(sprite.getX()>=700) sprites.remove(sprite);
                   if(sprite.getX()>=GameConstants.TOP_PATH_END_X) {
                       recyclablesToRemove.addLast(recyclable);
                       gameScreen.removeSprite(recyclable.getSprite());
                   }

                   if(sprite.getY()<=GameConstants.SPRITE_BECOMES_UNTOUCHABLE){
                       sprite.setState(GameConstants.UNTOUCHABLE);
                   }else if(sprite.getY()<=GameConstants.SPRITE_BECOMES_TOUCHABLE){
                       sprite.setState(GameConstants.TOUCHABLE);
                   }
                   checkCollision(recyclable);

               }catch (ConcurrentModificationException e){
                   Log.logError("Trying to update recyclable " + recyclable + " with time " + currentTime);
               }
           }
       }catch(ExceptionInInitializerError e){
           Log.logError("Trying to update sprites with time " + currentTime);
       }
       for(Recyclable recyclable: recyclablesToRemove){
           recyclables.remove(recyclable);
       }
       gameScreen.repaint();
   }
   public synchronized void addRecyclable(Recyclable r){
       try{
           recyclables.add(r);
           gameScreen.addSprite(r.getSprite());
       }catch(ExceptionInInitializerError e){
           Log.logError("Trying to add Recyclable with time " + currentTime);
       }
   }
    public synchronized void removeRecyclable(Recyclable r){
        recyclables.remove(r);
    }
   public synchronized void handleRecyclables(int flag, Recyclable r){
        if(flag == GameConstants.ADD_SPRITE) {
            addRecyclable(r);
        }
        else if (flag == GameConstants.REMOVE_SPRITE) removeRecyclable(r);
        else if (flag == GameConstants.UPDATE_SPRITES) updateRecyclables();
    }
   private synchronized void checkCollision(Recyclable r){
       if(r.getSprite().getState()==GameConstants.TOUCHABLE){
           if(gameScreen.hand.getX()>= r.getSprite().getX()+(GameConstants.SPRITE_X_OFFSET/2) &&
                   gameScreen.hand.getX()<= r.getSprite().getX()+(GameConstants.SPRITE_X_OFFSET*2)){
               if(gameScreen.hand.getY() >= r.getSprite().getY()+(GameConstants.SPRITE_Y_OFFSET/2) &&
                       gameScreen.hand.getY() <= r.getSprite().getY()+(GameConstants.SPRITE_Y_OFFSET*2)){
                   //Handle collision
                   //Log.logInfo("Collision detected on Sprite");

                   Log.logError("Sx="+r.getSprite().getX()+",Sy="+r.getSprite().getY()+
                                ",Hx="+gameScreen.hand.getX()+",Hy="+gameScreen.hand.getY()+
                                ",Hvx="+gameScreen.hand.getVelocityX()+",Hvy"+gameScreen.hand.getVelocityY());
                   //System.out.println("Collision detected on sprite");
                   r.getSprite().setState(GameConstants.UNTOUCHABLE);

                   //Handle sprite collision
                   if(gameScreen.hand.getVelocityX() > GameConstants.MIN_VELOCITY){
                       Path path= new Path();
                       Log.logInfo("Pushed Right");
                       Line collideLine = new Line(r.getSprite().getX(),r.getSprite().getY(),
                              r.getSprite().getX()+GameConstants.ITEM_PATH_END,r.getSprite().getY(),
                               GameConstants.ITEM_PATH_TIME);
                       path.addLine(collideLine);
                       r.getSprite().setPath(path);

                       r.getSprite().setHorizontalVelocity(GameConstants.HORIZONTAL_VELOCITY);
                   }else if(gameScreen.hand.getVelocityX() < -1 * GameConstants.MIN_VELOCITY){
                       Path path = new Path();
                       Log.logInfo("Pushed Left");
                       Line collideLine = new Line(r.getSprite().getX(),r.getSprite().getY(),
                               r.getSprite().getX()-GameConstants.ITEM_PATH_END,r.getSprite().getY(),
                               GameConstants.ITEM_PATH_TIME);
                       path.addLine(collideLine);
                       r.getSprite().setPath(path);

                       r.getSprite().setHorizontalVelocity(-1 * GameConstants.HORIZONTAL_VELOCITY);
                   }
               }
           }
       }

   }
   private void gameUpdateLoop(){
       long startTime = System.currentTimeMillis(); //in seconds



       while(true){
           currentTime = (System.currentTimeMillis()-startTime)/1000.0;
           if(generateMultiple)generateItems(currentTime);

           //see if hand is going through item and handle it
           // check coordinates here
           //gameScreen.checkCollisions();

           //see if hand hits powerup and handle it


           Recyclable r = new Recyclable(currentTime, RecyclableType.SKULL);
           //handleRecyclables(GameConstants.UPDATE_SPRITES, r);

           //tell the game manager to update, updates game screen
           updateRecyclables();


           //check to for winning condition.
       }
   }

   public static void main(String[] args){
       GameManager gm = GameManager.getInstance();
   }

}
