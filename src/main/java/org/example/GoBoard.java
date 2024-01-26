package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GoBoard implements Serializable {
    private final ArrayList<Stone> stones = new ArrayList<>();
    private int gridSize = 50;
    private int numberOfSquares = 8;
    @JsonIgnore
    private Intersection[][] intersections;
    public Intersection getIntersectionFromArray(int x, int y) {
        return intersections[x][y];
    }
    public GoBoard(){}
    public GoBoard(int numberOfSquares){
        this.numberOfSquares = numberOfSquares;
    }

    public void addStone(int x, int y, StoneColor color) {
        stones.add(new Stone(x, y, color));
    }

    public void addStone(Stone stone) {
        stones.add(stone);
    }

    public ArrayList<Stone> getStones() {
        return stones;
    }

    public int getGridSize() {
        return gridSize;
    }

    public int getNumberOfSquares() {
        return numberOfSquares;
    }
    public void setNumberOfSquares(int numberOfSquares){this.numberOfSquares=numberOfSquares;}




    public void setIntersections() {
        intersections = new Intersection[numberOfSquares+1][numberOfSquares+1];
        for (int x = 0; x < numberOfSquares + 1; x++) {
            for (int y = 0; y < numberOfSquares + 1; y++) {
                intersections[x][y] = new Intersection(this, (x + 1) * 50, (y + 1) * 50);
            }
        }
    }

    public boolean isInGoBoard(int x, int y) {
        if (x >= 50 && x <= (numberOfSquares + 1) * gridSize && y >= 50 && y <= (numberOfSquares + 1) * gridSize) {
            return true;
        }
        return false;
    }

    public Intersection getIntersection(int x, int y) {
        if (isInGoBoard(x, y)) {
            return intersections[(x / 50) - 1][(y / 50) - 1];
        } else {
            return null;
        }
    }

    @JsonIgnore
    public Intersection[][] getIntersections() {
        return intersections;
    }

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

    public static class Stone {
        int x;
        int y;

        StoneColor color;

        public StoneColor getColor() {
            return color;
        }

        public void setColor(StoneColor color) {
            this.color = color;
        }

        public Stone() {
        }

        public Stone(int x, int y, StoneColor color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "Stone{" +
                    "x=" + x +
                    ", y=" + y +
                    ", color=" + color +
                    '}';
        }
    }

    public enum StoneColor {
        BLACK, WHITE
    }

}


















































