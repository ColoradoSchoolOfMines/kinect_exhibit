package edu.mines.csci598.recycler.splashscreen.tetris.strategy;

import edu.mines.csci598.recycler.splashscreen.tetris.Figure;
import edu.mines.csci598.recycler.splashscreen.tetris.SquareBoard;

import java.util.List;

public interface TetrisStrategy {
    public List<Move> getMoves(Figure currentFigure, Figure nextFigure, SquareBoard board);
}
