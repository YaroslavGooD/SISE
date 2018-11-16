package core;

import java.util.LinkedList;
import java.util.Queue;

public class Node {
    public Queue<Node> children;
    public Board board;
    public Visit visit;
    public Movement fromWhichMove;
    public short recursionDepth;
    public Node parent;

    public Node(Board board, Movement fromWhich, Node parent, short currentDepth) {
        this.board = board;
        this.children = new LinkedList<>();
        this.visit = Visit.UNVISITED;
        this.fromWhichMove = fromWhich;
        this.recursionDepth = (short) (currentDepth + 1);
        this.parent = parent;
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
        if (board.moveLeftIsPossible() && fromWhichMove != Movement.RIGHT) {
            Board childBoard = board.clone();
            childBoard.moveLeft();
            children.add(new Node(childBoard, Movement.LEFT, this, recursionDepth));
        }
    }

    private void makeMoveRightBranch() {
        if (board.moveRightIsPossible() && fromWhichMove != Movement.LEFT) {
            Board childBoard = board.clone();
            childBoard.moveRight();
            children.add(new Node(childBoard, Movement.RIGHT, this, recursionDepth));
        }
    }

    private void makeMoveUpBranch() {
        if (board.moveUpIsPossible() && fromWhichMove != Movement.DOWN) {
            Board childBoard = board.clone();
            childBoard.moveUp();
            children.add(new Node(childBoard, Movement.UP, this, recursionDepth));
        }
    }

    private void makeMoveDownBranch() {
        if (board.moveDownIsPossible() && fromWhichMove != Movement.UP) {
            Board childBoard = board.clone();
            childBoard.moveDown();
            children.add(new Node(childBoard, Movement.DOWN, this, recursionDepth));
        }
    }

    public boolean isGoal() {
        return board.isCorrect();
    }

    public String toString() {
        String info = "";
        info += fromWhichMove;
        info += "\n" + board;
        return info;
    }
}
