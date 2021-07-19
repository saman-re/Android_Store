package com.example.ap_project.content.admin.products;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap_project.R;
import com.example.ap_project.activities.HomeActivity;
import com.example.ap_project.data.entities.Product;
import com.example.ap_project.data.repository.Repository;
import com.example.ap_project.data.repository.RepositoryCallback;
import com.example.ap_project.data.repository.Result;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdminProductAdaptor extends RecyclerView.Adapter<AdminProductAdaptor.ProductViewHolder> {

    private List<Product> products;

    public AdminProductAdaptor(List<Product> products) {
        this.products = products;
    }

    public AdminProductAdaptor(List<Product> products, String filter) {
        if (filter.equals("")) {
            this.products = products;
        } else {
            List<Product> filteredProduct = new ArrayList<>();
            for (Product product : products) {
                if (product.title.startsWith(filter)) {
                    filteredProduct.add(product);
                }
            }
            this.products = filteredProduct;
        }
    }

    @NonNull
    @NotNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdminProductAdaptor.ProductViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

//        User currentUser;

        TextView title, owner, price, phoneNumber;
        ImageView productImage;

        AppCompatImageButton productEditBtn, productDeleteBtn;

        View view;
        Repository repository;
        RepositoryCallback deleteProductCallback;

        public ProductViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_view_title);
            owner = itemView.findViewById(R.id.card_view_owner);
            price = itemView.findViewById(R.id.card_view_price);
            phoneNumber = itemView.findViewById(R.id.card_view_phone_number);
            productImage = itemView.findViewById(R.id.card_view_product_image);
            productEditBtn = itemView.findViewById(R.id.card_edit_button);
            productDeleteBtn = itemView.findViewById(R.id.card_delete_button);


            view = itemView;
            repository = Repository.getInstance(itemView.getContext());

            deleteProductCallback = new RepositoryCallback() {
                @Override
                public void onComplete(Result result) {

                    if (result instanceof Result.Success) {

                        Toast.makeText(itemView.getContext(), "deleted successfully", Toast.LENGTH_SHORT).show();

                    } else if (result instanceof Result.Error) {

                        Toast.makeText(itemView.getContext(), "an error occurred", Toast.LENGTH_SHORT).show();

                    }
                }
            };


            phoneNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    String number = phoneNumber.getText().toString();
                    intent.setData(Uri.parse("tel:" + number));

                    if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions((Activity) v.getContext(), new String[]{Manifest.permission.CALL_PHONE}, 1);

                    } else {

                        v.getContext().startActivity(intent);

                    }
                }
            });

        }

        public void bind(Product product) {
            title.setText(product.title);
            owner.setText("owner: " + product.username);
            price.setText("price: " + String.valueOf(product.price) + " $");
            phoneNumber.setText(product.phoneNumber);
            phoneNumber.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            Picasso.get().load(Uri.parse(product.imagePath))
                    .placeholder(R.drawable.card_image)
                    .error(R.drawable.card_image)
                    .fit().into(productImage);

//            if (currentUser.username.equals(product.username)) {
//                productEditBtn.setVisibility(View.VISIBLE);
                productDeleteBtn.setVisibility(View.VISIBLE);
//            } else {
//                productEditBtn.setVisibility(View.GONE);
//                productDeleteBtn.setVisibility(View.GONE);
//            }

//            productEditBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    SharedPreferences shareUser = v.getContext().getSharedPreferences("productEdit", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = shareUser.edit();
//                    editor.putInt("product_id", product.id);
//                    editor.apply();
//                    Navigation.findNavController(view).navigate(R.id.action_showProductsFragment_to_addProductFragment);
//                }
//            });

            productDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());

                    //title
                    alertDialogBuilder.setTitle("Are u sure??");
                    //message
                    alertDialogBuilder.setMessage("u want to delete this product?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    repository.deleteProduct(product, deleteProductCallback);
                                    CardView cardView = view.findViewById(R.id.card_view);
                                    view.setVisibility(View.GONE);
                                    Intent intent =new Intent(view.getContext(),HomeActivity.class);
                                    view.getContext().startActivity(intent);
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


        }
    }
}