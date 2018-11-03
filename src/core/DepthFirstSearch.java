package core;

public class DepthFirstSearch implements ISolver {

    private Board board;
    private Node root;
    private static char[] sequence; //Left, Right, Up, Down;
    private static boolean goalAchieved = false;
    private static short stepCounter= 0;

    public DepthFirstSearch(Board board, char[] sequence) {
        this.board = board;
        this.root = new Node(board);
        this.sequence = sequence;
    }

    public void run() {
        root.branch(sequence);
        stepCounter++;
        for (Node n : root.children) {
            if (n.isGoal()) {
                System.out.println("******SUCCESS*******");
                System.out.println(n.board);
                return;
            }
        }
        dfs(root);

        System.out.println("Steps: " + stepCounter);
    }

    /***
     * source: https://courses.cs.washington.edu/courses/cse326/03su/homework/hw3/dfs.html
     */
    public void dfs(Node c) {
        if (c.isGoal()) {
            goalAchieved = true;
            System.out.println("******SUCCESS*******");
            System.out.println(c.board);
            return;
        }
        else {
            if (goalAchieved)
                return;

            c.visit = Visit.INPROGRESS;
            for (Node n : c.children) {
                n.branch(sequence);
                stepCounter++;
                if (n.visit == Visit.UNVISITED) {
                    dfs(n);
                }
            }
            c.visit = Visit.VISITED;
        }
    }
}