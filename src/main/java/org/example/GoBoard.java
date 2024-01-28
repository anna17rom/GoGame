package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

// Klasa reprezentująca planszę do gry Go
public class GoBoard implements Serializable {
    private final ArrayList<Stone> stones = new ArrayList<>();
    private int gridSize = 50;
    private int numberOfSquares = 8;
    @JsonIgnore
    private Intersection[][] intersections;

    // Metoda pobierająca intersection z tablicy na podstawie współrzędnych
    public Intersection getIntersectionFromArray(int x, int y) {
        return intersections[x][y];
    }

    // Konstruktor domyślny
    public GoBoard() {}

    // Konstruktor z określoną liczbą pól
    public GoBoard(int numberOfSquares) {
        this.numberOfSquares = numberOfSquares;
    }

    // Metoda dodająca kamień na planszy
    public void addStone(int x, int y, StoneColor color) {
        stones.add(new Stone(x, y, color));
    }

    // Metoda dodająca kamień na planszy (przy użyciu obiektu klasy Stone)
    public void addStone(Stone stone) {
        stones.add(stone);
    }

    // Metoda zwracająca listę kamieni na planszy
    public ArrayList<Stone> getStones() {
        return stones;
    }

    // Metoda zwracająca rozmiar kratki na planszy
    public int getGridSize() {
        return gridSize;
    }

    // Metoda zwracająca liczbę pól na planszy
    public int getNumberOfSquares() {
        return numberOfSquares;
    }

    // Metoda ustawiająca liczbę pól na planszy
    public void setNumberOfSquares(int numberOfSquares) {
        this.numberOfSquares = numberOfSquares;
    }

    // Metoda ustawiająca intersekcje na planszy
    public void setIntersections() {
        intersections = new Intersection[numberOfSquares + 1][numberOfSquares + 1];
        for (int x = 0; x < numberOfSquares + 1; x++) {
            for (int y = 0; y < numberOfSquares + 1; y++) {
                intersections[x][y] = new Intersection(this, (x + 1) * 50, (y + 1) * 50);
            }
        }
    }

    // Metoda sprawdzająca, czy współrzędne są w obrębie planszy
    public boolean isInGoBoard(int x, int y) {
        if (x >= 50 && x <= (numberOfSquares + 1) * gridSize && y >= 50 && y <= (numberOfSquares + 1) * gridSize) {
            return true;
        }
        return false;
    }

    // Metoda zwracająca intersekcję na planszy na podstawie współrzędnych
    public Intersection getIntersection(int x, int y) {
        if (isInGoBoard(x, y)) {
            return intersections[(x / 50) - 1][(y / 50) - 1];
        } else {
            return null;
        }
    }

    @JsonIgnore
    // Metoda zwracająca dwuwymiarową tablicę intersekcji
    public Intersection[][] getIntersections() {
        return intersections;
    }

    // Metoda usuwająca kamień z planszy na podstawie współrzędnych
    public void removeStone(int x, int y) {
        Stone stoneToRemove = null;
        for (Stone stone : stones) {
            if (stone.getX() == x && stone.getY() == y) {
                stoneToRemove = stone;
            }
        }
        if (stoneToRemove != null) {
            stones.remove(stoneToRemove);
        }
    }

    // Klasa reprezentująca kamień na planszy
    public static class Stone {
        int x;
        int y;
        StoneColor color;

        // Metoda zwracająca kolor kamienia
        public StoneColor getColor() {
            return color;
        }

        // Metoda ustawiająca kolor kamienia
        public void setColor(StoneColor color) {
            this.color = color;
        }

        // Konstruktor domyślny
        public Stone() {}

        // Konstruktor z określonymi współrzędnymi i kolorem
        public Stone(int x, int y, StoneColor color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }

        // Metoda zwracająca współrzędną x
        public int getX() {
            return x;
        }

        // Metoda ustawiająca współrzędną x
        public void setX(int x) {
            this.x = x;
        }

        // Metoda zwracająca współrzędną y
        public int getY() {
            return y;
        }

        // Metoda ustawiająca współrzędną y
        public void setY(int y) {
            this.y = y;
        }

        // Metoda zwracająca reprezentację tekstową kamienia
        @Override
        public String toString() {
            return "Stone{" +
                    "x=" + x +
                    ", y=" + y +
                    ", color=" + color +
                    '}';
        }
    }

    // Wyliczenie reprezentujące kolory kamieni
    public enum StoneColor {
        BLACK, WHITE
    }

}
