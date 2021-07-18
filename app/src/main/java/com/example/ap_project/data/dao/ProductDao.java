package com.example.ap_project.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ap_project.data.entities.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("INSERT INTO Product(title ,price,owner_username,phone_number,image_path) VALUES(:title,:price,:username,:phoneNumber,:imagePath)")
    void insert(String title ,int price,String username,String phoneNumber,String imagePath);

    @Query("SELECT * FROM Product")
    List<Product> getProducts();

    @Query("SELECT * FROM product ORDER BY price ASC")
    List<Product> getASCPrice();

    @Query("SELECT * FROM product ORDER BY price DESC")
    List<Product> getDESCPrice();

    @Query("SELECT * FROM Product WHERE id=:id")
    Product getProduct(int id);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product item);

    @Query("DELETE FROM Product WHERE owner_username=:username")
    void deleteUserProduct(String username);

    @Query("SELECT COUNT(id) FROM Product WHERE owner_username=:username")
    int countUserProduct(String username);
}
