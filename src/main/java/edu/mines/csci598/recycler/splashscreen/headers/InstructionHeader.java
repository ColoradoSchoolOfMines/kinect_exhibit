package edu.mines.csci598.recycler.splashscreen.headers;


import edu.mines.csci598.recycler.splashscreen.graphics.CycleScreenCallback;
import edu.mines.csci598.recycler.splashscreen.graphics.GraphicsHelper;
import edu.mines.csci598.recycler.splashscreen.graphics.SplashScreenSection;
import edu.mines.csci598.recycler.splashscreen.graphics.UpdateScreenCallback;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class InstructionHeader implements SplashScreenSection {

    private UpdateScreenCallback callback;
    private Point topLeft;
    private Point bottomRight;

    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 20);
    private static final Font MESSAGE_FONT = new Font("SansSerif", Font.BOLD, 54);
    private static final Color TEXT_COLOR = new Color(240, 240, 240);
    private static final Color BACKGROUND_COLOR = new Color(100, 100, 100);

    public InstructionHeader() {

    }

    @Override
    public void initialize(Point topLeft, Point bottomRight, UpdateScreenCallback updateScreenCallback, CycleScreenCallback cycleScreenCallback) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    @Override
    public void draw(Graphics2D g) {
        drawBackground(g);
        drawMessage(g);
    }

    private void drawBackground(Graphics2D g) {
        Polygon frame = GraphicsHelper.getRectangle(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y);
        g.setColor(BACKGROUND_COLOR);
        g.fillPolygon(frame);
    }

    private void drawMessage(Graphics2D g) {
        String message = "Wave to begin the game!";

        g.setFont(MESSAGE_FONT);
        FontMetrics fontMetrics = g.getFontMetrics();
        Rectangle2D fontBounds = fontMetrics.getStringBounds(message, g);

        int height = bottomRight.y - topLeft.y;
        int width = bottomRight.x - topLeft.x;

        int totalXPadding = width - (int)fontBounds.getWidth();
        int fontStartX = topLeft.x + totalXPadding / 2;

        int totalYPadding = height - (int)fontBounds.getHeight();
        int fontStartY = topLeft.y + totalYPadding / 2 + (int)fontMetrics.getAscent();

        g.setColor(TEXT_COLOR);
        g.drawString(message, fontStartX, fontStartY);
    }

    @Override
    public void stop() {

    }
}

