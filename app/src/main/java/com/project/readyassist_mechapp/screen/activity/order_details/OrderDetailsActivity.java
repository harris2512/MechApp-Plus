package com.project.readyassist_mechapp.screen.activity.order_details;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.databinding.ActivityOrderDetailsBinding;
import com.project.readyassist_mechapp.helper.AppConstants;
import com.project.readyassist_mechapp.helper.SessionManager;
import com.project.readyassist_mechapp.screen.fragment.order_details.FragmentNewOrder;

public class OrderDetailsActivity extends AppCompatActivity {

    protected SessionManager sessionManager;

    protected ActivityOrderDetailsBinding detailsBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(detailsBinding.getRoot());


        init();

    }

    private void init() {


        orderSwipeGesture();

    }

    private void orderSwipeGesture() {
        detailsBinding.swipeOrderDetails.setThumb(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_swipe_right, null));
        detailsBinding.swipeOrderDetails.setThumbOffset(-16);

        detailsBinding.swipeOrderDetails.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

            }
        });
    }

    private void navigateNextState() {
        switch (detailsBinding.tvNewOrderSwipe.getText().toString()) {

            case AppConstants.swipeAcknowledge:
                detailsBinding.tvOrderDetailsOrderState.setText(AppConstants.stateAcknowledged);
                detailsBinding.tvNewOrderSwipe.setText(AppConstants.swipeStartVisit);
                detailsBinding.linearOrderDetailsCustomerDetails.setVisibility(View.VISIBLE);
                detailsBinding.tvOrderDetailsOrderPickKm.setVisibility(View.VISIBLE);
                detailsBinding.tvOrderDetailsOrderPickKmDuration.setVisibility(View.VISIBLE);
                detailsBinding.imgOrderDetailsPickNavMap.setVisibility(View.INVISIBLE);
                break;

            case AppConstants.swipeStartVisit:
                detailsBinding.tvOrderDetailsOrderState.setText(AppConstants.stateOnTheWay);
                detailsBinding.tvNewOrderSwipe.setText(AppConstants.swipeLocationReached);
                detailsBinding.linearOrderDetailsCustomerDetails.setVisibility(View.VISIBLE);
                detailsBinding.tvOrderDetailsOrderPickKm.setVisibility(View.VISIBLE);
                detailsBinding.tvOrderDetailsOrderPickKmDuration.setVisibility(View.VISIBLE);
                detailsBinding.imgOrderDetailsPickNavMap.setVisibility(View.VISIBLE);
                break;

            case AppConstants.swipeLocationReached:
                detailsBinding.tvOrderDetailsOrderState.setText(AppConstants.stateLocationReached);
                detailsBinding.tvNewOrderSwipe.setText(AppConstants.swipeStartWork);
                detailsBinding.linearOrderDetailsCustomerDetails.setVisibility(View.VISIBLE);
                detailsBinding.tvOrderDetailsOrderPickKm.setVisibility(View.VISIBLE);
                detailsBinding.tvOrderDetailsOrderPickKmDuration.setVisibility(View.VISIBLE);
                detailsBinding.imgOrderDetailsPickNavMap.setVisibility(View.VISIBLE);
                detailsBinding.includeOrderVehicleConfirmation.layoutOrderDetailsVehicleConfirmation
                        .setVisibility(View.VISIBLE);
                showToast("Location Reached");
                break;
        }

        resetSeekBar();
    }


    private void resetSeekBar() {
        detailsBinding.swipeOrderDetails.setProgress(0);
        detailsBinding.swipeOrderDetails.setThumb(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_swipe_right, null));
        detailsBinding.swipeOrderDetails.setThumbOffset(-16);
        detailsBinding.swipeOrderDetails.setEnabled(true);
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
