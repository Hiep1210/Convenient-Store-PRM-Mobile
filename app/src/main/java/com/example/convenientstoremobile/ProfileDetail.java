package com.example.convenientstoremobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.convenientstoremobile.Enum.ProjEnum;
import com.example.convenientstoremobile.api.APIService;
import com.example.convenientstoremobile.model.Product;
import com.example.convenientstoremobile.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileDetail extends AppCompatActivity {

    TextView tvName;
    ImageView backBtn,imgProfile,imgSave,btnChangeImage;
    EditText edtName,edtAddress,edtPhone;
    ProgressDialog progressDialog;
    Uri imgUri;
    SharedPreferences sharedpreferences;
    Context context;
    User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpreferences = getSharedPreferences(ProjEnum.MyPREFERENCES.getValue(), Context.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);


        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhone = findViewById(R.id.edtPhone);
        imgSave = findViewById(R.id.imgSave);
        tvName = findViewById(R.id.textView10);
        btnChangeImage = findViewById(R.id.changeImg);
        imgProfile= findViewById(R.id.imageView9);

        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent,"Selected picture"),200);
            }
        });

        EditText[] edts = {edtAddress,edtName,edtPhone};
        for (EditText item:edts   ) {
            item.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    imgSave.setImageResource(R.drawable.check_icon_blue);
                    return false;
                }
            });
        }

        progressDialog = new ProgressDialog(this);

        backBtn = findViewById(R.id.imgBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileDetail.this,UserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        getUserProfile();

        //update user profile
        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = sharedpreferences.getInt("user",0);
                User updateUser = user;
                updateUser.setName(edtName.getText().toString());
                updateUser.setAddress(edtAddress.getText().toString());
                updateUser.setPhone(edtPhone.getText().toString());

                progressDialog.show();

                APIService.apiService.updateUser(id,updateUser)
                        .enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                progressDialog.dismiss();
                                Toast.makeText(ProfileDetail.this, "Update profile Successful", Toast.LENGTH_SHORT).show();
                                getUserProfile();
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(context, " "+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
    private User getUserProfile() {
        progressDialog.show();

        int id = sharedpreferences.getInt("user",0);
        progressDialog.show();

        APIService.apiService.getUserById(id)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        user = response.body();
                        if(user != null){
                            tvName.setText(user.getName());
                            edtName.setText(user.getName());
                            edtAddress.setText(user.getAddress());
                            edtPhone.setText(user.getPhone());
                            Glide.with(ProfileDetail.this).load(R.drawable.avatar_default).into(imgProfile);
                            progressDialog.dismiss();
                        }
                        else{
                            Toast.makeText(context, "Retrieved Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(context, " "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        return user;
    }


//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//
//            // compare the resultCode with the
//            // SELECT_PICTURE constant
//            if (requestCode == 200) {
//                // Get the url of the image from data
//                Uri selectedImageUri = data.getData();
//                imgUri = selectedImageUri;
//                if (null != selectedImageUri) {
//
////
//                    Glide.with(ProfileDetail.this).load(selectedImageUri).error(R.drawable.avatar_default).into(imgProfile);
//                }
//            }
//        }
//    }
}