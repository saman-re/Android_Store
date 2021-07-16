package com.example.ap_project.content.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap_project.R;
import com.example.ap_project.data.entities.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ShowProductsFragment extends Fragment {

    FloatingActionButton addProductBtn;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_products, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        List<Product> products =new ArrayList<Product>();
        for (int i = 0; i < 20; i++) {

            products.add(new Product("salam",100,"","saman","0990"));

        }

        ProductAdapter productAdapter = new ProductAdapter(products);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addProductBtn = view.findViewById(R.id.add_product_btn);
        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_showProductsFragment_to_addProductFragment);
            }
        });

        return view;

    }
}
