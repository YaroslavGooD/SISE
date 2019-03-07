/***
 * Class loads input from file in format:
 * 4 4          <- width and height of the board
 * 1 2 3        <- elements of the board
 * 4 5 6
 * 7 0 8
 */

package core;

import exceptions.BadPuzzleException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputTextReader {

    private String fileName;
    private Scanner scanner;
    private List<Integer> elements;     //Used to check if elements on the lists do not repeat.

    public InputTextReader(String fileName) {
        this.fileName = fileName;
        this.elements = new ArrayList<>();

        try {
            scanner = new Scanner(new File(fileName));//.useDelimiter("\\s");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int[][] loadInputFileToBoard() {

        int tiles[][] = new int[0][];

        try {
            //Reading board dimensions
            int boardWidth = scanner.nextInt();
            int boardHeight = scanner.nextInt();
            //Creating tileboard
            tiles = new int[boardHeight][boardWidth];

            //Reading board values and looking for errors
            for (int col = 0; col < boardWidth; col++) {
                for (int row = 0; row < boardHeight; row++) {
                    tiles[row][col] = scanner.nextInt();
                    checkForErrors(tiles[row][col], boardWidth, boardHeight);
                }
            }
        } catch (BadPuzzleException e) {
            System.err.print("Input is incorrect.");
            System.exit(-1);
        } catch (Exception e) {
            System.err.print("Cannot read file.");
            System.exit(-2);
        } finally {
            scanner.close();
        }

        return tiles;
    }

    private void checkForErrors(int tile, int boardWidth, int boardHeight) throws BadPuzzleException {

        checkForIncorrectRangeError(tile, boardWidth, boardHeight);
        checkForRepeatingError(tile);
    }


    private void checkForIncorrectRangeError(int tile, int boardWidth, int boardHeight) throws BadPuzzleException {

        //If there are values out of range on the board
        if ((tile < 0) || (tile > (boardHeight * boardWidth - 1))) {
            throw new BadPuzzleException("Number is not in range {0..." + (boardHeight * boardWidth - 1) + "}");
        }
    }

    private void checkForRepeatingError(int tile) throws BadPuzzleException {

        //If values on the board are repeating.
        for (Integer n : elements) {
            if (tile == n.intValue()) {
                throw new BadPuzzleException("Values on the board cannot repeat.");
            }
        }

        elements.add(tile);
    }
}
