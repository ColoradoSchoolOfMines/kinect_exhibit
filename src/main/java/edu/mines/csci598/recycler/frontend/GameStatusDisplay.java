package edu.mines.csci598.recycler.frontend;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import edu.mines.csci598.recycler.frontend.graphics.TextSprite;
import edu.mines.csci598.recycler.frontend.graphics.TextSpritesHolder;

/**
 * The Game Status Display handles things like the score, or eventually  "game over" or other messages.
 * If you wan to give the user some feed back add your TextSprite here and make sure it is returned in the list
 * from the function getTextSprites()
 * User: jzeimen
 * Date: 11/23/12
 * Time: 10:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameStatusDisplay implements TextSpritesHolder {
    private static final Logger logger = Logger.getLogger(GameStatusDisplay.class);

    private int score;
    private TextSprite scoreSprite;
    private final Side side;
    String scoreFormat = "%06d";
    private boolean gameOver;

    public GameStatusDisplay(Side side) {
        this.side = side;
        score = 0;
        gameOver = false;
        Font f = new Font("Stencil",Font.BOLD, 40);
        Color c = Color.green;
        //If its on the left the sprites will need to have different positions.
        if(side == Side.LEFT){
            scoreSprite = new TextSprite("", f, c, 585, 70);
        }
        else {
            scoreSprite = new TextSprite("", f, c, 1100, 70);
        }
        scoreChanged();
    }

    @Override
    public List<TextSprite> getTextSprites() {
        return Arrays.asList(scoreSprite);
    }

    /**
     * Change the score the scoreSprite displays
     */
    private void scoreChanged() {
        scoreSprite.setMessage(String.format(scoreFormat, score));
    }

    public Side getSide() {
        return side;
    }

    public void incrementScore(int points) {
        if(!gameOver){
            score += points;
            //Keep score at or above zero
            score = Math.max(score, 0);
            scoreChanged();
        }
    }

    public int getScore() {
        return score;
    }

    public void setGameOverState(Boolean state) {
        gameOver = state;
    }
}
