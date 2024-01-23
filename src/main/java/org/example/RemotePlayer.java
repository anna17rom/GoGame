package org.example;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.GoBoard;
import org.example.Command;
import org.example.ServerResponse;
import org.example.GoServerV2.ClientIO;

import java.io.IOException;

public class RemotePlayer extends Player {
    private final static ObjectMapper MAPPER = new ObjectMapper();

    private final ClientIO io;

    public RemotePlayer(ClientIO io) {
        this.io = io;
    }

    @Override
    public ServerResponse.Mode mode() {
        ClientRequest request;
        do {
            request = waitForRequest();
        } while (request.getType() != Command.Type.START_2P_GAME
                && request.getType() != Command.Type.START_CPU_GAME);
        return request.getMode();
    }

    @Override
    public GoBoard.Stone nextMove(GoBoard board) {
        ClientRequest request;
        do {
            request = waitForRequest();
        } while (request == null || request.getType() != Command.Type.PUT_STONE);
        moveCount++;
        return request.getStone();
    }

    @Override
    public void sendGameStarted(int player, GoBoard board) {
        ServerResponse response = new ServerResponse();
        response.setPlayerNo(player);
        response.setBoard(board);
        response.setType(ServerResponse.Type.GAME_STARTED);
        try {
            io.writer.writeUTF(MAPPER.writeValueAsString(response));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
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
    public int getCapturedStones() {
        return capturedStones;
    }

    public void addCapturedStones(int nb) {
        capturedStones += nb;
    }


    public void removeCapturedStones(int nb) { capturedStones -= nb; }

}
