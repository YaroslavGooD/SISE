package solutions;

import core.Board;
import core.Node;
import core.Visit;
import exceptions.StopRecursionException;
import solutions.BruteForceSolution;

public class DepthFirstSearch extends BruteForceSolution {

    public DepthFirstSearch(Board board, char[] sequence) {
        super(board, sequence);
    }

    public void run() {

        startTime = System.currentTimeMillis();

        root.branch(sequence);

        //check if puzzle has been assmebled correctly already
        for (Node n : root.children) {
            if (n.isGoal()) {
                showStats();
                return;
            }
        }

        try {
            //recursion starts here
            dfs(root);
        } catch (StopRecursionException e) {
            //blank
        } finally {
            stopTime();
        }

        if (!goalAchieved) {
            System.out.println("Solution wasn't found");
        }

        showStats();
    }

    /***
     * source: https://courses.cs.washington.edu/courses/cse326/03su/homework/hw3/dfs.html
     */
    public void dfs(Node c) throws StopRecursionException {
        if (c.isGoal()) {
            sumUpSolution(c);
            throw new StopRecursionException("Recursion has ended.");
        } else {
            if (goalAchieved) return;

            c.visit = Visit.INPROGRESS;
            inProgressStateCounter++;

            for (Node n : c.children) {

                /***
                 * Uncomment code underneath if you want to see all visited states.
                 * JUST DO IT!
                 */
                 //System.out.println(n);


                n.branch(sequence);
                moves.add(n.fromWhichMove);
                if (n.visit == Visit.UNVISITED && n.recursionDepth <= recursionDepthLimit) {
                    if (maxRecursionDepth < n.recursionDepth) {
                        maxRecursionDepth = n.recursionDepth;
                        //System.out.println(maxRecursionDepth);
                    }
                    dfs(n);
                } else {
                    moves.remove((moves.size() - 1));
                }
            }

            c.visit = Visit.VISITED;
            if (!goalAchieved) {
                //Remove last element on the list.
                moves.remove((moves.size() - 1));
            }
            visitedStateCounter++;
        }
    }

    @Override
    protected void sumUpSolution(Node c) {
        goalAchieved = true;
        showStepsAndMoves(c);
    }
}