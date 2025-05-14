package com.jungle.client.ui;

import javax.swing.JFrame;

import com.jungle.client.service.ClientService;

public class MainFrame extends JFrame {
    private ClientService clientService;

    public MainFrame(ClientService clientService) {
        this.clientService = clientService;

        setTitle("Jungle Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        this.clientService.connect(); // change later
    }
}
