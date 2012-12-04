package edu.mines.csci598.recycler.splashscreen.headers;


import edu.mines.csci598.recycler.splashscreen.graphics.CycleScreenCallback;
import edu.mines.csci598.recycler.splashscreen.graphics.SplashScreenSection;
import edu.mines.csci598.recycler.splashscreen.graphics.UpdateScreenCallback;

import javax.swing.*;
import java.awt.*;

public class InstructionHeader implements SplashScreenSection {

    private Image image;

    public InstructionHeader() {
        image =  new ImageIcon("src/main/resources/SpriteImages/instructions_half.png").getImage();

    }

    @Override
    public void initialize(Point topLeft, Point bottomRight, UpdateScreenCallback updateScreenCallback, CycleScreenCallback cycleScreenCallback) {
    }

    @Override
    public void draw(Graphics2D g) {
    }

    @Override
    public void stop() {
    }
}

