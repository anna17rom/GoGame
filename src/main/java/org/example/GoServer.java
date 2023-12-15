package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.DataOutputStream;

public class GoServer extends JFrame {

    private static final int PORT = 8000;
    private ServerSocket serverSocket;
    private ExecutorService pool;
    private JTextArea logArea;

    public GoServer() {
        // Ustawienia okna
        setTitle("Serwer Go");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        try {
            serverSocket = new ServerSocket(PORT);
            pool = Executors.newCachedThreadPool();
        } catch (IOException e) {
            log("Błąd podczas tworzenia serwera: " + e.getMessage());
        }
    }

    public void startServer() {
        SwingUtilities.invokeLater(() -> setVisible(true));
        log("Serwer Go uruchomiony na porcie " + PORT);

        try {
            while (true) {
                Socket player1 = serverSocket.accept();
                log("Gracz 1 połączony.");

                GoSession session;
                BufferedReader inputFromPlayer1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
                String playerChoice = inputFromPlayer1.readLine();

                if ("TWO_PLAYERS".equals(playerChoice)) {
                    Socket player2 = serverSocket.accept();
                    log("Gracz 2 połączony.");
                    session = new GoSession(player1, player2);

                    // Wyślij informacje o rozpoczęciu gry dla obu graczy
                    DataOutputStream toPlayer1 = new DataOutputStream(player1.getOutputStream());
                    toPlayer1.writeBytes("START_GAME\n");

                    DataOutputStream toPlayer2 = new DataOutputStream(player2.getOutputStream());
                    toPlayer2.writeBytes("START_GAME\n");
                    // Wyświetl GoBoard dla obu graczy
                    SwingUtilities.invokeLater(() -> {
                        GoBoard sharedBoard = new GoBoard();
                        sharedBoard.displayBoardForPlayer1();
                    });

                    SwingUtilities.invokeLater(() -> {
                        GoBoard sharedBoard = new GoBoard();
                        sharedBoard.displayBoardForPlayer2();
                    });
                } else {
                    // Tutaj uruchom sesję z botem, jeśli wybrano grę z botem
                    session = new GoSession(player1, null);

                    // Wyślij informacje o rozpoczęciu gry dla pierwszego gracza
                    DataOutputStream toPlayer1 = new DataOutputStream(player1.getOutputStream());
                    toPlayer1.writeBytes("START_GAME\n");

                    // Wyświetl GoBoard dla pierwszego gracza
                    SwingUtilities.invokeLater(() -> {
                        GoBoard boardPlayer1 = new GoBoard();
                        boardPlayer1.displayBoardForBot();
                    });
                }

                pool.execute(session);
            }
        } catch (IOException e) {
            log("Błąd serwera: " + e.getMessage());
        }
    }



    private void log(String message) {
        SwingUtilities.invokeLater(() -> logArea.append(message + "\n"));
    }

    public static void main(String[] args) {
        new GoServer().startServer();
    }
}