package edu.sdccd.cisc191.sudoku.client.files;

import java.io.*;

public class BoardtoFile {
    private java.io.File file;
    String path;
    public static DataInputStream inputStream;

    public BoardtoFile(String path, String fileName) throws IOException {
        this.path = path;
        file = new java.io.File(path + "\\" + fileName);
        if(!file.isFile() && file.exists()) {
            throw new IOException("path " + "is not a file");
        }

        inputStream = new DataInputStream(new FileInputStream(file));
    }

    public String getPath() {
        return file.getAbsolutePath();
    }

    public boolean delete() {
        return file.delete();
    }

    public void copyBoardToFile(int[][] board) throws IOException {
        DataOutputStream os = new DataOutputStream(new FileOutputStream(file));

        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[i].length; j++)
            {
                os.writeInt(board[i][j]);
            }
        }

        os.close();

        System.out.println("Array of ints written!");
    }

    public void readBoardFromFile() throws IOException {
        while(inputStream.available() > 0) {
            int number = inputStream.readInt();
            System.out.print(number + " ");
        }

        inputStream.close();
    }

    public void writeToFile(String path, String newFile) throws IOException {
        FileWriter writer = new FileWriter(path + "\\" + newFile);

        int i = 0;
        while(inputStream.available() > 0) {
            if(i == 9) {
                writer.write("\n");
                i = 0;
            }
            int number = inputStream.readInt();
            writer.write(String.valueOf(number));
            writer.write(" ");
            i++;
        }

        writer.close();
        inputStream.close();
    }
}
