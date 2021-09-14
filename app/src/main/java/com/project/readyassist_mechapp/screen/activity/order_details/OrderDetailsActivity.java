package com.project.readyassist_mechapp.screen.activity.order_details;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.databinding.ActivityOrderDetailsBinding;
import com.project.readyassist_mechapp.screen.fragment.order_details.FragmentNewOrder;

public class OrderDetailsActivity extends AppCompatActivity {

    protected ActivityOrderDetailsBinding detailsBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(detailsBinding.getRoot());


        Fragment fragment = FragmentNewOrder.newInstance();
        // fragment.setArguments(b);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_order_details, fragment);
        ft.commit();


    }


}
