package com.jungle.dao;

import com.jungle.database.DatabaseManager;
import com.jungle.database.impl.UserDaoImpl;
import com.jungle.database.model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;


public class UserDaoTest {
    private static UserDaoImpl userDao;

    @BeforeClass
    public static void setup() throws SQLException {
        try (Connection conn = DatabaseManager.getConnection(); Statement stmt = conn.createStatement()) {

            stmt.execute("TRUNCATE TABLE users");

            stmt.execute("INSERT INTO users (username, password, email, balance) VALUES " +
                         "('admin', 'admin123', 'admin@example.com', 500.00)");
        }

        userDao = new UserDaoImpl();
    }

    @Before
    public void init() throws SQLException {
        // 每个测试前重置部分数据
        try (Connection conn = DatabaseManager.getConnection(); Statement stmt = conn.createStatement()) {
            // 确保测试用户存在
            stmt.executeUpdate("INSERT IGNORE INTO users (username, password, email, balance) VALUES " +
                               "('testuser', 'password123', 'test@example.com', 100.00)");
        }
    }

    @AfterClass
    public static void tearDown() {
        DatabaseManager.close();
    }

    @Test
    public void testCreateUser() throws SQLException {
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword("newpass");
        newUser.setEmail("new@example.com");
        newUser.setBalance(200.00);
        
        boolean result = userDao.createUser(newUser);
        assertTrue(result);
        
        User createdUser = userDao.getUserByUsername("newuser");
        assertNotNull(createdUser);
        assertEquals("new@example.com", createdUser.getEmail());
    }
}
