package com.project.readyassist_mechapp.screen.fragment.add_skill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.helper.SessionManager;
import com.project.readyassist_mechapp.helper.events.Events;
import com.project.readyassist_mechapp.helper.events.GlobalBus;

import org.greenrobot.eventbus.Subscribe;


public class FragmentVendorSkillCharges extends Fragment {


    protected View view;
    protected Fragment selectedFragment;
    protected SessionManager sessionManager;

    protected TextView tv_bottom_add_skill_submit;


    public static FragmentVendorSkillCharges newInstance() {
        return new FragmentVendorSkillCharges();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_vendor_skill_charges, container, false);

//         sessionManager = new SessionManager(getActivity());

        // register the event to listen.
        GlobalBus.getBus().register(this);


        init();

        return view;
    }

    private void init() {

        tv_bottom_add_skill_submit = view.findViewById(R.id.tv_bottom_add_skill_submit);


        tv_bottom_add_skill_submit.setOnClickListener(v -> {
            getActivity().finish();
        });


    }


    @Subscribe
    private void sendMessageToActivity(String message) {
        Events.FragmentActivityMessage msgFragmentToActivityEvent = new Events.
                FragmentActivityMessage(message);
        GlobalBus.getBus().post(msgFragmentToActivityEvent);
    }

    @Subscribe
    public void getMessage(Events.ActivityFragmentMessage activityFragmentMessage) {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        GlobalBus.getBus().unregister(this);
    }

}
