package org.example.DB;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import java.util.List;

@Entity
public class Session {
    @Id
    private String id;

    private int size;

    @OneToMany(cascade= CascadeType.ALL)
    private List<Player> players;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getSessionSize(){return size;}

    public void setSessionSize(int size){this.size=size;}
}