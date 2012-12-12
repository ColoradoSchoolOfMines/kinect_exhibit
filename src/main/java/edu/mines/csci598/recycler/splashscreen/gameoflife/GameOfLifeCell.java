package edu.mines.csci598.recycler.splashscreen.gameoflife;

public class GameOfLifeCell {

    private int state;
    private int newState;
    private int howManyNeighbor;
    private GameOfLifeCell[] neighbors;

    GameOfLifeCell() {
        state = 0;
        newState = 0;
        neighbors = new GameOfLifeCell[8];
    }

    void addNeighbor(GameOfLifeCell n) {
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
        }
    }

    public void setState() {
        state = 1;
    }

    public int getState() {
        return state;
    }

}
