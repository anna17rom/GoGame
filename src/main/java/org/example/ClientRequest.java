package org.example;

import org.example.GoBoard.Stone;
import org.example.Command;
import org.example.ServerResponse;
import org.example.ServerResponse.Mode;

// Klasa reprezentująca żądanie klienta
public class ClientRequest {

    private Command.Type type; // Typ żądania
    private Mode mode; // Tryb
    private int size; // Rozmiar
    private Stone stone; // Kamień na planszy
    private Player player; // Gracz

    // Konstruktor domyślny
    public ClientRequest() {
    }

    // Konstruktor przyjmujący typ i tryb
    public ClientRequest(Command.Type type, Mode mode) {
        this.type = type;
        this.mode = mode;
    }

    // Konstruktor przyjmujący typ i kamień
    public ClientRequest(Command.Type type, Stone stone) {
        this.type = type;
        this.stone = stone;
    }

    // Konstruktor przyjmujący typ i rozmiar
    public ClientRequest(Command.Type type, int size) {
        this.type = type;
        this.size = size;
    }

    // Konstruktor przyjmujący tylko typ
    public ClientRequest(Command.Type type) {
        this.type=type;
    }

    // Metoda zwracająca rozmiar
    public int getSize() {
        return size;
    }

    // Metoda ustawiająca rozmiar
    public void setSize(int size) {
        this.size = size;
    }

    // Metoda zwracająca kamień
    public Stone getStone() {
        return stone;
    }

    // Metoda ustawiająca kamień
    public void setStone(Stone stone) {
        this.stone = stone;
    }

    // Metoda zwracająca tryb
    public Mode getMode() {
        return mode;
    }

    // Metoda ustawiająca tryb
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    // Metoda zwracająca gracza
    public Player getPlayer() {
        return player;
    }

    // Metoda ustawiająca gracza
    public void setPlayer(Player player) {
        this.player = player;
    }

    // Metoda zwracająca typ
    public Command.Type getType() {
        return type;
    }

    // Metoda ustawiająca typ
    public void setType(Command.Type type) {
        this.type = type;
    }

}
