package com.oviahealth.assignment.Movie;

import android.app.Application;

public class OviaHealthApplication extends Application {

    private static OviaHealthApplication _CONTEXT = null;

    @Override
    public void onCreate() {
        super.onCreate();

        _CONTEXT = (OviaHealthApplication)this.getApplicationContext();
    }

    public static OviaHealthApplication getContext(){
        return _CONTEXT;
    }
}