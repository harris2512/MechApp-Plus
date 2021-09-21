package com.project.readyassist_mechapp.screen.activity.onboard;

import android.app.FragmentManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.databinding.ActivityOnboardBinding;
import com.project.readyassist_mechapp.helper.events.Events;
import com.project.readyassist_mechapp.helper.events.GlobalBus;
import com.project.readyassist_mechapp.screen.fragment.onboard.personal_info.FragmentPersonalOnboard;

import org.greenrobot.eventbus.Subscribe;

public class OnboardActivity extends AppCompatActivity {


    protected String selectedFragment;
    protected ActivityOnboardBinding onboardBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            setTheme(R.style.status_bar_black);
        }

        onboardBinding = ActivityOnboardBinding.inflate(getLayoutInflater());
        setContentView(onboardBinding.getRoot());


        init();

    }

    private void init() {


        onboardBinding.tvLabelOnboard.setText(R.string.label_onboard_personal_info);
        selectedFragment = "1";
        Fragment fragment = FragmentPersonalOnboard.newInstance();
        // fragment.setArguments(b);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_onboard, fragment);
        ft.commit();


        sendMessageToFragment("");

        onboardBinding.imgOnboardBack.setOnClickListener(v -> {

            FragmentManager fm = getFragmentManager();
            if (getSupportFragmentManager().popBackStackImmediate()) {
                fm.popBackStack();
            } else {
                finish();
            }

        });

    }

    @Subscribe
    public void sendMessageToFragment(String message) {
        Events.ActivityFragmentMessage activityFragmentMessageEvent =
                new Events.ActivityFragmentMessage(message);
        GlobalBus.getBus().post(activityFragmentMessageEvent);
    }

    @Subscribe
    public void getMessage(Events.FragmentActivityMessage msgFragmentToActivityEvent) {

        onboardBinding.tvCurrentPageOnboard.setText(msgFragmentToActivityEvent.getMessage());
        selectedFragment = msgFragmentToActivityEvent.getMessage();
        switch (selectedFragment) {

            case "1":
                onboardBinding.linearOnboardToolbar.setVisibility(View.VISIBLE);
                onboardBinding.tvLabelOnboard.setText(R.string.label_onboard_personal_info);
                break;

            case "2":
                onboardBinding.linearOnboardToolbar.setVisibility(View.VISIBLE);
                onboardBinding.tvLabelOnboard.setText(R.string.label_onboard_address_info);
                break;

            case "3":
                onboardBinding.linearOnboardToolbar.setVisibility(View.VISIBLE);
                onboardBinding.tvLabelOnboard.setText(R.string.label_onboard_profile_images_info);
                break;

            case "4":
                onboardBinding.linearOnboardToolbar.setVisibility(View.VISIBLE);
                onboardBinding.tvLabelOnboard.setText(R.string.label_onboard_identity_info);
                break;

            case "5":
                onboardBinding.linearOnboardToolbar.setVisibility(View.VISIBLE);
                onboardBinding.tvLabelOnboard.setText(R.string.label_onboard_gstin_info);
                break;

            case "6":
                onboardBinding.linearOnboardToolbar.setVisibility(View.VISIBLE);
                onboardBinding.tvLabelOnboard.setText(R.string.label_onboard_bank_info);
                break;

            case "7":
                onboardBinding.linearOnboardToolbar.setVisibility(View.GONE);
                break;

        }


    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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

        if (!selectedFragment.isEmpty() && selectedFragment.equals("7")) {
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


}
