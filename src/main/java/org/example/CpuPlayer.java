package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import static java.lang.Math.pow;

// Klasa reprezentująca gracza kontrolowanego przez komputer (CPU)
public class CpuPlayer extends Player {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    // Metoda określająca tryb gracza (w tym przypadku, gracz z botem)
    @Override
    public ServerResponse.Mode mode() {
        return ServerResponse.Mode.WITH_BOT;
    }

    // Metoda decydująca o kolejnym ruchu komputera
    @Override
    public GoBoard.Stone nextMove(GoBoard board){
        GoBoard.Stone stone=null;
        boolean NOempty = false;

        // Losowy wybór współrzędnych na planszy
        int size = board.getNumberOfSquares();
        int randomX = (int) (Math.random() * size);
        int randomY = (int) (Math.random() * size);

        // Tworzenie nowego kamienia (białego) na wybranych współrzędnych
        stone = new GoBoard.Stone((randomX + 1) * board.getGridSize(), (randomY + 1) * board.getGridSize(), GoBoard.StoneColor.WHITE);

        return stone;
    }

    // Metoda wysyłająca planszę do serwera
    @Override
    public void sendBoard(GoBoard board) {
        ServerResponse response = new ServerResponse();
        response.setType(ServerResponse.Type.MOVE_REQUEST);
        response.setBoard(board);
    }

    // Metoda wysyłająca informację o rozpoczęciu gry
    @Override
    public void sendGameStarted(int player, GoBoard board) {
        ServerResponse response = new ServerResponse();
        response.setPlayerNo(player);
        board.setNumberOfSquares(size);
        response.setBoard(board);
        response.setType(ServerResponse.Type.GAME_STARTED);
    }

    // Metoda zwracająca liczbę zdobytych kamieni
    public int getCapturedStones() {
        return capturedStones;
    }

    // Metoda zwracająca ilość przestrzeni na planszy
    @Override
    public int getIntersectionsInTerritories() {
        return IntersectionsInTerritories;
    }

    // Metoda dodająca zdobyte kamienie
    public void addCapturedStones(int nb) {
        capturedStones += nb;
    }

    // Metoda zwracająca terytoria
    @Override
    public Set<Set<Intersection>> getTerritories() {
        return territories;
    }

    // Metoda ustawiająca terytoria
    @Override
    public Set<Set<Intersection>> setTerritories(Set<Set<Intersection>> territories) {
        return this.territories=territories;
    }

    // Metoda dodająca terytorium
    @Override
    public void addTerritory(Set<Intersection> territory) {
        territories.add(territory);
    }

    // Metoda usuwająca zdobyte kamienie
    public void removeCapturedStones(int nb) {
        capturedStones -= nb;
    }

    // Metoda sprawdzająca, czy gracz zakończył turę
    @Override
    public boolean passed() {
        return this.IfPassed;
    }

    // Metoda ustawiająca zakończenie tury
    @Override
    public void EnforsedPass() {
        this.IfPassed=true;
    }

    // Metoda wysyłająca informację o zakończeniu gry
    @Override
    public void sendGameOver() {
        ServerResponse response = new ServerResponse();
        response.setType(ServerResponse.Type.GAME_ENDED);
    }

    // Metoda obliczająca terytoria
    public void calculateTerritory() {
        for (Set<Intersection> territory : territories) {
            IntersectionsInTerritories += territory.size();
        }
    }

    // Metoda ustawiająca rozmiar planszy
    public void setSize(int size){
        this.size=size;
    }
}
