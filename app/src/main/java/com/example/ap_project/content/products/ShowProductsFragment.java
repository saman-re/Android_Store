package com.example.ap_project.content.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.List;

public class ShowProductsFragment extends Fragment {

    FloatingActionButton addProductBtn;
    SearchView searchView;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_products, container, false);

        searchView = getActivity().findViewById(R.id.toolbar_search_view);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        Repository repository = Repository.getInstance(view.getContext());

        RepositoryCallback getProductCallback = new RepositoryCallback() {
            @Override
            public void onComplete(Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result instanceof Result.Success) {

                            List<Product> products = (List<Product>) ((Result.Success<?>) result).data;
                            final ProductAdapter[] productAdapter = {new ProductAdapter(products)};

                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {

                                    productAdapter[0] = new ProductAdapter(products,newText);
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
        addProductBtn = view.findViewById(R.id.add_product_btn);

        User user = null;
        while (user == null) {
            user = HomeActivity.getUser();
        }

        if (user.seller) {
            addProductBtn.setVisibility(View.VISIBLE);
        }

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_showProductsFragment_to_addProductFragment);
            }
        });

        return view;

    }
}
