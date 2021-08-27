package com.project.readyassist_mechapp.screen.activity.homepage;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.readyassist_mechapp.databinding.ActivityHomePageBinding;
import com.project.readyassist_mechapp.screen.activity.homepage.adapter.CurrentOrdersListAdapter;

public class HomePage extends AppCompatActivity {


    protected CurrentOrdersListAdapter ordersListAdapter;


    protected ActivityHomePageBinding homePageBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePageBinding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(homePageBinding.getRoot());


        init();


    }

    protected void init() {

        homePageBinding.recyclerCurrentOrders.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        setCurrentOrderListAdapter();

    }

    private void setCurrentOrderListAdapter() {
        ordersListAdapter = new CurrentOrdersListAdapter(this);
        homePageBinding.recyclerCurrentOrders.setAdapter(ordersListAdapter);

    }



}
