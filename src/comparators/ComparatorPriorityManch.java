package comparators;

import core.Node;

import java.util.Comparator;

public class ComparatorPriorityManch implements Comparator<Node> {
    @Override
        public int compare(Node o1, Node o2) {
            if(o1.board.manhattan() == o2.board.manhattan()) return 0;
            return (o1.board.manhattan() > o2.board.manhattan()) ? 1 :  -1;
        }
    }

