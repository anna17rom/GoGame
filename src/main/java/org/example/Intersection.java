package org.example;


import java.util.*;
import org.example.GoBoard;
import org.example.StoneChain;


public class Intersection {

    private final GoBoard goBoard ;

    private final int x,y;

    private StoneChain stoneChain;

    public Intersection(GoBoard goBoard, int x, int y) {
        this.goBoard = goBoard;
        this.x = x;
        this.y = y;
        this.stoneChain = null;
    }

    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }


    public StoneChain getStoneChain() {
        return stoneChain;
    }


    public void setStoneChain(StoneChain stoneChain) {
        this.stoneChain = stoneChain;
    }


    public boolean isEmpty() {
        return stoneChain == null;
    }


    public Set<StoneChain> getAdjacentStoneChains() {
        Set<StoneChain> adjacentStoneChains = new HashSet<StoneChain>();

        int[] dx = {-50,0,50,0}, dy = {0,-50,0,50};


        for (int i = 0; i < dx.length ; i++) {
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


    public List<Intersection> getEmptyNeighbors() {
        List<Intersection> emptyNeighbors = new ArrayList<Intersection>();

        int[] dx = {-50,0,50,0}, dy = {0,-50,0,50};
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


    public List<Intersection> getEmptyOrDeadNeighbors(Set<StoneChain> deadChains) {
        List<Intersection> emptyNeighbors = new ArrayList<Intersection>();

        int[] dx = {-50,0,50,0}, dy = {0,-50,0,50};
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

    public GoBoard getBoard() {
        return goBoard;
    }
}
