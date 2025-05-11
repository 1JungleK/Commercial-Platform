package com.jungle.database.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.jungle.database.DatabaseManager;
import com.jungle.database.dao.MessageDao;
import com.jungle.database.model.Message;

/**
 *  Implementation of the MessageDao interface for managing messages in the database.
 * 
 *  @author Zhixin Li
 * 
 *  @version May 11, 2025
 */

public class MessageDaoImpl implements MessageDao{
    
    @Override
    public boolean createMessage(int senderId, int receiverId, String content) throws SQLException {
        String sql = "INSERT INTO messages (sender_id, receiver_id, content) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            stmt.setString(3, content);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    @Override
    public List<Message> getMessagesBySenderAndReceiver(int senderId, int receiverId) throws SQLException {
        List<Message> messages = new LinkedList<>();
        String sql = "SELECT * FROM messages WHERE sender_id = ? AND receiver_id = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);

            try (ResultSet rs = stmt.executeQuery()) {
                

                while (rs.next()) {
                    messages.add(extractMessageFromResultSet(rs));
                }
            }
        }

        return messages;
    }

    private Message extractMessageFromResultSet(ResultSet rs) throws SQLException {
        Message message = new Message();
        message.setMessageId(rs.getInt("message_id"));
        message.setSenderId(rs.getInt("sender_id"));
        message.setReceiverId(rs.getInt("receiver_id"));
        message.setContent(rs.getString("content"));
        message.setTimestamp(rs.getTimestamp("timestamp"));
        return message;
    }
}
