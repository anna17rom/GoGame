package org.example;

import java.util.*;
import org.example.GoBoard;
import org.example.StoneChain;

public class Intersection {

    // Referencja do planszy GoBoard, do której należy skrzyżowanie
    private final GoBoard goBoard ;

    // Współrzędne x i y skrzyżowania
    private final int x, y;

    // Łańcuch kamieni związany ze skrzyżowaniem
    private StoneChain stoneChain;

    // Konstruktor skrzyżowania
    public Intersection(GoBoard goBoard, int x, int y) {
        this.goBoard = goBoard;
        this.x = x;
        this.y = y;
        this.stoneChain = null;
    }

    // Metoda zwracająca współrzędną x skrzyżowania
    public int getX() {
        return x;
    }

    // Metoda zwracająca współrzędną y skrzyżowania
    public int getY() {
        return y;
    }

    // Metoda zwracająca łańcuch kamieni związany ze skrzyżowaniem
    public StoneChain getStoneChain() {
        return stoneChain;
    }

    // Metoda ustawiająca łańcuch kamieni dla skrzyżowania
    public void setStoneChain(StoneChain stoneChain) {
        this.stoneChain = stoneChain;
    }

    // Metoda sprawdzająca, czy skrzyżowanie jest puste
    public boolean isEmpty() {
        return stoneChain == null;
    }

    // Metoda zwracająca zbiór sąsiednich łańcuchów kamieni
    public Set<StoneChain> getNeighborsStoneChains() {
        Set<StoneChain> neighborsStoneChains = new HashSet<>();
        int[][] directions = {{-50, 0}, {0, -50}, {50, 0}, {0, 50}};

        for (int[] direction: directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];

            if (goBoard.isInGoBoard(newX, newY)) {
                Intersection adjIntersection = goBoard.getIntersection(newX, newY);
                if (adjIntersection.stoneChain != null) {
                    neighborsStoneChains.add(adjIntersection.stoneChain);
                }
            }
        }

        return neighborsStoneChains;
    }

    // Metoda zwracająca listę pustych sąsiadów skrzyżowania
    public Set<Intersection> getEmptyNeighbors() {
        Set<Intersection> emptyNeighbors = new HashSet<>();
        int[][] directions = {{-50, 0}, {0, -50}, {50, 0}, {0, 50}};

        for (int[] direction: directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];
            if (goBoard.isInGoBoard(newX, newY)) {
                Intersection adjIntersection = goBoard.getIntersection(newX, newY);
                if (adjIntersection.isEmpty()) {
                    emptyNeighbors.add(adjIntersection);
                }
            }
        }

        return emptyNeighbors;
    }

    // Metoda zwracająca planszę GoBoard, do której należy skrzyżowanie
    public GoBoard getBoard() {
        return goBoard;
    }
}
