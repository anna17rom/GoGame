package org.example;

import java.io.*;
import java.io.IOException;
import java.net.*;

class GoSession implements Runnable {



    public static final int PLAYER1_WON = 1;
    public static final int PLAYER2_WON = 2;
    public static final int DRAW = 3;
    public static final int CONTINUE = 4;

    private Socket firstPlayer;
    private Socket secondPlayer;


    public GoSession(Socket firstPlayer, Socket secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    //// HAŃKA!!!!! LOGIKA TUTAJ!!!
    @Override
    public void run() {
        try {
            DataInputStream fromPlayer1 = new DataInputStream(firstPlayer.getInputStream());
            DataOutputStream toPlayer1 = new DataOutputStream(firstPlayer.getOutputStream());
            DataInputStream fromPlayer2 = new DataInputStream(secondPlayer.getInputStream());
            DataOutputStream toPlayer2 = new DataOutputStream(secondPlayer.getOutputStream());

            toPlayer1.writeInt(1);
            toPlayer2.writeInt(2);

            while (true) {
                // Implement Go game logic here
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}