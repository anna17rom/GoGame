package org.example;


import org.example.GoBoard;
import org.example.GoBoard.Stone;

import java.io.Serializable;

public class Command implements Serializable {

    Type type;
    Stone stone;

    public Command() {
    }

    public Command(Type type, Stone stone) {
        this.type = type;
        this.stone = stone;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Stone getStone() {
        return stone;
    }

    public void setStone(Stone stone) {
        this.stone = stone;
    }

    public enum Type {
        START_2P_GAME, START_CPU_GAME, END_GAME, PUT_STONE
    }
}

