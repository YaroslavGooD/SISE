package comparator;

import core.Node;

import java.util.Comparator;

public class ComparatorPriority implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return  o1.board.distance - o2.board.distance;
    }
}
