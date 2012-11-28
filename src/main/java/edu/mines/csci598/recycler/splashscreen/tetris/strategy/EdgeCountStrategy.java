package edu.mines.csci598.recycler.splashscreen.tetris.strategy;

import edu.mines.csci598.recycler.splashscreen.tetris.Figure;
import edu.mines.csci598.recycler.splashscreen.tetris.SquareBoard;

import java.util.ArrayList;
import java.util.List;

public class EdgeCountStrategy implements TetrisStrategy {
    public List<Move> getMoves(Figure currentFigure, Figure nextFigure, SquareBoard board) {
        boolean[][] bitBoard = TetrisPermutations.getBitBoard(board);
        TetrisPermutations.printBoard(bitBoard);
        TetrisPermutations.clearBitBoardRows(bitBoard, 0, 1);
        TetrisPermutations.printBoard(bitBoard);
        List<boolean[][]> boards = TetrisPermutations.getBoardPermutations(currentFigure, bitBoard);
        return new ArrayList<Move>();
    }
}
