package edu.mines.csci598.recycler.splashscreen.social;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.util.ArrayList;
import java.util.logging.Logger;

public class TwitterMessages implements SocialMessages {

    private static final Logger log = Logger.getLogger(TwitterMessages.class.getName());
    private Twitter twitter;

    public TwitterMessages() {
        twitter = new TwitterFactory().getInstance();
    }

    /**
     *
     * @return get the latest twitter mention via API
     */
    @Override
    public String retrieveLatestMessage() {
        if (!retrieveAllMessages().isEmpty())
            return retrieveAllMessages().get(0);
        return null;
    }

    /**
     *
     * @return get the last 20 twitter mentions from API
     */
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

    /**
     *
     * @return whether or not we were authorized to use twitter api
     * @throws TwitterException if we don't have proper OAuth keys
     */
    private boolean authorizeAccess() throws TwitterException {
        try {
            RequestToken requestToken = twitter.getOAuthRequestToken();
            AccessToken accessToken = null;
            accessToken = getAccessToken(requestToken, accessToken);
        } catch (IllegalStateException ie) {
            if (!twitter.getAuthorization().isEnabled()) {
                log.severe("OAuth consumer key/secret not set.");
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param requestToken send request to retrieve OAuthAccessToken
     * @param accessToken token that is set to OAuthAccessToken
     * @return the access token
     */
    private AccessToken getAccessToken(RequestToken requestToken, AccessToken accessToken) {
        while (null == accessToken) {
            try {
                accessToken = twitter.getOAuthAccessToken(requestToken);
            }
            catch (TwitterException te) {
                if (401 == te.getStatusCode())
                    log.severe("Unable to get the access token.");
                else
                    log.severe("Other status code " + te);

            }
        }
        return accessToken;
    }
}