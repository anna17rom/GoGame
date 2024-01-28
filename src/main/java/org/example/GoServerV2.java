package org.example;
import org.example.DB.GoDb;
import org.example.DB.JpaUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ServerResponse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class GoServerV2 {

    private static final int PORT = 8000;

    // Inicjalizacja obiektu ObjectMapper z biblioteki Jackson
    private final static ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String... args) throws IOException {

        // Pętla nieskończona dla obsługi wielu sesji graczy
        do {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Oczekiwanie na połączenie 1. gracza.");

                // Akceptacja połączenia od 1. gracza
                Socket p1Socket = serverSocket.accept();
                ClientIO p1IO = ClientIO.fromSocket(p1Socket);
                Player p1 = new RemotePlayer(p1IO);

                // Oczekiwanie na wybór trybu gry przez 1. gracza
                ServerResponse.Mode mode = p1.mode();
                System.out.println("1. gracz połączony");

                Player p2;
                if (mode == ServerResponse.Mode.MULTIPLAYER) {
                    // Utworzenie obiektu RemotePlayer dla 2. gracza w trybie wieloosobowym
                    p2 = new RemotePlayer(ClientIO.fromSocket(serverSocket.accept()));
                } else {
                    // Utworzenie obiektu CpuPlayer dla 2. gracza w trybie jednoosobowym
                    p2 = new CpuPlayer();
                    ((CpuPlayer) p2).setSize(p1.getSize());
                }

                // Oczekiwanie na wybór trybu gry przez 2. gracza
                p2.mode();
                System.out.println("2. gracz połączony");

                // Inicjalizacja sesji serwera z graczami i bazą danych
                ServerSession session = new ServerSession(p1, p2,  new GoDb(JpaUtil.getEntityManager()));
                session.start();

            } catch (Exception e) {
                System.out.println("Sesja zakończona błędem: " + e.getMessage());
                e.printStackTrace();
            }
        } while (true);
    }

    // Metoda do nasłuchiwania żądania gracza i przekazywania go do odpowiedniej metody konsumującej
    private static void listenForPlayerRequest(ClientIO playerIO, Consumer<ClientRequest> consumer) {
        new Thread(() -> {
            try {
                // Odczytanie ciągu znaków ze strumienia wejściowego i deserializacja go do obiektu ClientRequest
                String line = playerIO.reader.readUTF();
                ClientRequest req = MAPPER.readValue(line, ClientRequest.class);
                // Przekazanie żądania do konsumera
                consumer.accept(req);
            } catch (IOException e) {
                // Rzucenie wyjątku RuntimeException w przypadku błędu wejścia/wyjścia
                throw new RuntimeException(e);
            }
        }).start();
    }

    // Klasa reprezentująca strumienie wejściowy i wyjściowy klienta
    public static class ClientIO {

        public final DataInputStream reader;
        public final DataOutputStream writer;

        public ClientIO(DataInputStream reader, DataOutputStream writer) {
            this.reader = reader;
            this.writer = writer;
        }

        // Metoda fabryczna do tworzenia obiektu ClientIO na podstawie gniazda (Socket)
        public static ClientIO fromSocket(Socket socket) throws IOException {
            return new ClientIO(
                    new DataInputStream(socket.getInputStream()),
                    new DataOutputStream(socket.getOutputStream())
            );
        }
    }
}
