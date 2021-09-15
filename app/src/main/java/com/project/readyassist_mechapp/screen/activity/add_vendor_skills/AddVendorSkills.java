package com.project.readyassist_mechapp.screen.activity.add_vendor_skills;

import android.app.FragmentManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.databinding.ActivityAddVendorSkillsBinding;
import com.project.readyassist_mechapp.helper.events.Events;
import com.project.readyassist_mechapp.helper.events.GlobalBus;
import com.project.readyassist_mechapp.screen.fragment.add_skill.FragmentVendorSkills;

import org.greenrobot.eventbus.Subscribe;


public class AddVendorSkills extends AppCompatActivity {


    protected ActivityAddVendorSkillsBinding skillsBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skillsBinding = ActivityAddVendorSkillsBinding.inflate(getLayoutInflater());
        setContentView(skillsBinding.getRoot());


        init();


    }

    private void init() {

        skillsBinding.tvAddVendorSkillHeader.setText("Add Skill");

        Fragment fragment = FragmentVendorSkills.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_add_skill, fragment);
        ft.commit();


        skillsBinding.imgVendorAddSkillBack.setOnClickListener(v -> {
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

        skillsBinding.tvAddVendorSkillHeader.setText(msgFragmentToActivityEvent.getMessage());

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
