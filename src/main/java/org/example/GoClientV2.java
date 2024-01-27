package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ServerResponse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class GoClientV2 {

    private final static ObjectMapper MAPPER = new ObjectMapper();


    public static void main(String... args) {

        try {
            try (Socket socket = new Socket("localhost", 8000)) {

                DataInputStream is = new DataInputStream(socket.getInputStream());
                DataOutputStream os = new DataOutputStream(socket.getOutputStream());

                CountDownLatch latch = new CountDownLatch(0);
                ClientSession session = new ClientSession(s -> {
                    try {
                        os.writeUTF(MAPPER.writeValueAsString(s));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                new Thread(() -> {
                    while (true) {
                        try {
                            ServerResponse resp = MAPPER.readValue(is.readUTF(), ServerResponse.class);
                            session.handleResponse(resp);
                            latch.countDown();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();

                session.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}