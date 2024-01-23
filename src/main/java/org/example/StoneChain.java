package org.example;


import java.util.HashSet;
import java.util.Set;
import org.example.GoBoard.Stone;
import org.example.Player;
import org.example.Intersection;


public class StoneChain {
    private final Set<Intersection> stones;
    private final Set<Intersection> liberties;
    private final Player player;

    public StoneChain(Set<Intersection> stones, Set<Intersection> liberties, Player player) {
        this.stones = stones;
        this.liberties = liberties;
        this.player = player;
    }

    public StoneChain(Intersection stone, Player player) {
        stones = new HashSet<Intersection>();
        stones.add(stone);
        this.player = player;
        liberties = new HashSet<Intersection>(stone.getEmptyNeighbors());
    }

    public StoneChain(StoneChain stoneChain) {
        this.stones = new HashSet<Intersection>(stoneChain.stones);
        this.liberties = new HashSet<Intersection>(stoneChain.liberties);
        this.player = stoneChain.player;
    }

    public Player getPlayer() {
        return player;
    }
    public Set<Intersection> getLiberties() {
        return liberties;
    }
    public Set<Intersection> getStones() {
        return stones;
    }

    public void add(StoneChain stoneChain, Intersection playedStone) {
        this.stones.addAll(stoneChain.stones);
        this.liberties.addAll(stoneChain.liberties);
        // remove played stone from liberties
        this.liberties.remove(playedStone);
    }

    public StoneChain removeLiberty(Intersection playedStone) {
        StoneChain newStoneChain = new StoneChain(stones, liberties, player);
        newStoneChain.liberties.remove(playedStone);
        return newStoneChain;
    }

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
