package com.jungle.client;

import javax.swing.SwingUtilities;

import com.jungle.client.service.ClientService;
import com.jungle.client.ui.AppEntrance;

public class App {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 8080;

        ClientService clientService = new ClientService(serverAddress, serverPort);
        System.out.println("Connecting to server at " + serverAddress + ":" + serverPort);
        clientService.connect();


        if (clientService.isConnected()) {
            SwingUtilities.invokeLater(() -> {
                new AppEntrance(clientService).setVisible(true);
            });
        }

    }
}
