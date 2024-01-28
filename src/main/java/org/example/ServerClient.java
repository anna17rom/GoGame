package org.example;

import java.io.IOException;

// Klasa reprezentująca uruchomienie serwera i klienta w oddzielnych wątkach
public class ServerClient {

    // Metoda główna
    public static void main(String[] args) {
        // Uruchomienie serwera w oddzielnym wątku
        new Thread(() -> {
            try {
                GoServerV2.main();  // Wywołanie metody main serwera
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Uruchomienie klienta w oddzielnym wątku
        new Thread(() -> GoClientV2.main()).start();
    }

}
