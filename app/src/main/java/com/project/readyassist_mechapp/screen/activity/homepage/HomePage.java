package com.project.readyassist_mechapp.screen.activity.homepage;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.databinding.ActivityHomePageBinding;
import com.project.readyassist_mechapp.screen.activity.homepage.adapter.CurrentOrdersListAdapter;

import java.util.ResourceBundle;

public class HomePage extends AppCompatActivity {

    protected LinearLayoutManager linearLayoutManager;
    protected CurrentOrdersListAdapter ordersListAdapter;

    protected AnimationDrawable animNavMenu, animNavClose;


    protected ActivityHomePageBinding homePageBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePageBinding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(homePageBinding.getRoot());


        init();


    }

    protected void init() {

        setAnimNav();

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);

        setCurrentOrderListAdapter();

        homePageBinding.imgHomeBack.setOnClickListener(v -> {
            if (homePageBinding.layoutNavigation.getVisibility() == View.VISIBLE) {
                homePageBinding.layoutNavigation.setVisibility(View.GONE);

                if (animNavClose.isRunning())
                    animNavClose.stop();
                setAnimNav();
                /*This fades out a View:*/
                homePageBinding.layoutNavigation.animate().alpha(0.0f);

            } else {
                homePageBinding.layoutNavigation.setVisibility(View.VISIBLE);

                if (animNavMenu.isRunning())
                    animNavMenu.stop();
                setAnimNavClose();
                /*This fades it back in:*/
                homePageBinding.layoutNavigation.animate().alpha(1.0f);

            }
        });

    }

    private void setAnimNav() {
        homePageBinding.imgHomeBack.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.animate_home_nav_menu, null));
        animNavMenu = (AnimationDrawable) homePageBinding.imgHomeBack.getDrawable();
        animNavMenu.start();
    }

    private void setAnimNavClose() {
        homePageBinding.imgHomeBack.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.animate_home_nav_menu_close, null));
        animNavClose = (AnimationDrawable) homePageBinding.imgHomeBack.getDrawable();
        animNavClose.start();
    }


    private void setCurrentOrderListAdapter() {
        homePageBinding.recyclerCurrentOrders.setLayoutManager(linearLayoutManager);
        ordersListAdapter = new CurrentOrdersListAdapter(this);
        homePageBinding.recyclerCurrentOrders.setAdapter(ordersListAdapter);

        homePageBinding.recyclerCurrentOrders.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int currentFirstVisible = linearLayoutManager.findFirstVisibleItemPosition();
                if (currentFirstVisible > 0) {
                    //scroll up!

                } else {
                    //scroll down!
                }

            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Do something
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // Do something

                    if (homePageBinding.layoutNavigation.getVisibility() == View.VISIBLE) {
                        homePageBinding.layoutNavigation.setVisibility(View.GONE);

                        if (animNavClose.isRunning())
                            animNavClose.stop();
                        setAnimNav();
                        /*This fades out a View:*/
                        homePageBinding.layoutNavigation.animate().alpha(0.0f);
                    }

                } else {
                    // Do something
                }
            }
        });

    }


}
