package com.chatwithme;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {

        int PORT = 8000;
        ServerSocket serverSocket = new ServerSocket(PORT);

        // wait for an connection to be established --> binds it to a local socket --> returns the remote socket
        Socket localSocket = serverSocket.accept();

        // for I/O purposes
        DataOutputStream outputStream = new DataOutputStream(localSocket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(localSocket.getInputStream());

        System.out.println("connection succeeded!");

        ListenerThread listener = new ListenerThread(inputStream,"client");
        // listening
        listener.run();
        System.out.println("listening.......");

        // null in ASCII
        int data = 0;
        // while line feed is the input --> get user feedback --> send to the other end
        while (data!=10){
            data = System.in.read();
            outputStream.write(data);
        }
        outputStream.flush();
        outputStream.close();

        System.out.println("end of server transmission...exiting");
        // returns int -> local socket
        // localSocket.getLocalPort();

        // returns inetAddress -> ip address which is bind to the local socket
        // localSocket.getLocalAddress();


    }
}
