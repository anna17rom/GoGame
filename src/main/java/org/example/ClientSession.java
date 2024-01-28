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

// Klasa reprezentująca sesję klienta
public class ClientSession {

    private final Consumer<ClientRequest> requestConsumer; // Konsumer do obsługi żądań klienta
    private GoUI ui; // Interfejs użytkownika
    private CountDownLatch latch; // Zabezpieczenie wielowątkowe
    private volatile ServerResponse lastResponse; // Ostatnia odpowiedź serwera
    private int player; // Numer gracza
    private StoneColor color = StoneColor.BLACK; // Kolor kamienia
    private BlockingQueue<Stone> stonesSource; // Kolejka kamieni

    // Konstruktor klasy ClientSession
    public ClientSession(Consumer<ClientRequest> requestConsumer) {
        this.requestConsumer = requestConsumer;
        stonesSource = new LinkedBlockingQueue<>();
        this.latch = new CountDownLatch(1);
    }

    // Metoda ustawiająca interfejs użytkownika
    public void setUi(GoUI ui) {
        this.ui = ui;
    }

    // Metoda obsługująca odpowiedź od serwera
    public void handleResponse(ServerResponse response) {
        System.out.println(response.toString());
        lastResponse = response;
        if (response.getBoard() != null) {
            renderResponse(response.getBoard());
        }
        this.latch.countDown();
    }

    // Metoda inicjująca sesję klienta
    // Metoda rozpoczynająca sesję klienta
    public void start()  {

        // Inicjalizacja interfejsu użytkownika w oddzielnym wątku
        ui = new GoUI(null, stone -> stonesSource.add(stone));
        Thread uiThread = new Thread(ui);
        uiThread.start();

        // Odczyt typu gry i rozmiaru planszy od użytkownika
        Command.Type type = ui.readGameMode();
        int size = ui.readSize();
        ServerResponse response;

        // Pętla do komunikacji z serwerem przed rozpoczęciem gry
        do {
            // Tworzenie żądania klienta
            ClientRequest request = new ClientRequest(type, size);

            // Ustawianie trybu gry w zależności od wybranego typu
            if (type.equals(Command.Type.START_2P_GAME)) {
                request.setMode(ServerResponse.Mode.MULTIPLAYER);
            } else if (type.equals(Command.Type.START_CPU_GAME)) {
                request.setMode(ServerResponse.Mode.WITH_BOT);
            }

            request.setSize(size);

            // Wysyłanie żądania do serwera
            sendRequest(request);

            // Oczekiwanie na odpowiedź od serwera
            response = waitForServerResponse();

        } while (response.getType() != ServerResponse.Type.GAME_STARTED);

        // Ustawienie planszy w interfejsie użytkownika i jej renderowanie
        ui.setBoard(response.getBoard());
        ui.renderBoard(response.getBoard());

        // Informacja o rozpoczęciu gry
        System.out.println("Client: Game Started!");

        // Ustawienie numeru gracza i koloru kamienia
        this.player = response.getPlayerNo();
        this.color = this.player == 1 ? StoneColor.WHITE : StoneColor.BLACK;

        // Pętla do komunikacji w trakcie gry
        do {
            // Oczekiwanie na kamień od interfejsu użytkownika
            Stone stone = waitForStoneFromUI();

            // Sprawdzenie, czy gracz zdecydował się zakończyć turę
            if (stone.getX() == 0 && stone.getY() == 0) {
                sendRequest(new ClientRequest(Command.Type.PASS, stone));
            } else {
                // Ustawienie koloru kamienia i wysłanie żądania umieszczenia kamienia na planszy
                stone.setColor(this.color);
                sendRequest(new ClientRequest(Command.Type.PUT_STONE, stone));
            }

            // Oczekiwanie na odpowiedź od serwera
            response = waitForServerResponse();

        } while (response.getType() != ServerResponse.Type.GAME_ENDED);

        // Przerwanie wątku interfejsu użytkownika
        uiThread.interrupt();
    }


    // Metoda renderująca odpowiedź na planszy
    private void renderResponse(GoBoard board) {
        ui.renderBoard(board);
    }

    // Metoda oczekująca na kamień od interfejsu użytkownika
    private Stone waitForStoneFromUI() {
        try {
            return stonesSource.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Metoda do wysyłania żądania do serwera
    void sendRequest(ClientRequest request) {
        this.requestConsumer.accept(request);
    }

    // Metoda oczekująca na odpowiedź od serwera
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
