package core;

import java.util.Arrays;
import java.util.Stack;

/***
 * This class represents a board.
 */

public class Board implements Cloneable{

    private int width;          //number of columns of puzzle
    private int height;         //number of rows of puzzle
    public int tiles[][];       //represents tiles on the board
    private int hamming;
    private int manhattan;

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
// added

    public int[][] swap(int[][] board, int rowOffset, int colOffset){
        int[][] tempBoard =  deepCopy(board);
        tempBoard[zero.x][zero.y]= tiles[zero.x+rowOffset][zero.y+colOffset];
        tempBoard[zero.x+rowOffset][zero.y+colOffset]=0;

        return tempBoard;
    }

    public int manhattan(){
        int arrayPosition;
        int tile;
        manhattan = 0;
        for(int i = 0; i <width; i++){
            for(int j = 0; j<height; j++){
                tile = tiles[i][j];
                if(tile == 0)
                    continue;
                arrayPosition = 1+ j +(i*this.width);

                if(arrayPosition-tile == 0)
                    continue;
                double ii = Math.floor(((double)(tile-1))/this.width);
                double jj = (tile-1)%this.width;
                manhattan +=  (Math.abs(i-ii)+Math.abs(j-jj));
                //StdOut.println("Tile:" + tile + " [MOVES:"+ (Math.abs(i-ii)+Math.abs(j-jj))+"]       | Offsets: i "+  Math.abs(i-ii) + " j "+  Math.abs(j-jj));
            }
        }
        return manhattan;
    }

    public int hamming(){
        int arrayPosition;
        int tile;
        int displaced =0;

        for(int i = 0; i <width; i++){
            for(int j = 0; j<height; j++){
                tile = tiles[i][j];
                if(tile ==0)
                    continue;
                arrayPosition = 1+ j +(i*this.width);

                if(tile != arrayPosition)
                    displaced++;
                else
                    continue;
            }
        }
        return displaced;
    }


    private int[][]deepCopy(int[][] array){
        int[][] copy = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                copy[i][j] =array[i][j];
            }
        }
        return copy;
    }

    public Board twin() {
        int[][] twin = deepCopy(tiles);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (tiles[i][j] != 0 && tiles[i][j + 1] != 0 && j < (width - 1)) {
                    int swap = twin[i][j];
                    twin[i][j] = twin[i][j + 1];
                    twin[i][j + 1] = swap;
                    return new Board(twin);
                }
            }
        }
        return new Board(twin);
    }

    public boolean equals(Object y){
        Board that = (Board) y;
        if (y == null) return false;
        if (this == y) return true;
        if (this.getClass() != y.getClass()) return false;
        if (that.width != this.width) return false;

        return Arrays.deepEquals(this.tiles, that.tiles );
    }

    public Iterable<Board> neighbors(){
        Stack<Board> boards = new Stack<Board>();

        if(zero.x > 0){
            Board boardUP = new Board(swap(tiles,-1,0));
            boards.push(boardUP);
        }

        if(zero.x < width-1){
            Board boardDown = new Board(swap(tiles,1,0));
            boards.push(boardDown);
        }

        if(zero.y > 0){
            Board boardLeft = new Board(swap(tiles,0,-1));
            boards.push(boardLeft);
        }

        if(zero.y < width-1){
            Board boardRight = new Board(swap(tiles,0,1));
            boards.push(boardRight);
        }

        return boards;

    }




}
