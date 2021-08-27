package com.project.readyassist_mechapp.utils;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/*
 * Created by Harris on 12/2/2021.
 */

public class MyAppBold extends androidx.appcompat.widget.AppCompatTextView {

    public MyAppBold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/Poppins-Bold.ttf"));

    }
}

