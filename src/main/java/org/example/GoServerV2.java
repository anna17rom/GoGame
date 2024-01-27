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

    private final static ObjectMapper MAPPER = new ObjectMapper();


    public static void main(String... args) throws IOException {

        do {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Waiting for 1st player to connect.");
                Socket p1Socket = serverSocket.accept();
                ClientIO p1IO = ClientIO.fromSocket(p1Socket);
                Player p1 = new RemotePlayer(p1IO);
                // Wait for 1st player choose game mode
                ServerResponse.Mode mode = p1.mode();
                System.out.println("1st player connected");
                Player p2;
                if (mode == ServerResponse.Mode.MULTIPLAYER) {
                    p2 = new RemotePlayer(ClientIO.fromSocket(serverSocket.accept()));
                } else {
                    p2 = new CpuPlayer();
                    ((CpuPlayer) p2).setSize(p1.getSize());
                }
                p2.mode();
                System.out.println("2nd player connected");
                ServerSession session = new ServerSession(p1, p2);
                session.start();
            } catch (Exception e) {
                System.out.println("Session ended with error: " + e.getMessage());
            }
        } while (true);
    }

    private static void listenForPlayerRequest(ClientIO playerIO, Consumer<ClientRequest> consumer) {
        new Thread(() -> {
            try {
                String line = playerIO.reader.readUTF();
                ClientRequest req = MAPPER.readValue(line, ClientRequest.class);
                consumer.accept(req);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static class ClientIO {

        final DataInputStream reader;
        final DataOutputStream writer;

        private ClientIO(DataInputStream reader, DataOutputStream writer) {
            this.reader = reader;
            this.writer = writer;
        }

        public static ClientIO fromSocket(Socket socket) throws IOException {
            return new ClientIO(
                    new DataInputStream(socket.getInputStream()),
                    new DataOutputStream(socket.getOutputStream())
            );
        }
    }
}


































