package com.chatwithme;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

        int PORT;
        ServerSocket serverSocket;
        DataOutputStream outputStream;
        DataInputStream inputStream;
        Socket localSocket;
        ListenerThread listener;
        Scanner scanner = new Scanner(System.in);

        public Server(int port){
            PORT = port;
        }

        void configure() throws IOException {
            System.out.println("server up and running!");
            serverSocket  = new ServerSocket(PORT);
            // wait for an connection to be established --> binds it to a local socket --> returns the remote socket
            localSocket = serverSocket.accept();
            System.out.println("connection succeeded!");

            outputStream = new DataOutputStream(localSocket.getOutputStream());
            inputStream = new DataInputStream(localSocket.getInputStream());
        }

        void listen(){
            listener = new ListenerThread(inputStream,"client");
            // listening
            listener.run();
        }

        void startConversation(BufferedReader reader) throws IOException {
            String data;
            // while line feed is the input --> get user feedback --> send to the other end
            while (true){
                data = scanner.nextLine();
                if(data.equals("end")){
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
