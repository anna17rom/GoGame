package org.example;


import org.example.GoBoard;
import org.example.GoBoard.Stone;
import org.example.GoBoard.StoneColor;
import org.example.Command;
import org.example.ServerResponse;
import org.example.ClientRequest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class ClientSession {

    private final Consumer<ClientRequest> requestConsumer;
    private GoUI ui;
    private CountDownLatch latch;
    private volatile ServerResponse lastResponse;
    private int player;
    private StoneColor color = StoneColor.BLACK;
    private BlockingQueue<Stone> stonesSource;

    public ClientSession(Consumer<ClientRequest> requestConsumer) {
        this.requestConsumer = requestConsumer;
        stonesSource = new LinkedBlockingQueue<>();
    }
    public void setUi(GoUI ui) {
        this.ui = ui;
    }

    public void handleResponse(ServerResponse response) {
        System.out.println(response.toString());
        lastResponse = response;
        if (response.getBoard() != null) {
            renderResponse(response.getBoard());
        }
        this.latch.countDown();
    }


    public void start()  {

        ui = new GoUI(null, stone -> stonesSource.add(stone));
        Thread uiThread = new Thread(ui);
        uiThread.start();

        Command.Type type = ui.readGameMode();
        int size = ui.readSize();
        ServerResponse response;
        do {
            ClientRequest request = new ClientRequest(type,size);
            if (type.equals(Command.Type.START_2P_GAME)) {
                request.setMode(ServerResponse.Mode.MULTIPLAYER);
            } else if (type.equals(Command.Type.START_CPU_GAME)) {
                request.setMode(ServerResponse.Mode.WITH_BOT);
            }
            request.setSize(size);
            sendRequest(request);
            response = waitForServerResponse();
        } while (response.getType() != ServerResponse.Type.GAME_STARTED);
        ui.setBoard(response.getBoard());
        ui.renderBoard(response.getBoard());


        System.out.println("Client: Game Started!");
        this.player = response.getPlayerNo();
        this.color = this.player == 1 ? StoneColor.WHITE : StoneColor.BLACK;
        do {
            Stone stone = waitForStoneFromUI();
            if (stone.getX()==0 && stone.getY()==0){
                sendRequest(new ClientRequest(Command.Type.PASS, stone));
            }else{
                stone.setColor(this.color);
                sendRequest(new ClientRequest(Command.Type.PUT_STONE, stone));}
            response = waitForServerResponse();
        } while (response.getType() != ServerResponse.Type.GAME_ENDED);
        uiThread.interrupt();
    }

    private void renderResponse(GoBoard board) {
        ui.renderBoard(board);
    }

    private Stone waitForStoneFromUI() {
        try {
            return stonesSource.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    void sendRequest(ClientRequest request) {
        this.requestConsumer.accept(request);
    }

    private ServerResponse waitForServerResponse() {
        try {
            latch = new CountDownLatch(1);
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return lastResponse;
    }


}

































