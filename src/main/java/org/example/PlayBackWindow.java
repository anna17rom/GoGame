package org.example;

import org.example.DB.GoDb;
import org.example.DB.JpaUtil;
import org.example.DB.Move;
import org.example.DB.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class PlayBackWindow extends JFrame {
    private JTextField sessionIDField;
    private String sessionID;
    private JButton okButton;
    public BlockingQueue<String> sessionQ = new LinkedBlockingQueue<>();
    private Consumer<GoBoard.Stone> stoneConsumer;

    public PlayBackWindow(Consumer<GoBoard.Stone> stoneConsumer) {
        this.stoneConsumer = stoneConsumer;
        initializeComponents();
        setupLayout();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        sessionIDField = new JTextField(20);

        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sessionID = sessionIDField.getText();
                sessionQ.add(sessionID);
                dispose();
                PlayBackGameOpen(stoneConsumer);
            }
        });
    }

    private void setupLayout() {
        setLayout(new FlowLayout());
        add(new JLabel("Enter Session ID: "));
        add(sessionIDField);
        add(new JLabel(" "));
        add(okButton);
    }

    /*public String getSessionID() {
        return sessionID;
    }*/

    public String readSessionID() {
        try {
            return sessionQ.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    } public void PlayBackGameOpen(Consumer<GoBoard.Stone> stoneConsumer) {
        JFrame newWindow = new JFrame("Playback Window");
        newWindow.setSize(500, 500);
        newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newWindow.setLayout(new BorderLayout());
        newWindow.setVisible(true);
        String id = this.readSessionID();
        GoDb goDb = new GoDb(JpaUtil.getEntityManager());

        if (id!=null) {
            Session session = goDb.getSessionByID(id);
            List<Move> moves = goDb.getMovesForSession(id);
            GoBoard playbackGoBoard = new GoBoard(session.getSize());
            BoardPanel playbackBoardPanel = new BoardPanel(playbackGoBoard, stoneConsumer);
            newWindow.add(playbackBoardPanel, BorderLayout.CENTER);
            playbackBoardPanel.setVisible(true);
            Timer timer = new Timer(1000, null);
            timer.addActionListener(new ActionListener() {
                private int currentMoveIndex = 0;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentMoveIndex < moves.size()) {
                        Move move = moves.get(currentMoveIndex);
                        GoBoard.StoneColor stoneColor;
                        int x = move.getX();
                        int y = move.getY();
                        int color = move.getColor();
                        if (color == 1) {
                            stoneColor = GoBoard.StoneColor.WHITE;
                        } else {
                            stoneColor = GoBoard.StoneColor.BLACK;
                        }
                        GoBoard.Stone stone = new GoBoard.Stone(x, y, stoneColor);
                        playbackGoBoard.addStone(stone);
                        playbackBoardPanel.setGoBoard(playbackGoBoard);
                        playbackBoardPanel.render();
                        currentMoveIndex++;

                        if (currentMoveIndex >= moves.size()) {
                            timer.stop();
                        }
                    }
                }
            });

            timer.start();
        }
    }
}