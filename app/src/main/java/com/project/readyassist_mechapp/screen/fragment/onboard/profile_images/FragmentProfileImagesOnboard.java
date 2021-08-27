package com.project.readyassist_mechapp.screen.fragment.onboard.profile_images;

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
import com.project.readyassist_mechapp.screen.fragment.onboard.address_info.FragmentAddressOnboard;
import com.project.readyassist_mechapp.screen.fragment.onboard.document_info.FragmentDocumentOnboard;

import org.greenrobot.eventbus.Subscribe;

public class FragmentProfileImagesOnboard extends Fragment {

    protected View view;
    protected Fragment selectedFragment;
    protected SessionManager sessionManager;

    protected TextView tv_onboard_profile_images_next;

    public static FragmentProfileImagesOnboard newInstance() {
        return new FragmentProfileImagesOnboard();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_images, container, false);

        // sessionManager = new SessionManager(getActivity());

        // register the event to listen.
        GlobalBus.getBus().register(this);

        init();

        return view;
    }

    private void init() {

        sendMessageToActivity("3");

        initializeVariable();

    }

    private void initializeVariable() {

        tv_onboard_profile_images_next = view.findViewById(R.id.tv_onboard_profile_images_next);

        tv_onboard_profile_images_next.setOnClickListener(v -> {

            Fragment fragment = FragmentDocumentOnboard.newInstance();
            // fragment.setArguments(b);
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_onboard, fragment);
            ft.addToBackStack("c");
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
