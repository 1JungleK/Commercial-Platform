package com.jungle.client.service;

import java.io.IOException;

import com.jungle.client.network.Client;
import com.jungle.protocol.LoginRequest;
import com.jungle.protocol.RegisterRequest;
import com.jungle.protocol.Request;
import com.jungle.protocol.Response;
import com.jungle.protocol.ResponseStatus;

public class ClientService {
    private Client client;

    public ClientService(String serverAddress, int serverPort) {
        this.client = new Client(serverAddress, serverPort);
    }

    public void connect() {
        try {
            client.connect();
            System.out.println("Connected to server.");
        } catch (Exception e) {
            System.err.println("Failed to connect: " + e.getMessage());
        }
    }

    public void disconnect() {
        client.disconnect();
        System.out.println("Disconnected from server.");
    }

    public Response sendRequest(Request request) throws IOException, ClassNotFoundException {
        
        return client.sendRequest(request);
        
    }

    public boolean isConnected() {
        return client.isConnected();
    }

    public Response login(String username, String password) {
        try {
            LoginRequest request = new LoginRequest();
            request.setUsername(username);
            request.setPassword(password);
            
            return sendRequest(request);
        } catch (IOException | ClassNotFoundException e) {
            Response response = new Response();
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage("Communication error: " + e.getMessage());
            return response;
        }
    }

    public Response register(String username, String password, String email) {
        try {
            RegisterRequest request = new RegisterRequest();
            request.setUsername(username);
            request.setPassword(password);
            request.setEmail(email);
            
            return sendRequest(request);
        } catch (IOException | ClassNotFoundException e) {
            Response response = new Response();
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage("Communication error: " + e.getMessage());
            return response;
        }
    }
}
