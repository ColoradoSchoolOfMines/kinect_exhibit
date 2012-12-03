package edu.mines.csci598.recycler.splashscreen.footers;


import edu.mines.csci598.recycler.splashscreen.social.TwitterMessages;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TwitterFooter extends JPanel implements ActionListener {

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
        setVisible(true);
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

    public static void main(String[] arg) {
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(1280, 720);

        TwitterFooter footer = new TwitterFooter();
        mainFrame.add(footer);

        mainFrame.setVisible(true);
    }
}
