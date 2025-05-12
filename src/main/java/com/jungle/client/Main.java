package com.jungle.client;

import com.jungle.client.service.ClientService;
import com.jungle.client.ui.LoginForm;

public class Main {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 8080;

        ClientService clientService = new ClientService(serverAddress, serverPort);
        System.out.println("Connecting to server at " + serverAddress + ":" + serverPort);
        clientService.connect();
        System.out.println("Connected to server.");

        // Example usage
        if (clientService.isConnected()) {
            // Perform login
            new LoginForm(clientService).setVisible(true);
        }
    }
}
