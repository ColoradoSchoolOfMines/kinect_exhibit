package edu.mines.csci598.recycler.splashscreen.highscores;

import javax.swing.*;

public class SavePlayer {
    public void submitPlayerScore(long score) {

        ImageIcon image = takePhoto();
        String initials = getInitials();

        //create object with score
        PlayerHighScoreInformation playerHighScoreInformation = new PlayerHighScoreInformation(initials, score, image);

        SerializePlayerInformation.savePlayerHighScoreInformation(playerHighScoreInformation);

        startSplashProcess();
    }

    private ImageIcon takePhoto() {
        return new ImageIcon();
    }

    private String getInitials() {
        return "";
    }

    private void startSplashProcess() {

    }
}
