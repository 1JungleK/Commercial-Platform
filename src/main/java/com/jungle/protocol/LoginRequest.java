package com.jungle.protocol;

/**
 *  LoginRequest class represents a request to log in to the system.
 *  It extends the Request class and contains
 *  the username and password fields.
 * 
 *  @author Zhixin Li
 * 
 *  @version May 11, 2025
 */

public class LoginRequest extends Request {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    public LoginRequest() {
        super(RequestType.LOGIN);
    }

    public LoginRequest(String username, String password) {
        super(RequestType.LOGIN);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
