package com.project.readyassist_mechapp.screen.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.helper.SessionManager;
import com.project.readyassist_mechapp.helper.events.Events;
import com.project.readyassist_mechapp.helper.events.GlobalBus;

import org.greenrobot.eventbus.Subscribe;


public class FragmentOnboardPersonal extends Fragment implements View.OnClickListener {

    protected View view;
    protected Fragment selectedFragment;
    protected SessionManager sessionManager;

    protected EditText edt_onboard_gstin_no;
    protected TextView btn_onboard_personal_next;
    protected CheckBox check_onboard_GSTIN;
    protected Button btn_onboard_verify_gstin;


    public static FragmentOnboardPersonal newInstance() {
        return new FragmentOnboardPersonal();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_onboard_personal, container, false);

        // sessionManager = new SessionManager(getActivity());

        // register the event to listen.
        GlobalBus.getBus().register(this);

        init();


        return view;
    }

    private void init() {

        initializeVariable();

        sendMessageToActivity("personal");

        check_onboard_GSTIN.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                edt_onboard_gstin_no.setVisibility(View.VISIBLE);
                btn_onboard_verify_gstin.setVisibility(View.VISIBLE);
                return;
            }

            edt_onboard_gstin_no.setVisibility(View.GONE);
            btn_onboard_verify_gstin.setVisibility(View.GONE);

        });

        /*OnClick*/
        btn_onboard_personal_next.setOnClickListener(this);

    }

    private void initializeVariable() {

        btn_onboard_personal_next = view.findViewById(R.id.btn_onboard_personal_next);
        check_onboard_GSTIN = view.findViewById(R.id.check_onboard_GSTIN);
        edt_onboard_gstin_no = view.findViewById(R.id.edt_onboard_gstin_no);
        btn_onboard_verify_gstin = view.findViewById(R.id.btn_onboard_verify_gstin);


    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

//            case R.id.:
//                break;
//
//            case R.id.:
//                break;
//
//            case R.id.:
//                break;

            case R.id.btn_onboard_personal_next:

                Fragment fragment = FragmentOnboardDocument.newInstance();
                // fragment.setArguments(b);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_onboard, fragment);
                ft.commit();

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

}