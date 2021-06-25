package com.example.ap_project.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ap_project.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedUser = getSharedPreferences("profile_username", Context.MODE_PRIVATE);
//        sharedUser.edit().clear().apply();
        String username = sharedUser.getString("username", "");
        Log.d("TAG", sharedUser.getString("username", ""));

        if (username.length()!= 0){

            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }

    }
}
