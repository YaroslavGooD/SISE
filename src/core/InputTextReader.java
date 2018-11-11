package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InputTextReader {

    private String fileName;
    private Scanner scanner;

    public InputTextReader(String fileName) {
        this.fileName = fileName;
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
            tiles = new int[boardWidth][boardHeight];

            //Reading board values
            for (int col = 0; col < boardWidth; col++) {
                for (int row = 0; row < boardHeight; row++) {
                    tiles[row][col] = scanner.nextInt();
                }
            }
        }
        catch (Exception e) {
            System.err.print("Cannot read file.");
        }
        finally {
            scanner.close();
        }

        return tiles;
    }
}
