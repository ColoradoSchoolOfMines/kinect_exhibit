package edu.mines.csci598.recycler.frontend;

import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: jrramey11
 * Date: 11/15/12
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Score {
    private int scoreTotal;
    private static final Logger logger = Logger.getLogger(Score.class);

    public Score(){
        logger.trace("Constructing new Score Object.");
        init();
    }

    public void init(){
        scoreTotal = 0;
        logger.trace("Score Initialized: " + scoreTotal);
    }

    public synchronized void increaseScore(){
        scoreTotal++;
        logger.debug("Score increased: " + scoreTotal);
    }

    public synchronized void decreaseScore(){
        scoreTotal--;
        logger.debug("Score decreased: " + scoreTotal);
    }

    public int getScore(){
        logger.trace("Returning score: " + scoreTotal);
        return scoreTotal;
    }

    public String getStringScore(){
        logger.trace("Returning string score: " + scoreTotal);
        return Integer.toString(scoreTotal);
    }

}
