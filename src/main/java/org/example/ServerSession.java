package org.example;


import org.example.GoBoard;
import org.example.GoBoard.Stone;
import org.example.StoneChain;

import java.util.HashSet;
import java.util.Set;

public class ServerSession {

    private final Player p1;
    private final Player p2;


    public ServerSession(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }



    public void start() {
        GoBoard board = new GoBoard();

        p1.sendGameStarted(0, board);
        p2.sendGameStarted(1, board);
        board.setIntersections();
        System.out.println("Started new game session");
        Player currentPlayer = p1;
        do {
            boolean validMove = false;
            while (!validMove){
                Stone stone = currentPlayer.nextMove(board);
                /*stone.setBoard(board);*/
                Intersection intersection = new Intersection(board,stone.getX(),stone.getY());
                if (isValide(intersection,currentPlayer,stone)) {
                    validMove=true;
                    System.out.println("P1 move #" + currentPlayer.getMoveCount() + ": " + stone.toString());
                    board.addStone(stone);
                } else {
                    System.out.println("Invalid move . Try again.");
                }
                sendBoard(board);
            }

            currentPlayer = (currentPlayer == p1) ? p2 : p1;

        } while (true);
    }

    private boolean isValide(Intersection intersection, Player player,Stone Mystone) {

        if (!intersection.getBoard().isInGoBoard(intersection.getX(),intersection.getY())) return false;

        // Preventing playing over another stone
        if (intersection.getStoneChain() != null) return false;

        Set<Intersection> capturedStones = null;
        Set<StoneChain> capturedStoneChains = null;

        Set<StoneChain> adjStoneChains = intersection.getAdjacentStoneChains();
        StoneChain newStoneChain = new StoneChain(intersection, player);
        intersection.setStoneChain(newStoneChain);
        for (StoneChain stoneChain : adjStoneChains) {
            if (stoneChain.getPlayer() == player) {
                newStoneChain.add(stoneChain, intersection);
            } else {
                stoneChain.removeLiberty(intersection);
                if (stoneChain.getLiberties().size() == 0) {
                    stoneChain.die();
                    for (Intersection stonesInChain: stoneChain.getStones()){
                        intersection.getBoard().removeStone(intersection.getX(), intersection.getY());
                    }
                }
            }
        }

        // Preventing suicide or ko and re-adding liberty
        if (newStoneChain.getLiberties().size() == 0 ) {
            for (StoneChain chain : intersection.getAdjacentStoneChains()) {
                chain.getLiberties().add(intersection);
            }
            intersection.setStoneChain(null);
            return false;
        }

        for (Intersection stone : newStoneChain.getStones()) {
            stone.setStoneChain(newStoneChain);
        }

        return true;
    }

    private void sendBoard(GoBoard board) {
        p1.sendBoard(board);
        p2.sendBoard(board);
    }
}


    /*private boolean isValide(Stone stone, GoBoard board) {
        if (isIntersectionOccupied(board, stone.getX(), stone.getY())) {
            return false;
        }
        return true;
    }
    private boolean isIntersectionOccupied(GoBoard board, int x, int y) {
        for (Stone existingStone : board.getStones()) {
            if (existingStone.getX() == x && existingStone.getY() == y) {
                return true;
            }
        }
        return false;
    }*/