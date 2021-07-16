package com.example.ap_project.content.products;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap_project.R;
import com.example.ap_project.data.entities.Product;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;

    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @NotNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_product,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductAdapter.ProductViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView title,owner,price,phoneNumber;
        ImageView productImage;
        View view = itemView;

        public ProductViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_view_title);
            owner = itemView.findViewById(R.id.card_view_owner);
            price= itemView.findViewById(R.id.card_view_price);
            phoneNumber = itemView.findViewById(R.id.card_view_phone_number);
            productImage = itemView.findViewById(R.id.card_view_product_image);
        }

        public void bind(Product product){
            title.setText(product.title);
            owner.setText(product.username);
            price.setText(String.valueOf(product.price));
            phoneNumber.setText(product.phoneNumber);
//            Picasso.get().load(Uri.parse(product.imagePath)).fit().into(productImage);
        }
    }
}
