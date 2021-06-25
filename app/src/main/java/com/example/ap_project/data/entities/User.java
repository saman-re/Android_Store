package com.example.ap_project.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    @ColumnInfo(name = "username")
    @NonNull
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "phone_number")
    public String phoneNumber;

    @ColumnInfo(name ="image_path")
    public String imagePath;

    @ColumnInfo(name = "seller")
    public boolean seller;

    public User(String username, String password, String phoneNumber, String imagePath,boolean seller) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.imagePath = imagePath;
        this.seller = seller;
    }
}
