package core;

import solutions.*;

public class Main {

    public static void main(String[] args) {

        InputTextReader itr = new InputTextReader("src/files/example15puzzle.txt");
        int tiles[][] = itr.loadInputFileToBoard();
        char[] sequence = {'R', 'L', 'U', 'D'};

        Board board = new Board(tiles);

//        ISolver solution = new DepthFirstSearch(board, sequence);
//        ISolver solution = new BreadthFirstSearch(board, sequence);
        ISolver solution = new AStar(board, sequence, "hamm");
        solution.run();
    }
}
