package com.example.ap_project.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.ap_project.data.dao.ShoppingBasketDao;
import com.example.ap_project.data.dao.ShoppingItemDao;
import com.example.ap_project.data.dao.UserDao;
import com.example.ap_project.data.entities.ShoppingBasket;
import com.example.ap_project.data.entities.ShoppingItem;
import com.example.ap_project.data.entities.User;

@Database(entities = {User.class, ShoppingItem.class, ShoppingBasket.class},version = 1)
abstract public class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ShoppingItemDao shoppingItemDao();
    public abstract ShoppingBasketDao shoppingBasketDao();
}
