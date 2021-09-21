package com.project.readyassist_mechapp.screen.fragment.my_account;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetDialog;
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

            final Dialog dialog = new Dialog(getActivity(), R.style.TransparentDialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_alert_actions);
            dialog.setCancelable(false);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

            final TextView tv_dialog_alert_title = dialog.findViewById(R.id.tv_dialog_alert_title);
            final  TextView tv_dialog_alert_desc = dialog.findViewById(R.id.tv_dialog_alert_desc);
            final TextView tv_dialog_alert_cancel = dialog.findViewById(R.id.tv_dialog_alert_cancel);
            final TextView tv_dialog_alert_submit = dialog.findViewById(R.id.tv_dialog_alert_submit);

            tv_dialog_alert_title.setText("Logout");
            tv_dialog_alert_desc.setText("Are you sure want to logout from application?");

            tv_dialog_alert_submit.setOnClickListener(v1 -> {
                sendMessageToActivity("1");
                selectedFragment = FragmentCurrentOrders.newInstance();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_home, selectedFragment);
                ft.commit();

                dialog.dismiss();

            });
            tv_dialog_alert_cancel.setOnClickListener(v1 -> {
                dialog.dismiss();
            });


            dialog.show();
            dialog.getWindow().setAttributes(lp);


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
