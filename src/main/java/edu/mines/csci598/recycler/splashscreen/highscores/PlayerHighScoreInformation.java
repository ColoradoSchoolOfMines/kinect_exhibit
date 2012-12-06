package edu.mines.csci598.recycler.splashscreen.highscores;

import javax.swing.*;
import java.io.Serializable;
import java.util.Comparator;

public class PlayerHighScoreInformation implements Serializable {

    private ImageIcon playerImage;
    private String playerInitials;
    private long playerScore;

    public PlayerHighScoreInformation(String playerInitials, long playerScore, ImageIcon playerImage) {
        this.playerInitials = playerInitials;
        this.playerScore = playerScore;
        this.playerImage = playerImage;
    }

    public ImageIcon getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(ImageIcon playerPlayerImage) {
        this.playerImage = playerPlayerImage;
    }

    public String getPlayerInitials() {
        return playerInitials;
    }

    public void setPlayerInitials(String playerInitials) {
        this.playerInitials = playerInitials;
    }

    public long getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(long playerScore) {
        this.playerScore = playerScore;
    }


    public static Comparator<PlayerHighScoreInformation> playerInitialsComporator
            = new Comparator<PlayerHighScoreInformation>() {

        /**
         *
         * @param playerInformation1 first player to compare
         * @param playerInformation2 second player to compare
         * @return integer on how players compared
         */
        public int compare(PlayerHighScoreInformation playerInformation1, PlayerHighScoreInformation playerInformation2) {

            String initials1 = playerInformation1.getPlayerInitials();
            String initials2 = playerInformation2.getPlayerInitials();
            return initials1.compareTo(initials2);
        }

    };
}
