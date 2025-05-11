package com.jungle.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import com.jungle.database.dao.ItemDao;
import com.jungle.database.dao.MessageDao;
import com.jungle.database.dao.UserDao;
import com.jungle.database.impl.ItemDaoImpl;
import com.jungle.database.impl.MessageDaoImpl;
import com.jungle.database.impl.UserDaoImpl;
import com.jungle.database.model.User;

public class ClientHandler implements Runnable{
    private final ConcurrentHashMap<Integer, Socket> clients;
    private final Socket clientSocket;
    
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean running;
    private User currentUser;
    private int clientId;

    private UserDao userDao;
    private ItemDao itemDao;
    private MessageDao messageDao;

    public ClientHandler(Socket clientSocket, ConcurrentHashMap<Integer, Socket> clients) {
        this.clientSocket = clientSocket;
        this.clients = clients;
        this.running = true;

        this.userDao = new UserDaoImpl();
        this.itemDao = new ItemDaoImpl();
        this.messageDao = new MessageDaoImpl();
    }


    @Override
    public void run() {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(clientSocket.getInputStream());

            while (running) {
                
            }
            
        } catch (IOException e) {
            System.err.println("Error initializing streams: " + e.getMessage());
        } finally {
            closeConnection();

        }
    }

    public void closeConnection() {
        this.running = false;

        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing client connection: " + e.getMessage());
        }
    }
}
