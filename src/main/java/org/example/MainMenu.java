package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JMenuBar {

    public interface MenuListener {
        void onTwoPlayersSelected();
        void onBotGameSelected();
        void onExitSelected();
    }

    public MainMenu(MenuListener listener) {
        JMenu fileMenu = new JMenu("File");
        add(fileMenu);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);

        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listener.onExitSelected();
            }
        });

        JMenu optionsMenu = new JMenu("Options");
        add(optionsMenu);

        JMenuItem twoPlayersMenuItem = new JMenuItem("Two Players");
        optionsMenu.add(twoPlayersMenuItem);

        JMenuItem botGameMenuItem = new JMenuItem("Game with Bot");
        optionsMenu.add(botGameMenuItem);

        twoPlayersMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listener.onTwoPlayersSelected();
            }
        });

        botGameMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listener.onBotGameSelected();
            }
        });
    }
}

