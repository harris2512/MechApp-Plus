package com.project.readyassist_mechapp.screen.fragment.my_account;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.helper.AppConstants;
import com.project.readyassist_mechapp.helper.SessionManager;
import com.project.readyassist_mechapp.helper.events.Events;
import com.project.readyassist_mechapp.helper.events.GlobalBus;
import com.project.readyassist_mechapp.screen.fragment.current_orders.FragmentCurrentOrders;
import com.project.readyassist_mechapp.utils.RAUtils;

import org.greenrobot.eventbus.Subscribe;

public class FragmentMyAccount extends Fragment {

    protected View view;
    protected Fragment selectedFragment;
    protected SessionManager sessionManager;
    protected ToggleButton toggle_account_status;
    protected TextView tv_label_account_off_duty, tv_label_account_on_duty, tv_account_user_sign_out;
    protected ScrollView scroll_account;


    public static FragmentMyAccount newInstance() {
        return new FragmentMyAccount();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_account, container, false);

        // sessionManager = new SessionManager(getActivity());

        // register the event to listen.
        GlobalBus.getBus().register(this);

        init();

        return view;
    }

    private void init() {

        sendMessageToActivity("2");

        initializeVariable();

        toggle_account_status.setOnCheckedChangeListener((arg0, isChecked) -> {

            tv_label_account_on_duty.setTextColor(isChecked ?
                    getContext().getResources().getColor(R.color.black) :
                    getContext().getResources().getColor(R.color.colorGreyLight));

            tv_label_account_off_duty.setTextColor(!isChecked ?
                    getContext().getResources().getColor(R.color.black) :
                    getContext().getResources().getColor(R.color.colorGreyLight));

        });

        stateScrollView();


        /*OnScroll*/
        tv_account_user_sign_out.setOnClickListener(v -> {
            sendMessageToActivity("1");
            selectedFragment = FragmentCurrentOrders.newInstance();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_home, selectedFragment);
            ft.commit();
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    private void stateScrollView() {
        scroll_account.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_SCROLL:
                case MotionEvent.ACTION_MOVE:
                    // Log.e("SCROLL", "ACTION_SCROLL");
                    sendMessageToActivity("scroll_account");
                    break;
                case MotionEvent.ACTION_DOWN:
                    // Log.e("SCROLL", "ACTION_DOWN");
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    // Log.e("SCROLL", "SCROLL_STOP");
                    break;
            }
            return false;
        });
    }

    private void initializeVariable() {
        scroll_account = view.findViewById(R.id.scroll_account);
        toggle_account_status = view.findViewById(R.id.toggle_account_status);
        tv_label_account_off_duty = view.findViewById(R.id.tv_label_account_off_duty);
        tv_label_account_on_duty = view.findViewById(R.id.tv_label_account_on_duty);
        tv_account_user_sign_out = view.findViewById(R.id.tv_account_user_sign_out);

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

    private void dismissPb() {
        RAUtils.dismissProgressDialog();
    }

    private void showPb() {
        RAUtils.showProgressDialog(getContext(), true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        GlobalBus.getBus().unregister(this);
    }

}
