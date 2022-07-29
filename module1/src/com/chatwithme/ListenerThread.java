package com.chatwithme;

import java.io.DataInputStream;
import java.io.IOException;

public class ListenerThread implements Runnable{
    String oppositionParty;
    DataInputStream in;

    public ListenerThread(DataInputStream inputStream, String identifier){
        in = inputStream;
        oppositionParty = identifier;
    }

    @Override
    public void run() {
        // listens for other parties to communicate --> infinite loop
        String message = "";
        while(true){
            try {
                message = in.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Client :" + message);
        }
    }

}
