package core;

import core.Board;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        InputTextReader itr = new InputTextReader("/home/bambi/IdeaProjects/15_puzzle/src/files/example15puzzle.txt");
        int tiles[][] = itr.loadInputFileToBoard();
        char[] sequence = {'R', 'L', 'U','D'};


        Board board = new Board(tiles);

        ISolver solution = new DepthFirstSearch(board, sequence);
        solution.run();

    }
}
