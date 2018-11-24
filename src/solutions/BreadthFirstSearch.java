package solutions;

import core.Board;
import core.Node;
import core.Visit;
import exceptions.StopRecursionException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch extends BruteForceSolution {

    private Queue<Node> listOfNodes;

    public BreadthFirstSearch(Board board, char[] sequence, String filename) {
        super(board, sequence, filename);
        this.listOfNodes = new LinkedList<>();
    }

    public void run() {

        startTime = System.currentTimeMillis();
        root.branch(sequence);
        for (Node n : root.children) {
            if (n.isGoal()) {
                System.out.println("******SUCCESS*******");
                stopTime();
                showStats();
                write(n);
                return;
            }
        }
        try {
            root.recursionDepth = bfs(root);
        } catch (StopRecursionException e) {
        } finally {
            stopTime();
        }
        if (!goalAchieved) {
            System.out.println("Solution wasn't found");
        }

        showStats();
        write(root);

    }

    public void write(Node c) {
        try {
            writeSolution(c, "bfs" + root.board.getWidth() + "x" + root.board.getHeight() + "_sol");
            writeStats(c, "bfs" + root.board.getWidth() + "x" + root.board.getHeight() + "_stats" );
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public short bfs(Node c) throws StopRecursionException {
        c.visit = Visit.INPROGRESS;
        inProgressStateCounter++;
        c.visit = Visit.VISITED;
        visitedStateCounter++;

        if (c.isGoal()) {
            sumUpSolution(c);
            return c.recursionDepth;
//            throw new StopRecursionException("Recursion has ended.");
        }

        for (Node n : c.children) {
            //System.out.println(n);
            listOfNodes.add(n);
            n.branch(sequence);
            n.visit = Visit.INPROGRESS;
            inProgressStateCounter++;

            if (n.isGoal()) {
                sumUpSolution(n);
                return n.recursionDepth;
//                throw new StopRecursionException("Recursion has ended.");
            }
        }

        while (!listOfNodes.isEmpty()) {
            Node toCheck = listOfNodes.poll();
            toCheck.visit = Visit.VISITED;
            visitedStateCounter++;
            for (Node n : toCheck.children) {
                /***
                 * Uncomment code underneath if you want to see all visited states.
                 */
                // System.out.println(n);

                n.branch(sequence);
                n.visit = Visit.INPROGRESS;
                inProgressStateCounter++;

                if (n.isGoal()) {
                    sumUpSolution(n);
                    maxRecursionDepth = n.recursionDepth;
                    return n.recursionDepth;
//                    throw new StopRecursionException("Recursion has ended.");
                }

                if (n.recursionDepth <= recursionDepthLimit) {

                    listOfNodes.add(n);
                }
            }
        }
        return -1;
    }

    @Override
    protected void sumUpSolution(Node c) {
        goalAchieved = true;
        findPath(c);
        showStepsAndMoves(c);

    }

    private void findPath(Node n) {
        while (n.parent != null) {
            moves.add(0, n.fromWhichMove);
            n = n.parent;
        }
    }
}