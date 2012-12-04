package edu.mines.csci598.recycler.frontend.graphics;

import java.awt.Component;
import java.awt.Graphics2D;

import org.apache.log4j.Logger;


/**
 * Created with IntelliJ IDEA.
 * User: Amanreet
 * Date: 11/27/12
 * Time: 9:17 PM
 * This class takes care of displaying the splash screen
 */
public class InstructionScreen {

    private static final Logger logger = Logger.getLogger(InstructionScreen.class);
    private Sprite background;

    public InstructionScreen() {
        background = new Sprite("src/main/resources/SpriteImages/FinalSpriteImages/instructions_full.jpg", 0, 0);
    }

    public void paint(Graphics2D g2d, Component canvas) {
        g2d.drawImage(background.getImage(), background.getX(), background.getY(), canvas);
    }

}

