package edu.mines.csci598.recycler.splashscreen.tetris.strategy;

import edu.mines.csci598.recycler.splashscreen.tetris.Figure;
import edu.mines.csci598.recycler.splashscreen.tetris.SquareBoard;

import java.util.ArrayList;
import java.util.List;

public class TetrisPermutations {
    public static boolean[][] getBitBoard(SquareBoard board) {
        int boardHeight = board.getBoardHeight();
        int boardWidth = board.getBoardWidth();
        boolean[][] bitBoard = new boolean[boardHeight][boardWidth];
        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < boardHeight; y++) {
                bitBoard[y][x] = (board.getSquareColor(x, y) != null);
            }
        }

        return bitBoard;
    }

    public static void clearBitBoardRows(boolean[][] board, int startRow, int endRow) {
        for (int y = startRow; y <= endRow; y++) {
            for (int x = 0; x < board[y].length; x++) {
                board[y][x] = false;
            }
        }
    }

    public static List<boolean[][]> getBoardPermutations(Figure figure, boolean[][] board) {
        return new ArrayList<boolean[][]>();
    }

    public static void printBoard(boolean[][] board) {
        System.out.print("+");
        for (int i = 0; i < board[0].length; i++) {
            System.out.print("-");
        }
        System.out.println("+");

        for (int y = 0; y < board.length; y++) {
            System.out.print("|");
            for (int x = 0; x < board[y].length; x++) {
                System.out.print(board[y][x] ? "1" : "0");
            }
            System.out.println("|");
        }

        System.out.print("+");
        for (int i = 0; i < board[0].length; i++) {
            System.out.print("-");
        }
        System.out.println("+");
    }
}
