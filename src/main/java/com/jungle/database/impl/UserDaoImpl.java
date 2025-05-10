package com.jungle.database.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.jungle.database.dao.UserDao;
import com.jungle.database.model.User;

public class UserDaoImpl implements UserDao {

    @Override
    public boolean createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, email, balance) VALUES (?, ?, ?, ?)";

        return false;
    }
}
