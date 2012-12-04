package edu.mines.csci598.recycler.splashscreen.footers;


import edu.mines.csci598.recycler.splashscreen.graphics.CycleScreenCallback;
import edu.mines.csci598.recycler.splashscreen.graphics.GraphicsHelper;
import edu.mines.csci598.recycler.splashscreen.graphics.SplashScreenSection;
import edu.mines.csci598.recycler.splashscreen.graphics.UpdateScreenCallback;
import edu.mines.csci598.recycler.splashscreen.social.TwitterMessages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class TwitterFooter implements ActionListener, SplashScreenSection {

    private ArrayList<String> messages;
    private TwitterMessages twitterMessages;
    private Timer timer;
    private int messageNumber;
    private Point topLeft;
    private Point bottomRight;
    private UpdateScreenCallback callback;

    private static String TITLE = "Twitter";

    public TwitterFooter() {
        messageNumber = 0;
        twitterMessages = new TwitterMessages();
        messages = twitterMessages.retrieveAllMessages();
        timer = new Timer(10000, this);
        timer.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        timer.start();
        updateMessage();
    }

    private void updateMessage() {
        if (messageNumber == messages.size()) {
            messageNumber = 0;
            messages = twitterMessages.retrieveAllMessages();
        }

        messageNumber++;
        callback.updateScreen();
    }

    private static String convertToHtml(String text)
    {
        return "<html>" + text.replaceAll("\n", "<br />") +"</html>";
    }

    @Override
    public void initialize(Point topLeft, Point bottomRight, UpdateScreenCallback updateScreenCallback, CycleScreenCallback cycleScreenCallback) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.callback = updateScreenCallback;
    }

    @Override
    public void draw(Graphics2D g) {
        drawBackground(g);
        drawTitle(g);
        drawMessage(g);
    }

    private void drawBackground(Graphics2D g) {
        Polygon frame = GraphicsHelper.getRectangle(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y);
        g.setColor(new Color(250, 250, 250));
        g.fillPolygon(frame);
    }

    private void drawTitle(Graphics2D g) {
        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        g.setColor(new Color(40, 40, 40));
        g.drawString(TITLE, topLeft.x + 20, topLeft.y + 20);
    }

    private void drawMessage(Graphics2D g) {
        String message = messages.get(messageNumber);

        g.setFont(new Font("SansSerif", Font.PLAIN, 16));
        FontMetrics fontMetrics = g.getFontMetrics();
        Rectangle2D fontBounds = fontMetrics.getStringBounds(message, g);

        int height = bottomRight.y - topLeft.y;
        int width = bottomRight.x - topLeft.x;

        int totalXPadding = width - (int)fontBounds.getWidth();
        int fontStartX = topLeft.x + totalXPadding / 2;

        int totalYPadding = height - (int)fontBounds.getHeight();
        int fontStartY = topLeft.y + totalYPadding / 2 + (int)fontMetrics.getAscent();

        g.setColor(new Color(40, 40, 40));
        g.drawString(message, fontStartX, fontStartY);
    }

    @Override
    public void stop() {
        timer.stop();
    }
}
