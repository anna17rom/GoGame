package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class GoServer extends JFrame {

    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;

    public static void main(String[] args) {
        GoServer display = new GoServer();
    }

    public GoServer() {
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        setSize(550, 300);
        setTitle("GoServer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            textArea.append(new Date() + ":     Server started at socket 8000\n");
            int sessionNum = 1;

            while (true) {
                textArea.append(new Date() + ":     Waiting for players to join session " + sessionNum + "\n");

                // Connection to player1
                Socket firstPlayer = serverSocket.accept();
                textArea.append(new Date() + ":     Player 1 joined session " + sessionNum + ". Player 1's IP address " + firstPlayer.getInetAddress().getHostAddress() + "\n");
                new DataOutputStream(firstPlayer.getOutputStream()).writeInt(PLAYER1);

                // Connection to player2
                Socket secondPlayer = serverSocket.accept();
                textArea.append(new Date() + ":     Player 2 joined session " + sessionNum + ". Player 2's IP address " + secondPlayer.getInetAddress().getHostAddress() + "\n");
                new DataOutputStream(secondPlayer.getOutputStream()).writeInt(PLAYER2);

                // Starting the thread for two players
                textArea.append(new Date() + ":     Starting a thread for session " + sessionNum++ + "...\n");
                GoSession task = new GoSession(firstPlayer, secondPlayer);
                Thread t1 = new Thread(task);
                t1.start();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    // Pozostała część kodu pozostaje taka sama
}
