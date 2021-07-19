package com.example.ap_project.content.admin.products;

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

public class AdminProductsFragment extends Fragment {

    SearchView searchView;
    Button ascBtn,descBtn;
    FloatingActionButton goToUser;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_product_fragment, container, false);

        searchView = getActivity().findViewById(R.id.admin_toolbar_search_view);
        searchView.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        ascBtn = view.findViewById(R.id.ascending_sort_button);
        descBtn =view.findViewById(R.id.descending_sort_button);

        goToUser =view.findViewById(R.id.go_to_users_btn);

        Repository repository = Repository.getInstance(view.getContext());

        RepositoryCallback getProductCallback = new RepositoryCallback() {
            @Override
            public void onComplete(Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result instanceof Result.Success) {

                            List<Product> products = (List<Product>) ((Result.Success<?>) result).data;
                            final AdminProductAdaptor[] productAdapter = {new AdminProductAdaptor(products)};

                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {

                                    productAdapter[0] = new AdminProductAdaptor(products,newText);
                                    recyclerView.setAdapter(productAdapter[0]);
                                    return false;
                                }
                            });

                            recyclerView.setAdapter(productAdapter[0]);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                        } else if (result instanceof Result.Error) {

                            Toast.makeText(getContext(), "an error occurred", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        };

        repository.getProducts(getProductCallback);

        goToUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_adminProductsFragment4_to_adminUserFragment5);
            }
        });

        int initialColor=Color.parseColor("#344955");
        ascBtn.setBackgroundColor(initialColor);
        descBtn.setBackgroundColor(initialColor);

        ascBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                repository.getASCPrice(getProductCallback);
                descBtn.setBackgroundColor(Color.GRAY);
                ascBtn.setBackgroundColor(initialColor);
            }
        });

        descBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.getDESCPrice(getProductCallback);
                ascBtn.setBackgroundColor(Color.GRAY);
                descBtn.setBackgroundColor(initialColor);
            }
        });

        return view;

    }
}
