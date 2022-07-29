package com.chatwithme;

import java.io.DataInputStream;
import java.io.IOException;

public class ListenerThread implements Runnable{

    String oppositionParty;
    DataInputStream in;
    boolean stop = true;

    public ListenerThread(DataInputStream inputStream, String identifier){
        in = inputStream;
        oppositionParty = identifier;
    }

    @Override
    public void run() {
        // listens for other parties to communicate --> infinite loop
        System.out.println("listening from " + oppositionParty);
        String message;
        while(stop){
            try {
                // if there is data to read....
                while(in.available()>0) {
                    message = in.readUTF();
                    if (message.equals("finish")) {
                        System.out.println("stopped listening from " + oppositionParty);
                        in.close();
                        return;
                    } else {
                        System.out.println(oppositionParty + " : " + message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // to terminate the process from outside
    public void stop(){
        stop = false;
    }


}
