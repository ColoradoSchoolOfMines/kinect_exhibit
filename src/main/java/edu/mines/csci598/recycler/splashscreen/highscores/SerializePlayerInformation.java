package edu.mines.csci598.recycler.splashscreen.highscores;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//Andrew Suter-Morris
public class SerializePlayerInformation {

    private static final int NUM_PLAYER_SCORES_TO_STORE = 10;
    private static final String SCORES_FILE = "scores.dat";

    public static ArrayList<PlayerHighScoreInformation> retrievePlayerHighScoreInformation() {
        return retrievePlayerHighScoreInformation(SCORES_FILE);
    }

    public static ArrayList<PlayerHighScoreInformation> retrievePlayerHighScoreInformation(String fileName) {
        Object deserializedHighScores = new ArrayList<PlayerHighScoreInformation>();
        File file = new File(fileName);
        if (file.isFile() && file.canRead()) {
            try {
                ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file));
                deserializedHighScores = oin.readObject();
                oin.close();
            }
            catch (Exception ioe) {
                ioe.printStackTrace();
            }
        }

        return (ArrayList<PlayerHighScoreInformation>) deserializedHighScores;
    }

    public static void storePlayerHighScoreInformation(ArrayList<PlayerHighScoreInformation> highScores) {
        storePlayerHighScoreInformation(highScores, SCORES_FILE);
    }

    public static void storePlayerHighScoreInformation(ArrayList<PlayerHighScoreInformation> highScores, String fileName) {
        List<PlayerHighScoreInformation> abbreviatedList;

        try {
            abbreviatedList = highScores.subList(0, NUM_PLAYER_SCORES_TO_STORE - 1);
        }
        catch (IndexOutOfBoundsException ioobe) {
            abbreviatedList = highScores;
        }

        try {
            ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(fileName));
            oout.writeObject(abbreviatedList);
            oout.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void savePlayerHighScoreInformation(PlayerHighScoreInformation highScore) {
        ArrayList<PlayerHighScoreInformation> players = retrievePlayerHighScoreInformation();
        players.add(highScore);
        players = SortPlayerInformation.sortByScore(players);
        if (players.size() > 10)
            players = (ArrayList<PlayerHighScoreInformation>) players.subList(0, 10);
        storePlayerHighScoreInformation(players);
    }
}