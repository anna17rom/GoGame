package org.example;

import org.example.GoBoard.Stone;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;


public class BoardPanel extends JPanel {

    private GoBoard goBoard;
    private final Consumer<Stone> stoneConsumer;
    JPanel panel;

    public BoardPanel(GoBoard goBoard, Consumer<Stone> stoneConsumer) {
        this.goBoard = goBoard;
        this.stoneConsumer = stoneConsumer;
        initializeUI();
    }

    public void setGoBoard(GoBoard board) {
        this.goBoard = board;
    }

    public void render() {
        panel.repaint();
    }

    private void initializeUI() {
        setSize(goBoard.getGridSize() * goBoard.getNumberOfSquares() + 100, goBoard.getGridSize() * goBoard.getNumberOfSquares() + 100);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
                drawGrid(g);
                drawStones(g);
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                int closestX = Math.round((float) (x - 50) / goBoard.getGridSize()) * goBoard.getGridSize() + 50;
                int closestY = Math.round((float) (y - 50) / goBoard.getGridSize()) * goBoard.getGridSize() + 50;

                if (isCircleClicked(closestX, closestY, x, y)) {
                    goBoard.addStone(closestX, closestY, GoBoard.StoneColor.BLACK);
                    repaint();
                }
                stoneConsumer.accept(new Stone(closestX, closestY, GoBoard.StoneColor.BLACK));
            }
        });

        add(panel);
        setVisible(true);
    }


    private void drawBoard(Graphics g) {
        g.setColor(new Color(244, 164, 96));
        g.fillRect(50, 50, goBoard.getGridSize() * goBoard.getNumberOfSquares(), goBoard.getGridSize() * goBoard.getNumberOfSquares());
    }

    private void drawGrid(Graphics g) {
        g.setColor(new Color(139, 69, 19));
        for (int i = 50; i <= goBoard.getGridSize() * goBoard.getNumberOfSquares(); i += goBoard.getGridSize()) {
            for (int j = 50; j <= goBoard.getGridSize() * goBoard.getNumberOfSquares(); j += goBoard.getGridSize()) {
                g.drawRect(i, j, goBoard.getGridSize(), goBoard.getGridSize());
            }
        }
    }

    private void drawStones(Graphics g) {
        for (Stone stone : goBoard.getStones()) {
            g.setColor(Color.BLACK);
            g.fillOval(stone.x - 10, stone.y - 10, 20, 20);
        }
    }

    private boolean isCircleClicked(int centerX, int centerY, int clickX, int clickY) {
        int radius = 10;
        return Math.pow(clickX - centerX, 2) + Math.pow(clickY - centerY, 2) <= Math.pow(radius, 2);
    }

}