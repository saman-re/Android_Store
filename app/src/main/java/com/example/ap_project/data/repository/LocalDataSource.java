package com.example.ap_project.data.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.ap_project.data.AppDatabase;
import com.example.ap_project.data.entities.User;

public class LocalDataSource {

    private final AppDatabase db;

    public LocalDataSource(Context context){
        db = Room.databaseBuilder(context,
                AppDatabase.class, "my-database").build();
    }

    public User getUser(String username){
        return db.userDao().getUser(username);
    }
    public void insertUser(String username,String password ,String phone,String imagePath ,boolean seller){
        db.userDao().insertUser(username,password,phone,imagePath,seller);
    }
}
