package org.example;

import java.awt.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class GoClient extends JFrame implements Runnable {

    Socket socket;
    private DataInputStream fromServer;
    private DataOutputStream toServer;

    public static void main(String[] args) {
        GoClient client = new GoClient();
        client.setBounds(100, 100, 400, 400);
        client.init();
        client.setVisible(true);
    }

    public void init() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3, 3, 0, 0));
        p.setBorder(new LineBorder(Color.black, 1));
        add(p, BorderLayout.CENTER);

        connectToServer();
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 8000);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException ex) {
            System.err.println(ex);
        }

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            int player = fromServer.readInt();
            // TODO: Add your custom logic here for communication with the server
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
