package com.chatwithme.Thread;

import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.IOException;

public class ListenerThread implements Runnable{

    String oppositionParty;
    DataInputStream in;
    TextArea msgArea;

    public ListenerThread(DataInputStream inputStream, String identifier, TextArea msgArea){
        in = inputStream;
        oppositionParty = identifier;
        this.msgArea = msgArea;
    }

    @Override
    public void run() {
        // listens for other parties to communicate --> infinite loop
        System.out.println("listening from " + oppositionParty);
        String message;
        while(true){
            try {
                if(in.available()>0){
                    String msg = in.readUTF();
                    msgArea.appendText("\n"+oppositionParty+" : "+msg);
                    if(msg.equals("over")){
                        msgArea.appendText("\nExiting.........");
                        return;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
