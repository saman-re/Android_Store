package com.example.ap_project.content.products;

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
import androidx.core.app.ActivityCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap_project.R;
import com.example.ap_project.activities.HomeActivity;
import com.example.ap_project.data.entities.Product;
import com.example.ap_project.data.entities.User;
import com.example.ap_project.data.repository.Repository;
import com.example.ap_project.data.repository.RepositoryCallback;
import com.example.ap_project.data.repository.Result;
import com.squareup.picasso.Picasso;

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
                .inflate(R.layout.layout_product, parent, false);
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

        User currentUser;

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

            currentUser = HomeActivity.getUser();

            view =itemView;
            repository = Repository.getInstance(itemView.getContext());

            deleteProductCallback = new RepositoryCallback() {
                @Override
                public void onComplete(Result result) {

                    if (result instanceof Result.Success) {

//                        Toast.makeText(itemView.getContext(), "deleted successfully", Toast.LENGTH_SHORT).show();

                    } else if (result instanceof Result.Error) {

//                        Toast.makeText(itemView.getContext(), "an error occurred", Toast.LENGTH_SHORT).show();

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

            if (currentUser.username.equals(product.username)) {
                productEditBtn.setVisibility(View.VISIBLE);
                productDeleteBtn.setVisibility(View.VISIBLE);
            } else {
                productEditBtn.setVisibility(View.GONE);
                productDeleteBtn.setVisibility(View.GONE);
            }

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

                                    repository.deleteProduct(product,deleteProductCallback);
                                    view.setVisibility(View.GONE);

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
