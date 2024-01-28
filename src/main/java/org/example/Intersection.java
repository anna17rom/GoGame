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
    public Set<StoneChain> getAdjacentStoneChains() {
        Set<StoneChain> adjacentStoneChains = new HashSet<>();

        int[] dx = {-50, 0, 50, 0}, dy = {0, -50, 0, 50};

        for (int i = 0; i < dx.length; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (goBoard.isInGoBoard(newX, newY)) {
                Intersection adjIntersection = goBoard.getIntersection(newX, newY);
                if (adjIntersection.stoneChain != null) {
                    adjacentStoneChains.add(adjIntersection.stoneChain);
                }
            }
        }

        return adjacentStoneChains;
    }

    // Metoda zwracająca listę pustych sąsiadów skrzyżowania
    public List<Intersection> getEmptyNeighbors() {
        List<Intersection> emptyNeighbors = new ArrayList<>();

        int[] dx = {-50, 0, 50, 0}, dy = {0, -50, 0, 50};
        assert dx.length == dy.length : "dx and dy should have the same length";

        for (int i = 0; i < dx.length; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (goBoard.isInGoBoard(newX, newY)) {
                Intersection adjIntersection = goBoard.getIntersection(newX, newY);
                if (adjIntersection.isEmpty()) {
                    emptyNeighbors.add(adjIntersection);
                }
            }
        }

        return emptyNeighbors;
    }

    // Metoda zwracająca listę pustych lub martwych sąsiadów skrzyżowania
    public List<Intersection> getEmptyOrDeadNeighbors(Set<StoneChain> deadChains) {
        List<Intersection> emptyNeighbors = new ArrayList<>();

        int[] dx = {-50, 0, 50, 0}, dy = {0, -50, 0, 50};
        assert dx.length == dy.length : "dx and dy should have the same length";

        for (int i = 0; i < dx.length; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (goBoard.isInGoBoard(newX, newY)) {
                Intersection adjIntersection = goBoard.getIntersection(newX, newY);
                if (adjIntersection.isEmpty() || deadChains.contains(adjIntersection.getStoneChain())) {
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
