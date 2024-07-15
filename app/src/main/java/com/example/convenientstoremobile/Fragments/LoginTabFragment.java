package com.example.convenientstoremobile.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.convenientstoremobile.Enum.ProjEnum;
import com.example.convenientstoremobile.HomeActivity;
import com.example.convenientstoremobile.MainActivity;
import com.example.convenientstoremobile.R;
import com.example.convenientstoremobile.ResetPasswordActivity;
import com.example.convenientstoremobile.api.APIService;
import com.example.convenientstoremobile.model.Product;
import com.example.convenientstoremobile.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginTabFragment extends Fragment {
    TextView email;
    TextView pass;
    TextView forgetPass;
    Button login;
    float v = 0;

    Context context;

    String emailPatter = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        email = root.findViewById(R.id.editTextEmail);
        pass = root.findViewById(R.id.editTextPassword);
        forgetPass = root.findViewById(R.id.textViewForgetPassword);
        login = root.findViewById(R.id.buttonLogin);

        email.setTranslationX(800);
        pass.setTranslationX(800);
        forgetPass.setTranslationX(800);
        login.setTranslationX(800);

        email.setAlpha(v);
        pass.setAlpha(v);
        forgetPass.setAlpha(v);
        login.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformAuth();
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
        progressDialog = new ProgressDialog(context);
    }

    private void PerformAuth() {
        SharedPreferences sharedpreferences = context.getSharedPreferences(ProjEnum.MyPREFERENCES.getValue(), Context.MODE_PRIVATE);

        String emails = email.getText().toString().trim();
        String passwords = pass.getText().toString();

        if(passwords.isEmpty()){
            pass.setError("Enter Proper Password");
        }else{
            progressDialog.setMessage("Please Wait While Login");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            APIService.apiService.getAllUser("name eq '" + emails + "' and password eq '" + passwords + "'")
                    .enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                            List<User> user = response.body();
                            progressDialog.dismiss();
                            if(user != null && !user.isEmpty()){
                                Toast.makeText(context, "Log In Successful, Welcome "+user.get(0).getName(), Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putInt("user", user.get(0).getId());
                                editor.commit();

                                Gson gson = new Gson();
                                editor.putString("userbody", gson.toJson(user.get(0)));
                                editor.commit();

                                sendUserToNextActivity();
                            }
                            else{
                                Toast.makeText(context, "Log In Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(context, " "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void sendUserToNextActivity(){
//        Intent intent = new Intent(context, HomeActivity.class);
        Intent intent = new Intent(context, HomeActivity.class);
        startActivity(intent);
    }
}
