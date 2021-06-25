package com.example.ap_project.data.repository;

public interface RepositoryCallback<T> {
    void onComplete(Result<T> result);
}
