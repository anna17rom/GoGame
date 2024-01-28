package org.example;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.GoBoard;
import org.example.Command;
import org.example.ServerResponse;
import org.example.GoServerV2.ClientIO;

import java.io.IOException;
import java.util.Set;

// Klasa reprezentująca zdalnego gracza
public class RemotePlayer extends Player {
    private final static ObjectMapper MAPPER = new ObjectMapper();
    private final ClientIO io;

    // Konstruktor przyjmujący obiekt ClientIO, który reprezentuje połączenie z klientem
    public RemotePlayer(ClientIO io) {
        this.io = io;
    }

    // Metoda implementująca tryb gry dla zdalnego gracza
    @Override
    public ServerResponse.Mode mode() {
        ClientRequest request;
        do {
            request = waitForRequest();
            size = request.getSize();
        } while (request.getType() != Command.Type.START_2P_GAME
                && request.getType() != Command.Type.START_CPU_GAME);
        return request.getMode();
    }

    // Metoda implementująca następny ruch dla zdalnego gracza
    @Override
    public GoBoard.Stone nextMove(GoBoard board) {
        ClientRequest request;
        do {
            request = waitForRequest();
            this.IfPassed = false;
            if (request.getType() == Command.Type.PASS) {
                this.IfPassed = true;
            }
        } while (request == null || (request.getType() != Command.Type.PUT_STONE && request.getType() != Command.Type.PASS));
        moveCount++;
        return request.getStone();
    }

    // Metoda wysyłająca informację o rozpoczęciu gry do zdalnego gracza
    @Override
    public void sendGameStarted(int player, GoBoard board) {
        ServerResponse response = new ServerResponse();
        response.setPlayerNo(player);
        board.setNumberOfSquares(size);
        response.setBoard(board);
        response.setType(ServerResponse.Type.GAME_STARTED);
        try {
            io.writer.writeUTF(MAPPER.writeValueAsString(response));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Metoda wysyłająca planszę do zdalnego gracza
    @Override
    public void sendBoard(GoBoard board) {
        ServerResponse response = new ServerResponse();
        response.setType(ServerResponse.Type.MOVE_REQUEST);
        response.setBoard(board);
        try {
            io.writer.writeUTF(MAPPER.writeValueAsString(response));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Metoda oczekująca na żądanie od zdalnego gracza
    private ClientRequest waitForRequest() {
        ClientRequest request;
        try {
            String raw = io.reader.readUTF();
            request = MAPPER.readValue(raw, ClientRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return request;
    }

    // Implementacje pozostałych metod abstrakcyjnych z klasy Player
    public int getCapturedStones() {
        return capturedStones;
    }

    public void addCapturedStones(int nb) {
        capturedStones += nb;
    }

    @Override
    public int getIntersectionsInTerritories() {
        return IntersectionsInTerritories;
    }



    @Override
    public Set<Set<Intersection>> getTerritories() {
        return territories;
    }

    @Override
    public Set<Set<Intersection>> setTerritories(Set<Set<Intersection>> territories) {
        return this.territories=territories;
    }



    @Override
    public void addTerritory(Set<Intersection> territory) {
        territories.add(territory);
    }

    @Override
    public void calculateTerritory() {
        for (Set<Intersection> territory : territories) {
            IntersectionsInTerritories += territory.size();
        }
    }
    @Override
    public void EnforsedPass() {
        this.IfPassed=true;
    }
    // Metoda usuwająca zdobyte kamienie
    public void removeCapturedStones(int nb) {
        capturedStones -= nb;
    }

    // Metoda sprawdzająca, czy gracz zatwierdził pas
    @Override
    public boolean passed() {
        return this.IfPassed;
    }

    // Metoda wysyłająca informację o zakończeniu gry do zdalnego gracza
    @Override
    public void sendGameOver() {
        ServerResponse response = new ServerResponse();
        response.setType(ServerResponse.Type.GAME_ENDED);
    }

}
