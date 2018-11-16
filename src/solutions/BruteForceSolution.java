package solutions;

import core.Board;
import core.Movement;
import core.Node;
import exceptions.StopRecursionException;

import java.util.ArrayList;
import java.util.List;

public abstract class BruteForceSolution implements ISolver {
    protected Board board;
    protected Node root;
    protected static char[] sequence;                 // Left, Right, Up, Down;
    protected static boolean goalAchieved = false;
    protected static List<Movement> moves;
    protected static final int recursionDepthLimit = 10000;  // Minimum value cannot be less than 21. max 5065
    protected static int visitedStateCounter = 0;
    protected static int inProgressStateCounter = 0;
    protected static int maxRecursionDepth = 0;
    protected static long startTime;
    protected static long endTime;
    protected static long estimatedTime;

    public BruteForceSolution(Board board, char[] sequence) {
        this.board = board;
        this.root = new Node(board, Movement.NONE, null, (short) -1);
        this.sequence = sequence;
        this.moves = new ArrayList<>();
    }

    @Override
    abstract public void run();

    protected abstract void sumUpSolution(Node c) throws StopRecursionException;

    protected void stopTime() {
        endTime = System.currentTimeMillis();
        estimatedTime = endTime - startTime;
    }

    protected void showStats() {
        System.out.println("Visited states    : " + visitedStateCounter);
        System.out.println("In progress states: " + inProgressStateCounter);
        System.out.println("Time [ms]         : " + estimatedTime);
        System.out.println("Max recursion     : " + (maxRecursionDepth));
    }

    protected void showStepsAndMoves(Node n) {
        System.out.println("******SUCCESS*******");
        System.out.println("Steps             : " + (n.recursionDepth));
        System.out.println("Moves             : " + showMovesListAsSequence());
    }

    protected String showMovesListAsSequence() {
        StringBuilder sequence = new StringBuilder();
        for (Movement move : moves) {
            switch (move) {
                case UP:
                    sequence.append("U");
                    break;
                case DOWN:
                    sequence.append("D");
                    break;
                case LEFT:
                    sequence.append("L");
                    break;
                case RIGHT:
                    sequence.append("R");
                    break;
                default:
                    System.err.println("Something goes wrong with movement conversion.");
            }
        }
        return sequence.toString();
    }
}
