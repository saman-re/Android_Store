package com.example.ap_project.data.repository;

import android.content.Context;

import com.example.ap_project.application.MyApplication;
import com.example.ap_project.data.entities.User;

public class Repository {

    private static Repository repository;

    private final LocalDataSource localDataSource;

    private Repository(Context context) {
        localDataSource = new LocalDataSource(context);
    }

    public static Repository getInstance(Context context) {
        if (repository == null) {
            repository = new Repository(context);
        }
        return repository;
    }

    public void getUser(String username, RepositoryCallback<User> callback) {
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    User user = localDataSource.getUser(username);
                    callback.onComplete(new Result.Success<>(user));
                } catch (Exception e) {
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });
    }

    public void insertUser(String username, String password, String phone, String imagePath, boolean seller, RepositoryCallback<User> callback) {
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    localDataSource.insertUser(username, password, phone, imagePath, seller);
                    callback.onComplete(new Result.Success<>(null));
                } catch (Exception e) {
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });
    }

    public void deleteUser(String username, RepositoryCallback<Void> callback) {
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    localDataSource.deleteUser(username);
                    callback.onComplete(new Result.Success<>(null));
                } catch (Exception e) {
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });
    }

    public void updateUser(String username, String password, String phoneNumber, String imagePath, RepositoryCallback<Void> callback) {
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    localDataSource.updateUser(username, password, phoneNumber, imagePath);
                    callback.onComplete(new Result.Success<>(null));
                } catch (Exception e) {
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });
    }

    public void insertProduct(String title,int price,String username,String phoneNumber,String imagePath,RepositoryCallback<Void> callback){
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    localDataSource.insertProduct(title, price, username, phoneNumber, imagePath);
                    callback.onComplete(new Result.Success<>(null));
                } catch (Exception e) {
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });
    }
}
