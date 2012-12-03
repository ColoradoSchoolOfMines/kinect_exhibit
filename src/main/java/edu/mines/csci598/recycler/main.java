package edu.mines.csci598.recycler;

import edu.mines.csci598.recycler.backend.GameManager;
import edu.mines.csci598.recycler.backend.OpenNIHandTrackerInputDriver;
import edu.mines.csci598.recycler.splashscreen.highscores.SavePlayer;

public class main {
    public static void main(String[] args) {
        GameManager man = new GameManager( "Kinect Application" );
        OpenNIHandTrackerInputDriver driver = new OpenNIHandTrackerInputDriver();
        driver.installInto( man );
    }
}
