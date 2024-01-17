package org.example;

import org.example.GoBoard;

import java.util.HashMap;
import java.util.Map;

public class ServerResponse {

    int playerNo;
    Type type;
    GoBoard board;

    Map<Integer, Integer> playerScores = new HashMap<>();

    public GoBoard getBoard() {
        return board;
    }

    public void setBoard(GoBoard board) {
        this.board = board;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }

    public Map<Integer, Integer> getPlayerScores() {
        return playerScores;
    }

    public void setPlayerScores(Map<Integer, Integer> playerScores) {
        this.playerScores = playerScores;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        GAME_STARTED, MOVE_REQUEST, GAME_ENDED, SCORES
    }

    public enum Mode {
        MULTIPLAYER, WITH_BOT
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "playerNo=" + playerNo +
                ", type=" + type +
                ", board=" + board +
                ", playerScores=" + playerScores +
                '}';
    }
}
