package org.example;


import org.example.GoBoard;
import org.example.ServerResponse;

public class CpuPlayer extends Player {

    @Override
    public ServerResponse.Mode mode() {
        return ServerResponse.Mode.WITH_BOT;
    }

    @Override
    public GoBoard.Stone nextMove(GoBoard board) {
        return null;
    }

    @Override
    public void sendGameStarted(int player, GoBoard board) {

    }

    @Override
    public void sendBoard(GoBoard board) {

    }

}
