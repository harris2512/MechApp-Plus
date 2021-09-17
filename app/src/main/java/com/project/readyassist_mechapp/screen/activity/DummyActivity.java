package com.project.readyassist_mechapp.screen.activity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.databinding.ActivityDummyBinding;
import com.project.readyassist_mechapp.helper.SlideToUnlock;

public class DummyActivity extends AppCompatActivity {


    private Toast toast;

    protected ActivityDummyBinding dummyBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            setTheme(R.style.status_bar_black);
        }

        dummyBinding = ActivityDummyBinding.inflate(getLayoutInflater());
        setContentView(dummyBinding.getRoot());

        init();

    }

    protected void init() {


        dummyBinding.slideToUnlock.setExternalListener(new SlideToUnlock.OnSlideToUnlockEventListener() {
            @Override
            public void onSlideToUnlockCanceled() {
                showToast("Canceled");
            }

            @Override
            public void onSlideToUnlockDone() {
                showToast("Unlocked");
            }
        });

        dummyBinding.slideToUnlock2.setExternalListener(new SlideToUnlock.OnSlideToUnlockEventListener() {
            @Override
            public void onSlideToUnlockCanceled() {
                showToast("Canceled");
            }

            @Override
            public void onSlideToUnlockDone() {
                showToast("Unlocked");
            }
        });


    }


    private void showToast(String text) {
        if (toast != null) {
            toast.cancel();
        }

        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }


}
