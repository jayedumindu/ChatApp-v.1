package com.chatwithme.controller;

import com.chatwithme.Thread.ListenerThread;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;

public class clientController {
    public JFXTextField msgField;
    public TextArea msgPane;

    DataOutputStream outputStream;
    DataInputStream inputStream;
    Socket localSocket;

    public void initialize() throws IOException {
        int PORT = 8000;
        localSocket = new Socket("localhost",PORT);

        outputStream = new DataOutputStream(localSocket.getOutputStream());
        inputStream = new DataInputStream(localSocket.getInputStream());

        Timer timer = new Timer();
        timer.schedule(new ListenerThread(inputStream,"client", msgPane,timer),1000,2000);
    }

    public void sendMsg(ActionEvent actionEvent) throws IOException {
        String msg = msgField.getText();
        msgPane.appendText("\nMe : " + msg);
        outputStream.writeUTF(msg);
        outputStream.flush();
        msgField.clear();
    }

    public void clear(ActionEvent actionEvent) {
        msgPane.clear();
    }
}
