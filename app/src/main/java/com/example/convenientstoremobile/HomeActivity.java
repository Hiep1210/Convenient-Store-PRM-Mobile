package com.example.convenientstoremobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.convenientstoremobile.Enum.ProjEnum;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class HomeActivity extends AppCompatActivity {
    TextView tvGreet; ImageView avatar;
    Button profileBtn, orderDetailBtn, foodOrderingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        bindElements();
        bindElementToActivity();
        SharedPreferences sharedpreferences = getSharedPreferences(ProjEnum.MyPREFERENCES.getValue(), Context.MODE_PRIVATE);
        int id = sharedpreferences.getInt("user", 0);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(HomeActivity.this);
        if(id == 0){
            id = Integer.parseInt(googleSignInAccount.getId());
        }




        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,UserActivity.class );
                intent.putExtra("test", "test");
                startActivity(intent);
            }
        });

        orderDetailBtn = findViewById(R.id.btn_OrderDetail);
        orderDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,OrderActivity.class );
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void bindElements(){
        tvGreet = findViewById(R.id.tvGreet);
        avatar = findViewById(R.id.avatar);
        profileBtn = findViewById(R.id.btn_Profile);
        orderDetailBtn = findViewById(R.id.btn_OrderDetail);
        foodOrderingBtn = findViewById(R.id.btn_foodOrdering);
    }

    public void bindElementToActivity(){
        foodOrderingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(HomeActivity.this,ProductActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ProfileDetail.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }
}