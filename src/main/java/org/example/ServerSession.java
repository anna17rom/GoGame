package org.example;

import org.example.GoBoard;
import org.example.GoBoard.Stone;

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
        System.out.println("Started new game session");
        Player currentPlayer = p1;
        do {
            boolean validMove = false;
            while (!validMove){
                Stone stone = currentPlayer.nextMove(board);
                if (isValide(stone, board)) {
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

    private boolean isValide(Stone stone, GoBoard board) {
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
    }

    private void sendBoard(GoBoard board) {
        p1.sendBoard(board);
        p2.sendBoard(board);
    }

}

