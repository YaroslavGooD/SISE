package core;

import core.Board;

import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {

        InputTextReader itr = new InputTextReader("D:/OldPC/Files/3Rok/SISE/PROJECT/SISE/SISE/src/files/wrong15puzzle.txt");
        int tiles[][] = itr.loadInputFileToBoard();
        char[] sequence = {'L', 'R', 'U','D'};


        Board board = new Board(tiles);


//        ISolver solution = new DepthFirstSearch(board, sequence);
//        solution.run();

        AStar aStar = new AStar(board);

        if (!aStar.isSolvable())
            System.out.println("No solution possible");
        else {
            System.out.println("Minimum number of moves = " + aStar.moves());
            for(Iterator descItr = aStar.solution().descendingIterator();descItr.hasNext();)
            {
                System.out.println(descItr.next());
            }
        }
    }
}
