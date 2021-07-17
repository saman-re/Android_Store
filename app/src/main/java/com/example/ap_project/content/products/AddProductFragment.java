package com.example.ap_project.content.products;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.ap_project.R;
import com.example.ap_project.activities.HomeActivity;
import com.example.ap_project.data.entities.User;
import com.example.ap_project.data.repository.Repository;
import com.example.ap_project.data.repository.RepositoryCallback;
import com.example.ap_project.data.repository.Result;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class AddProductFragment extends Fragment {

    User user;
    EditText productTitleEditText, productPriceEditText;
    AppCompatImageButton productImageBtn;
    AppCompatButton saveBtn;

    Uri selectedImage;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        user= HomeActivity.getUser();
        productTitleEditText =view.findViewById(R.id.edit_text_product_title);
        productPriceEditText =view.findViewById(R.id.edit_text_product_price);
        productImageBtn =view.findViewById(R.id.add_product_photo);
        saveBtn =view.findViewById(R.id.button_save_product);

        final String[] productTitle = new String[1];
        final String[] productPrice = new String[1];

        Repository repository = Repository.getInstance(view.getContext());

        RepositoryCallback addProductCallback = new RepositoryCallback() {
            @Override
            public void onComplete(Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result instanceof Result.Success) {

                            Toast.makeText(getActivity(), "Product added successfully", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigate(R.id.action_addProductFragment_to_showProductsFragment);

                        } else if (result instanceof Result.Error) {

                            Toast.makeText(getContext(), "an error occurred", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        };

        productImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                mStartForResult.launch(photoPickerIntent);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productTitle[0] = productTitleEditText.getText().toString();
                productPrice[0] = productPriceEditText.getText().toString();
                Log.d("call", String.valueOf(productPrice[0]));
                if (productTitle[0].equals("")){
                    Toast.makeText(v.getContext(),"enter the title",Toast.LENGTH_SHORT).show();
                }else if (productPrice[0].equals("")){
                    Toast.makeText(v.getContext(),"enter the price",Toast.LENGTH_SHORT).show();
                }else if (selectedImage == null){
                    Toast.makeText(v.getContext(),"select a image for your product",Toast.LENGTH_SHORT).show();
                }else{
                    repository.insertProduct(productTitle[0],Integer.parseInt(productPrice[0]), user.username, user.phoneNumber,selectedImage.toString(),addProductCallback);
                }
            }
        });

        return view;

    }
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

//                    Toast.makeText(getActivity(),Activity.DEFAULT_KEYS_DISABLE,Toast.LENGTH_SHORT).show();
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        selectedImage = result.getData().getData();

//                        Picasso.get().load(selectedImage).into(imageView);
//                        Log.d("TAGmn'", selectedImage.toString());
//                        repository.updateUser(user.username, user.password, user.phoneNumber, selectedImage.toString(), updateUserCallback);
                    }
                }
            });


}
