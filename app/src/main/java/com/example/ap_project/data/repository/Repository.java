package com.example.ap_project.data.repository;

import android.content.Context;

import com.example.ap_project.application.MyApplication;
import com.example.ap_project.data.entities.Product;
import com.example.ap_project.data.entities.User;

import java.util.List;

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
    public void getProducts(RepositoryCallback<List<Product>> callback){
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Product> products = localDataSource.getProducts();
                    callback.onComplete(new Result.Success<>(products));
                } catch (Exception e) {
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });
    }

    public void getASCPrice(RepositoryCallback<List<Product>> callback){
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Product> products = localDataSource.getASCPrice();
                    callback.onComplete(new Result.Success<>(products));
                } catch (Exception e) {
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });
    }
    public void getDESCPrice(RepositoryCallback<List<Product>> callback){
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Product> products = localDataSource.getDESCPrice();
                    callback.onComplete(new Result.Success<>(products));
                } catch (Exception e) {
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });
    }

    public void getProduct(int id,RepositoryCallback<Product> callback){
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    Product product =localDataSource.getProduct(id);
                    callback.onComplete(new Result.Success<>(product));
                }catch (Exception e){
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });
    }

    public void updateProduct(Product product,RepositoryCallback<Void> callback){
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    localDataSource.updateProduct(product);
                    callback.onComplete(new Result.Success<>(null));
                } catch (Exception e) {
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });
    }

    public void deleteProduct(Product product,RepositoryCallback<Void> callback){
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    localDataSource.deleteProduct(product);
                    callback.onComplete(new Result.Success<>(null));
                } catch (Exception e) {
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });
    }

    public void deleteUserProduct(String username, RepositoryCallback<Void> callback){
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    localDataSource.deleteUserProduct(username);
                    callback.onComplete(new Result.Success<>(null));
                }catch (Exception e){
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });
    }
}
