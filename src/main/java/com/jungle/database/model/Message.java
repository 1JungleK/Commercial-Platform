package com.jungle.database.model;

import java.sql.Timestamp;

/**
 *  The model of message
 * 
 *  @author Zhixin Li
 * 
 *  @version May 10, 2025
 */

public class Message {
    private int messageId;
    private int senderId;
    private int receiverId;
    private String content;
    private Timestamp timestamp;

    public Message() { };
    
    public Message(int messageId, int senderId, int receiverId, String content, Timestamp timestamp) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
