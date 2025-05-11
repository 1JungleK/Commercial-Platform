package com.jungle.database.dao;

import com.jungle.database.model.Message;
import java.sql.SQLException;
import java.util.List;

/**
 *  The interface of message-dao
 * 
 *  @author Zhixin Li
 * 
 *  @version May 10, 2025
 */

public interface MessageDao {

    boolean createMessage(int senderId, int receiverId, String content) throws SQLException;

    List<Message> getMessagesBySenderAndReceiver(int senderId, int receiverId) throws SQLException;

}
