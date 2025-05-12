package com.jungle.client.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import com.jungle.protocol.Request;
import com.jungle.protocol.Response;

/**
 *  Client class for handling network communication with the server.
 * 
 *  @author Zhixin Li
 * 
 *  @version May 12, 2025
 */

public class Client {
    private String serverAddress;
    private int serverPort;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private volatile boolean connected;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.connected = false;
    }

    public void connect() {
        try {
            socket = new Socket(serverAddress, serverPort);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();

            inputStream = new ObjectInputStream(socket.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void disconnect() {
        connected = false;
        
        try {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error during disconnect: " + e.getMessage());
        }
    }

    public synchronized Response sendRequest(Request request) throws IOException, ClassNotFoundException {
        if (!connected) {
            System.err.println("Not connected to server.");
            return null;
        }

        outputStream.writeObject(request);
        outputStream.flush();

        Response response = (Response) inputStream.readObject();
        return response;
    }

    public boolean isConnected() {
        return connected;
    }
}
