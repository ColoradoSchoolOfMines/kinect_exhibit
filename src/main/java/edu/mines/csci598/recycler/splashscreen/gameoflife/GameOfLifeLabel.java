package edu.mines.csci598.recycler.splashscreen.gameoflife;

import javax.swing.*;
import java.awt.*;

public class GameOfLifeLabel extends JLabel {

    static final int size = 15;
    static final Dimension dim = new Dimension(size, size);
    static final Color[] color = {Color.DARK_GRAY, Color.LIGHT_GRAY};
    private int state, newState;
    private int howManyNeighbor;
    private GameOfLifeLabel[] neighbour = new GameOfLifeLabel[8];

    GameOfLifeLabel() {
        state = newState = 0;
        setOpaque(true);
        setBackground(color[0]);
        this.setPreferredSize(dim);
    }

    void addNeighbour(GameOfLifeLabel n) {
        neighbour[howManyNeighbor++] = n;
    }

    void checkState() {
        int howManyLive = 0;

        for(int i = 0; i < howManyNeighbor; i++)
            howManyLive += neighbour[i].state;

        if(state == 1) {
            checkLivingNeighborsToDetermineState(howManyLive);
        }
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
            setBackground(color[state]);
        }
    }

    public void setState() {
        state = 1;
        setBackground(Color.LIGHT_GRAY);
    }

}
