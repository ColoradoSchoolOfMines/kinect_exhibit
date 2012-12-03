package edu.mines.csci598.recycler.splashscreen.highscores;



import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static junit.framework.Assert.assertEquals;

public class PlayerHighScoreSerializationTest {

    private ArrayList<PlayerHighScoreInformation> scores;
    private ImageIcon playerImage;
    private long playerScore;
    private String playerInitials;

    @Before
    public void setup() throws IOException {
        playerImage = new ImageIcon(getClass().getResource("recycle.jpg"));
        playerScore = 50;
        playerInitials = "ATM";

        File f = new File("testEmptyPlayer.dat");
        f = new File("testOnePlayer.dat");
        f = new File("testMultiplePlayer.dat");
    }

    @Test
    public void serializeEmptyPlayerHighScoreList() {
        scores = new ArrayList<PlayerHighScoreInformation>();
        SerializePlayerInformation.storePlayerHighScoreInformation(scores, "testEmptyPlayer.dat");
    }

    @Test
    public void serializeOnePlayerInHighScoreList() {
        scores = new ArrayList<PlayerHighScoreInformation>();
        scores.add(new PlayerHighScoreInformation(playerInitials, playerScore, playerImage));
        SerializePlayerInformation.storePlayerHighScoreInformation(scores, "testOnePlayer.dat");
    }

    @Test
    public void serializeMultiplePlayersInHighScoreList() {
        scores = new ArrayList<PlayerHighScoreInformation>();
        scores.add(new PlayerHighScoreInformation(playerInitials, playerScore, playerImage));
        scores.add(new PlayerHighScoreInformation(playerInitials, playerScore + 1, playerImage));
        SerializePlayerInformation.storePlayerHighScoreInformation(scores, "testMultiplePlayer.dat");
    }

    @Test
    public void deserializeEmptyPlayerHighScoreList() {
        scores = SerializePlayerInformation.retrievePlayerHighScoreInformation("testEmptyPlayer.dat");
        assertEquals(0, scores.size());
    }

    @Test
    public void deserializeOnePlayerInHighScoreList() {
        scores = SerializePlayerInformation.retrievePlayerHighScoreInformation("testOnePlayer.dat");
        assertEquals(1, scores.size());
        PlayerHighScoreInformation player1 = scores.get(0);
        assertEquals(50, player1.getPlayerScore());
        assertEquals("ATM", player1.getPlayerInitials());
    }

    @Test
    public void deserializeMultiplePlayersInHighScoreList() {
        scores = SerializePlayerInformation.retrievePlayerHighScoreInformation("testMultiplePlayer.dat");
        assertEquals(2, scores.size());
        PlayerHighScoreInformation player1 = scores.get(1);
        assertEquals(51, player1.getPlayerScore());
        assertEquals("ATM", player1.getPlayerInitials());
    }
}
