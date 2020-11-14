package com.example.criminalintent;

import android.app.Application;
import android.content.Context;

public class GlobalVariables extends Application {

//    Context mContext;
    CrimeComponent mComponent=DaggerCrimeComponent.builder().crimeModule(new CrimeModule()).build();

    public CrimeComponent getComponent() {
        return mComponent;
    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        mContext=getApplicationContext();
//    }

}
