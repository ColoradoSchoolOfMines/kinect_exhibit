package edu.mines.csci598.recycler.splashscreen.highscores;

import javax.swing.*;
import java.io.Serializable;
import java.util.Comparator;

//Andrew Suter-Morris
public class PlayerHighScoreInformation implements Serializable {

    private ImageIcon _playerImage;
    private String _playerInitials;
    private long _playerScore;

    public PlayerHighScoreInformation(String playerInitials, long playerScore, ImageIcon playerImage) {
        _playerInitials = playerInitials;
        _playerScore = playerScore;
        _playerImage = playerImage;
    }

    public ImageIcon getPlayerImage() {
        return _playerImage;
    }

    public void setPlayerImage(ImageIcon playerPlayerImage) {
        this._playerImage = playerPlayerImage;
    }

    public String getPlayerInitials() {
        return _playerInitials;
    }

    public void setPlayerInitials(String playerInitials) {
        this._playerInitials = playerInitials;
    }

    public long getPlayerScore() {
        return _playerScore;
    }

    public void setPlayerScore(long playerScore) {
        this._playerScore = playerScore;
    }

    public static Comparator<PlayerHighScoreInformation> playerInitialsComporator
            = new Comparator<PlayerHighScoreInformation>() {

        public int compare(PlayerHighScoreInformation playerInformation1, PlayerHighScoreInformation playerInformation2) {

            String initials1 = playerInformation1.getPlayerInitials();
            String initials2 = playerInformation2.getPlayerInitials();
            return initials1.compareTo(initials2);
        }

    };
}
