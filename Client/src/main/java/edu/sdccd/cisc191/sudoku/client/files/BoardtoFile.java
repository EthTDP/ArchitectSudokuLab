package edu.sdccd.cisc191.sudoku.client.files;

import java.io.*;

/**
 * Class to send the board to a file.
 */
public class BoardtoFile {
    private static java.io.File file;
    String path;
    public static DataInputStream inputStream;
    static FileWriter writer;

    /**
     * Create a new BoardtoFile constructor that takes in parameters and throws IOException
     *
     * @param path     the path
     * @param fileName the file name
     * @throws IOException the io exception
     */
    public BoardtoFile(String path, String fileName) throws IOException {
        this.path = path;
        file = new java.io.File(path + "\\" + fileName);
        if(!file.isFile() && file.exists()) {
            throw new IOException("path " + "is not a file");
        }

        if(!file.exists()) {
            if(file.createNewFile())
                inputStream = new DataInputStream(new FileInputStream(file));
        } else {
            inputStream = new DataInputStream(new FileInputStream(file));
        }
    }

    /**
     * Gets the path.
     *
     * @return the path
     */
    public String getPath() {
        return file.getAbsolutePath();
    }

    /**
     * Check to see if we have deleted the file.
     *
     * @return delete?
     */
    public boolean delete() {
        return file.delete();
    }

    /**
     * Write the board to file.
     *
     * @param board         the board
     * @param unsolvedBoard the unsolved board
     * @param minutes       the minutes
     * @param seconds       the seconds
     * @throws IOException the io exception
     */
    public void writeBoardToFile(int[][] board, int[][] unsolvedBoard, int minutes, int seconds) throws IOException {
        if(file.exists()) {
            if(file.createNewFile())
                System.out.println("refreshed file!");
        }

        DataOutputStream os = new DataOutputStream(new FileOutputStream(file));

        for (int[] row : board) {
            for (int num : row) {
                os.writeShort(num);
            }
            os.writeBytes("\n");
        }
        os.writeBytes("\n");

        // Write the second 2D array (unsolvedBoard)
        for (int[] row : unsolvedBoard) {
            for (int num : row) {
                os.writeShort(num);
            }
            os.writeBytes("\n");
        }
        os.writeBytes("\n");

        // Write minutes and seconds
        os.writeShort(minutes);
        os.writeShort(seconds);
        os.writeBytes("\n");

        System.out.println("Arrays and integers written to file.");
    }

    /**
     * Read the board from the file
     *
     * @throws IOException the io exception
     */
    public void readBoardFromFile() throws IOException {
        int[][] board = readArray();
        int[][] unsolvedBoard = readArray();
        int minutes = inputStream.readShort();
        int seconds = inputStream.readShort();

        System.out.println("Unsolved Board:");
        printArray(board);
        System.out.println("Solved Board:");
        printArray(unsolvedBoard);
        System.out.println("Minutes: " + minutes);
        System.out.println("Seconds: " + seconds);
    }


    private int[][] readArray() throws IOException {
        int[][] array = new int[9][9]; // Assuming size is known

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                int value = inputStream.readShort();
                array[i][j] = value;
            }
            // Skip newline
            inputStream.readLine();
        }

        // Skip empty line
        inputStream.readLine();

        return array;
    }


    private void printArray(int[][] array) {
        for (int[] row : array) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }

    /**
     * Write board to a file that is readable.
     *
     * @param path    the path
     * @param newFile the new file
     * @throws IOException the io exception
     */
    public void writeToFile(String path, String newFile) throws IOException {
        File file = new File(path + "\\" + newFile);
        if(file.exists()) {
            if(file.createNewFile())
                System.out.println("refreshed file!");
        }

        writer = new FileWriter(path + "\\" + newFile);
        resetInputStream();

        int data;

        while(inputStream.available() > 0) {
            if((data = inputStream.read()) != -1 ) {
                if(data == '\n') {
                    writer.write("\n");
                    continue;
                }
            }

            writer.write(String.valueOf(data));
            writer.write(" ");
        }

        writer.flush();
    }

    /**
     * Close the input stream.
     *
     * @throws IOException the io exception
     */
    public static void closeInputStream() throws IOException {
        inputStream.close();
    }

    /**
     * Close the writer.
     *
     * @throws IOException the io exception
     */
    public static void closeWriter() throws IOException {
        writer.close();
    }

    /**
     * Reset input stream.
     *
     * @throws FileNotFoundException the file not found exception
     */
    public static void resetInputStream() throws FileNotFoundException {
        inputStream = new DataInputStream(new FileInputStream(file));
    }
}
