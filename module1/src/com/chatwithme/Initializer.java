package com.chatwithme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Initializer {
    public static void main(String[] args) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        // serverThread
        Runnable serverRunnable = () -> {
            Server server = new Server(8000);
            try {
                server.configure();
                server.startConversation(bufferedReader);
                server.listen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        // clientThread
        Runnable clientRunnable = () -> {
            Client client = new Client(8000);
            try {
                client.configure();
                client.startConversation(bufferedReader);
                //client.listen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        new Thread(serverRunnable).start();
        new Thread(clientRunnable).start();
    }
}
