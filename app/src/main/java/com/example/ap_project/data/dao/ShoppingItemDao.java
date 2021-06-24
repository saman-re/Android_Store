package com.example.ap_project.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import com.example.ap_project.data.entities.ShoppingItem;

import java.util.List;

@Dao
public interface ShoppingItemDao {
    @Query("INSERT INTO ShoppingItem(title ,price,owner_username,image_path) VALUES(:title,:price,:username,:imagePath)")
    void insert(String title ,int price,String username,String imagePath);

    @Query("SELECT * FROM ShoppingItem WHERE owner_username!=:username")
    List<ShoppingItem> getShoppingItems(String username);

    @Query("SELECT * FROM shoppingitem WHERE owner_username=:username")
    List<ShoppingItem> getUserShoppingItems(String username);

    @Delete
    void deleteUser(ShoppingItem item);
}
