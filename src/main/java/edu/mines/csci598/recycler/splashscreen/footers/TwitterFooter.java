package edu.mines.csci598.recycler.splashscreen.footers;


import edu.mines.csci598.recycler.splashscreen.social.TwitterMessages;

import javax.swing.*;
import java.util.ArrayList;

public class TwitterFooter extends JPanel {

    private ArrayList<String> messages;
    private TwitterMessages twitterMessages;

    public TwitterFooter() {
        super();
        twitterMessages = new TwitterMessages();
        messages = twitterMessages.retrieveAllMessages();
    }


}
