package edu.mines.csci598.recycler.splashscreen.highscores;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializePlayerInformation {

    private static final int NUM_PLAYER_SCORES_TO_STORE = 10;
    private static final String SCORES_FILE = "scores.dat";
    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(SerializePlayerInformation.class.getName());

    /**
     *
     * @return call retrievePlayerHighScoreInformation from the standard scores file
     */
    public static ArrayList<PlayerHighScoreInformation> retrievePlayerHighScoreInformation() {
        return retrievePlayerHighScoreInformation(SCORES_FILE);
    }

    /**
     *
     * @param fileName file to load scores from
     * @return array list of deserialized player high scores
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<PlayerHighScoreInformation> retrievePlayerHighScoreInformation(String fileName) {
        Object deserializedHighScores = new ArrayList<PlayerHighScoreInformation>();
        File file = new File(fileName);
        if (file.isFile() && file.canRead()) {
            try {
                ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file));
                deserializedHighScores = oin.readObject();
                oin.close();
            }
            catch (Exception e) {
                log.severe("Unable to deserialize high scores, " + e);
            }
        }

        return (ArrayList<PlayerHighScoreInformation>) deserializedHighScores;
    }

    /**
     *
     * @param highScores the high scores that we need to store to default scores file
     */
    public static void storePlayerHighScoreInformation(ArrayList<PlayerHighScoreInformation> highScores) {
        storePlayerHighScoreInformation(highScores, SCORES_FILE);
    }

    /**
     * Additionally, besides storing, this truncates players to only the top 10
     * @param highScores the high scores that we need to store
     * @param fileName file to store scores too
     */
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
        catch (IOException e) {
            log.severe("Unable to serialize high scores, " + e);
        }
    }

    /**
     *  Save a single player, but retrieve pre-existing scores, sort and store
     * @param highScore save a single player to the scores.dat file
     */
    public static void savePlayerHighScoreInformation(PlayerHighScoreInformation highScore) {
        ArrayList<PlayerHighScoreInformation> players = retrievePlayerHighScoreInformation();
        players.add(highScore);
        players = SortPlayerInformation.sortByScore(players);
        if (players.size() > NUM_PLAYER_SCORES_TO_STORE)
            players = (ArrayList<PlayerHighScoreInformation>) players.subList(0, NUM_PLAYER_SCORES_TO_STORE);
        storePlayerHighScoreInformation(players);
    }
}