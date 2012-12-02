package edu.mines.csci598.recycler.splashscreen.highscores;


import edu.mines.csci598.recycler.backend.GameManager;
import javax.swing.*;

public class SavePlayer {
    GameManager man;
    
    public void submitPlayerScore(long score) {

        ImageIcon image = takePhoto( man );
        String initials = getInitials();

        //create object with score
        PlayerHighScoreInformation playerHighScoreInformation = new PlayerHighScoreInformation(initials, score, image);

        SerializePlayerInformation.savePlayerHighScoreInformation(playerHighScoreInformation);

        startSplashProcess();
    }
    
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
