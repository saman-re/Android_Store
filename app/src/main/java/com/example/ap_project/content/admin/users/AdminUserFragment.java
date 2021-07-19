package com.example.ap_project.content.admin.users;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap_project.R;
import com.example.ap_project.activities.HomeActivity;
import com.example.ap_project.data.entities.Product;
import com.example.ap_project.data.entities.User;
import com.example.ap_project.data.repository.Repository;
import com.example.ap_project.data.repository.RepositoryCallback;
import com.example.ap_project.data.repository.Result;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdminUserFragment extends Fragment {

    SearchView searchView;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_user_fragment, container, false);

        searchView = getActivity().findViewById(R.id.admin_toolbar_search_view);
        searchView.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        Repository repository = Repository.getInstance(view.getContext());

        RepositoryCallback getProductCallback = new RepositoryCallback() {
            @Override
            public void onComplete(Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result instanceof Result.Success) {

                            List<User> users = (List<User>) ((Result.Success<?>) result).data;
                            final AdminUserAdaptor[] userAdapter = {new AdminUserAdaptor(users)};

                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {

                                    userAdapter[0] = new AdminUserAdaptor(users,newText);
                                    recyclerView.setAdapter(userAdapter[0]);
                                    return false;
                                }
                            });

                            recyclerView.setAdapter(userAdapter[0]);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                        } else if (result instanceof Result.Error) {

                            Toast.makeText(getContext(), "an error occurred", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        };

        repository.getUsers(getProductCallback);


        return view;

    }
}