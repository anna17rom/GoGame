package org.example;

import org.example.ServerResponse.Mode;

import java.util.HashSet;
import java.util.Set;

public abstract class Player {
    protected int IntersectionsInTerritories;
    Boolean IfPassed=false;
    protected int capturedStones;
    protected Set<Set<Intersection>> territories=new HashSet<>();
    int size;
    private String name;
    protected int moveCount = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract Mode mode();

    public abstract GoBoard.Stone nextMove(GoBoard board);

    public abstract void sendGameStarted(int player, GoBoard board);

    public int getMoveCount() {
        return moveCount;
    }

    public abstract void sendBoard(GoBoard board);

    public abstract int getCapturedStones();

    public abstract void addCapturedStones(int nb);


    public abstract int getIntersectionsInTerritories();

    public abstract Set<Set<Intersection>> getTerritories();

    public abstract Set<Set<Intersection>> setTerritories(Set<Set<Intersection>> territories);


    public abstract void addTerritory(Set<Intersection> territories);

    public abstract void calculateTerritory();

    public abstract void removeCapturedStones(int nb);

    public abstract boolean passed();

    public abstract void sendGameOver();
    public abstract void EnforsedPass();
    protected int getSize(){return this.size;}
}
