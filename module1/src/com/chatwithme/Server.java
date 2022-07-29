package com.chatwithme;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws IOException {

        int PORT = 8000;
        ServerSocket serverSocket = new ServerSocket(PORT);

        System.out.println("server up and running!");
        // wait for an connection to be established --> binds it to a local socket --> returns the remote socket
        Socket localSocket = serverSocket.accept();
        System.out.println("connection succeeded!");

        // for I/O purposes --> encodes byte-streams (UTF-8)
        DataOutputStream outputStream = new DataOutputStream(localSocket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(localSocket.getInputStream());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        ListenerThread listener = new ListenerThread(inputStream,"client");
        // listening
        listener.run();

        String data;
        // while line feed is the input --> get user feedback --> send to the other end
        while (true){
            data = bufferedReader.readLine();
            if(data.equals("finish")){
                break;
            }
            outputStream.writeUTF(data);
            outputStream.flush();
        }

        listener.stop();

        outputStream.close();
        bufferedReader.close();

        System.out.println("end of server transmission...exiting");

    }
}
