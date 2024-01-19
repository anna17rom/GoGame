package org.example;

import org.example.GoBoard;
import org.example.GoBoard.Stone;

public class ServerSession {

    private Player p1;
    private Player p2;

    public ServerSession(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public void start() {
        GoBoard board = new GoBoard();

        p1.sendGameStarted(0, board);
        p2.sendGameStarted(1, board);
        System.out.println("Started new game session");
        do {
            Stone stone = p1.nextMove(board);
            System.out.println("P1 move #" + p1.getMoveCount() + ": " + stone.toString());
            board.addStone(stone);
            stone = p2.nextMove(board);
            System.out.println("P2 move #" + p2.getMoveCount() + ": " + stone.toString());
            board.addStone(stone);
        } while (true);
    }

}

