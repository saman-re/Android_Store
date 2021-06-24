package com.example.ap_project.data.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.ap_project.data.AppDatabase;

public class LocalDataSource {

    private AppDatabase db;

    public LocalDataSource(Context context){
        db = Room.databaseBuilder(context,
                AppDatabase.class, "my-database").build();
    }


}
