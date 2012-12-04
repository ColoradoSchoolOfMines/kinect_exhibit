package edu.mines.csci598.recycler.splashscreen.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameofLife  extends JPanel implements ActionListener {

    private GameOfLifeLabel[][] cells;
    private Timer timer;
    private static final int NUM_ROWS = 50;
    private static final int NUM_COLS = 50;
    private int genNumber;

    GameofLife(int nbRow, int nbCol) {
        genNumber = 0;

        cells = new GameOfLifeLabel[nbRow+2][nbCol+2];
        for(int r = 0; r < nbRow+2; r++)
            for(int c = 0; c < nbCol+2; c++)
                cells[r][c] = new GameOfLifeLabel();


        JPanel panel = new JPanel(new GridLayout(nbRow, nbCol, 1, 1));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        for(int r = 1; r < nbRow+1; r++) {
            for(int c = 1; c < nbCol+1; c++) {
                panel.add(cells[r][c]);
                cells[r][c].addNeighbour(cells[r-1][c]);
                cells[r][c].addNeighbour(cells[r+1][c]);
                cells[r][c].addNeighbour(cells[r][c-1]);
                cells[r][c].addNeighbour(cells[r][c+1]);
                cells[r][c].addNeighbour(cells[r-1][c-1]);
                cells[r][c].addNeighbour(cells[r-1][c+1]);
                cells[r][c].addNeighbour(cells[r+1][c-1]);
                cells[r][c].addNeighbour(cells[r+1][c+1]);
            }
        }

        int gridSize = NUM_COLS * NUM_ROWS;
        int numRandomElements = gridSize / 5;
        Random rand = new Random();
        for (int i = 0; i < numRandomElements; i++) {
            int randomNum = rand.nextInt(gridSize + 1);
            int xCoord = randomNum / 50;
            int yCoord = randomNum % 50;
            cells[xCoord][yCoord].setState();
        }

        add(panel, BorderLayout.CENTER);
        panel = new JPanel(new GridLayout(1,3));

        add(panel, BorderLayout.SOUTH);
        setLocation(20, 20);
        setVisible(true);
        timer = new Timer(100, this);
        timer.start();
        updateBoard();
    }

    private void updateBoard() {
        genNumber++;
        for (GameOfLifeLabel[] labelsToCheck : cells) {
            for (GameOfLifeLabel checkedLabel : labelsToCheck) {
                checkedLabel.checkState();
            }
        }
        for (GameOfLifeLabel[] labels : cells) {
            for (GameOfLifeLabel label : labels) {
                label.updateState();
            }
        }
    }

    public synchronized void actionPerformed(ActionEvent e) {
        e.getSource();
        timer.start();
        updateBoard();
    }


    public static void main(String[] arg) {
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(1280, 720);

        GameofLife gameofLife = new GameofLife(NUM_COLS, NUM_ROWS);
        mainFrame.add(gameofLife);

        mainFrame.setVisible(true);
    }
}