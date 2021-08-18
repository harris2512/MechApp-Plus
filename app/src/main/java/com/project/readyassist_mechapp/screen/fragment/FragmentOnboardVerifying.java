package com.project.readyassist_mechapp.screen.fragment;

import android.annotation.SuppressLint;
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

public class FragmentOnboardVerifying extends Fragment implements View.OnClickListener {


    protected View view;
    protected Fragment selectedFragment;
    protected SessionManager sessionManager;

    protected TextView btn_onboard_verifying_submit;


    public static FragmentOnboardVerifying newInstance() {
        return new FragmentOnboardVerifying();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_onboard_verifying, container, false);

        // sessionManager = new SessionManager(getActivity());

        // register the event to listen.
        GlobalBus.getBus().register(this);

        init();


        return view;
    }

    private void init() {

        initializeVariable();

        sendMessageToActivity("verifying");

        btn_onboard_verifying_submit.setOnClickListener(this);
    }

    private void initializeVariable() {
        btn_onboard_verifying_submit = view.findViewById(R.id.btn_onboard_verifying_submit);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_onboard_verifying_submit:
                requireActivity().finish();
                break;

        }
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

        // unregister the registered event.
        GlobalBus.getBus().unregister(this);

    }

}
