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
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.convenientstoremobile.Enum.ProjEnum;
import com.example.convenientstoremobile.HomeActivity;
import com.example.convenientstoremobile.MainActivity;
import com.example.convenientstoremobile.R;
import com.example.convenientstoremobile.api.APIService;
import com.example.convenientstoremobile.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupTabFragment extends Fragment {
    EditText userName, pass, address, phone;
    EditText confirmPass;
    Button signup;
    float v = 0;
    Context context;

    String emailPatter = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        userName = root.findViewById(R.id.editTextUserName);
        address = root.findViewById(R.id.editTextAddress);
        phone = root.findViewById(R.id.editTextPhone);
        pass = root.findViewById(R.id.editTextPasswordS);
        confirmPass = root.findViewById(R.id.editTextConfirmPassword);
        signup = root.findViewById(R.id.buttonSignup);
//        mAuth = FirebaseAuth.getInstance();
//        mUser = mAuth.getCurrentUser();

        userName.setTranslationX(800);
        address.setTranslationX(800);
        phone.setTranslationX(800);
        pass.setTranslationX(800);
        confirmPass.setTranslationX(800);
        signup.setTranslationX(800);

        userName.setAlpha(v);
        address.setAlpha(v);
        phone.setAlpha(v);
        pass.setAlpha(v);
        confirmPass.setAlpha(v);
        signup.setAlpha(v);

        userName.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        address.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        phone.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        confirmPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();
        signup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1300).start();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformAuth();
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
        String userNames = userName.getText().toString().trim();
        String addresses = address.getText().toString();
        String phones = phone.getText().toString();
        String passwords = pass.getText().toString();
        String confirmPasswords = confirmPass.getText().toString();

        if (passwords.isEmpty()) {
            pass.setError("Enter Proper Password");
        } else if (!passwords.equals(confirmPasswords)) {
            confirmPass.setError("Password Does Not Match");
        } else {
            progressDialog.setMessage("Please Wait While Signup");
            progressDialog.setTitle("Signup");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            SharedPreferences sharedpreferences = context.getSharedPreferences(ProjEnum.MyPREFERENCES.getValue(), Context.MODE_PRIVATE);

            User user = new User(userNames, addresses, phones, passwords);

            APIService.apiService.addUser(user)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            User user = response.body();
                            progressDialog.dismiss();
                            if(user != null){
                                Toast.makeText(context, "Sign Up Successful, Welcome "+user.getName(), Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putInt("user", user.getId());
                                editor.commit();

                                sendUserToNextActivity();
                            }
                            else{
                                Toast.makeText(context, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(context, " "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

//            mAuth.createUserWithEmailAndPassword(emails, passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    String[] parts = emails.split("@");
//
//                    if (task.isSuccessful()) {
//                        User user = new User(parts[0], emails, "", "");
//                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    progressDialog.dismiss();
//                                    sendUserToNextActivity();
//                                }
//                            }
//                        });
//                    } else {
//                        progressDialog.dismiss();
//                        Toast.makeText(context, "" + task.getException(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(context, HomeActivity.class);
        startActivity(intent);
    }

}
