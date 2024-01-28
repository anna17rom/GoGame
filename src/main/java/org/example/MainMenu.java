package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JMenuBar {

    // Interfejs listenera do obsługi zdarzeń z menu
    public interface MenuListener {
        void onTwoPlayersSelected();
        void onBotGameSelected();
        void onExitSelected();
        void onRecordedGamePlaybackSelected();
    }

    // Konstruktor klasy MainMenu
    public MainMenu(MenuListener listener) {
        // Tworzenie menu "File"
        JMenu fileMenu = new JMenu("File");
        add(fileMenu);

        // Tworzenie elementu menu "Exit"
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);

        // Dodanie słuchacza zdarzeń dla elementu menu "Exit"
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Wywołanie metody listenera po wybraniu "Exit"
                listener.onExitSelected();
            }
        });

        // Tworzenie menu "Options"
        JMenu optionsMenu = new JMenu("Options");
        add(optionsMenu);

        // Tworzenie elementu menu "Two Players"
        JMenuItem twoPlayersMenuItem = new JMenuItem("Two Players");
        optionsMenu.add(twoPlayersMenuItem);

        // Tworzenie elementu menu "Game with Bot"
        JMenuItem botGameMenuItem = new JMenuItem("Game with Bot");
        optionsMenu.add(botGameMenuItem);

        // Tworzenie elementu menu "Recorded Game Playback"
        JMenuItem recordedGamePlaybackMenuItem = new JMenuItem("Recorded Game Playback");
        optionsMenu.add(recordedGamePlaybackMenuItem);

        // Dodanie słuchacza zdarzeń dla elementu menu "Two Players"
        twoPlayersMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Wywołanie metody listenera po wybraniu "Two Players"
                listener.onTwoPlayersSelected();
            }
        });

        // Dodanie słuchacza zdarzeń dla elementu menu "Game with Bot"
        botGameMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Wywołanie metody listenera po wybraniu "Game with Bot"
                listener.onBotGameSelected();
            }
        });

        // Dodanie słuchacza zdarzeń dla elementu menu "Recorded Game Playback"
        recordedGamePlaybackMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Wywołanie metody listenera po wybraniu "Recorded Game Playback"
                listener.onRecordedGamePlaybackSelected();
            }
        });
    }
}
