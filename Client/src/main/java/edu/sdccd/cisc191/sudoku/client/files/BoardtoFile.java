package edu.sdccd.cisc191.sudoku.client.files;

import java.io.*;

public class BoardtoFile {
    private static java.io.File file;
    String path;

    public static DataInputStream inputStream;

    static FileWriter writer;

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

    public String getPath() {
        return file.getAbsolutePath();
    }

    public boolean delete() {
        return file.delete();
    }

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

    public void readBoardFromFile() throws IOException {
        int[][] board = readArray();
        int[][] unsolvedBoard = readArray();
        int minutes = inputStream.readShort();
        int seconds = inputStream.readShort();

        // Do whatever you need to do with the read data
        // For example, print them
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

    public static void closeInputStream() throws IOException {
        inputStream.close();
    }

    public static void closeWriter() throws IOException {
        writer.close();
    }

    public static void resetInputStream() throws FileNotFoundException {
        inputStream = new DataInputStream(new FileInputStream(file));
    }
}
