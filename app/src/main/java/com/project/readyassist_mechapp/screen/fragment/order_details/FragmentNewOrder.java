package com.project.readyassist_mechapp.screen.fragment.order_details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.helper.AppConstants;
import com.project.readyassist_mechapp.helper.SessionManager;


public class FragmentNewOrder extends Fragment {

    protected View view;
    protected Fragment selectedFragment;
    protected SessionManager sessionManager;

    protected TextView tv_order_details_order_state, tv_order_details_order_pick_km,
            tv_order_details_order_pick_km_duration, tv_new_order_swipe;
    protected ImageView img_order_details_pick_nav_map;
    protected LinearLayout linear_order_details_customer_details;

    protected SeekBar myseek;


    public static FragmentNewOrder newInstance() {
        return new FragmentNewOrder();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_order, container, false);

        // sessionManager = new SessionManager(getActivity());

        init();

        return view;
    }

    private void init() {

        tv_order_details_order_state = view.findViewById(R.id.tv_order_details_order_state);
        tv_order_details_order_pick_km = view.findViewById(R.id.tv_order_details_order_pick_km);
        tv_order_details_order_pick_km_duration = view.findViewById(R.id.tv_order_details_order_pick_km_duration);
        img_order_details_pick_nav_map = view.findViewById(R.id.img_order_details_pick_nav_map);
        linear_order_details_customer_details = view.findViewById(R.id.linear_order_details_customer_details);

        tv_new_order_swipe = view.findViewById(R.id.tv_new_order_swipe);


        myseek = view.findViewById(R.id.swipe_new_order);

        swipeGesture();


    }

    @SuppressLint("ClickableViewAccessibility")
    private void swipeGesture() {

        myseek.setThumb(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_swipe_right, null));
        myseek.setThumbOffset(-16);

        myseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (seekBar.getProgress() > 85) {
                    seekBar.setThumb(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.ic_swipe_right_green, null));
                    seekBar.setProgress(100);
                    seekBar.setEnabled(false);

                    navigateNextState();

                    return;
                }

                resetSeekBar();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                if (seekBar.getProgress() > 50) {
                    resetSeekBar();
                }

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


//                if (progress > 50 && progress < 85 && fromUser) {
//                    switch (tv_new_order_swipe.getText().toString()) {
//                        case AppConstants.swipeAcknowledge:
//                            tv_new_order_swipe.setText(AppConstants.swipeStartVisit);
//                            break;
//                        case AppConstants.swipeStartVisit:
//                            tv_new_order_swipe.setText(AppConstants.swipeLocationReached);
//                            break;
//                        case AppConstants.swipeLocationReached:
//                            tv_new_order_swipe.setText(AppConstants.swipeStartWork);
//                            break;
//                    }
//                    return;
//                }
//
//                if (progress < 50 && fromUser) {
//                    switch (tv_new_order_swipe.getText().toString()) {
//                        case AppConstants.swipeAcknowledge:
//                        case AppConstants.swipeStartVisit:
//                            tv_new_order_swipe.setText(AppConstants.swipeAcknowledge);
//                            break;
//                        case AppConstants.swipeLocationReached:
//                            tv_new_order_swipe.setText(AppConstants.swipeStartVisit);
//                            break;
//                        case AppConstants.swipeStartWork:
//                            tv_new_order_swipe.setText(AppConstants.swipeLocationReached);
//                            break;
//                    }
//                }


            }
        });


    }

    private void resetSeekBar() {
        myseek.setProgress(0);
        myseek.setThumb(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_swipe_right, null));
        myseek.setThumbOffset(-16);
        myseek.setEnabled(true);
    }

    private void navigateNextState() {

        switch (tv_new_order_swipe.getText().toString()) {

            case AppConstants.swipeAcknowledge:
                tv_order_details_order_state.setText(AppConstants.stateAcknowledged);
                tv_new_order_swipe.setText(AppConstants.swipeStartVisit);
                linear_order_details_customer_details.setVisibility(View.VISIBLE);
                tv_order_details_order_pick_km.setVisibility(View.VISIBLE);
                tv_order_details_order_pick_km_duration.setVisibility(View.VISIBLE);
                img_order_details_pick_nav_map.setVisibility(View.INVISIBLE);
                break;

            case AppConstants.swipeStartVisit:
                tv_order_details_order_state.setText(AppConstants.stateOnTheWay);
                tv_new_order_swipe.setText(AppConstants.swipeLocationReached);
                linear_order_details_customer_details.setVisibility(View.VISIBLE);
                tv_order_details_order_pick_km.setVisibility(View.VISIBLE);
                tv_order_details_order_pick_km_duration.setVisibility(View.VISIBLE);
                img_order_details_pick_nav_map.setVisibility(View.VISIBLE);
                break;

            case AppConstants.swipeLocationReached:
                tv_order_details_order_state.setText(AppConstants.stateLocationReached);
                tv_new_order_swipe.setText(AppConstants.swipeStartWork);
                linear_order_details_customer_details.setVisibility(View.VISIBLE);
                tv_order_details_order_pick_km.setVisibility(View.VISIBLE);
                tv_order_details_order_pick_km_duration.setVisibility(View.VISIBLE);
                img_order_details_pick_nav_map.setVisibility(View.VISIBLE);
                showToast("Location Reached");
                break;
        }

        resetSeekBar();

    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


}
