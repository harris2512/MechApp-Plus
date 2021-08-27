package com.project.readyassist_mechapp.screen.fragment.onboard.gstin_info;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
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
import com.project.readyassist_mechapp.screen.fragment.onboard.bank_details.FragmentBankDetails;
import com.project.readyassist_mechapp.screen.fragment.onboard.document_info.FragmentDocumentOnboard;
import com.project.readyassist_mechapp.screen.fragment.onboard.profile_images.FragmentProfileImagesOnboard;

import org.greenrobot.eventbus.Subscribe;

public class FragmentGSTINOnboard extends Fragment implements CompoundButton.OnCheckedChangeListener {

    protected View view;
    protected Fragment selectedFragment;
    protected SessionManager sessionManager;

    protected CheckBox check_onboard_gstin, check_onboard_terms_privacy;
    protected TextView tv_label_onboard_gstin_no, edt_onboard_gstin_no, tv_onboard_gstin_next;
    protected ImageButton img_check_onboard_gstin;
    protected LinearLayout linear_onboard_gstin_details;

    public static FragmentGSTINOnboard newInstance() {
        return new FragmentGSTINOnboard();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gstin_info, container, false);

        // sessionManager = new SessionManager(getActivity());

        // register the event to listen.
        GlobalBus.getBus().register(this);

        init();

        return view;
    }

    private void init() {

        sendMessageToActivity("5");

        initializeVariable();


        check_onboard_gstin.setOnCheckedChangeListener(this);


        edt_onboard_gstin_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 5) {
                    img_check_onboard_gstin.setVisibility(View.VISIBLE);

                    return;
                }
                img_check_onboard_gstin.setVisibility(View.GONE);
                linear_onboard_gstin_details.setVisibility(View.GONE);

            }
        });

        img_check_onboard_gstin.setOnClickListener(v -> {
            linear_onboard_gstin_details.setVisibility(View.VISIBLE);
        });


        tv_onboard_gstin_next.setOnClickListener(v -> {

            Fragment fragment = FragmentBankDetails.newInstance();
            // fragment.setArguments(b);
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_onboard, fragment);
            ft.addToBackStack("e");
            ft.commit();

        });


    }

    private void initializeVariable() {

        check_onboard_gstin = view.findViewById(R.id.check_onboard_gstin);
        check_onboard_terms_privacy = view.findViewById(R.id.check_onboard_terms_privacy);
        tv_label_onboard_gstin_no = view.findViewById(R.id.tv_label_onboard_gstin_no);
        edt_onboard_gstin_no = view.findViewById(R.id.edt_onboard_gstin_no);
        img_check_onboard_gstin = view.findViewById(R.id.img_check_onboard_gstin);
        linear_onboard_gstin_details = view.findViewById(R.id.linear_onboard_gstin_details);
        tv_onboard_gstin_next = view.findViewById(R.id.tv_onboard_gstin_next);

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


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getId() == R.id.check_onboard_gstin) {


            tv_label_onboard_gstin_no.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            edt_onboard_gstin_no.setVisibility(isChecked ? View.VISIBLE : View.GONE);


        } else if (buttonView.getId() == R.id.check_onboard_terms_privacy) {

        }

    }


}
