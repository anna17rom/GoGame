package org.example;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.util.Date;

public class GoServer extends JFrame {

    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;

    private JTextArea textArea;

    public GoServer() {
        MainMenu.MenuListener menuListener = new MainMenu.MenuListener() {
            @Override
            public void onTwoPlayersSelected() {
                startGame(false); // Uruchom grę dla dwóch graczy
            }

            @Override
            public void onBotGameSelected() {
                startGame(true); // Uruchom grę z botem
            }

            @Override
            public void onExitSelected() {
                System.exit(0);
            }
        };

        MainMenu mainMenu = new MainMenu(menuListener);
        setJMenuBar(mainMenu);

        // Utwórz obszar tekstowy
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Ustawienia ramki
        setSize(550, 300);
        setTitle("GoServer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void startGame(boolean withBot) {
        // Kod rozpoczynający grę (jak wcześniej)
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GoServer();
            }
        });
    }
}
