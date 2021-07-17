package com.example.ap_project.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import com.example.ap_project.data.entities.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("INSERT INTO Product(title ,price,owner_username,phone_number,image_path) VALUES(:title,:price,:username,:phoneNumber,:imagePath)")
    void insert(String title ,int price,String username,String phoneNumber,String imagePath);

    @Query("SELECT * FROM Product")
    List<Product> getProducts();

    @Query("SELECT * FROM Product WHERE owner_username=:username")
    List<Product> getUserShoppingItems(String username);

    @Delete
    void deleteUser(Product item);
}
