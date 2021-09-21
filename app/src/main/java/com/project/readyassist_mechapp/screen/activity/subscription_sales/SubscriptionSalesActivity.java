package com.project.readyassist_mechapp.screen.activity.subscription_sales;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.databinding.ActivitySubscriptionSalesBinding;

public class SubscriptionSalesActivity extends AppCompatActivity {


    protected ActivitySubscriptionSalesBinding salesBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            setTheme(R.style.status_bar_black);
        }

        salesBinding = ActivitySubscriptionSalesBinding.inflate(getLayoutInflater());
        setContentView(salesBinding.getRoot());


        init();

    }

    private void init(){

    }

}
