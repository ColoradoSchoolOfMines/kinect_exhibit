package edu.mines.csci598.recycler.splashscreen.highscores;



import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static junit.framework.Assert.assertEquals;

public class PlayerHighScoreSerializationTest {

    private ArrayList<PlayerHighScoreInformation> _scores;
    private ImageIcon _playerImage;
    private long _playerScore;
    private String _playerInitials;

    @Before
    public void setup() throws IOException {
        _playerImage = new ImageIcon(getClass().getResource("recycle.jpg"));
        _playerScore = 50;
        _playerInitials = "ATM";

        File f = new File("testEmptyPlayer.dat");
        f = new File("testOnePlayer.dat");
        f = new File("testMultiplePlayer.dat");
    }

    @Test
    public void serializeEmptyPlayerHighScoreList() {
        _scores = new ArrayList<PlayerHighScoreInformation>();
        SerializePlayerInformation.storePlayerHighScoreInformation(_scores, "testEmptyPlayer.dat");
    }

    @Test
    public void serializeOnePlayerInHighScoreList() {
        _scores = new ArrayList<PlayerHighScoreInformation>();
        _scores.add(new PlayerHighScoreInformation(_playerInitials, _playerScore, _playerImage));
        SerializePlayerInformation.storePlayerHighScoreInformation(_scores, "testOnePlayer.dat");
    }

    @Test
    public void serializeMultiplePlayersInHighScoreList() {
        _scores = new ArrayList<PlayerHighScoreInformation>();
        _scores.add(new PlayerHighScoreInformation(_playerInitials, _playerScore, _playerImage));
        _scores.add(new PlayerHighScoreInformation(_playerInitials, _playerScore+1, _playerImage));
        SerializePlayerInformation.storePlayerHighScoreInformation(_scores, "testMultiplePlayer.dat");
    }

    @Test
    public void deserializeEmptyPlayerHighScoreList() {
        _scores = SerializePlayerInformation.retrievePlayerHighScoreInformation("testEmptyPlayer.dat");
        assertEquals(0, _scores.size());
    }

    @Test
    public void deserializeOnePlayerInHighScoreList() {
        _scores = SerializePlayerInformation.retrievePlayerHighScoreInformation("testOnePlayer.dat");
        assertEquals(1, _scores.size());
        PlayerHighScoreInformation player1 = _scores.get(0);
        assertEquals(50, player1.getPlayerScore());
        assertEquals("ATM", player1.getPlayerInitials());
    }

    @Test
    public void deserializeMultiplePlayersInHighScoreList() {
        _scores = SerializePlayerInformation.retrievePlayerHighScoreInformation("testMultiplePlayer.dat");
        assertEquals(2, _scores.size());
        PlayerHighScoreInformation player1 = _scores.get(1);
        assertEquals(51, player1.getPlayerScore());
        assertEquals("ATM", player1.getPlayerInitials());
    }
}
