package org.example;

import java.io.IOException;

public class ServerClient {


        public static void main(String[] args) {
            // Create and start the server in a separate thread
            new Thread(() -> {
                try {
                    GoServerV2.main();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Create and start the first client in a separate thread
            new Thread(() -> GoClientV2.main()).start();

        }

}
