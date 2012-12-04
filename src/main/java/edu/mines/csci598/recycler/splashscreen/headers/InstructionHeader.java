package edu.mines.csci598.recycler.splashscreen.headers;


import edu.mines.csci598.recycler.splashscreen.graphics.CycleScreenCallback;
import edu.mines.csci598.recycler.splashscreen.graphics.SplashScreenSection;
import edu.mines.csci598.recycler.splashscreen.graphics.UpdateScreenCallback;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class InstructionHeader implements SplashScreenSection {

    private Image image;
    private ImageObserver imageObserver;
    private UpdateScreenCallback callback;
    private Point topLeft;
    private Point bottomRight;

    public InstructionHeader(Component imageObserver) {
        image =  new ImageIcon("src/main/resources/SpriteImages/FinalSpriteImages/instructions_half.jpg").getImage();
        this.imageObserver = imageObserver;
    }

    @Override
    public void initialize(Point topLeft, Point bottomRight, UpdateScreenCallback updateScreenCallback, CycleScreenCallback cycleScreenCallback) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    @Override
    public void draw(Graphics2D g) {
        int width = bottomRight.x - topLeft.x;
        int height = bottomRight.y - topLeft.y;
        g.drawImage(image, topLeft.x, topLeft.y, width, height, imageObserver);
    }

    @Override
    public void stop() {

    }
}

