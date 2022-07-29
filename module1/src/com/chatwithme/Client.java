package com.chatwithme;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

        int PORT;
        DataOutputStream outputStream;
        DataInputStream inputStream;
        Socket localSocket;
        ListenerThread listener;

        public Client(int port){
            PORT = port;
        }

        void configure() throws IOException {
            localSocket = new Socket("localhost",PORT);

            outputStream = new DataOutputStream(localSocket.getOutputStream());
            inputStream = new DataInputStream(localSocket.getInputStream());
        }

        void listen () {
            listener = new ListenerThread(inputStream, "server");
            // listening
            listener.run();
        }

        void startConversation (BufferedReader reader) throws IOException {
            String data;
            // while line feed is the input --> get user feedback --> send to the other end
            while (true) {
                data = reader.readLine();
                if (data.equals("end")) {
                    break;
                }
                outputStream.writeUTF(data);
                outputStream.flush();
            }
            outputStream.close();
            reader.close();

            System.out.println("end of transmission...exiting");
        }
}
