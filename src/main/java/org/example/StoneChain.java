package org.example;

import java.util.HashSet;
import java.util.Set;
import org.example.GoBoard.Stone;
import org.example.Player;
import org.example.Intersection;

public class StoneChain {
    // Zbiór kamieni tworzących łańcuch kamieni na planszy Go.
    private final Set<Intersection> stones;

    // Zbiór pustych sąsiednich przecięć, określających wolności łańcucha kamieni.
    private final Set<Intersection> liberties;

    // Gracz, do którego należy łańcuch kamieni.
    private final Player player;

    // Konstruktor tworzący łańcuch kamieni na podstawie zestawu kamieni, wolności i gracza.
    public StoneChain(Set<Intersection> stones, Set<Intersection> liberties, Player player) {
        this.stones = stones;
        this.liberties = liberties;
        this.player = player;
    }

    // Konstruktor tworzący łańcuch kamieni z pojedynczym przecięciem i graczem.
    public StoneChain(Intersection stone, Player player) {
        stones = new HashSet<Intersection>();
        stones.add(stone);
        this.player = player;
        liberties = new HashSet<Intersection>(stone.getEmptyNeighbors());
    }

    // Konstruktor kopiujący łańcuch kamieni.
    public StoneChain(StoneChain stoneChain) {
        this.stones = new HashSet<Intersection>(stoneChain.stones);
        this.liberties = new HashSet<Intersection>(stoneChain.liberties);
        this.player = stoneChain.player;
    }

    // Metoda zwracająca gracza przypisanego do łańcucha kamieni.
    public Player getPlayer() {
        return player;
    }

    // Metoda zwracająca zbiór wolności łańcucha kamieni.
    public Set<Intersection> getLiberties() {
        return liberties;
    }

    // Metoda zwracająca zbiór kamieni w łańcuchu.
    public Set<Intersection> getStones() {
        return stones;
    }

    // Metoda dodająca kamienie i wolności z innego łańcucha kamieni do bieżącego łańcucha.
    public void add(StoneChain stoneChain, Intersection playedStone) {
        this.stones.addAll(stoneChain.stones);
        this.liberties.addAll(stoneChain.liberties);
        // Usunięcie zagranej kamieni z wolności
        this.liberties.remove(playedStone);
    }

    // Metoda tworząca nowy łańcuch kamieni po usunięciu jednej z wolności.
    public StoneChain removeLiberty(Intersection playedStone) {
        StoneChain newStoneChain = new StoneChain(stones, liberties, player);
        newStoneChain.liberties.remove(playedStone);
        return newStoneChain;
    }

    // Metoda usuwająca łańcuch kamieni z planszy.
    public void die() {
        for (Intersection rollingStone : this.stones) {
            rollingStone.setStoneChain(null);
            Set<StoneChain> adjacentStoneChains = rollingStone.getAdjacentStoneChains();
            for (StoneChain stoneChain : adjacentStoneChains) {
                stoneChain.liberties.add(rollingStone);
            }
        }
        this.player.addCapturedStones(this.stones.size());
    }
}
