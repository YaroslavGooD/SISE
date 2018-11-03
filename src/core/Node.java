package core;

import java.util.LinkedList;
import java.util.Queue;

public class Node {
    public Queue<Node> children;
    public Board board;
    public Visit visit;

    public Node(Board board) {
        this.board = board;
        this.children = new LinkedList<>();
        this.visit = Visit.UNVISITED;
    }

    public void branch(char sequence[]) {

        for (int movmentBranch = 0; movmentBranch < sequence.length; movmentBranch++) {

            switch (sequence[movmentBranch]) {
                case 'L':
                    makeMoveLeftBranch();
                    break;
                case 'R':
                    makeMoveRightBranch();
                    break;
                case 'U':
                    makeMoveUpBranch();
                    break;
                case 'D':
                    makeMoveDownBranch();
                    break;
                default:
                    System.err.println("Impossible movement. Sequence letters should be {L, R, U, D}.");
                    break;

            }
        }
    }

    private void makeMoveLeftBranch() {
        if (board.moveLeftIsPossible()) {
            Board childBoard = board.clone();
            childBoard.moveLeft();
            children.add(new Node(childBoard));

            System.out.println("L\n" + childBoard);
        }
    }

    private void makeMoveRightBranch() {
        if (board.moveRightIsPossible()) {
            Board childBoard = board.clone();
            childBoard.moveRight();
            children.add(new Node(childBoard));

            System.out.println("R\n" + childBoard);
        }
    }

    private void makeMoveUpBranch() {
        if (board.moveUpIsPossible()) {
            Board childBoard = board.clone();
            childBoard.moveUp();
            children.add(new Node(childBoard));

            System.out.println("U\n" + childBoard);
        }
    }

    private void makeMoveDownBranch() {
        if (board.moveDownIsPossible()) {
            Board childBoard = board.clone();
            childBoard.moveDown();
            children.add(new Node(childBoard));

            System.out.println("D\n" + childBoard);
        }
    }

    public boolean isGoal () {
        return board.isCorrect();
    }
}