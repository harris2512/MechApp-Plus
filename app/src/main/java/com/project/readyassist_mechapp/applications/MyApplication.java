package com.project.readyassist_mechapp.applications;

import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication mInstance;


    @Override
    public void onCreate() {
        super.onCreate();

        /*Initialize the Instance*/
        mInstance = this;


    }


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

}
