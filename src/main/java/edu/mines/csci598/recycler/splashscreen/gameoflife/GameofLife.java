package edu.mines.csci598.recycler.splashscreen.gameoflife;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class GameofLife  extends JPanel implements ActionListener {

    private GameOfLifeLabel[][] _cells;
    private Timer _timer;
    private static final int NUM_ROWS = 50;
    private static final int NUM_COLS = 50;
    private boolean _isDone;
    private int _genNumber;

    GameofLife(int nbRow, int nbCol) {
        _genNumber = 0;

        _cells = new GameOfLifeLabel[nbRow+2][nbCol+2];
        for(int r = 0; r < nbRow+2; r++) {
            for(int c = 0; c < nbCol+2; c++) {
                _cells[r][c] = new GameOfLifeLabel();
            }
        }

        JPanel panel = new JPanel(new GridLayout(nbRow, nbCol, 1, 1));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        for(int r = 1; r < nbRow+1; r++) {
            for(int c = 1; c < nbCol+1; c++) {
                panel.add(_cells[r][c]);
                _cells[r][c].addNeighbour(_cells[r-1][c]); 
                _cells[r][c].addNeighbour(_cells[r+1][c]);
                _cells[r][c].addNeighbour(_cells[r][c-1]);
                _cells[r][c].addNeighbour(_cells[r][c+1]);
                _cells[r][c].addNeighbour(_cells[r-1][c-1]);
                _cells[r][c].addNeighbour(_cells[r-1][c+1]); 	
                _cells[r][c].addNeighbour(_cells[r+1][c-1]); 	
                _cells[r][c].addNeighbour(_cells[r+1][c+1]); 	
            }
        }

        int gridSize = NUM_COLS * NUM_ROWS;
        int numRandomElements = gridSize / 5;
        Random rand = new Random();
        for (int i = 0; i < numRandomElements; i++) {
            int randomNum = rand.nextInt(gridSize + 1);
            int xCoord = randomNum / 50;
            int yCoord = randomNum % 50;
            _cells[xCoord][yCoord].setState();
        }

        add(panel, BorderLayout.CENTER);
        panel = new JPanel(new GridLayout(1,3));

        add(panel, BorderLayout.SOUTH);
        setLocation(20, 20);
        setVisible(true);
        _timer = new Timer(100, this);
        _timer.start();
        updateBoard();
    }

    private void updateBoard() {
        _genNumber++;
        for (GameOfLifeLabel[] labelsToCheck : _cells) {
            for (GameOfLifeLabel checkedLabel : labelsToCheck) {
                checkedLabel.checkState();
            }
        }
        for (GameOfLifeLabel[] labels : _cells) {
            for (GameOfLifeLabel label : labels) {
                label.updateState();
            }
        }

        if (_genNumber > 300)
            setIsDone(true);
    }

    public synchronized void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        _timer.start();
        updateBoard();
    }

    public boolean isDone() {
        return _isDone;
    }

    private void setIsDone(boolean val) {
        _isDone = val;
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