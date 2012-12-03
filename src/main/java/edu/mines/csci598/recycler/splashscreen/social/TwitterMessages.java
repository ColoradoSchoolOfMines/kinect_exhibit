package edu.mines.csci598.recycler.splashscreen.social;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.util.ArrayList;
import java.util.logging.Logger;

public class TwitterMessages implements SocialMessages {

    private final Logger log = Logger.getLogger(TwitterMessages.class.getName());
    private Twitter twitter;

    public TwitterMessages() {
        twitter = new TwitterFactory().getInstance();
    }

    @Override
    public String retrieveLatestMessage() {
        if (!retrieveAllMessages().isEmpty())
            return retrieveAllMessages().get(0);
        return null;
    }

    public ArrayList<String> retrieveAllMessages() {
        ArrayList<String> messageList = new ArrayList<String>();

        try {
            if (!authorizeAccess())
                return messageList;
            ResponseList<Status> twitterStatuses = twitter.getMentionsTimeline();
            for (Status status : twitterStatuses)
                messageList.add("@"+ status.getUser().getScreenName() + " - " + status.getText());

        }
        catch (TwitterException te) {
            log.severe("Failed to get timeline: " + te.getMessage());
        }

        return messageList;
    }

    private boolean authorizeAccess() throws TwitterException {
        try {
            RequestToken requestToken = twitter.getOAuthRequestToken();
            AccessToken accessToken = null;
            accessToken = getAccessToken(requestToken, accessToken);
            log.info("Got access token.");
            log.info("Access token: " + accessToken.getToken());
            log.info("Access token secret: " + accessToken.getTokenSecret());
        } catch (IllegalStateException ie) {
            if (!twitter.getAuthorization().isEnabled()) {
                log.severe("OAuth consumer key/secret is not set.");
                return false;
            }
        }
        return true;
    }

    private AccessToken getAccessToken(RequestToken requestToken, AccessToken accessToken) {
        while (null == accessToken) {
            log.info("Open the following URL and grant access to your account:");
            log.info(requestToken.getAuthorizationURL());
            try {
                accessToken = twitter.getOAuthAccessToken(requestToken);
            }
            catch (TwitterException te) {
                if (401 == te.getStatusCode()) {
                    log.severe("Unable to get the access token.");
                } else {
                    te.printStackTrace();
                }
            }
        }
        return accessToken;
    }
}