package com.jungle.database.model;

import java.io.Serializable;

/**
 *  The model of users in database
 * 
 *  @author Zhixin Li
 * 
 *  @version May 10, 2025
 */

public class User implements Serializable {
    private int userId;
    private String username;
    private String password;
    private String email;
    private double balance;

    public User() { }

    public User(int userId, String username, String password, String email, double balance) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
