package org.example;

import org.example.GoBoard.Stone;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class BoardPanel extends JPanel {

    private GoBoard goBoard; // Plansza Go
    private final Consumer<Stone> stoneConsumer; // Konsumer do przetwarzania kamieni
    private JButton passButton; // Przycisk "Pass" dla pominięcia tury

    // Konstruktor klasy BoardPanel
    public BoardPanel(GoBoard goBoard, Consumer<Stone> stoneConsumer) {
        this.goBoard = goBoard;
        this.stoneConsumer = stoneConsumer;
        initializeUI(); // Inicjalizacja interfejsu użytkownika
    }

    // Metoda czyszcząca planszę
    public void clearBoard() {
        goBoard.getStones().clear();
        repaint();
    }

    // Metoda dodająca kamień do planszy
    public void addStoneToBoard(Stone stone) {
        goBoard.addStone(stone.getX(), stone.getY(), stone.getColor());
        repaint();
    }

    // Metoda ustawiająca planszę Go
    public void setGoBoard(GoBoard board) {
        this.goBoard = board;
    }

    // Metoda do odświeżania widoku planszy
    public void render() {
        repaint();
    }

    // Inicjalizacja interfejsu użytkownika
    private void initializeUI() {
        setLayout(new BorderLayout());

        passButton = new JButton("Pass"); // Przycisk "Pass" dla pominięcia tury
        passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stoneConsumer.accept(new Stone(0,0, GoBoard.StoneColor.BLACK)); // Akcja Pass, wysyłanie pustego kamienia
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(passButton);

        add(buttonPanel, BorderLayout.NORTH);
        setSize(goBoard.getGridSize() * goBoard.getNumberOfSquares() + 100, goBoard.getGridSize() * goBoard.getNumberOfSquares() + 100);

        addMouseListener(new MouseAdapter() {
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

    }

    // Metoda do rysowania komponentu
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawGrid(g);
        drawStones(g);
    }

    // Metoda do rysowania planszy
    private void drawBoard(Graphics g) {
        g.setColor(new Color(244, 164, 96));
        g.fillRect(50, 50, goBoard.getGridSize() * goBoard.getNumberOfSquares(), goBoard.getGridSize() * goBoard.getNumberOfSquares());
    }

    // Metoda do rysowania siatki na planszy
    private void drawGrid(Graphics g) {
        g.setColor(new Color(139, 69, 19));
        for (int i = 50; i <= goBoard.getGridSize() * goBoard.getNumberOfSquares(); i += goBoard.getGridSize()) {
            for (int j = 50; j <= goBoard.getGridSize() * goBoard.getNumberOfSquares(); j += goBoard.getGridSize()) {
                g.drawRect(i, j, goBoard.getGridSize(), goBoard.getGridSize());
            }
        }
    }

    // Metoda do rysowania kamieni na planszy
    private void drawStones(Graphics g) {
        for (Stone stone : goBoard.getStones()) {
            g.setColor(stone.color == GoBoard.StoneColor.BLACK ? Color.BLACK : Color.WHITE);
            g.fillOval(stone.x - 10, stone.y - 10, 20, 20);
        }
    }

    // Metoda sprawdzająca, czy kliknięto w okrąg
    private boolean isCircleClicked(int centerX, int centerY, int clickX, int clickY) {
        int radius = 10;
        return Math.pow(clickX - centerX, 2) + Math.pow(clickY - centerY, 2) <= Math.pow(radius, 2);
    }

}
