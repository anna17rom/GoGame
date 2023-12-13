package org.example;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class GoSession implements Runnable
{

    private Socket player1;
    private Socket player2;

    public GoSession(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
    }
    // HAŃKA!!!!! LOGIKA TUTAJ!!!
    @Override
    public void run() {
        try (
                DataInputStream input1 = new DataInputStream(player1.getInputStream());
                DataOutputStream output1 = new DataOutputStream(player1.getOutputStream());
                DataInputStream input2 = new DataInputStream(player2.getInputStream());
                DataOutputStream output2 = new DataOutputStream(player2.getOutputStream())
        ) {
            output1.writeUTF("Start the Go game. You are Player 1 (Black stones).");
            output2.writeUTF("Start the Go game. You are Player 2 (White stones).");

            while (true) {
                // Player 1 makes a move
                String movePlayer1 = input1.readUTF();
                output2.writeUTF("Opponent's move: " + movePlayer1);

                // Player 2 makes a move
                String movePlayer2 = input2.readUTF();
                output1.writeUTF("Opponent's move: " + movePlayer2);

                // Simulate some game logic (replace this with actual Go game logic)
                boolean gameEnded = Math.random() < 0.05;  // Randomly end the game for demonstration

                if (gameEnded) {
                    output1.writeUTF("Game Over! You win!");
                    output2.writeUTF("Game Over! You lose!");
                    break;
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

