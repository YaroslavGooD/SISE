package solutions;


import comparator.ComparatorPriority;
import core.Board;
import core.Node;
import core.Visit;
import exceptions.StopRecursionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;


public class AStar extends BruteForceSolution {

    private PriorityQueue<Node> list;
    private String metrix;

    public AStar(Board board, char[] sequence, String metrix, String filename) {
        super(board, sequence, filename);
        this.metrix = metrix;
        list = new PriorityQueue<Node>(10000, new ComparatorPriority());
    }

    public void run() {
        startTime = System.currentTimeMillis();
        try {
            root.recursionDepth = astar(root);
        } catch (StopRecursionException e) {
            e.printStackTrace();
        } finally {
            stopTime();
        }
        if (!goalAchieved) {
            System.out.println("Solution wasn't found");
        }

        showStats();
        try {
            writeSolution(root, metrix);
            writeStats(root, "astr_" + metrix);
        } catch (IOException e) {
            e.getMessage();
        }

    }

    @Override
    protected void sumUpSolution(Node c) throws StopRecursionException {
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


    public short astar(Node c) throws StopRecursionException {
        HashSet<Node> visited = new HashSet<Node>();
        list.add(c);
        while (list.size() > 0) {
            Node nodeToCheck = list.poll();
            nodeToCheck.visit = Visit.VISITED;
            visitedStateCounter++;
            if (nodeToCheck.recursionDepth > recursionDepthLimit) {
                maxRecursionDepth = nodeToCheck.recursionDepth;
            }
            if (nodeToCheck.isGoal()) {
                sumUpSolution(nodeToCheck);
                maxRecursionDepth = nodeToCheck.recursionDepth;
                return nodeToCheck.recursionDepth;
//              throw new StopRecursionException("Recursion has ended.");
            }
            if (nodeToCheck.recursionDepth < recursionDepthLimit) {
                nodeToCheck.branch(sequence);
                for (Node k : nodeToCheck.children) {
                    k.visit = Visit.INPROGRESS;
                    inProgressStateCounter++;
//                    System.out.println(k);

                    if (metrix.compareTo("manh") == 0) {
                        k.board.manhattan();
                    } else {
                        k.board.hamming();
                    }
                    if(!visited.contains(k)) {
                        visited.add(k);
                        list.add(k);
                    }

                }

            }
        }
        return -1;
    }
}

