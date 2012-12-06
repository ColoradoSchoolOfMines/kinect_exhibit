package edu.mines.csci598.recycler.splashscreen.highscores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortPlayerInformation {

    /**
     *
     * @param playerHighScoreInformationList list to be sorted by score
     * @return sorted array list
     */
    public static ArrayList<PlayerHighScoreInformation> sortByScore(ArrayList<PlayerHighScoreInformation> playerHighScoreInformationList) {
        Collections.sort(playerHighScoreInformationList, new Comparator<PlayerHighScoreInformation>() {
            public int compare(PlayerHighScoreInformation object1, PlayerHighScoreInformation object2) {
            	return ((Long)object1.getPlayerScore()).compareTo(object2.getPlayerScore());
            }
        });
        Collections.reverse(playerHighScoreInformationList);
        return playerHighScoreInformationList;
    }

    /**
     *
     * @param playerHighScoreInformationList list to be sorted by initials
     * @return sorted list
     */
    public static ArrayList<PlayerHighScoreInformation> sortByInitials(ArrayList<PlayerHighScoreInformation> playerHighScoreInformationList) {
        Collections.sort(playerHighScoreInformationList, PlayerHighScoreInformation.playerInitialsComporator);
        return playerHighScoreInformationList;
    }
}
