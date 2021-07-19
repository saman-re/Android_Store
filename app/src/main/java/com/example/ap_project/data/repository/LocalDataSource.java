package com.example.ap_project.data.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.ap_project.data.AppDatabase;
import com.example.ap_project.data.entities.Product;
import com.example.ap_project.data.entities.User;

import java.util.List;

public class LocalDataSource {

    private final AppDatabase db;

    public LocalDataSource(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "my-database").build();
    }

    public User getUser(String username) {
        return db.userDao().getUser(username);
    }

    public void insertUser(String username, String password, String phone, String imagePath, boolean seller) {
        db.userDao().insertUser(username, password, phone, imagePath, seller);
    }

    public void deleteUser(String username) {
        db.userDao().deleteUser(username);
    }

    public void updateUser(String username, String password, String phoneNumber, String imagePath) {
        db.userDao().updateUser(username, password, phoneNumber, imagePath);
    }

    public void insertProduct(String title, int price, String username, String phoneNumber, String imagePath) {
        db.ProductDao().insert(title, price, username, phoneNumber, imagePath);
    }

    public List<Product> getProducts() {
        return db.ProductDao().getProducts();
    }

    public List<Product> getASCPrice() {
        return db.ProductDao().getASCPrice();
    }

    public List<Product> getDESCPrice() {
        return db.ProductDao().getDESCPrice();
    }

    public Product getProduct(int id) {
        return db.ProductDao().getProduct(id);
    }

    public void updateProduct(Product product) {
        db.ProductDao().updateProduct(product);
    }

    public void deleteProduct(Product product) {
        db.ProductDao().deleteProduct(product);
    }

    public void deleteUserProduct(String username) {
        db.ProductDao().deleteUserProduct(username);
    }

    public int countUserProduct(String username) {
        return db.ProductDao().countUserProduct(username);
    }

    public int countAllProduct() {
        return db.ProductDao().countAllProduct();
    }

    public int sumPrices() {
        return db.ProductDao().sumPrices();
    }

    public int countUsers() {
        return db.userDao().countUsers();
    }

    public List<User> getUsers(){
        return db.userDao().getAllUsers();
    }

    public User getBestSeller() {
        List<User> users = db.userDao().getAllUsers();
        int max = 0;
        User bestUser = null;
        for (User user : users) {
            int count=this.countUserProduct(user.username);
            if (count >= max){
                bestUser =user;
                max =count;
            }
        }
        return bestUser;
    }
}
