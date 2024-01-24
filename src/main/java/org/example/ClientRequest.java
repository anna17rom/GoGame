package org.example;

import org.example.GoBoard.Stone;
import org.example.Command;
import org.example.ServerResponse;
import org.example.ServerResponse.Mode;



public class ClientRequest {
    private Command.Type type;
    private Mode mode;
    private int size;
    private Stone stone;
    private Player player;

    public ClientRequest() {
    }

    public ClientRequest(Command.Type type, Mode mode) {
        this.type = type;
        this.mode = mode;
    }

    public ClientRequest(Command.Type type, Stone stone) {
        this.type = type;
        this.stone = stone;
    }

    public ClientRequest(Command.Type type, int size) {
        this.type = type;
        this.size = size;
    }

    public ClientRequest(Command.Type type) {
        this.type=type;
    }


    public int getSize() {
        return size;
    }

    public void getSize(int size) {
        this.size = size;
    }

    public Stone getStone() {
        return stone;
    }

    public void setStone(Stone stone) {
        this.stone = stone;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Command.Type getType() {
        return type;
    }

    public void setType(Command.Type type) {
        this.type = type;
    }
    public void setSize(int size){this.size=size;}

}
