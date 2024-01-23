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
    private JComboBox<String> sizeComboBox;

    private boolean continueToPlay = true;
    private boolean waiting = true;
    private MainMenu mainMenu;
    private BoardPanel gameBoard;
    private BlockingQueue<Command.Type> menuQ = new LinkedBlockingQueue<>();
    private BlockingQueue<Integer> sizeQ = new LinkedBlockingQueue<>();

    private int size;
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
        if (gameBoard == null) {
            gameBoard = new BoardPanel(board, stoneConsumer);
            add(gameBoard, BorderLayout.CENTER);
            getContentPane().add(gameBoard);
            gameBoard.setVisible(true);
        }


        gameBoard.setGoBoard(board);
        gameBoard.render();
    }

    private void setupMainMenu() {
        MainMenu.MenuListener menuListener = new MainMenu.MenuListener() {
            @Override
            public void onTwoPlayersSelected() {
                menuQ.add(Command.Type.START_2P_GAME);
                showBoardSizeSelection();
            }

            @Override
            public void onBotGameSelected() {
                menuQ.add(Command.Type.START_CPU_GAME);
                showBoardSizeSelection();
            }

            @Override
            public void onExitSelected() {
                System.exit(0);
            }
        };

        mainMenu = new MainMenu(menuListener);
        setJMenuBar(mainMenu);
    }
    private void showBoardSizeSelection() {
        String[] sizes = {"9x9", "13x13", "19x19"};
        sizeComboBox = new JComboBox<>(sizes);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Select Board Size:"));
        panel.add(sizeComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Board Size Selection",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String selectedSize = (String) sizeComboBox.getSelectedItem();
            handleBoardSizeSelection(selectedSize);
        }
    }
    private void handleBoardSizeSelection(String selectedSize) {
        switch (selectedSize) {
            case "9x9":
                sizeQ.add(8);
                break;
            case "13x13":
                sizeQ.add(12);
                break;
            case "19x19":
                sizeQ.add(18);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + selectedSize);
        }}

    public void setBoard(GoBoard board) {
        this.board = board;
    }
    public int readSize(){
        try {
            return sizeQ.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Command.Type readGameMode() {
        try {
            return menuQ.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
































