package com.example.ap_project.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private static User user;
    final int GALLERY_REQUEST = 500;
    CircleImageView imageView;
    Uri selectedImage;
    Toolbar toolbar;

    RepositoryCallback updateUserCallback;
    Repository repository;

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
        imageView = headerView.findViewById(R.id.profile_image);
        View navActionView = navigationView.getMenu().getItem(1).getSubMenu().getItem(0).getActionView();
        AppCompatButton logoutBtn, delAccountBtn;
        logoutBtn = navActionView.findViewById(R.id.nav_logout_btn);
        delAccountBtn = navActionView.findViewById(R.id.nav_del_account_btn);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }

        RepositoryCallback getUserCallback = new RepositoryCallback() {
            @Override
            public void onComplete(Result result) {
                if (result instanceof Result.Success) {
                    user = (User) ((Result.Success<?>) result).data;
//                    Toast.makeText(getApplicationContext(),user.toString(),Toast.LENGTH_SHORT).show();
                    Log.d("TAGE", user.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("TAGmn", user.imagePath);
                            if (!user.imagePath.equals("")) {
                                Picasso.get().load(Uri.parse(user.imagePath)).fit().into(imageView);
                            }
                            userName.setText(user.username);
                            phoneNumber.setText(user.phoneNumber);
                            if (user.seller) {
                                navigationView.getMenu().getItem(0).getSubMenu().getItem(0).setTitle("product count");
                                navigationView.getMenu().getItem(0).getSubMenu().getItem(1).setTitle("account type: seller");
                            } else {
                                navigationView.getMenu().getItem(0).getSubMenu().getItem(0).setVisible(false);
                                navigationView.getMenu().getItem(0).getSubMenu().getItem(1).setTitle("account type: customer");
                            }
                        }
                    });
                } else if (result instanceof Result.Error) {
//                    Toast.makeText(getApplicationContext(), "Error happened", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        RepositoryCallback deleteUserProduct = new RepositoryCallback() {
            @Override
            public void onComplete(Result result) {
                if (result instanceof Result.Success) {

                } else if (result instanceof Result.Error) {

                }
            }

        };

        RepositoryCallback deleteUserCallback = new RepositoryCallback() {
            @Override
            public void onComplete(Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result instanceof Result.Success) {

                            Toast.makeText(getApplicationContext(), "user deleted successfully", Toast.LENGTH_SHORT).show();
                            sharedUser.edit().clear().apply();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();

                        } else if (result instanceof Result.Error) {

                            Toast.makeText(getApplicationContext(), "cant delete user", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        };
        updateUserCallback = new RepositoryCallback() {
            @Override
            public void onComplete(Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result instanceof Result.Success) {

                            Toast.makeText(getApplicationContext(), "user updated successfully", Toast.LENGTH_SHORT).show();

                        } else if (result instanceof Result.Error) {

                            Toast.makeText(getApplicationContext(), "cant update user", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        };

        repository = Repository.getInstance(HomeActivity.this);
        repository.getUser(username, getUserCallback);

//        Picasso.get().load(Uri.parse("content://media/external/images/media/5073")).into(imageView);
        //set click listener for image view must be handle for getting image
        imageView.setClickable(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //start from here
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                mStartForResult.launch(photoPickerIntent);
//                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

//                Log.d("TAG234", user.toString()+"/"+selectedImage);
//                repository.updateUser(user.username,user.password,user.phoneNumber,selectedImage.toString(),updateUserCallback);
            }
        });


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);

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
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);

                //title
                alertDialogBuilder.setTitle("Are u sure??");
                //message
                alertDialogBuilder.setMessage("your account and all of your product will delete permanently")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                repository.deleteUser(username, deleteUserCallback);
                                repository.deleteUserProduct(username,deleteUserProduct);
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

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
//        BottomNavigationView bottomNav =findViewById(R.id.bottom_navigation);
//        NavigationUI.setupWithNavController(bottomNav , navController);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setOpenableLayout(drawerLayout)
                .build();

        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

//                    Toast.makeText(getActivity(),Activity.DEFAULT_KEYS_DISABLE,Toast.LENGTH_SHORT).show();
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        selectedImage = result.getData().getData();

                        Picasso.get().load(selectedImage).into(imageView);
                        Log.d("TAGmn'", selectedImage.toString());
                        repository.updateUser(user.username, user.password, user.phoneNumber, selectedImage.toString(), updateUserCallback);
                    }
                }
            });


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK)
//            switch (requestCode) {
//                case GALLERY_REQUEST:
//                    selectedImage = data.getData();
//
//                    Picasso.get().load(selectedImage).fit().into(imageView);
//                    Log.d("TAGmn'", selectedImage.toString());
//                    repository.updateUser(user.username, user.password, user.phoneNumber, selectedImage.toString(), updateUserCallback);
//
//                    break;
//            }
//    }

    public static User getUser() {
        return user;
    }
}
