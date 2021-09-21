package com.project.readyassist_mechapp.screen.fragment.onboard.personal_info;

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
import com.project.readyassist_mechapp.screen.fragment.onboard.address_info.FragmentAddressOnboard;

import org.greenrobot.eventbus.Subscribe;

public class FragmentPersonalOnboard extends Fragment {

    protected View view;
    protected Fragment selectedFragment;
    protected SessionManager sessionManager;

    protected EditText edt_onboard_personal_name, edt_onboard_personal_email, edt_onboard_personal_mobile;
    protected TextView tv_error_onboard_personal, tv_onboard_personal_next;


    public static FragmentPersonalOnboard newInstance() {
        return new FragmentPersonalOnboard();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal_info, container, false);

        // sessionManager = new SessionManager(getActivity());

        // register the event to listen.
        GlobalBus.getBus().register(this);

        init();

        return view;
    }

    private void init() {

        sendMessageToActivity("1");

        initializeVariable();

        /*OnClick*/
        tv_onboard_personal_next.setOnClickListener(v -> {

            /*Check the input fields validation*/
            checkInputFieldsValidation();
        });

    }

    private void initializeVariable() {

        edt_onboard_personal_name = view.findViewById(R.id.edt_onboard_personal_name);
        edt_onboard_personal_email = view.findViewById(R.id.edt_onboard_personal_email);
        edt_onboard_personal_mobile = view.findViewById(R.id.edt_onboard_personal_mobile);
        tv_error_onboard_personal = view.findViewById(R.id.tv_error_onboard_personal);
        tv_onboard_personal_next = view.findViewById(R.id.tv_onboard_personal_next);

    }

    private void checkInputFieldsValidation() {

        if (!isNameValid()) {
            setErrorInputField(getString(R.string.err_provide_full_name));
        } else if (!isEmailValid()) {
            setErrorInputField(getString(R.string.err_provide_valid_email));
        } else if (!isMobileValid()) {
            setErrorInputField(getString(R.string.err_mobile_no_digits));
        } else {
            tv_error_onboard_personal.setVisibility(View.INVISIBLE);


            Fragment fragment = FragmentAddressOnboard.newInstance();
            // fragment.setArguments(b);
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_onboard, fragment);
            ft.addToBackStack("a");
            ft.commit();

        }

    }

    private void setErrorInputField(String message) {
        tv_error_onboard_personal.setText(message);
        tv_error_onboard_personal.setVisibility(View.VISIBLE);
    }

    private boolean isNameValid() {
        return edt_onboard_personal_name.getText().toString().trim().length() > 0;
    }

    private boolean isEmailValid() {
        boolean isValid = true;
        if (edt_onboard_personal_email.getText().toString().trim().length() > 0) {
            isValid = edt_onboard_personal_email.getText().toString().contains("@");
        }
        return isValid;
    }

    private boolean isMobileValid() {
        boolean isValid = true;
        if (edt_onboard_personal_mobile.getText().toString().length() > 0) {
            isValid = edt_onboard_personal_mobile.getText().toString().length() == 10;
        }
        return isValid;
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
