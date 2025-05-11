package com.jungle.database.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.jungle.database.DatabaseManager;
import com.jungle.database.dao.ItemDao;
import com.jungle.database.model.Item;

/**
 *  Implementation of the ItemDao interface for managing items in the database.
 * 
 *  @author Zhixin Li
 * 
 *  @version May 11, 2025
 */

public class ItemDaoImpl implements ItemDao {
    
    @Override
    public boolean createItem(Item item) throws SQLException {
        String sql = "INSERT INTO items (name, description, price, owner_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getOwnerId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    @Override
    public Item getItemById(int itemId) throws SQLException {
        String sql = "SELECT * FROM items WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemId);

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractItemFromResultSet(rs);
                }
                return null;
            }
        }
    }

    @Override
    public boolean updateItem(Item item) throws SQLException {
        String sql = "UPDATE items SET name = ?, description = ?, price = ?, owner_id = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getOwnerId());
            stmt.setInt(5, item.getItemId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    @Override
    public boolean deleteItem(int itemId) throws SQLException {
        String sql = "DELETE FROM items WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    @Override
    public List<Item> getItemsByOwnerId(int ownerId) throws SQLException {
        List<Item> items = new LinkedList<>();
        String sql = "SELECT * FROM items WHERE owner_id = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ownerId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(extractItemFromResultSet(rs));
                }
            }
        }
        return items;   
    }

    @Override
    public List<Item> getItemsByKeyword(String keyword) throws SQLException {
        List<Item> items = new LinkedList<>();
        String sql = "SELECT * FROM items WHERE name LIKE ? OR description LIKE ?";
        String searchKey = "%" + keyword + "%";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, searchKey);
            stmt.setString(2, searchKey);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(extractItemFromResultSet(rs));
                }
            }
        }
        return items;
    }

    @Override
    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new LinkedList<>();
        String sql = "SELECT * FROM items";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(extractItemFromResultSet(rs));
                }
            }
        }
        return items;
    }

    private Item extractItemFromResultSet(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setItemId(rs.getInt("item_id"));
        item.setName(rs.getString("name"));
        item.setDescription(rs.getString("description"));
        item.setPrice(rs.getDouble("price"));
        item.setOwnerId(rs.getInt("owner_id"));
        return item;
    }
}
