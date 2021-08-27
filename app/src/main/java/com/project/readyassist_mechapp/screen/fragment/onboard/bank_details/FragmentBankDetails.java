package com.project.readyassist_mechapp.screen.fragment.onboard.bank_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.helper.SessionManager;
import com.project.readyassist_mechapp.helper.events.Events;
import com.project.readyassist_mechapp.helper.events.GlobalBus;
import com.project.readyassist_mechapp.screen.fragment.onboard.document_info.FragmentDocumentOnboard;
import com.project.readyassist_mechapp.screen.fragment.onboard.gstin_info.FragmentGSTINOnboard;
import com.project.readyassist_mechapp.screen.fragment.onboard.verifying.FragmentOnboardVerifying;

import org.greenrobot.eventbus.Subscribe;

public class FragmentBankDetails extends Fragment {

    protected View view;
    protected Fragment selectedFragment;
    protected SessionManager sessionManager;

    protected TextView tv_onboard_bank_next;

    public static FragmentBankDetails newInstance() {
        return new FragmentBankDetails();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bank_details, container, false);

        // sessionManager = new SessionManager(getActivity());

        // register the event to listen.
        GlobalBus.getBus().register(this);

        init();

        return view;
    }

    private void init() {

        sendMessageToActivity("6");

        initializeVariable();

    }

    private void initializeVariable() {

        tv_onboard_bank_next = view.findViewById(R.id.tv_onboard_bank_next);

        tv_onboard_bank_next.setOnClickListener(v -> {

            Fragment fragment = FragmentOnboardVerifying.newInstance();
            // fragment.setArguments(b);
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_onboard, fragment);
            ft.addToBackStack(null);
            ft.commit();

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




