package edu.mines.csci598.recycler.splashscreen.gameoflife;

import edu.mines.csci598.recycler.splashscreen.graphics.CycleScreenCallback;
import edu.mines.csci598.recycler.splashscreen.graphics.GraphicsHelper;
import edu.mines.csci598.recycler.splashscreen.graphics.SplashScreenSection;
import edu.mines.csci598.recycler.splashscreen.graphics.UpdateScreenCallback;

import java.awt.*;
import java.util.Random;
import java.util.TimerTask;

public class GameofLife implements SplashScreenSection {

    private static final int TIMER_DELAY = 100;
    private static final int NUM_ROWS = 50;
    private static final int NUM_COLS = 50;
    private java.util.Timer gameofLifeUpdate;
    private Point topLeft;
    private Point bottomRight;
    private UpdateScreenCallback updateScreenCallback;
    private CycleScreenCallback cycleScreenCallback;
    private int timerUpdateCount = 0;
    private static final Color[] colors = {Color.DARK_GRAY, Color.LIGHT_GRAY};
    private GameOfLifeCell[][] cells;

    public GameofLife() {
        cells = new GameOfLifeCell[NUM_ROWS+2][NUM_COLS+2];

        for(int r = 0; r < NUM_ROWS+2; r++) {
            for(int c = 0; c < NUM_COLS+2; c++) {
                cells[r][c] = new GameOfLifeCell();
            }
        }

        for(int r = 1; r < NUM_ROWS+1; r++) {
            for(int c = 1; c < NUM_COLS+1; c++) {
                cells[r][c].addNeighbor(cells[r - 1][c]);
                cells[r][c].addNeighbor(cells[r + 1][c]);
                cells[r][c].addNeighbor(cells[r][c - 1]);
                cells[r][c].addNeighbor(cells[r][c + 1]);
                cells[r][c].addNeighbor(cells[r - 1][c - 1]);
                cells[r][c].addNeighbor(cells[r - 1][c + 1]);
                cells[r][c].addNeighbor(cells[r + 1][c - 1]);
                cells[r][c].addNeighbor(cells[r + 1][c + 1]);
            }
        }
    }

    private void updateBoard() {
        for (GameOfLifeCell[] labelsToCheck : cells) {
            for (GameOfLifeCell checkedCell : labelsToCheck) {
                checkedCell.checkState();
            }
        }
        for (GameOfLifeCell[] cellsToCheck : cells) {
            for (GameOfLifeCell cell : cellsToCheck) {
                cell.updateState();
            }
        }
    }


    private void drawGame(Graphics2D g) {
        int height = bottomRight.y - topLeft.y;
        int width = bottomRight.x - topLeft.x;

        int rowHeight = height / NUM_ROWS;
        for (int row = 0; row < NUM_ROWS; row++) {
            g.drawLine(0, (row * rowHeight) + topLeft.y, width, (row * rowHeight) + topLeft.y);
        }

        int colWidth = width / NUM_COLS;
        for (int col = 0; col < NUM_COLS; col++) {
            g.drawLine((col*colWidth), topLeft.y, (col*colWidth), height + topLeft.y);
        }

        for (int r = 0; r < NUM_ROWS; r++) {
            for (int c = 0; c < NUM_COLS; c++) {
                int cellTopLeftX = topLeft.x + (c*colWidth);
                int cellTopLeftY = topLeft.y + (r * rowHeight);
                int cellBottomLeftX = cellTopLeftX + colWidth;
                int cellBottomLeftY = cellTopLeftY + rowHeight;
                Polygon rectangle = GraphicsHelper.getRectangle(cellTopLeftX, cellTopLeftY, cellBottomLeftX ,cellBottomLeftY);

                if (cells[r][c].getState() == 1) {
                    g.setColor(colors[1]);
                } else {
                    g.setColor(colors[0]);
                }

                g.fillPolygon(rectangle);
            }
        }
    }

    @Override
    public void initialize(Point topLeft, Point bottomRight, UpdateScreenCallback updateScreenCallback, CycleScreenCallback cycleScreenCallback) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.updateScreenCallback = updateScreenCallback;
        this.cycleScreenCallback = cycleScreenCallback;
    }

    @Override
    public void draw(Graphics2D g) {
        drawGame(g);
    }

    @Override
    public void startThreads() {
        int gridSize = NUM_COLS * NUM_ROWS;
        int numRandomElements = gridSize / 5;
        Random rand = new Random();
        for (int i = 0; i < numRandomElements; i++) {
            int randomNum = rand.nextInt(gridSize + 1);
            int xCoord = randomNum / 50;
            int yCoord = randomNum % 50;
            cells[xCoord][yCoord].setState();
        }

        gameofLifeUpdate = new java.util.Timer();
        gameofLifeUpdate.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                GameofLife.this.updateScreenCallback.updateScreen();
                updateBoard();
                timerUpdateCount++;

                if (timerUpdateCount == 100) {
                    GameofLife.this.cycleScreenCallback.cycleScreen(GameofLife.this);
                }
            }
        }, TIMER_DELAY, TIMER_DELAY);
    }

    @Override
    public void stopThreads() {
        gameofLifeUpdate.cancel();
    }

}