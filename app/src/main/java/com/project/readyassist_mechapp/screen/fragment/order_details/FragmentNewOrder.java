package com.project.readyassist_mechapp.screen.fragment.order_details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.helper.SessionManager;


public class FragmentNewOrder extends Fragment {

    protected View view;
    protected Fragment selectedFragment;
    protected SessionManager sessionManager;

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

                if (seekBar.getProgress() > 90) {
                    seekBar.setThumb(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.ic_swipe_right_green, null));
                    seekBar.setEnabled(false);
                    return;
                }

                seekBar.setProgress(0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress > 90) {
                    seekBar.setThumb(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.ic_swipe_right_green, null));
                    seekBar.setEnabled(false);
                }

            }
        });


    }


}
