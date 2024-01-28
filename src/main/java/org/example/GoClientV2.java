package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ServerResponse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class GoClientV2 {

    // Inicjalizacja obiektu ObjectMapper z biblioteki Jackson
    private final static ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String... args) {

        try {
            // Inicjalizacja gniazda (socket) dla połączenia z serwerem na lokalnym hoście (localhost) na porcie 8000
            try (Socket socket = new Socket("localhost", 8000)) {

                // Inicjalizacja strumieni wejściowego i wyjściowego
                DataInputStream is = new DataInputStream(socket.getInputStream());
                DataOutputStream os = new DataOutputStream(socket.getOutputStream());

                // Inicjalizacja obiektu CountDownLatch z liczbą zdarzeń ustawioną na 0
                CountDownLatch latch = new CountDownLatch(0);

                // Inicjalizacja obiektu ClientSession, który obsługuje komunikację z serwerem
                ClientSession session = new ClientSession(s -> {
                    try {
                        // Zapisanie obiektu sesji (s) jako ciągu znaków do strumienia wyjściowego
                        os.writeUTF(MAPPER.writeValueAsString(s));
                    } catch (IOException e) {
                        // Rzucenie wyjątku RuntimeException w przypadku błędu wejścia/wyjścia
                        throw new RuntimeException(e);
                    }
                });

                // Uruchomienie nowego wątku do nasłuchiwania odpowiedzi serwera
                new Thread(() -> {
                    while (true) {
                        try {
                            // Odczytanie ciągu znaków ze strumienia wejściowego i deserializacja go do obiektu ServerResponse
                            ServerResponse resp = MAPPER.readValue(is.readUTF(), ServerResponse.class);
                            // Obsługa odpowiedzi serwera przez sesję klienta
                            session.handleResponse(resp);
                            // Zmniejszenie licznika CountDownLatch
                            latch.countDown();
                        } catch (IOException e) {
                            // Rzucenie wyjątku RuntimeException w przypadku błędu wejścia/wyjścia
                            throw new RuntimeException(e);
                        }
                    }
                }).start();

                // Uruchomienie sesji klienta
                session.start();

            }
        } catch (IOException e) {
            // Wyświetlenie śladu stosu w przypadku błędu wejścia/wyjścia
            e.printStackTrace();
        }
    }
}
