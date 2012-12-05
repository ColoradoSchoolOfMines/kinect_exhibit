package edu.mines.csci598.recycler.splashscreen.highscores;


import edu.mines.csci598.recycler.backend.GameManager;
import javax.swing.*;

public class SavePlayer {
    GameManager man;

    /**
     * This is the hand-off between end-of-game and the splash screen
     * It will record the score, take a photo, grab initials and store it
     * to a top ten flat file.
     *
     * @param score      the score that the player(s) achieved
     */
    public void submitPlayerScore(long score) {

        ImageIcon image = takePhoto( man );
        String initials = getInitials();

        //create object with score
        PlayerHighScoreInformation playerHighScoreInformation = new PlayerHighScoreInformation(initials, score, image);

        SerializePlayerInformation.savePlayerHighScoreInformation(playerHighScoreInformation);

        startSplashProcess();
    }

    /**
     * This is the hand-off between end-of-game and the splash screen
     * It will record the score, take a photo, grab initials and store it
     * to a top ten flat file.
     *
     * @param score      the score that the player(s) achieved
     * @param man        the game manager allows us to take the photo
     */
    public void submitPlayerScore( long score, GameManager man ) {
        this.man = man;
        
        ImageIcon image = takePhoto( man );
        String initials = getInitials();

        //create object with score
        PlayerHighScoreInformation playerHighScoreInformation = new PlayerHighScoreInformation(initials, score, image);

        SerializePlayerInformation.savePlayerHighScoreInformation(playerHighScoreInformation);

        startSplashProcess();
    }

    private ImageIcon takePhoto( GameManager man ) {  
        ImageIcon pic = new ImageIcon( man.getImage() );
        
        return pic;
    }

    private String getInitials() {
        return "";
    }

    private void startSplashProcess() {

    }
}
