package com.example.ap_project.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class ShoppingItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "price")
    public int price;

    @ColumnInfo(name = "image_path")
    public String imagePath;

    @ColumnInfo(name = "owner_username")
    public String username;

    public ShoppingItem(String title, int price, String username) {
        this.title = title;
        this.price = price;
        this.username = username;
        this.imagePath="";
    }
    @Ignore
    public ShoppingItem(String title, int price, String imagePath, String username) {
        this.title = title;
        this.price = price;
        this.imagePath = imagePath;
        this.username = username;
    }
}
