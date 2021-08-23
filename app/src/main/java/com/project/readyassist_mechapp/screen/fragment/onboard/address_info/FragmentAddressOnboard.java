package com.project.readyassist_mechapp.screen.fragment.onboard.address_info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.helper.SessionManager;
import com.project.readyassist_mechapp.helper.events.Events;
import com.project.readyassist_mechapp.helper.events.GlobalBus;
import com.project.readyassist_mechapp.screen.fragment.onboard.profile_images.FragmentProfileImagesOnboard;

import org.greenrobot.eventbus.Subscribe;

public class FragmentAddressOnboard extends Fragment {


    protected View view;
    protected Fragment selectedFragment;
    protected SessionManager sessionManager;

    protected EditText edt_onboard_address_location, edt_onboard_address_first, edt_onboard_address_second;
    protected TextView tv_error_onboard_address, tv_onboard_address_next;


    public static FragmentAddressOnboard newInstance() {
        return new FragmentAddressOnboard();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_address_info, container, false);

        // sessionManager = new SessionManager(getActivity());

        // register the event to listen.
        GlobalBus.getBus().register(this);

        init();

        return view;
    }

    private void init() {

        sendMessageToActivity("2");

        initializeVariable();

        /*OnClick*/
        tv_onboard_address_next.setOnClickListener(v -> {

            /*Check the input fields validation*/
            checkInputFieldsValidation();
        });

    }

    private void initializeVariable() {
        edt_onboard_address_location = view.findViewById(R.id.edt_onboard_address_location);
        edt_onboard_address_first = view.findViewById(R.id.edt_onboard_address_first);
        edt_onboard_address_second = view.findViewById(R.id.edt_onboard_address_second);
        tv_error_onboard_address = view.findViewById(R.id.tv_error_onboard_address);
        tv_onboard_address_next = view.findViewById(R.id.tv_onboard_address_next);
    }

    private void checkInputFieldsValidation() {

        if (!isLocationValid()) {
            setErrorInputField("Please provide your valid location.");
        } else {
            tv_error_onboard_address.setVisibility(View.INVISIBLE);

            Fragment fragment = FragmentProfileImagesOnboard.newInstance();
            // fragment.setArguments(b);
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_onboard, fragment);
            ft.addToBackStack("c");
            ft.commit();

        }

    }

    private void setErrorInputField(String message) {
        tv_error_onboard_address.setText(message);
        tv_error_onboard_address.setVisibility(View.VISIBLE);
    }

    private boolean isLocationValid() {
        return edt_onboard_address_location.getText().toString().trim().length() > 5;
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
