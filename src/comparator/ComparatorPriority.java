package comparator;

import core.Node;

import java.util.Comparator;

public class ComparatorPriorityHam implements Comparator<Node> {
    @Override
        public int compare(Node o1, Node o2) {
            if(o1.board.distance == o2.board.distance) return 0;
            return (o1.board.distance > o2.board.distance) ? 1 :  -1;
        }
    }

