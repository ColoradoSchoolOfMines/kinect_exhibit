/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mines.csci598.recycler.splashscreen.highscores;

import edu.mines.csci598.recycler.backend.GameManager;
import edu.mines.csci598.recycler.backend.OpenNIHandTrackerInputDriver;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author jimmie
 */
public class TestSavePlayer {
    public static void main(String[] args) {
        GameManager man = new GameManager( "TestSavePlayer" );
        OpenNIHandTrackerInputDriver driver = new OpenNIHandTrackerInputDriver();
        driver.installInto( man );
        SavePlayer sp = new SavePlayer();
        sp.submitPlayerScore( 10, man);
        ArrayList<PlayerHighScoreInformation> highscores = SerializePlayerInformation.retrievePlayerHighScoreInformation();

        ImageIcon icon = new ImageIcon();
        icon.setImage( highscores.get(0).getPlayerImage().getImage() );
        JOptionPane.showMessageDialog(null, icon);
    }
}
