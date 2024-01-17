package org.example;

import org.example.BoardPanel;
import org.example.GoBoard;
import org.example.MainMenu;
import org.example.Command;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class GoUI extends JFrame implements Runnable {

    private final JLabel titleLabel = new JLabel();
    private final JLabel statusLabel = new JLabel();

    private boolean continueToPlay = true;
    private boolean waiting = true;
    private MainMenu mainMenu;
    private BoardPanel gameBoard;
    private BlockingQueue<Command.Type> menuQ = new LinkedBlockingQueue<>();

    private GoBoard board;

    private final Consumer<GoBoard.Stone> stoneConsumer;
    public GoUI(GoBoard board, Consumer<GoBoard.Stone> stoneConsumer) {
        this.board = board;
        this.stoneConsumer = stoneConsumer;
    }

    @Override
    public void run() {
        setTitle("Go Client");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setupMainMenu();

        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setBorder(new LineBorder(Color.black, 1));
        statusLabel.setBorder(new LineBorder(Color.black, 1));

        add(titleLabel, BorderLayout.NORTH);
        add(statusLabel, BorderLayout.SOUTH);

        this.mainMenu.setVisible(true);
        setVisible(true);

    }

    public void renderBoard(GoBoard board) {
        gameBoard = new BoardPanel(board, stoneConsumer);
        add(gameBoard, BorderLayout.CENTER);
        gameBoard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = getX();
                int y = getY();

                statusLabel.setText("Waiting for the other player to move");
                waiting = false;
                stoneConsumer.accept(new GoBoard.Stone(x, y, null));
            }
        });
        gameBoard.setGoBoard(board);
        gameBoard.setVisible(true);
        getContentPane().add(gameBoard);
        gameBoard.render();
    }

    private void setupMainMenu() {
        MainMenu.MenuListener menuListener = new MainMenu.MenuListener() {
            @Override
            public void onTwoPlayersSelected() {
                menuQ.add(Command.Type.START_2P_GAME);
            }

            @Override
            public void onBotGameSelected() {
                menuQ.add(Command.Type.START_CPU_GAME);
            }

            @Override
            public void onExitSelected() {
                System.exit(0);
            }
        };

        mainMenu = new MainMenu(menuListener);
        setJMenuBar(mainMenu);
    }

    public void setBoard(GoBoard board) {
        this.board = board;
    }

    public Command.Type readGameMode() {
        try {
            return menuQ.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
