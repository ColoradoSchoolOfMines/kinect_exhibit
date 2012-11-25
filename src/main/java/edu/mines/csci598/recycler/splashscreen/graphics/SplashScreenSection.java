package edu.mines.csci598.recycler.splashscreen.graphics;

import java.awt.*;

public interface SplashScreenSection {
    public void initialize(Point topLeft, Point bottomRight, UpdateScreenCallback callback);
    public void draw(Graphics2D g);
    public void stop();
}