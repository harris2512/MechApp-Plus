package com.project.readyassist_mechapp.screen.activity.create_service_provider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.databinding.ActivityOnboardServiceProvidersBinding;


public class CreateServiceProviderActivity extends AppCompatActivity {


    protected ActivityOnboardServiceProvidersBinding providersBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            setTheme(R.style.status_bar_black);
        }

        providersBinding = ActivityOnboardServiceProvidersBinding.inflate(getLayoutInflater());
        setContentView(providersBinding.getRoot());


        init();

    }


    private void init() {

        providersBinding.imgCreateServiceProviderBack.setOnClickListener(v -> {
            finish();
        });

        providersBinding.tvSubmitServiceProvider.setOnClickListener(v -> {
            finish();
        });

    }


}
