package com.example.ap_project.content.admin.users;

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
import com.example.ap_project.activities.AdminActivity;
//import com.example.ap_project.data.entities.Product;
import com.example.ap_project.data.entities.User;
import com.example.ap_project.data.repository.Repository;
import com.example.ap_project.data.repository.RepositoryCallback;
import com.example.ap_project.data.repository.Result;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdminUserAdaptor extends RecyclerView.Adapter<AdminUserAdaptor.ProductViewHolder> {

    private List<User> users;

    public AdminUserAdaptor(List<User> users) {
        this.users = users;
    }

    public AdminUserAdaptor(List<User> users, String filter) {
        if (filter.equals("")) {
            this.users = users;
        } else {
            List<User> filteredProduct = new ArrayList<>();
            for (User user : users) {
                if (user.username.startsWith(filter)) {
                    filteredProduct.add(user);
                }
            }
            this.users = filteredProduct;
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
    public void onBindViewHolder(@NonNull @NotNull AdminUserAdaptor.ProductViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

//        User currentUser;

        TextView title, owner, sellerState, phoneNumber;
        ImageView UserImage;

        AppCompatImageButton UserDeleteBtn;

        View view;
        Repository repository;
        RepositoryCallback deleteUserCallback;

        public ProductViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_view_title);
            owner = itemView.findViewById(R.id.card_view_owner);
            sellerState = itemView.findViewById(R.id.card_view_price);
            phoneNumber = itemView.findViewById(R.id.card_view_phone_number);
            UserImage = itemView.findViewById(R.id.card_view_product_image);
            UserDeleteBtn = itemView.findViewById(R.id.card_delete_button);


            view = itemView;
            repository = Repository.getInstance(itemView.getContext());

            deleteUserCallback = new RepositoryCallback() {
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

        public void bind(User user) {
            title.setText(user.username);
            owner.setText("username: " + user.username);
            sellerState.setText("seller: " + String.valueOf(user.seller));
            phoneNumber.setText(user.phoneNumber);
            phoneNumber.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            Picasso.get().load(Uri.parse(user.imagePath))
                    .placeholder(R.drawable.card_image)
                    .error(R.drawable.card_image)
                    .fit().into(UserImage);

            UserDeleteBtn.setVisibility(View.VISIBLE);

            UserDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());

                    //title
                    alertDialogBuilder.setTitle("Are u sure??");
                    //message
                    alertDialogBuilder.setMessage("u want to delete this user?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    repository.deleteUser(user.username,deleteUserCallback);
                                    repository.deleteUserProduct(user.username, deleteUserCallback);
                                    CardView cardView = view.findViewById(R.id.card_view);
                                    view.setVisibility(View.GONE);
                                    Intent intent =new Intent(view.getContext(), AdminActivity.class);
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