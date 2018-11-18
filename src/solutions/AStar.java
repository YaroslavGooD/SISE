package solutions;


import comparator.ComparatorPriorityHam;
import core.Board;
import core.Node;
import core.Visit;
import exceptions.StopRecursionException;

import java.util.PriorityQueue;


public class AStar extends BruteForceSolution{

    private PriorityQueue<Node> list;
    private String metrix;

    public AStar(Board board, char[] sequence, String metrix) {
        super(board, sequence);
        this.metrix = metrix;
        list = new PriorityQueue<Node>(new ComparatorPriorityHam());
    }

    public void run() {
        startTime = System.currentTimeMillis();
        if(root.isGoal()) {
            showStats();
            stopTime();
            return;
        }
        try {
            astar(root);
        } catch (StopRecursionException e) {
        } finally {
            stopTime();
        }
        if (!goalAchieved) {
            System.out.println("Solution wasn't found");
        }

        showStats();
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
    public void astar(Node c) throws StopRecursionException {
        list.add(c);
        while(list.size() > 0) {
            Node n = list.poll();
            inProgressStateCounter++;
            n.visit = Visit.INPROGRESS;
            if(n.recursionDepth < recursionDepthLimit) {
                n.branch(sequence);
                for (Node k : n.children) {
//                    System.out.println(k);
                    if(k.recursionDepth > recursionDepthLimit) {
                        maxRecursionDepth = k.recursionDepth;
                    }
                    if (k.isGoal()) {
                        sumUpSolution(k);
                        maxRecursionDepth = k.recursionDepth;
                        throw new StopRecursionException("Recursion has ended.");
                    }

                    if(metrix.compareTo("manch") == 0) {
                        k.board.manhattan();
                    }
                    else {
                        k.board.hamming();
                    }
                    k.visit = Visit.VISITED;
                    visitedStateCounter++;
                    list.add(k);
                }
            }
        }
    }
}

