package comparators;

import core.Node;

import java.util.Comparator;

public class ComparatorPriorityHam implements Comparator<Node> {
    @Override
        public int compare(Node o1, Node o2) {
            if(o1.board.hamming() == o2.board.hamming()) return 0;
            return (o1.board.hamming() > o2.board.hamming()) ? 1 :  -1;
        }
    }

