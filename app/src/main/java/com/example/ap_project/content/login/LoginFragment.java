package com.example.ap_project.content.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.example.ap_project.activities.AdminActivity;
import com.example.ap_project.activities.HomeActivity;
import com.example.ap_project.activities.LoginActivity;
import com.example.ap_project.data.entities.User;
import com.example.ap_project.data.repository.Repository;
import com.example.ap_project.data.repository.RepositoryCallback;
import com.example.ap_project.data.repository.Result;

public class LoginFragment extends Fragment {

    EditText userNameEditText, passwordEditText;
    TextView registerTextView;
    Button signInButton;
    TextView errTextView;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        userNameEditText = view.findViewById(R.id.edit_text_username);
        passwordEditText = view.findViewById(R.id.edit_text_password);
        registerTextView = view.findViewById(R.id.text_view_register);
        signInButton = view.findViewById(R.id.sign_in_button);
        errTextView = view.findViewById(R.id.error_text_view);

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

                RepositoryCallback callback = new RepositoryCallback<User>() {
                    @Override
                    public void onComplete(Result result) {
                        if (result instanceof Result.Success) {
                            User user = ((Result.Success<User>) result).data;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (user == null) {

                                        errTextView.setText("username not found");
                                        errTextView.setVisibility(View.VISIBLE);

                                    } else if (user.password.equals(password)) {

                                        Toast.makeText(getActivity().getApplicationContext(),"Welcome "+user.username,Toast.LENGTH_SHORT).show();

                                        Context context=getActivity();
                                        SharedPreferences shareUser =context.getSharedPreferences("profile_username", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor=shareUser.edit();
                                        editor.putString("username",userName);
                                        editor.apply();
                                        Intent intent = new Intent(getContext(), HomeActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();

                                    } else {

                                        errTextView.setText("wrong password");
                                        errTextView.setVisibility(View.VISIBLE);

                                    }
                                }
                            });
                        } else if (result instanceof Result.Error) {

                            String Err = ((Result.Error<Exception>) result).exception.getMessage();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    errTextView.setText("error in getting data happened");

                                }
                            });
                            Log.d("Fetching Data", Err);
                        }
                    }
                };

                if (userName.equals("") || password.equals("")) {

                    Toast.makeText(getActivity(), "Please fill out all forms", Toast.LENGTH_SHORT).show();

                }else if(userName.equals("admin") && password.equals("admin")){

                    Toast.makeText(getActivity(), "welcome Admin", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(getActivity(), AdminActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
                else {
                    //handle connecting to data base
                    Repository.getInstance(getContext()).getUser(userName, callback);

                }
            }
        });

        return view;
    }
}
