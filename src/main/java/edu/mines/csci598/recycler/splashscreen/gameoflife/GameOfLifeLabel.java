package edu.mines.csci598.recycler.splashscreen.gameoflife;

import javax.swing.*;
import java.awt.*;

public class GameOfLifeLabel extends JLabel {

    private static final int size = 15;
    private static final Dimension dimension = new Dimension(size, size);
    private static final Color[] colors = {Color.DARK_GRAY, Color.LIGHT_GRAY};
    private int state;
    private int newState;
    private int howManyNeighbor;
    private GameOfLifeLabel[] neighbors;

    GameOfLifeLabel() {
        state = 0;
        newState = 0;
        setOpaque(true);
        setBackground(colors[0]);
        this.setPreferredSize(dimension);
        neighbors = new GameOfLifeLabel[8];
    }

    void addNeighbour(GameOfLifeLabel n) {
        neighbors[howManyNeighbor++] = n;
    }

    void checkState() {
        int howManyLive = 0;

        for(int i = 0; i < howManyNeighbor; i++)
            howManyLive += neighbors[i].state;

        if(state == 1)
            checkLivingNeighborsToDetermineState(howManyLive);
        else {
            if(howManyLive == 3)
                newState = 1;
        }
    }

    private void checkLivingNeighborsToDetermineState(int howManyLive) {
        if(howManyLive < 2)
            newState = 0;
        if(howManyLive > 3)
            newState = 0;
    }

    void updateState() {
        if(state != newState) {
            state = newState;
            setBackground(colors[state]);
        }
    }

    public void setState() {
        state = 1;
        setBackground(Color.LIGHT_GRAY);
    }

}
