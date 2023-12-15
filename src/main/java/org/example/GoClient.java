package org.example;
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class GoClient extends JFrame {


    private Socket socket;
    private DataOutputStream toServer;
    private MainMenu mainMenu;

    public GoClient() {
        setTitle("Go Client");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setupMainMenu();
        connectToServer();
    }

    private void setupMainMenu() {
        MainMenu.MenuListener menuListener = new MainMenu.MenuListener() {
            @Override
            public void onTwoPlayersSelected() {
                sendGameChoice("TWO_PLAYERS");
            }

            @Override
            public void onBotGameSelected() {
                sendGameChoice("BOT_GAME");
            }

            @Override
            public void onExitSelected() {
                System.exit(0);
            }
        };

        mainMenu = new MainMenu(menuListener);
        setJMenuBar(mainMenu);
    }

    private void sendGameChoice(String choice) {
        try {
            toServer.writeBytes(choice + "\n");
            System.out.println("Wysłano wybór gry: " + choice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 8000);
            toServer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new GoClient().setVisible(true);
    }
}

