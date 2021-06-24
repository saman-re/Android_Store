package com.example.ap_project.content.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.ap_project.R;

import org.jetbrains.annotations.NotNull;

public class RegisterFragment extends Fragment {

    EditText userNameEditText, passwordEditText, phoneNumberEditText;
    SwitchCompat sellerOption;
    TextView loginTextView;
    Button singUpButton;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        userNameEditText = view.findViewById(R.id.edit_text_username);
        passwordEditText = view.findViewById(R.id.edit_text_password);
        phoneNumberEditText = view.findViewById(R.id.edit_text_phone_number);
        sellerOption =view.findViewById(R.id.seller_option_switch);
        loginTextView = view.findViewById(R.id.text_view_login);
        singUpButton = view.findViewById(R.id.sign_up_button);

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

        singUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName, password, phoneNumber;
                boolean seller;

                userName = userNameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                phoneNumber = phoneNumberEditText.getText().toString();
                seller =sellerOption.isChecked();

                if(userName.equals("") || password.equals("") || phoneNumber.equals("")){
                    Toast.makeText(getActivity(),"Please fill out all forms", Toast.LENGTH_SHORT).show();
                }else {
                    //handle connecting to data base
                    String a = String.format("%s,,%s,,%s %s", userName, password, phoneNumber,seller);
                    Toast.makeText(getActivity(), a, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

    }
}
