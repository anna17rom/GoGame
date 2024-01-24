package org.example.DB;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Move {
    @Id
    private String id;
    private Integer x;
    private Integer y;
    private Integer color;

    private Integer moveNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getMoveNo() {
        return moveNo;
    }

    public void setMoveNo(Integer moveNo) {
        this.moveNo = moveNo;
    }
}