package core;
import java.util.ArrayDeque;

public class AStar {
    private Node goalNode;
    private MinPQ<Node> pq = new MinPQ<Node>();
    private MinPQ<Node> pqTwin = new MinPQ<Node>();

    public AStar(Board initial){
        Board initialBoard;
        ArrayDeque<Board> neighbors = new ArrayDeque<Board>();
        initialBoard = initial;

        Node currentNode = new Node(initial, null, 0);
        Node currentTwin = new Node(initial.twin(), null, 0);
        pq.insert(currentNode);
        pqTwin.insert(currentTwin);

        while(!currentNode.board.isCorrect() && !currentTwin.board.isCorrect()){

            currentNode = pq.delMin();
            currentTwin = pqTwin.delMin();

            for(Board b : currentNode.board.neighbors()) {
                if(!b.equals(currentNode.board))
                    pq.insert(new Node(b, currentNode, currentNode.moves +1));
            }

            for(Board b : currentTwin.board.neighbors()) {
                if(!b.equals(currentTwin.board))
                    pqTwin.insert(new Node(b, currentTwin, currentTwin.moves +1));
            }
        }

        if(currentNode.board.isCorrect())
            goalNode = currentNode;
        else
            goalNode = currentTwin;
    }

    public ArrayDeque<Board> solution(){
        ArrayDeque<Board> trace = new ArrayDeque<Board>();
        trace.addLast(goalNode.board);
        while (goalNode.previous != null){
            goalNode = goalNode.previous;
            trace.addLast(goalNode.board);

        }
        return trace;
    }

    public boolean isSolvable(){
        return goalNode != null;
    }

    public int moves(){
        return goalNode.moves;
    }
}
