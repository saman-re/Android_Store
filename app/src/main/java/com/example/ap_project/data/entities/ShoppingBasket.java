package com.example.ap_project.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ShoppingBasket {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "itemId")
    public int itemId;

    public ShoppingBasket(String username, int itemId) {
        this.username = username;
        this.itemId = itemId;
    }
}
