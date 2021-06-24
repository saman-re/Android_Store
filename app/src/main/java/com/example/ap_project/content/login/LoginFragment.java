package com.example.ap_project.content.login;

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
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.ap_project.R;

public class LoginFragment extends Fragment {

    EditText userNameEditText, passwordEditText;
    TextView registerTextView;
    Button signInButton;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        userNameEditText = view.findViewById(R.id.edit_text_username);
        passwordEditText = view.findViewById(R.id.edit_text_password);
        registerTextView = view.findViewById(R.id.text_view_register);
        signInButton = view.findViewById(R.id.sign_in_button);

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName, password;
                userName = userNameEditText.getText().toString();
                password = passwordEditText.getText().toString();

                if (userName.equals("") || password.equals("")) {
                    Toast.makeText(getActivity(), "Please fill out all forms", Toast.LENGTH_SHORT).show();
                } else {
                    //handle connecting to data base
                    String a = String.format("%s,,%s", userName, password);
                    Toast.makeText(getActivity(), a, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
