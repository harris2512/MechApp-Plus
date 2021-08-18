package com.project.readyassist_mechapp.screen.activity.onboard;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.databinding.ActivityOnboardBinding;
import com.project.readyassist_mechapp.helper.events.Events;
import com.project.readyassist_mechapp.helper.events.GlobalBus;
import com.project.readyassist_mechapp.screen.fragment.FragmentOnboardPersonal;

import org.greenrobot.eventbus.Subscribe;

public class OnboardActivity extends AppCompatActivity {


    protected AnimationDrawable stepperPersonalAnim, stepperDocumentAnim, stepperVerifyingAnim,
            stepperLinePersonal, stepperLineDocument;


    protected ActivityOnboardBinding onboardBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onboardBinding = ActivityOnboardBinding.inflate(getLayoutInflater());
        setContentView(onboardBinding.getRoot());


        init();

    }

    private void init() {

        onboardBinding.layoutOnboardTimeline.tvOnboardTimeFirst.setBackgroundResource(
                R.drawable.animate_stepper_round);
        onboardBinding.layoutOnboardTimeline.tvOnboardTimeSecond.setBackgroundResource(
                R.drawable.animate_stepper_round);
        onboardBinding.layoutOnboardTimeline.tvOnboardTimeThird.setBackgroundResource(
                R.drawable.animate_stepper_round);

        onboardBinding.layoutOnboardTimeline.viewOnboardTimeFirst.setBackgroundResource(
                R.drawable.animate_stepper_line);
        onboardBinding.layoutOnboardTimeline.viewOnboardTimeSecond.setBackgroundResource(
                R.drawable.animate_stepper_line);


        stepperPersonalAnim = (AnimationDrawable)
                onboardBinding.layoutOnboardTimeline.tvOnboardTimeFirst.getBackground();
        stepperDocumentAnim = (AnimationDrawable)
                onboardBinding.layoutOnboardTimeline.tvOnboardTimeSecond.getBackground();
        stepperVerifyingAnim = (AnimationDrawable)
                onboardBinding.layoutOnboardTimeline.tvOnboardTimeThird.getBackground();

        stepperLinePersonal = (AnimationDrawable)
                onboardBinding.layoutOnboardTimeline.viewOnboardTimeFirst.getBackground();
        stepperLineDocument = (AnimationDrawable)
                onboardBinding.layoutOnboardTimeline.viewOnboardTimeSecond.getBackground();

        Fragment fragment = FragmentOnboardPersonal.newInstance();
        // fragment.setArguments(b);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_onboard, fragment);
        ft.commit();

        sendMessageToFragment("");

    }

    @Subscribe
    public void sendMessageToFragment(String message) {
        Events.ActivityFragmentMessage activityFragmentMessageEvent =
                new Events.ActivityFragmentMessage(message);
        GlobalBus.getBus().post(activityFragmentMessageEvent);
    }

    @Subscribe
    public void getMessage(Events.FragmentActivityMessage msgFragmentToActivityEvent) {

        switch (msgFragmentToActivityEvent.getMessage()) {

            case "personal":


                break;

            case "document":

                stepperPersonalAnim.start();
                stepperLinePersonal.start();

                break;

            case "verifying":

                stepperDocumentAnim.start();
                stepperVerifyingAnim.start();

                stepperLineDocument.start();


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


}
