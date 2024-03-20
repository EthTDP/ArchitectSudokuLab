package edu.sdccd.cisc191.sudoku.module6;

import edu.sdccd.cisc191.sudoku.common.CommonTest;
import edu.sdccd.cisc191.sudoku.server.SudokuServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;

public class NetworkingTest {

    @Test
    void testNetwork() throws Exception {
        SudokuServer server = new SudokuServer();
        server.start(4444);

        ServerSocket socket = server.currentServerSocket();
        Assertions.assertEquals(4444, socket.getLocalPort());
    }
}
