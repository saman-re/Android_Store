package com.example.ap_project.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.ap_project.R;
import com.example.ap_project.data.entities.User;
import com.example.ap_project.data.repository.Repository;
import com.example.ap_project.data.repository.RepositoryCallback;
import com.example.ap_project.data.repository.Result;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String username;
        SharedPreferences sharedUser = getSharedPreferences("profile_username", Context.MODE_PRIVATE);
//        sharedUser.edit().clear().apply();
        username = sharedUser.getString("username", "");

        NavigationView navigationView = findViewById(R.id.main_nav_drawer);
        View headerView = navigationView.getHeaderView(0);
        TextView userName = headerView.findViewById(R.id.text_view_username);
        TextView phoneNumber = headerView.findViewById(R.id.text_view_phone_number);
        CircleImageView imageView =headerView.findViewById(R.id.profile_image);
        View navActionView =  navigationView.getMenu().getItem(1).getSubMenu().getItem(0).getActionView();
        AppCompatButton logoutBtn,delAccountBtn;
        logoutBtn = navActionView.findViewById(R.id.nav_logout_btn);
        delAccountBtn = navActionView.findViewById(R.id.nav_del_account_btn);

        RepositoryCallback callback = new RepositoryCallback() {
            @Override
            public void onComplete(Result result) {
                if (result instanceof Result.Success) {
                    user = (User) ((Result.Success<?>) result).data;
//                    Toast.makeText(getApplicationContext(),user.toString(),Toast.LENGTH_SHORT).show();
                    Log.d("TAGE", user.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            userName.setText(user.username);
                            phoneNumber.setText(user.phoneNumber);
                            if(user.seller){
                                navigationView.getMenu().getItem(0).getSubMenu().getItem(0).setTitle("product count");
                                navigationView.getMenu().getItem(0).getSubMenu().getItem(1).setTitle("account type: seller");
                            }else{
                                navigationView.getMenu().getItem(0).getSubMenu().getItem(0).setVisible(false);
                                navigationView.getMenu().getItem(0).getSubMenu().getItem(1).setTitle("account type: customer");
                            }
                        }
                    });
                } else if (result instanceof Result.Error) {
                    Toast.makeText(getApplicationContext(), "Error happened", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        Repository.getInstance(HomeActivity.this).getUser(username, callback);
//        Log.d("menu", navigationView.getMenu().getItem(1).getSubMenu().getItem(0).getActionView().toString());
        //set click listener for image view must be handle for getting image
        imageView.setClickable(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"hello",Toast.LENGTH_SHORT).show();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder =new AlertDialog.Builder(HomeActivity.this);

                //title
                alertDialogBuilder.setTitle("want to logout??");
                //message
                alertDialogBuilder.setMessage("")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sharedUser.edit().clear().apply();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        delAccountBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder =new AlertDialog.Builder(HomeActivity.this);

                //title
                alertDialogBuilder.setTitle("Are u sure??");
                //message
                alertDialogBuilder.setMessage("your account and all of your product will delete permanently")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sharedUser.edit().clear().apply();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

    }

    public User getUser() {
        return user;
    }
}
