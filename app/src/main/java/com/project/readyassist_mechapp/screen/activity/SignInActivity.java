package com.project.readyassist_mechapp.screen.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.databinding.ActivitySignInBinding;
import com.project.readyassist_mechapp.screen.activity.onboard.OnboardActivity;

public class SignInActivity extends AppCompatActivity {


    protected boolean isSignInClick = false;


    protected ActivitySignInBinding signInBinding;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInBinding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(signInBinding.getRoot());


//        signInBinding.rocketImage.setBackgroundResource(R.drawable.animate_file);


        signInBinding.btnSigninSubmit.setOnClickListener(v -> {

            if (isSignInClick) {
                startActivity(new Intent(this, OnboardActivity.class));
                return;
            }

            if (signInBinding.edtSigninOtp.getVisibility() == View.GONE) {
                signInBinding.edtSigninOtp.setVisibility(View.VISIBLE);
                signInBinding.tvSigninEdit.setVisibility(View.VISIBLE);

                isSignInClick = true;
                signInBinding.edtSigninMobile.setEnabled(false);
                signInBinding.btnSigninSubmit.setText("Sign In");
                return;
            }


            signInBinding.edtSigninOtp.setVisibility(View.GONE);

            isSignInClick = false;
            signInBinding.edtSigninMobile.setEnabled(true);
            signInBinding.btnSigninSubmit.setText("Send OTP");

        });

        signInBinding.tvSigninEdit.setOnClickListener(v -> {
            if (signInBinding.tvSigninEdit.getVisibility() == View.VISIBLE) {
                signInBinding.edtSigninOtp.setVisibility(View.GONE);
                signInBinding.tvSigninEdit.setVisibility(View.GONE);

                isSignInClick = false;
                signInBinding.edtSigninOtp.getText().clear();
                signInBinding.edtSigninMobile.setEnabled(true);
                signInBinding.btnSigninSubmit.setText("Send OTP");
            }
        });

    }


}
