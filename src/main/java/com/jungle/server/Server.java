package com.jungle.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  Server class that handles incoming client connections and manages client sessions.
 * 
 *  @author Zhixin Li
 * 
 *  @version May 11, 2025
 */

public class Server {
    private static final int DEFAULT_PORT = 8080;

    private int port;
    private boolean running;
    private ExecutorService threadPool;
    private ConcurrentHashMap<Integer, Socket> clients = new ConcurrentHashMap<>();

    
    public Server() {
        this.port = DEFAULT_PORT;
        this.running = false;
    }

    public Server(int port) {
        this.port = port;
        this.running = false;
    }

    public void start() {
        if (running) {
            System.out.println("Server is already running.");
            return;
        }

        running = true;
        threadPool = Executors.newFixedThreadPool(10);
        System.out.println("Server started on port " + port);
        
        try (ServerSocket ss = new ServerSocket(port)) {
            while (running) {
                Socket clientSocket = ss.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                clients.putIfAbsent(clientSocket.hashCode(), clientSocket);
                threadPool.submit(new ClientHandler(clientSocket, clients));
            }
        } catch (Exception e) {
            System.err.println("Error starting server: " + e.getMessage());
        } finally {
            stop();
        }
    }

    public void stop() {
        if (!running) {
            System.out.println("Server is not running.");
            return;
        }

        running = false;
        threadPool.shutdown(); // Shut down the thread pool
        System.out.println("Server stopped.");
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
