package com.project.readyassist_mechapp.screen.activity.signin;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.databinding.ActivitySignInBinding;
import com.project.readyassist_mechapp.screen.activity.homepage.HomePage;
import com.project.readyassist_mechapp.screen.activity.onboard.OnboardActivity;
import com.project.readyassist_mechapp.screen.activity.vendor_skill.VendorSkillActivity;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {


    protected boolean isSignInClick = false;


    protected ActivitySignInBinding signInBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInBinding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(signInBinding.getRoot());

        signInBinding.btnSigninSubmit.setOnClickListener(v -> {

            if (isSignInClick) {
                startActivity(new Intent(this, HomePage.class));
                return;
            }

            if (signInBinding.edtSigninOtp.getVisibility() == View.GONE) {
                signInBinding.edtSigninOtp.setVisibility(View.VISIBLE);
                signInBinding.tvSigninEdit.setVisibility(View.VISIBLE);

                isSignInClick = true;
                signInBinding.edtSigninMobile.setEnabled(false);
                signInBinding.btnSigninSubmit.setText(R.string.label_sign_in);
                return;
            }


            signInBinding.edtSigninOtp.setVisibility(View.GONE);

            isSignInClick = false;
            signInBinding.edtSigninMobile.setEnabled(true);
            signInBinding.btnSigninSubmit.setText(R.string.label_send_otp);

        });

        signInBinding.tvSigninEdit.setOnClickListener(v -> {
            if (signInBinding.tvSigninEdit.getVisibility() == View.VISIBLE) {
                signInBinding.edtSigninOtp.setVisibility(View.GONE);
                signInBinding.tvSigninEdit.setVisibility(View.GONE);

                isSignInClick = false;
                Objects.requireNonNull(signInBinding.edtSigninOtp.getText()).clear();
                signInBinding.edtSigninMobile.setEnabled(true);
                signInBinding.btnSigninSubmit.setText(R.string.label_send_otp);
            }
        });


        signInBinding.imgSigninBanner.setOnClickListener(v -> {
            startActivity(new Intent(this, OnboardActivity.class));
        });

        signInBinding.tvSigninLabel.setOnClickListener(v -> {
            startActivity(new Intent(this, VendorSkillActivity.class));
        });




    }


}
