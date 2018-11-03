package core;

/***
 * This class represents a board.
 */

public class Board implements Cloneable{

    private int width;          //number of columns of puzzle
    private int height;         //number of rows of puzzle
    public int tiles[][];       //represents tiles on the board

    public class ZeroPosition {
        public int y;
        public int x;
    }
    public ZeroPosition zero;

    public Board(int tiles[][]) {
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.tiles = tiles;
        zero = new ZeroPosition();
        lookForZero();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getTiles() { return tiles; }

    public int getZeroY() {
        return zero.y;
    }

    public int getZeroX() {
        return zero.x;
    }

    public void setZero(int row, int col) {
        this.zero.x = col;
        this.zero.y = row;
    }

    private void lookForZero() {
        zero = new ZeroPosition();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tiles[x][y] == 0) {
                    zero.y = y;
                    zero.x = x;
                    return;
                }
            }
        }
    }

    public boolean moveLeftIsPossible() {
        if (zero.x == 0)
            return false;
        return true;
    }

    public boolean moveRightIsPossible() {
        if (zero.x == (width - 1))
            return false;
        return true;
    }

    public boolean moveDownIsPossible() {
        if (zero.y == (height - 1))
            return false;
        return true;
    }

    public boolean moveUpIsPossible() {
        if (zero.y == 0)
            return false;
        return true;
    }

    private void swapZeroWith(int xTile, int yTile) {
        int tempTile = tiles[xTile][yTile];
        tiles[xTile][yTile] = 0;
        tiles[zero.x][zero.y] = tempTile;

        zero.y = yTile;
        zero.x = xTile;
    }

    public void moveLeft() {
        swapZeroWith((zero.x - 1), zero.y);
    }

    public void moveRight() {
        swapZeroWith((zero.x + 1), zero.y);
    }

    public void moveUp() {
        swapZeroWith(zero.x, (zero.y - 1));
    }

    public void moveDown() {
        swapZeroWith(zero.x, (zero.y + 1));
    }

    public boolean isCorrect() {
        if (zeroOnBadPosition()) {
            return false;
        }

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                int correctValue = ((col * width) + row) + 1;
                if ((tiles[row][col] != correctValue) && (tiles[row][col] > 0))  {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean zeroOnBadPosition() {
        if (tiles[width - 1][height - 1] != 0)
            return true;
        else
            return false;
    }

    public Board clone() {

        int tilesCopy[][] = new int[width][height];
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                tilesCopy[row][col] = tiles[row][col];
            }
        }
        Board boardCopy = new Board(tilesCopy);

        return boardCopy;
    }

    @Override
    public String toString() {
        StringBuilder boardInfo = new StringBuilder();
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                boardInfo.append(tiles[row][col] + " ");
            }
            boardInfo.append("\n");
        }

        return boardInfo.toString();
    }
}
