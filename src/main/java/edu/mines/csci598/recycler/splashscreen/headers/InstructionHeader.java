package edu.mines.csci598.recycler.splashscreen.headers;


import edu.mines.csci598.recycler.splashscreen.graphics.SplashScreenSection;
import edu.mines.csci598.recycler.splashscreen.graphics.UpdateScreenCallback;

import javax.swing.*;
import java.awt.*;

public class InstructionHeader implements SplashScreenSection {

    private Image image;

    public InstructionHeader() {
        image = new ImageIcon("src/main/resources/SpriteImages/FinalSpriteImages/instructions_half.png").getImage();
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(image));
    }

    public static void main(String[] arg) {
    }

    @Override
    public void initialize(Point topLeft, Point bottomRight, UpdateScreenCallback callback) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void draw(Graphics2D g) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void stop() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
