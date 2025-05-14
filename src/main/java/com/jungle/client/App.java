package com.jungle.client;

import javax.swing.SwingUtilities;

import com.jungle.client.service.ClientService;
import com.jungle.client.ui.AppEntrance;
import com.jungle.client.ui.MainFrame;

public class App {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 8080;

        ClientService clientService = new ClientService(serverAddress, serverPort);
        System.out.println("Connecting to server at " + serverAddress + ":" + serverPort);
        clientService.connect();


        if (clientService.isConnected()) {
            System.out.println("Connected to server.");
            SwingUtilities.invokeLater(() -> {
                AppEntrance appEntrance = new AppEntrance(clientService, () -> {
                    MainFrame mainFrame = new MainFrame(clientService);
                    mainFrame.setVisible(true);
                });
                appEntrance.setVisible(true);
            });
        } else {
            System.out.println("Failed to connect to server.");
        }
    }
}
