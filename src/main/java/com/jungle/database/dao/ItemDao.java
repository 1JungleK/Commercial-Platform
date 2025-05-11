package com.jungle.database.dao;

import com.jungle.database.model.Item;
import java.sql.SQLException;
import java.util.List;

/**
 *  The interface of item-dao
 * 
 *  @author Zhixin Li
 * 
 *  @version May 10, 2025
 */

public interface ItemDao {

    boolean createItem(Item item) throws SQLException;

    Item getItemById(int itemId) throws SQLException;

    List<Item> getItemsByKeyword(String keyword) throws SQLException;

    List<Item> getItemsByOwnerId(int ownerId) throws SQLException;

    boolean updateItem(Item item) throws SQLException;

    boolean deleteItem(int itemId) throws SQLException;

    List<Item> getAllItems() throws SQLException;

}
