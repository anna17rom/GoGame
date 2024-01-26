package org.example;

import java.io.IOException;

public class ServerClient {


        public static void main(String[] args) {
            new Thread(() -> {
                try {
                    GoServerV2.main();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

  
            new Thread(() -> GoClientV2.main()).start();

        }

}
