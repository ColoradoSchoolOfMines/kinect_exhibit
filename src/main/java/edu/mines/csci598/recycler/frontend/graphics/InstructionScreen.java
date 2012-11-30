package edu.mines.csci598.recycler.frontend.graphics;

import edu.mines.csci598.recycler.backend.GameManager;
import edu.mines.csci598.recycler.frontend.PlayerHand;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Amanreet
 * Date: 11/27/12
 * Time: 9:17 PM
 * This class takes care of displaying the splash screen
 */
public class InstructionScreen {
    private static final Logger logger = Logger.getLogger(GameScreen.class);

    private Sprite background;
    private GameManager gameManager;
    private int waveTimes;

    public InstructionScreen(GameManager gameManager) {
        this.gameManager = gameManager;
        background = new Sprite("src/main/resources/SpriteImages/instructions.jpg",0,0);
    }

    public void paint(Graphics2D g2d, Component canvas) {
        g2d.drawImage(background.getImage(), background.getX(), background.getY(), canvas);
    }
}

