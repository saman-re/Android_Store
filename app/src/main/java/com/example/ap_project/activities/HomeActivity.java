package com.example.ap_project.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ap_project.R;
import com.example.ap_project.data.entities.User;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String username;
        SharedPreferences sharedUser=getSharedPreferences("profile_username", Context.MODE_PRIVATE);
        username = sharedUser.getString("username","");
        TextView textView=findViewById(R.id.starter);
        textView.setText(username);
    }
}
