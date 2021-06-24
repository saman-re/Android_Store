package com.example.ap_project.data.repository;

public class Repository {

    private static Repository repository;

    private LocalDataSource localDataSource;

    private Repository() {

    }

    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }

        return repository;
    }
}
