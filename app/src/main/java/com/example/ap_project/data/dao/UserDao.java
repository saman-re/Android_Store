package com.example.ap_project.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.ap_project.data.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("INSERT INTO User(username ,password,phone_number,image_path,seller) VALUES(:username,:password,:phoneNumber,:imagePath,:seller)")
    void insertUser(String username ,String password,String phoneNumber,String imagePath,boolean seller);

    @Query("SELECT * FROM User WHERE username = :username")
    User getUser(String username);

    @Query("UPDATE User SET password = :password, phone_number = :phoneNumber,image_path=:imagePath WHERE username=:username")
    void updateUser(String username ,String password,String phoneNumber,String imagePath);

    @Query("DELETE FROM User WHERE username=:username")
    void deleteUser(String username);

    @Query("SELECT COUNT(username) FROM User")
    int countUsers();

    @Query("SELECT * FROM User")
    List<User> getAllUsers();
}
