package edu.mines.csci598.recycler.splashscreen.credits;

import edu.mines.csci598.recycler.splashscreen.graphics.CycleScreenCallback;
import edu.mines.csci598.recycler.splashscreen.graphics.SplashScreenSection;
import edu.mines.csci598.recycler.splashscreen.graphics.UpdateScreenCallback;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class CreditsScreen implements SplashScreenSection {

    private static final int DIVISION_PIXELS_FROM_RIGHT = 300;

    private static final int TIMER_DELAY = 10000;
    private int selectedScore;
    private Timer scoreSwitch;
    private Point topLeft;
    private Point bottomRight;
    private UpdateScreenCallback updateScreenCallback;
    private CycleScreenCallback cycleScreenCallback;
    private int timerUpdateCount = 0;

    public CreditsScreen() {
        selectedScore = 0;
    }

    private void drawDivision(Graphics2D g) {
        int height = bottomRight.y - topLeft.y;
        int width = bottomRight.x - topLeft.x;

        BufferedImage originalImage;
        try {
            originalImage = ImageIO.read((getClass().getResource("/SpriteImages/team.jpg")));
            /*int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            BufferedImage resizedImage = new BufferedImage(width, height, type);
            g = resizedImage.createGraphics();*/
            g.drawImage(originalImage, topLeft.x, topLeft.y, width, height, null);
        } catch (IOException e) {

        }



    }

    @Override
    public void initialize(Point topLeft, Point bottomRight, UpdateScreenCallback updateScreenCallback, CycleScreenCallback cycleScreenCallback) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.updateScreenCallback = updateScreenCallback;
        this.cycleScreenCallback = cycleScreenCallback;

        scoreSwitch = new Timer();
        scoreSwitch.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                CreditsScreen.this.updateScreenCallback.updateScreen();

                timerUpdateCount++;

                if (timerUpdateCount == 10) {
                    CreditsScreen.this.cycleScreenCallback.cycleScreen(CreditsScreen.this);
                }
            }
        }, TIMER_DELAY, TIMER_DELAY);
    }

    @Override
    public void draw(Graphics2D g) {
        drawDivision(g);
    }

    @Override
    public void stop() {
        scoreSwitch.cancel();
    }
}
