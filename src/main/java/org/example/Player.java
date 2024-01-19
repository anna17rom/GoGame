package org.example;

import org.example.GoBoard;
import org.example.ServerResponse.Mode;

public abstract class Player {

    private String name;
    protected int moveCount = 0;

    public abstract Mode mode();
    public abstract GoBoard.Stone nextMove(GoBoard board);

    public abstract void sendGameStarted(int player, GoBoard board);

    public int getMoveCount() {
        return moveCount;
    }

    public abstract void sendBoard(GoBoard board);
}
