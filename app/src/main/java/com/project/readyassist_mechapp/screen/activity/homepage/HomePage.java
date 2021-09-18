package com.project.readyassist_mechapp.screen.activity.homepage;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.databinding.ActivityHomePageBinding;
import com.project.readyassist_mechapp.helper.events.Events;
import com.project.readyassist_mechapp.helper.events.GlobalBus;
import com.project.readyassist_mechapp.screen.activity.homepage.adapter.CurrentOrdersListAdapter;
import com.project.readyassist_mechapp.screen.fragment.current_orders.FragmentCurrentOrders;
import com.project.readyassist_mechapp.screen.fragment.my_account.FragmentMyAccount;
import com.project.readyassist_mechapp.screen.fragment.my_earnings.FragmentEarnings;
import com.project.readyassist_mechapp.screen.fragment.onboard.personal_info.FragmentPersonalOnboard;
import com.project.readyassist_mechapp.utils.RAUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ResourceBundle;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

    protected Fragment selectedFragment;
    protected FragmentTransaction ft;
    protected String fragmentState;

    protected AnimationDrawable animNavMenu, animNavClose;


    protected ActivityHomePageBinding homePageBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            setTheme(R.style.status_bar_black);
        }

        homePageBinding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(homePageBinding.getRoot());


        init();


    }

    protected void init() {

        setAnimNav();

        homePageBinding.tvHomeHeader.setText("Active Requests");
        fragmentState = "1";
        selectedFragment = FragmentCurrentOrders.newInstance();
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_home, selectedFragment);
        ft.commit();


        sendMessageToFragment("");


        /*Onclick*/
        homePageBinding.imgHomeMenu.setOnClickListener(this);
        homePageBinding.tvOrdersNavHome.setOnClickListener(this);
        homePageBinding.tvAccountNavHome.setOnClickListener(this);
        homePageBinding.tvEarningNavHome.setOnClickListener(this);

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.img_home_menu:

                if (homePageBinding.layoutNavigation.getVisibility() == View.GONE) {
                    homePageBinding.layoutNavigation.setVisibility(View.VISIBLE);
                    if (animNavMenu.isRunning())
                        animNavMenu.stop();
                    setAnimNavClose();
                    /*This fades it back in:*/
                    homePageBinding.layoutNavigation.animate().alpha(1.0f);
                    return;
                }

                closeNavigationMenu();

                break;

            case R.id.tv_orders_nav_home:
                fragmentState = "1";
                selectedFragment = FragmentCurrentOrders.newInstance();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_home, selectedFragment);
                ft.commit();

                closeNavigationMenu();

                break;

            case R.id.tv_account_nav_home:
                fragmentState = "2";
                selectedFragment = FragmentMyAccount.newInstance();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_home, selectedFragment);
                ft.commit();

                closeNavigationMenu();

                break;

            case R.id.tv_earning_nav_home:
                fragmentState = "3";
                selectedFragment = FragmentEarnings.newInstance();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_home, selectedFragment);
                ft.commit();

                closeNavigationMenu();
                break;

        }

    }


    @Subscribe
    public void sendMessageToFragment(String message) {
        Events.ActivityFragmentMessage activityFragmentMessageEvent =
                new Events.ActivityFragmentMessage(message);
        GlobalBus.getBus().post(activityFragmentMessageEvent);
    }


    private void setAnimNav() {
        homePageBinding.imgHomeMenu.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.animate_home_nav_menu, null));
        animNavMenu = (AnimationDrawable) homePageBinding.imgHomeMenu.getDrawable();
        animNavMenu.start();
    }

    private void setAnimNavClose() {
        homePageBinding.imgHomeMenu.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.animate_home_nav_menu_close, null));
        animNavClose = (AnimationDrawable) homePageBinding.imgHomeMenu.getDrawable();
        animNavClose.start();
    }


    @Subscribe
    public void getMessage(Events.FragmentActivityMessage msgFragmentToActivityEvent) {
        fragmentState = msgFragmentToActivityEvent.getMessage();

        homePageBinding.imgOrderNavHome.setVisibility(fragmentState.equalsIgnoreCase("1")
                || fragmentState.equalsIgnoreCase("scroll_order") ? View.GONE : View.VISIBLE);
        homePageBinding.tvOrdersNavHome.setVisibility(fragmentState.equalsIgnoreCase("1")
                || fragmentState.equalsIgnoreCase("scroll_order") ? View.GONE : View.VISIBLE);

        switch (msgFragmentToActivityEvent.getMessage()) {

            case "scroll_order":

            case "scroll_account":
                closeNavigationMenu();
                break;

            case "1":
                homePageBinding.tvHomeHeader.setText("Active Requests");

                break;

            case "2":
                homePageBinding.tvHomeHeader.setText("My Account");

                break;

            case "3":
                homePageBinding.tvHomeHeader.setText("My Earning");

                break;

        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        // Register event.
        GlobalBus.getBus().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // UnRegister event.
        GlobalBus.getBus().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // UnRegister event.
        GlobalBus.getBus().unregister(this);
    }

    @Override
    public void onBackPressed() {

        if (!TextUtils.isEmpty(fragmentState) && fragmentState.equals("7")) {
            finish();
            return;
        }
        FragmentManager fm = getFragmentManager();
        if (getSupportFragmentManager().popBackStackImmediate()) {
            fm.popBackStack();
        } else {
            finish();
            super.onBackPressed();
        }

    }

    private void closeNavigationMenu() {
        if (homePageBinding.layoutNavigation.getVisibility() == View.VISIBLE) {
            homePageBinding.layoutNavigation.setVisibility(View.GONE);
            if (animNavClose.isRunning())
                animNavClose.stop();
            setAnimNav();
            /*This fades out a View:*/
            homePageBinding.layoutNavigation.animate().alpha(0.0f);
        }
    }


}
