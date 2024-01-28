package org.example;

import org.example.GoBoard;

import java.util.HashMap;
import java.util.Map;

// Klasa reprezentująca odpowiedź serwera wysyłaną do klienta
public class ServerResponse {

    int playerNo;  // Numer gracza
    Type type;  // Typ odpowiedzi
    int size;  // Rozmiar planszy
    GoBoard board = new GoBoard(size);  // Plansza gry

    Map<Integer, Integer> playerScores = new HashMap<>();  // Mapa przechowująca wyniki graczy

    // Getter i setter dla planszy gry
    public GoBoard getBoard() {
        return board;
    }

    public void setBoard(GoBoard board) {
        this.board = board;
    }

    // Getter i setter dla numeru gracza
    public int getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }

    // Getter i setter dla wyników graczy
    public Map<Integer, Integer> getPlayerScores() {
        return playerScores;
    }

    public void setPlayerScores(Map<Integer, Integer> playerScores) {
        this.playerScores = playerScores;
    }

    // Getter i setter dla typu odpowiedzi
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    // Getter i setter dla rozmiaru planszy
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    // Enumeracja reprezentująca różne typy odpowiedzi serwera
    public enum Type {
        GAME_STARTED, MOVE_REQUEST, GAME_ENDED, SCORES
    }

    // Enumeracja reprezentująca różne tryby gry
    public enum Mode {
        MULTIPLAYER, WITH_BOT
    }

    // Przesłonięta metoda toString dla łatwego debugowania
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
