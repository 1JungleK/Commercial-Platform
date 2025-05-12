package com.jungle.database.model;

import java.io.Serializable;

/**
 *  The model of items in database
 * 
 *  @author Zhixin Li
 * 
 *  @version May 10, 2025
 */

public class Item implements Serializable {
    private int itemId;
    private String name;
    private double price;
    private String description;
    private int ownerId;

    public Item() { }

    public Item(int itemId, String name, double price, String description, int ownerId) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.ownerId = ownerId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
