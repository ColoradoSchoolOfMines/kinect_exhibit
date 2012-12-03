package edu.mines.csci598.recycler.splashscreen.footers;


import edu.mines.csci598.recycler.splashscreen.graphics.SplashScreenSection;
import edu.mines.csci598.recycler.splashscreen.graphics.UpdateScreenCallback;
import edu.mines.csci598.recycler.splashscreen.social.TwitterMessages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TwitterFooter implements ActionListener, SplashScreenSection {

    private ArrayList<String> messages;
    private TwitterMessages twitterMessages;
    private Timer timer;
    private int messageNumber;
    private boolean checkForNewMessages;
    private JLabel twitterLabel;

    public TwitterFooter() {
        checkForNewMessages = false;
        messageNumber = 0;
        twitterMessages = new TwitterMessages();
        messages = twitterMessages.retrieveAllMessages();
        timer = new Timer(10000, this);
        timer.start();
        /*scoreSwitch.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                selectedScore = (selectedScore + 1) % top10Scores.size();
                HighScoreScreen.this.callback.updateScreen();
            }
        }, TIMER_DELAY, TIMER_DELAY);*/
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        timer.start();
        updateMessage();
    }

    private void updateMessage() {
        if (messages.isEmpty()) {
            //do nada
        }
    }

    private static String convertToHtml(String text)
    {
        return "<html>" + text.replaceAll("\n", "<br />") +"</html>";
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
