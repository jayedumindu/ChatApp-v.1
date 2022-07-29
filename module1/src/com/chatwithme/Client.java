package com.chatwithme;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {

        int PORT = 8000;
        Socket socket = new Socket("localhost", PORT);

        // for I/O purposes
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        ListenerThread listener = new ListenerThread(inputStream,"server");
        // listening
        listener.run();

        // in UTF-8
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

        System.out.println("end of client transmission...exiting");


    }
}
