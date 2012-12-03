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
        String message = messages.get(messageNumber);
        message = convertToHtml(message);
        messageNumber++;

    }

    private static String convertToHtml(String text)
    {
        return "<html>" + text.replaceAll("\n", "<br />") +"</html>";
    }

    @Override
    public void initialize(Point topLeft, Point bottomRight, UpdateScreenCallback callback) {

    }

    @Override
    public void draw(Graphics2D g) {

    }

    @Override
    public void stop() {

    }
}
