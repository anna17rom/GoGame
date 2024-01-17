package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

public class GoBoard implements Serializable {
    private final ArrayList<Stone> stones = new ArrayList<>();
    private final int gridSize = 50;
    private final int numberOfSquares = 8;

    public void addStone(int x, int y,StoneColor color) {
        stones.add(new Stone(x, y,color));
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


















































