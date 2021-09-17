package com.project.readyassist_mechapp.screen.activity.order_details;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.databinding.ActivityOrderDetailsBinding;
import com.project.readyassist_mechapp.helper.AppConstants;
import com.project.readyassist_mechapp.helper.SessionManager;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;


public class OrderDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    protected SessionManager sessionManager;

    protected String selectedPaymentType;

    /*Google Map*/
    protected MapView dialog_map_route;
    protected Marker mCurrentMarker, mPickUpMarker, mDropMarker;

    protected ActivityOrderDetailsBinding detailsBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            setTheme(R.style.status_bar_black);
        }

        detailsBinding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(detailsBinding.getRoot());


        init();

    }

    private void init() {

        orderSwipeGesture();

        /*OnClick*/
        detailsBinding.linearOrderDetailsCustomerDetails.setOnClickListener(this);
        detailsBinding.imgOrderDetailsPickNavMap.setOnClickListener(this);
        detailsBinding.includeOrderDetailsOrderCompleted.imgOrderDetailsDropNavMap.setOnClickListener(this);
        detailsBinding.includeOrderVehicleConfirmation.imgOrderDetailsVehicleNoLike.setOnClickListener(this);
        detailsBinding.includeOrderVehicleConfirmation.imgOrderDetailsVehicleNoDislike.setOnClickListener(this);
        detailsBinding.includeOrderVehicleConfirmation.imgOrderDetailsVehicleNameLike.setOnClickListener(this);
        detailsBinding.includeOrderVehicleConfirmation.imgOrderDetailsVehicleNameDislike.setOnClickListener(this);
        detailsBinding.includeOrderDetailsPayment.tvOrderDetailsPaymentType.setOnClickListener(this);
        detailsBinding.includeOrderDetailsPayment.btnOrderDetailsPaymentSubmit.setOnClickListener(this);
        detailsBinding.includeOrderDetailsOrderCompleted.btnOrderDetailsFailed.setOnClickListener(this);
    }

    private void orderSwipeGesture() {
        detailsBinding.swipeOrderDetails.setThumb(ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_swipe_right, null));
        detailsBinding.swipeOrderDetails.setThumbOffset(-16);

        detailsBinding.swipeOrderDetails.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (seekBar.getProgress() > 90) {
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

                Transition transition = new Slide(Gravity.TOP);
                transition.setDuration(200);
                transition.addTarget(detailsBinding.linearOrderDetailsCustomerDetails);
                TransitionManager.beginDelayedTransition(detailsBinding.linearOrderDetailsPickLocation,
                        transition);

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

                Transition transitionMap = new Slide(Gravity.END);
                transitionMap.setDuration(200);
                transitionMap.addTarget(detailsBinding.imgOrderDetailsPickNavMap);
                TransitionManager.beginDelayedTransition(detailsBinding.includeOrderVehicleConfirmation
                        .layoutOrderDetailsVehicleConfirmation, transitionMap);

                detailsBinding.imgOrderDetailsPickNavMap.setVisibility(View.VISIBLE);
                break;

            case AppConstants.swipeLocationReached:
                detailsBinding.tvOrderDetailsOrderState.setText(AppConstants.stateLocationReached);
                detailsBinding.tvNewOrderSwipe.setText(AppConstants.swipeStartWork);
                detailsBinding.linearOrderDetailsCustomerDetails.setVisibility(View.GONE);
                detailsBinding.linearOrderDetailsPickLocation.setVisibility(View.GONE);
                detailsBinding.tvOrderDetailsOrderPickKm.setVisibility(View.GONE);
                detailsBinding.tvOrderDetailsOrderPickKmDuration.setVisibility(View.GONE);
                detailsBinding.imgOrderDetailsPickNavMap.setVisibility(View.GONE);

                Transition transitions = new Slide(Gravity.TOP);
                transitions.setDuration(200);
                transitions.addTarget(detailsBinding.includeOrderVehicleConfirmation.layoutOrderDetailsVehicleConfirmation);
                TransitionManager.beginDelayedTransition(detailsBinding.layoutOrderDetailsSwipe, transitions);

                detailsBinding.includeOrderVehicleConfirmation.layoutOrderDetailsVehicleConfirmation
                        .setVisibility(View.VISIBLE);
                break;

            case AppConstants.swipeStartWork:
                detailsBinding.tvOrderDetailsOrderState.setText(AppConstants.stateWorkInProgress);
                detailsBinding.tvNewOrderSwipe.setText(AppConstants.swipeCompleted);
                detailsBinding.tvOrderDetailsOrderDate.setVisibility(View.GONE);
                detailsBinding.tvOrderDetailsOrderEta.setVisibility(View.GONE);
                detailsBinding.layoutOrderDetailsVehicleDetails.setVisibility(View.GONE);
                detailsBinding.tvOrderDetailsServiceName.setVisibility(View.GONE);
                detailsBinding.tvOrderDetailsServiceDesc.setVisibility(View.GONE);
                detailsBinding.linearOrderDetailsCustomerDetails.setVisibility(View.GONE);
                detailsBinding.linearOrderDetailsPickLocation.setVisibility(View.GONE);
                detailsBinding.tvOrderDetailsOrderPickKm.setVisibility(View.GONE);
                detailsBinding.tvOrderDetailsOrderPickKmDuration.setVisibility(View.GONE);
                detailsBinding.imgOrderDetailsPickNavMap.setVisibility(View.GONE);
                detailsBinding.includeOrderVehicleConfirmation.layoutOrderDetailsVehicleConfirmation
                        .setVisibility(View.GONE);
                detailsBinding.includeOrderDetailsOrderCompleted.layoutOrderDetailsOrderCompleted
                        .setVisibility(View.VISIBLE);

                break;

            case AppConstants.swipeCompleted:
                detailsBinding.tvOrderDetailsOrderState.setText(AppConstants.stateSuccess);
                detailsBinding.tvNewOrderSwipe.setText(AppConstants.swipeCloseOrder);
                detailsBinding.tvOrderDetailsOrderDate.setVisibility(View.GONE);
                detailsBinding.tvOrderDetailsOrderEta.setVisibility(View.GONE);
                detailsBinding.layoutOrderDetailsVehicleDetails.setVisibility(View.VISIBLE);
                detailsBinding.tvOrderDetailsServiceName.setVisibility(View.VISIBLE);
                detailsBinding.tvOrderDetailsServiceDesc.setVisibility(View.VISIBLE);
                detailsBinding.linearOrderDetailsCustomerDetails.setVisibility(View.GONE);
                detailsBinding.linearOrderDetailsPickLocation.setVisibility(View.GONE);
                detailsBinding.tvOrderDetailsOrderPickKm.setVisibility(View.GONE);
                detailsBinding.tvOrderDetailsOrderPickKmDuration.setVisibility(View.GONE);
                detailsBinding.imgOrderDetailsPickNavMap.setVisibility(View.GONE);
                detailsBinding.includeOrderVehicleConfirmation.layoutOrderDetailsVehicleConfirmation
                        .setVisibility(View.GONE);
                detailsBinding.includeOrderDetailsOrderCompleted.layoutOrderDetailsOrderCompleted
                        .setVisibility(View.GONE);
                detailsBinding.includeOrderDetailsPayment.layoutOrderDetailsPayment
                        .setVisibility(View.VISIBLE);

                break;

            case AppConstants.swipeCloseOrder:
                showToast("Thank you., Order Closed.");
                //  startActivity(new Intent(this, HomePage.class));
                finish();
                break;

        }

        resetSeekBar();
    }

    private void resetSeekBar() {
        detailsBinding.swipeOrderDetails.setProgress(0);
        detailsBinding.swipeOrderDetails.setThumb(ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_swipe_right, null));
        detailsBinding.swipeOrderDetails.setThumbOffset(-16);
        detailsBinding.swipeOrderDetails.setEnabled(true);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.linear_order_details_customer_details:
                dialogConnectCustomer();
                break;

            case R.id.img_order_details_pick_nav_map:
                dialogMapRoute(1);
                break;

            case R.id.img_order_details_drop_nav_map:
                dialogMapRoute(2);
                break;

            case R.id.img_order_details_vehicle_no_like:
                detailsBinding.includeOrderVehicleConfirmation.imgOrderDetailsVehicleNoLike
                        .setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(),
                                R.drawable.ic_oval_green, null));
                detailsBinding.includeOrderVehicleConfirmation.imgOrderDetailsVehicleNoDislike
                        .setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(),
                                R.drawable.ic_oval_white, null));

                break;

            case R.id.img_order_details_vehicle_no_dislike:
                detailsBinding.includeOrderVehicleConfirmation.imgOrderDetailsVehicleNoDislike
                        .setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(),
                                R.drawable.ic_oval_red, null));
                detailsBinding.includeOrderVehicleConfirmation.imgOrderDetailsVehicleNoLike
                        .setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(),
                                R.drawable.ic_oval_white, null));
                break;

            case R.id.img_order_details_vehicle_name_like:
                detailsBinding.includeOrderVehicleConfirmation.imgOrderDetailsVehicleNameLike
                        .setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(),
                                R.drawable.ic_oval_green, null));
                detailsBinding.includeOrderVehicleConfirmation.imgOrderDetailsVehicleNameDislike
                        .setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(),
                                R.drawable.ic_oval_white, null));
                break;

            case R.id.img_order_details_vehicle_name_dislike:
                detailsBinding.includeOrderVehicleConfirmation.imgOrderDetailsVehicleNameDislike
                        .setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(),
                                R.drawable.ic_oval_red, null));
                detailsBinding.includeOrderVehicleConfirmation.imgOrderDetailsVehicleNameLike
                        .setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(),
                                R.drawable.ic_oval_white, null));
                break;

            case R.id.btn_order_details_failed:
                showOrderFailedDialog();
                break;

            case R.id.tv_order_details_payment_type:
                showOrderPaymentTypeDialog();
                break;

            case R.id.btn_order_details_payment_submit:
                detailsBinding.includeOrderDetailsPayment.layoutOrderDetailsPaymentInvoiceDetails
                        .setVisibility(View.VISIBLE);
                break;

        }
    }

    private void showOrderPaymentTypeDialog() {
        BottomSheetDialog sheetDialog = new BottomSheetDialog(this, R.style.TransparentDialog);
        sheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sheetDialog.setContentView(R.layout.dialog_order_details_failed);
        sheetDialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(sheetDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView tv_label_dialog_order_failed_reason = sheetDialog.findViewById(R.id.tv_label_dialog_order_failed_reason);
        ListView list_view_dialog_order_failed = sheetDialog.findViewById(R.id.list_view_dialog_order_failed);
        TextView btn_cancel_dialog_order_failed = sheetDialog.findViewById(R.id.btn_cancel_dialog_order_failed);
        Button btn_submit_dialog_order_failed = sheetDialog.findViewById(R.id.btn_submit_dialog_order_failed);

        tv_label_dialog_order_failed_reason.setText("Choose Payment *");

        selectedPaymentType = "";
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.layout_dialog_order_failed_list_items, R.id.tv_dialog_order_failed_item,
                AppConstants.arrayPaymentType);
        list_view_dialog_order_failed.setAdapter(adapter);
        list_view_dialog_order_failed.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list_view_dialog_order_failed.setOnItemClickListener((adapterView, view, position, id) -> {
            if (list_view_dialog_order_failed.isItemChecked(position)) {
                selectedPaymentType = String.valueOf(adapterView.getItemAtPosition(position));
            }
        });

        btn_submit_dialog_order_failed.setOnClickListener(v -> {

            if (!TextUtils.isEmpty(selectedPaymentType)) {
                showToast(selectedPaymentType);
                detailsBinding.includeOrderDetailsPayment.tvOrderDetailsPaymentType.setText(selectedPaymentType);
                sheetDialog.dismiss();
                return;
            }

            showToast("Choose the payment type!");

        });

        btn_cancel_dialog_order_failed.setOnClickListener(v -> {
            sheetDialog.dismiss();
        });


        sheetDialog.show();
        sheetDialog.getWindow().setAttributes(lp);
    }

    private void showOrderFailedDialog() {
        BottomSheetDialog sheetDialog = new BottomSheetDialog(this, R.style.TransparentDialog);
        sheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sheetDialog.setContentView(R.layout.dialog_order_details_failed);
        sheetDialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(sheetDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ListView list_view_dialog_order_failed = sheetDialog.findViewById(R.id.list_view_dialog_order_failed);
        TextView btn_cancel_dialog_order_failed = sheetDialog.findViewById(R.id.btn_cancel_dialog_order_failed);
        Button btn_submit_dialog_order_failed = sheetDialog.findViewById(R.id.btn_submit_dialog_order_failed);

        ArrayList<String> selectedFailedReason = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.layout_dialog_order_failed_list_items, R.id.tv_dialog_order_failed_item,
                AppConstants.arrayFailedReason);
        list_view_dialog_order_failed.setAdapter(adapter);
        list_view_dialog_order_failed.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list_view_dialog_order_failed.setOnItemClickListener((adapterView, view, position, id) -> {
            if (list_view_dialog_order_failed.isItemChecked(position)) {
                selectedFailedReason.add(String.valueOf(adapterView.getItemAtPosition(position)));
            } else {
                selectedFailedReason.remove(String.valueOf(adapterView.getItemAtPosition(position)));
            }
        });

        btn_submit_dialog_order_failed.setOnClickListener(v -> {

            if (selectedFailedReason.size() > 0) {
                showToast(selectedFailedReason.toString());
                sheetDialog.dismiss();
                return;
            }

            showToast("Choose your failed reason!");

        });

        btn_cancel_dialog_order_failed.setOnClickListener(v -> {
            sheetDialog.dismiss();
        });


        sheetDialog.show();
        sheetDialog.getWindow().setAttributes(lp);
    }

    private void dialogMapRoute(int status) {
        final Dialog dialog = new Dialog(this, R.style.TransparentDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_map_route);
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        /*Initialize*/
        dialog_map_route = dialog.findViewById(R.id.dialog_map_route);
        final Button dialog_map_route_cancel = dialog.findViewById(R.id.dialog_map_route_cancel);
        final Button dialog_map_route_navigate = dialog.findViewById(R.id.dialog_map_route_navigate);

        Bundle mapViewBundle = null;
        dialog_map_route.onCreate(mapViewBundle);
        dialog_map_route.getMapAsync(googleMap -> {

            /*Get User Current Location*/
            if (mCurrentMarker != null) {
                mCurrentMarker.remove();
            }
            mCurrentMarker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(12.9083463, 77.6320718))
                    //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                    .icon(bitmapDescriptorFromVector(this, R.drawable.ic_location, R.color.colorYellowPrimary))
                    .draggable(false)
            );

            PolylineOptions options;
            LatLngBounds.Builder builder;
            LatLngBounds bounds = null;
            switch (status) {
                case 1:
                    /*Get Order Pick Location*/
                    if (mPickUpMarker != null) {
                        mPickUpMarker.remove();
                    }
                    mPickUpMarker = googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(12.8812501, 77.6118152))
                            .icon(bitmapDescriptorFromVector(this, R.drawable.ic_location, R.color.colorGreenPrimaryDark))
                            .draggable(false)
                    );
                    mapRoutePolyline(mCurrentMarker.getPosition(), mPickUpMarker.getPosition());

                    options = new PolylineOptions().width(6).color(Color.GRAY).geodesic(true);
                    options.add(mCurrentMarker.getPosition());
                    options.add(mPickUpMarker.getPosition());
                    googleMap.addPolyline(options);

                    builder = new LatLngBounds.Builder();
                    builder.include(new LatLng(mCurrentMarker.getPosition().latitude,
                            mCurrentMarker.getPosition().longitude));
                    builder.include(new LatLng(mPickUpMarker.getPosition().latitude,
                            mPickUpMarker.getPosition().longitude));
                    bounds = builder.build();

                    break;

                case 2:
                    /*Get Order Drop Location*/
                    if (mDropMarker != null) {
                        mDropMarker.remove();
                    }
                    mDropMarker = googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(12.8812501, 77.6118152))
                            .icon(bitmapDescriptorFromVector(this, R.drawable.ic_location, R.color.colorRedPrimary))
                            .draggable(false)
                    );

                    mapRoutePolyline(mCurrentMarker.getPosition(), mDropMarker.getPosition());

                    options = new PolylineOptions().width(6).color(Color.BLACK).geodesic(true);
                    options.add(mCurrentMarker.getPosition());
                    options.add(mDropMarker.getPosition());
                    googleMap.addPolyline(options);

                    builder = new LatLngBounds.Builder();
                    builder.include(new LatLng(mCurrentMarker.getPosition().latitude,
                            mCurrentMarker.getPosition().longitude));
                    builder.include(new LatLng(mDropMarker.getPosition().latitude,
                            mDropMarker.getPosition().longitude));
                    bounds = builder.build();

                    break;
            }

            /*Set User Current Location*/
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = (int) ((getResources().getDisplayMetrics().heightPixels) * 0.10);
            int padding = (int) (width * 0.10);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, 0));
        });

        dialog_map_route.onStart();


        dialog_map_route_navigate.setOnClickListener(v -> {

            /*Navigate to the Google Map*/
            switch (status) {
                case 1://Pick Location
                    navigateGoogleMap(mCurrentMarker.getPosition().latitude,
                            mCurrentMarker.getPosition().longitude, mPickUpMarker.getPosition().latitude,
                            mPickUpMarker.getPosition().longitude);

                    break;
                case 2://Drop Location
                    navigateGoogleMap(mCurrentMarker.getPosition().latitude,
                            mCurrentMarker.getPosition().longitude, mDropMarker.getPosition().latitude,
                            mDropMarker.getPosition().longitude);

                    break;
            }

            dialog.dismiss();
        });

        dialog_map_route_cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }

    private void navigateGoogleMap(double originLat, double originLng, double desLat, double desLng) {
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)",
                desLat, desLng, "Order Location");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                showToast("Please install the Google Map!");
            }
        }
    }

    private void dialogConnectCustomer() {
        BottomSheetDialog sheetDialog = new BottomSheetDialog(this, R.style.TransparentDialog);
        sheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        sheetDialog.setContentView(R.layout.dialog_bottom_loading);
        sheetDialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(sheetDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView tv_dialog_bottom_loading_title = sheetDialog.findViewById(R.id.tv_dialog_bottom_loading_title);
        TextView tv_dialog_bottom_loading_desc = sheetDialog.findViewById(R.id.tv_dialog_bottom_loading_desc);
        tv_dialog_bottom_loading_title.setText("Connecting..");


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sheetDialog.dismiss();
            }
        }, 6000);

        sheetDialog.show();
        sheetDialog.getWindow().setAttributes(lp);

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void mapRoutePolyline(LatLng startPosition, LatLng endPosition) {
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableId,
                                                        @DrawableRes int color) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableId);
        assert vectorDrawable != null;
        vectorDrawable.setBounds(0, 0, 48, 48);
//        vectorDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

//        Paint paint = new Paint();
//        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.ADD));
//        canvas.drawBitmap(bitmap, 0f, 0f, paint);

        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


}
