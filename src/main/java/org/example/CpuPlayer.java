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

    @Override
    public int getCapturedStones() {
        return 0;
    }

    @Override
    public void addCapturedStones(int nb) {

    }

    @Override
    public void removeCapturedStones(int nb) {

    }

    @Override
    public boolean passed() {
        return false;
    }

    @Override
    public void sendGameOver() {

    }

}
