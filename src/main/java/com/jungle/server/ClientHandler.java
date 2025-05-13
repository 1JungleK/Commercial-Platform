package com.jungle.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

import com.jungle.database.dao.ItemDao;
import com.jungle.database.dao.MessageDao;
import com.jungle.database.dao.UserDao;
import com.jungle.database.impl.ItemDaoImpl;
import com.jungle.database.impl.MessageDaoImpl;
import com.jungle.database.impl.UserDaoImpl;
import com.jungle.database.model.User;
import com.jungle.protocol.ForgotPwdRequest;
import com.jungle.protocol.LoginRequest;
import com.jungle.protocol.RegisterRequest;
import com.jungle.protocol.Request;
import com.jungle.protocol.Response;
import com.jungle.protocol.ResponseStatus;

/**
 *  ClientHandler class that handles individual client connections and processes requests.
 * 
 *  @author Zhixin Li
 * 
 *  @version May 11, 2025
 */

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
                try {
                    // Read request object from client
                    Request request = (Request) inputStream.readObject();
                    
                    // Process the request and get a response
                    Response response = processRequest(request);
                    
                    // Send response back to client
                    outputStream.writeObject(response);
                    outputStream.flush();
                    
                } catch (ClassNotFoundException e) {
                    System.err.println("Error: Invalid object received from client - " + e.getMessage());
                    sendErrorResponse("Invalid request format");
                } catch (EOFException | SocketException e) {
                    // Client disconnected
                    System.out.println("Client disconnected: " + clientId);
                    running = false;
                } catch (Exception e) {
                    System.err.println("Error processing client request: " + e.getMessage());
                    e.printStackTrace();
                    sendErrorResponse("Server error: " + e.getMessage());
                }
            }
            
        } catch (IOException e) {
            System.err.println("Error initializing streams: " + e.getMessage());
        } finally {
            closeConnection();
            clients.remove(clientId);
        }
    }

    private Response processRequest(Request request) {
        Response response = new Response();

        try {
            switch (request.getType()) {
                case LOGIN:
                    handleLogin(request, response);
                    break;
                
                case REGISTER:
                    handleRegister(request, response);
                    break;

                case LOGOUT:
                    handleLogout(request, response);
                    break;

                case FORGOT_PWD:
                    hadleForgotPwd(request, response);
                    break;

                default:
                    response.setStatus(ResponseStatus.ERROR);
                    response.setMessage("Unknown request type.");
                    break;
            }
        } catch (Exception e) {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage("Server error: " + e.getMessage());
            e.printStackTrace();
        }

        return response;
    }

    private void handleLogin(Request request, Response response) throws SQLException {
        LoginRequest loginRequest = (LoginRequest) request;
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        boolean isValidUser = userDao.validateCredentials(username, password);

        if (isValidUser) {
            currentUser = userDao.getUserByUsername(username);
            clientId = currentUser.getUserId();

            if (clients.containsKey(clientId)) {
                response.setStatus(ResponseStatus.ERROR);
                response.setMessage("User already logged in.");
                return;
            } else {
                clients.putIfAbsent(clientId, clientSocket);
                response.setStatus(ResponseStatus.SUCCESS);
                response.setMessage("Login successful.");
                response.setData(currentUser);
            }
        } else {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage("Invalid username or password.");
        }
    }

    private void handleRegister(Request request, Response response) throws SQLException {
        RegisterRequest registerRequest = (RegisterRequest) request;
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String email = registerRequest.getEmail();

        if (username == null || username.isEmpty() || password == null || password.isEmpty() || email == null || email.isEmpty()) {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage("Username, password, and email cannot be empty.");
            return;
        }

        User existingUser = userDao.getUserByUsername(username);
        if (existingUser != null) {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage("Username already exists");
            return;
        }
    
        existingUser = userDao.getUserByEmail(email);
        if (existingUser != null) {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage("Email already in use");
            return;
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setBalance(0.0);

        boolean success = userDao.createUser(newUser);
        
        if (success) {
            response.setStatus(ResponseStatus.SUCCESS);
            response.setMessage("Registration successful");
            
            User createdUser = userDao.getUserByUsername(newUser.getUsername());
            response.setData(createdUser);
        } else {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage("Failed to register user");
        }
    }

    private void handleLogout(Request request, Response response) {
        if (currentUser != null) {
            clients.remove(currentUser.getUserId());
            currentUser = null;
        }
        
        response.setStatus(ResponseStatus.SUCCESS);
        response.setMessage("Logged out successfully");
    }

    private void hadleForgotPwd(Request request, Response response) throws SQLException {
        ForgotPwdRequest forgotPwdRequest = (ForgotPwdRequest) request;
        String username = forgotPwdRequest.getUsername();
        String email = forgotPwdRequest.getEmail();
        String newPassword = forgotPwdRequest.getPassword();

        if (username == null || username.isEmpty() || email == null || email.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage("Username, email, and new password cannot be empty.");
            return;
        }

        User user = userDao.getUserByUsername(username);
        if (user == null) {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage("User not found");
            return;
        }

        if (!user.getEmail().equals(email)) {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage("Email does not match the registered email");
            return;
        }

        user.setPassword(newPassword);
        boolean success = userDao.updateUser(user);

        if (success) {
            response.setStatus(ResponseStatus.SUCCESS);
            response.setMessage("Password reset successful");
        } else {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage("Failed to reset password");
        }
    }

    private void sendErrorResponse(String message) {
        try {
            Response response = new Response();
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage(message);
            
            synchronized (outputStream) {
                outputStream.writeObject(response);
                outputStream.flush();
            }
        } catch (IOException e) {
            System.err.println("Failed to send error response: " + e.getMessage());
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
