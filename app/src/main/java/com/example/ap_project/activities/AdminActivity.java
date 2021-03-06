package com.example.ap_project.activities;

import android.database.CursorJoiner;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ap_project.R;
import com.example.ap_project.data.entities.User;
import com.example.ap_project.data.repository.Repository;
import com.example.ap_project.data.repository.RepositoryCallback;
import com.example.ap_project.data.repository.Result;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView navigationView;
    MenuItem numProduct, sumPrices, numUsers, bestSeller;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toolbar =findViewById(R.id.my_admin_toolbar);
        navigationView = findViewById(R.id.admin_nav_drawer);
        numProduct = navigationView.getMenu().getItem(0).getSubMenu().getItem(0);
        sumPrices = navigationView.getMenu().getItem(0).getSubMenu().getItem(1);
        numUsers = navigationView.getMenu().getItem(1).getSubMenu().getItem(0);
        bestSeller = navigationView.getMenu().getItem(1).getSubMenu().getItem(1);

        Repository repository = Repository.getInstance(getApplicationContext());

        RepositoryCallback countProductCallBack = new RepositoryCallback() {
            @Override
            public void onComplete(Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result instanceof Result.Success) {

                            int data = ((Result.Success<Integer>) result).data;
                            String key = String.format("number of products : %d", data);
                            numProduct.setTitle(key);

                        } else if (result instanceof Result.Error) {

                        }
                    }
                });
            }
        };

        RepositoryCallback sumOfPricesCallBack = new RepositoryCallback() {
            @Override
            public void onComplete(Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result instanceof Result.Success) {

                            int data = ((Result.Success<Integer>) result).data;
                            String key = String.format("sum of product prices: %d", data);
                            sumPrices.setTitle(key);

                        } else if (result instanceof Result.Error) {

                        }
                    }
                });
            }
        };

        RepositoryCallback numberOfUserCallBack = new RepositoryCallback() {
            @Override
            public void onComplete(Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result instanceof Result.Success) {

                            int data = ((Result.Success<Integer>) result).data;
                            String key = String.format("number of users: %d", data);
                            numUsers.setTitle(key);

                        } else if (result instanceof Result.Error) {

                        }
                    }
                });
            }
        };
        RepositoryCallback bestSellerCallBack = new RepositoryCallback() {
            @Override
            public void onComplete(Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result instanceof Result.Success) {

                            User user = ((Result.Success<User>) result).data;
                            String key = String.format("number of users: %s", user.username);
                            bestSeller.setTitle(key);

                        } else if (result instanceof Result.Error) {

                        }
                    }
                });
            }
        };

        repository.countAllProduct(countProductCallBack);
        repository.sumPrices(sumOfPricesCallBack);
        repository.countUsers(numberOfUserCallBack);
        repository.getBestSeller(bestSellerCallBack);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setOpenableLayout(drawerLayout)
                .build();

        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

    }
}