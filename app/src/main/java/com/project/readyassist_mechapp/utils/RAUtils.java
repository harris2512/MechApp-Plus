package com.project.readyassist_mechapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;


import com.project.readyassist_mechapp.R;

import java.util.Objects;


public class RAUtils {

    public static Dialog progressDialog;
    public static AlertDialog alertDialog;

    public static void showProgressDialog(Context context, boolean isShow) {
        if (!((Activity) context).isFinishing()) {


            progressDialog = new Dialog(context);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
            progressDialog.setContentView(R.layout.dialog_progressbar);
            progressDialog.setCancelable(false);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(progressDialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

            Objects.requireNonNull(progressDialog.getWindow())
                    .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            /*showPb*/
            if (isShow) {
                progressDialog.show();
                progressDialog.getWindow().setAttributes(lp);
            }


        }
    }

    public static void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


}
