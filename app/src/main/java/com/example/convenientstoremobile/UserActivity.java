package com.example.convenientstoremobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.convenientstoremobile.Enum.ProjEnum;

public class UserActivity extends AppCompatActivity {
    TextView editBtn , name;
    ImageView imgProfile, imageView2;
    Button btnLogout;
    ProgressDialog progressDialog;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedpreferences = getSharedPreferences(ProjEnum.MyPREFERENCES.getValue(), Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        editBtn = findViewById(R.id.txtEditProfile);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.parseColor("#f1f3f5"));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.setBackgroundColor(Color.WHITE);
                    }
                }, 50);
                Intent intent = new Intent(UserActivity.this, ProfileDetail.class);
                startActivity(intent);
                finish();
            }
        });

        name = findViewById(R.id.tvName);
        imgProfile = findViewById(R.id.imgProfile);
        btnLogout = findViewById(R.id.buttonLogout);
        imageView2 = findViewById(R.id.imageView2);
        progressDialog = new ProgressDialog(this);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Logging You Out");
                progressDialog.setTitle("Log Out");
                progressDialog.show();

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove("user");
                editor.apply();

                progressDialog.dismiss();
                Toast.makeText(context, "Good Bye", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }}
