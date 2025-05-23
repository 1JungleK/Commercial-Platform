package com.jungle.database.dao;

import java.sql.SQLException;
import com.jungle.database.model.User;

/**
 *  The interface of user-dao
 * 
 *  @author Zhixin Li
 * 
 *  @version May 10, 2025
 */

public interface UserDao {

    boolean createUser(User user) throws SQLException;

    User getUserById(int userId) throws SQLException;

    User getUserByUsername(String username) throws SQLException;

    User getUserByEmail(String email) throws SQLException;

    boolean updateUser(User user) throws SQLException;

    boolean deleteUser(int userId) throws SQLException;

    boolean validateCredentials(String username, String password) throws SQLException;

}
