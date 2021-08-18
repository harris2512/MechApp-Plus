package com.project.readyassist_mechapp.screen.activity.splash;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.databinding.SplashScreenBinding;
import com.project.readyassist_mechapp.screen.activity.SignInActivity;


public class SplashScreenActivity extends AppCompatActivity {


    protected SplashScreenBinding screenBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenBinding = SplashScreenBinding.inflate(getLayoutInflater());
        setContentView(screenBinding.getRoot());

        if (isDarkModeOn()) {
            screenBinding.layoutSplash.setBackgroundColor(getResources().getColor(R.color.black));
        } else {
            screenBinding.layoutSplash.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        init();


    }

    public void init() {

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }, 2000);


    }

    private boolean isDarkModeOn() {
        return (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES;
    }


}
