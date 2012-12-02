package edu.mines.csci598.recycler.frontend;

import edu.mines.csci598.recycler.frontend.graphics.Sprite;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: jrramey11
 * Date: 11/15/12
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class Strikes {
    private int strikesTotal;
    private static final Logger logger = Logger.getLogger(Score.class);
    Sprite[] sprites;
    public Strikes(){
        logger.trace("Constructing strikes object.");
        init();
    }

    private void init(){
        strikesTotal = 0;
        //Needs to be better defined.
        sprites = new Sprite[5];
        logger.trace("Initialized sprites array and strikes: " + strikesTotal);
    }

    public synchronized void increaseStrikes(){
        strikesTotal++;
        logger.debug("Strikes increased: " + strikesTotal);
    }

    public synchronized void decreaseStrikes(){
        strikesTotal--;
        logger.debug("Strikes decreased: " + strikesTotal);
    }

    public synchronized void decreaseStrikes(int decrementValue){
        strikesTotal = strikesTotal - decrementValue;
        logger.debug("Strikes decreased by value: " + decrementValue + " strikes: " + strikesTotal);
    }

    public int getStrikes(){
        logger.trace("Returned strikes " + strikesTotal);
        return strikesTotal;
    }

    public String getStringStrikes(){
        logger.trace("Returned string strikes: " + strikesTotal);
        return Integer.toString(strikesTotal);
    }

    public void pushToSprites(Sprite s){
        logger.trace("Pushing sprite on array");
        sprites[sprites.length] = s;

    }


}
