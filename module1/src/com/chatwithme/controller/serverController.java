package com.chatwithme.controller;

import com.chatwithme.Thread.ListenerThread;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class serverController {
    public TextArea msgPane;
    public JFXTextField msgField;

    DataOutputStream outputStream;
    DataInputStream inputStream;
    ServerSocket serverSocket;
    Socket localSocket;
    ListenerThread listener;

    public void initialize() throws IOException {
        // setting up the server
        msgPane.setText("server up and running!");
        int PORT = 8000;
        serverSocket  = new ServerSocket(PORT);
        // wait for an connection to be established --> binds it to a local socket --> returns the remote socket
        localSocket = serverSocket.accept();
        System.out.println("connection succeeded!");

        outputStream = new DataOutputStream(localSocket.getOutputStream());
        inputStream = new DataInputStream(localSocket.getInputStream());

        listener = new ListenerThread(inputStream,"client", msgPane);
        // start listening
        listener.run();
    }

    public void sendMsg(ActionEvent actionEvent) throws IOException {
        String msg = msgField.getText();
        outputStream.writeUTF(msg);
        outputStream.flush();
        msgField.clear();
    }

    public void clear(ActionEvent actionEvent) {
    }

    public void openUpClient(ActionEvent actionEvent) throws IOException {
        Stage clientStage = new Stage();
        Scene client = new Scene(FXMLLoader.load(this.getClass().getClassLoader().getResource("com/chatwithme/view/server.fxml")));
        clientStage.setScene(client);
        clientStage.setTitle("Client App");
        clientStage.show();
    }

}
