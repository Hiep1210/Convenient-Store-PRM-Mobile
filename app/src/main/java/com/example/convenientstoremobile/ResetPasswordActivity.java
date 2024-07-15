package com.example.convenientstoremobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.convenientstoremobile.Enum.ProjEnum;
import com.example.convenientstoremobile.api.APIService;
import com.example.convenientstoremobile.common.JavaMailAPI;
import com.example.convenientstoremobile.common.PasswordGenerator;
import com.example.convenientstoremobile.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {
    TextView email, name;
    Button next;
    float v = 0;
    ImageView backButton;

    String emailPatter = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    Context context;
    SharedPreferences sharedpreferences;
    User foundUser =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email = findViewById(R.id.editTextEmailR);
        name = findViewById(R.id.editUserName);
        next = findViewById(R.id.buttonNext);
        backButton = findViewById(R.id.back_button);
        progressDialog = new ProgressDialog(this);

        email.setTranslationX(800);
        name.setTranslationX(800);
        next.setTranslationX(800);

        email.setAlpha(v);
        next.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        name.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        next.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformResetPass();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void PerformResetPass(){
        String emails = email.getText().toString();

        if(!emails.matches(emailPatter)){
            email.setError("Enter Correct Email");
        }else{
            sharedpreferences = getSharedPreferences(ProjEnum.MyPREFERENCES.getValue(), Context.MODE_PRIVATE);

            progressDialog.setMessage("Please Wait While Reset Password");
            progressDialog.setTitle("Reset Password");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            String newPassword = PasswordGenerator.generateStrongPassword();
            String subject = "Your New Password";
            String message = "Your new password is " + newPassword;

            JavaMailAPI javaMailAPI = new JavaMailAPI(this, emails, subject, message);

            javaMailAPI.execute();

            updateUser(newPassword);
        }
    }

    private void updateUser(String newPassword) {
        String username = name.getText().toString();

        APIService.apiService.getAllUser("name eq '"+username+"'")
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        List<User> u = response.body();
                        if(u == null){
                            Toast.makeText(ResetPasswordActivity.this, "cant found user with this name", Toast.LENGTH_SHORT).show();
                            call.cancel();
                        }
                        else {
                            foundUser = u.get(0);

                            Gson gson = new Gson();
                            User user = foundUser;
                            user.setPassword(newPassword);

                            APIService.apiService.updateUser(user.getId(),user)
                                    .enqueue(new Callback<User>() {
                                        @Override
                                        public void onResponse(Call<User> call, Response<User> response) {
                                            sharedpreferences.edit().putString("userbody", gson.toJson(response.body())).apply();
                                            progressDialog.dismiss();
                                            sendUserToNextActivity();
                                        }

                                        @Override
                                        public void onFailure(Call<User> call, Throwable t) {
                                            Toast.makeText(context, " "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Toast.makeText(context, " "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendUserToNextActivity(){
        Intent intent = new Intent(ResetPasswordActivity.this, ResetNotification.class);
        startActivity(intent);
    }
//    private void getUser(){
//        String username = name.getText().toString();
//
//        APIService.apiService.getAllUser("name eq '"+username+"'")
//                .enqueue(new Callback<List<User>>() {
//                    @Override
//                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                        List<User> u = response.body();
//                        if(u == null){
//                            Toast.makeText(ResetPasswordActivity.this, "cant found user with this name", Toast.LENGTH_SHORT).show();
//                            call.cancel();
//                        }
//                        else {
//                            foundUser = u.get(0);
//                            foundUser
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<User>> call, Throwable t) {
//                        Toast.makeText(context, " "+t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
}