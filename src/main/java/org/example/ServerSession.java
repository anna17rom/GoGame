package org.example;

import org.example.GoBoard;
import org.example.GoBoard.Stone;
import org.example.DB.*;
import org.example.StoneChain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static java.lang.Math.pow;

public class ServerSession {
    private final Player p1;
    private final Player p2;
    private final GoDb db;
    Set<Set<Intersection>> territories = new HashSet<>();
    Set<Intersection> visited = new HashSet<>();
    int moveNum;

    public ServerSession(Player p1, Player p2, GoDb db) {
        this.p1 = p1;
        this.p2 = p2;
        this.db = db;
        moveNum=0;
    }

    public void start() {
        String id = UUID.randomUUID().toString();
        GoBoard board = new GoBoard();

        p1.setName(UUID.randomUUID().toString());
        p2.setName(UUID.randomUUID().toString());
        p1.sendGameStarted(0, board);
        p2.sendGameStarted(1, board);
        board.setIntersections();
        System.out.println("Started new game session");
        db.savePlayers(id,board.getNumberOfSquares(), Arrays.asList(p1.getName(), p2.getName()));
        Player currentPlayer = p1;
        int invalideMoves;
        do {
            invalideMoves = 0;
            boolean validMove = false;
            while (!validMove && !isEndOfGame()) {
                if (invalideMoves>=pow(board.getNumberOfSquares()+1,2)){
                    currentPlayer.EnforsedPass();
                    System.out.println("Player has passed, and want to and the game");
                }
                Stone stone = currentPlayer.nextMove(board);
                if (currentPlayer.passed()) {
                    sendBoard(board);
                    currentPlayer = (currentPlayer == p1) ? p2 : p1;
                } else {
                    Intersection intersection = null;
                    for (Intersection[] intersection1 : board.getIntersections()) {
                        for (Intersection intersection2 : intersection1) {
                            if (intersection2.getX() == stone.getX() && intersection2.getY() == stone.getY()) {
                                intersection = intersection2;
                            }
                        }

                    }
                    if (isValid(intersection, currentPlayer, board)) {
                        validMove = true;
                        System.out.println("P1 move #" + currentPlayer.getMoveCount() + ": " + stone.toString());
                        board.addStone(stone);
                        db.saveMove(id, currentPlayer.getName(), stone, moveNum++);
                        sendBoard(board);
                    } else {
                        System.out.println("Invalid move . Try again.");
                        invalideMoves++;
                    }
                    sendBoard(board);
                }

            }

            currentPlayer = (currentPlayer == p1) ? p2 : p1;

        } while (!isEndOfGame());
        calculateTerritoriesForPlayers(board);
        p1.calculateTerritory();
        p2.calculateTerritory();
        int total1 = p1.getIntersectionsInTerritories()+ p2.getCapturedStones();
        int total2 = p2.getIntersectionsInTerritories()+ p1.getCapturedStones();
        System.out.println("Scores for P1: Scores for captured territories: "+p1.getIntersectionsInTerritories() + " for captured stones:" + p2.getCapturedStones()+" Total: "+total1);
        System.out.println("Scores for P2: Scores for captured territories: "+p2.getIntersectionsInTerritories() + " for captured stones:" + p1.getCapturedStones()+" Total: "+total2);
        p1.sendGameOver();
        p2.sendGameOver();
    }

    private boolean isValid(Intersection intersection, Player player,GoBoard board) {
        if (!isValidPosition(intersection, board) || isOccupied(intersection)) {
            return false;
        }

        Set<StoneChain> neighborsStoneChains = intersection.getNeighborsStoneChains();
        StoneChain newStoneChain = new StoneChain(intersection, player);
        intersection.setStoneChain(newStoneChain);
        for (StoneChain stoneChain : neighborsStoneChains) {
            if (stoneChain.getPlayer() == player) {
                newStoneChain.addToStoneChain(stoneChain, intersection);
            } else {
                removeLibertyAndCaptureIfNecessary(stoneChain, intersection, board);
            }
        }
        // Preventing suicide or ko and re-adding liberty
        if (newStoneChain.getLiberties().size() == 0) {
            intersection.getNeighborsStoneChains().forEach(chain -> chain.getLiberties().add(intersection));
            intersection.setStoneChain(null);
            return false;
        }
        newStoneChain.getStones().forEach(stone -> stone.setStoneChain(newStoneChain));
        return true;
    }
    private boolean isValidPosition(Intersection intersection, GoBoard board) {
        return board.isInGoBoard(intersection.getX(), intersection.getY());
    }

    private boolean isOccupied(Intersection intersection) {
        return intersection.getStoneChain() != null;
    }
    private void removeLibertyAndCaptureIfNecessary(StoneChain stoneChain, Intersection intersection, GoBoard board) {
        stoneChain.removeLiberty(intersection);
        if (stoneChain.getLiberties().isEmpty()) {
            captureChain(stoneChain, board);
        }
    }

    private void captureChain(StoneChain stoneChain, GoBoard board) {
        stoneChain.dieAndUpdateScore();
        stoneChain.getStones().forEach(stone -> board.removeStone(stone.getX(), stone.getY()));
    }

    private boolean isEndOfGame() {
        return p1.passed() && p2.passed();
    }
    private void sendBoard(GoBoard board) {
        p1.sendBoard(board);
        p2.sendBoard(board);
    }
    private void calculateTerritoriesForPlayers(GoBoard board) {


        for (int i = 0; i <= board.getNumberOfSquares(); ++i) {
            for (int j = 0; j <= board.getNumberOfSquares(); ++j) {
                Intersection intersection = board.getIntersectionFromArray(i, j);
                if (intersection.isEmpty() && intersection.getStoneChain() == null && !visited.contains(intersection)) {
                    Set<Intersection> territory = calculateTerritory(intersection, visited, new HashSet<>());
                    territories.add(territory);
                    if (isTerritoryOwnedByPlayer(territory, p1)) {
                        p1.addTerritory(territory);
                    } else if (isTerritoryOwnedByPlayer(territory, p2)) {
                        p2.addTerritory(territory);
                    }

                }
            }

        }
    }

    private Set<Intersection> calculateTerritory(Intersection current, Set<Intersection> visited, Set<Intersection> territory) {
        visited.add(current);
        territory.add(current);

        for (Intersection neighbor : current.getEmptyNeighbors()) {
            if (!visited.contains(neighbor) && !territory.contains(neighbor)) {
                territory.addAll(calculateTerritory(neighbor, visited, territory));
            }
        }

        return territory;
    }

    // Check if the territory is owned by the specified player
    private boolean isTerritoryOwnedByPlayer(Set<Intersection> territory, Player player) {
        Set<Player> owners = new HashSet<>();

        for (Intersection intersection : territory) {
            Set<StoneChain> adjStoneChains = intersection.getNeighborsStoneChains();
            for (StoneChain stoneChain : adjStoneChains) {
                owners.add(stoneChain.getPlayer());
                //if(owners.size()==2){break;}
            }
        }

        return owners.size() == 1 && owners.contains(player);
    }
}