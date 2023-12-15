package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import org.example.Stone;

public class GoBoard extends JFrame {
    private final ArrayList<Stone> stones = new ArrayList<>();
    private final int gridSize = 50;
    private final int numberOfSquares = 8;
    private int boardSize  = gridSize * numberOfSquares + 100; ;

    public GoBoard() {
        this.setSize(boardSize, boardSize);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Go Board");

        JPanel panel = new JPanel() {
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

                // Check if the click is near the intersection point
                int gridSize = 50;
                int closestX = Math.round((float) (x - 50) / gridSize) * gridSize + 50;
                int closestY = Math.round((float) (y - 50) / gridSize) * gridSize + 50;

                // Check if the click is within the bounds of a circle at the intersection
                if (isCircleClicked(closestX, closestY, x, y)) {
                    stones.add(new Stone(closestX, closestY));
                    repaint();
                }
            }
        });

        this.add(panel);
    }
    private void drawBoard(Graphics g) {
        g.setColor(new Color(244, 164, 96)); // Light brown color
        g.fillRect(50, 50, gridSize * numberOfSquares, gridSize * numberOfSquares);
    }
    private void drawGrid(Graphics g) {
        g.setColor(new Color(139, 69, 19)); // Brown color
        for (int i = 50; i <= gridSize * numberOfSquares; i += gridSize) {
            for (int j = 50; j <= gridSize * numberOfSquares; j += gridSize) {
                g.drawRect(i, j, gridSize, gridSize);
            }
        }
    }
    private void drawStones(Graphics g) {
        for (Stone stone : stones) {
            g.setColor(Color.BLACK);
            g.fillOval(stone.x - 10, stone.y - 10, 20, 20);
        }
    }

    private boolean isCircleClicked(int centerX, int centerY, int clickX, int clickY) {
        int radius = 10; // Adjust the radius as needed
        return Math.pow(clickX - centerX, 2) + Math.pow(clickY - centerY, 2) <= Math.pow(radius, 2);
    }

    public void displayBoardForPlayer1() {
        setTitle("Go Board - Player 1");
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public void displayBoardForPlayer2() {
        setTitle("Go Board - Player 2");
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public void displayBoardForBot() {
        setTitle("Go Board - vs Bot");
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public void closeBoard() {
        SwingUtilities.invokeLater(() -> setVisible(false));
    }


    // Example usage
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GoBoard goBoard = new GoBoard();
            goBoard.displayBoardForPlayer1();
        });
    }
}
