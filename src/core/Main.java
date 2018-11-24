package core;

import solutions.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        InputTextReader itr = new InputTextReader("src/files/a.txt");
        int tiles[][] = itr.loadInputFileToBoard();
        String filename = "a.txt";
        char[] sequence = {'R', 'L', 'U', 'D'};
        Board board = new Board(tiles);

//        ISolver solution = new DepthFirstSearch(board, sequence);
//        ISolver solution = new BreadthFirstSearch(board, sequence);
        ISolver solution = new AStar(board, sequence, "manh",filename);

        try {
            solution.run();
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
