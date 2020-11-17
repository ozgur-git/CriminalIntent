package com.example.criminalintent;

import android.app.Application;
import android.content.Context;

public class GlobalVariables extends Application {

    static int count=0;

//    Context mContext;
    CrimeComponent mComponent;

    public CrimeComponent getComponent() {
        return mComponent;
    }

    public GlobalVariables() {
        System.out.println("global variable class is called:"+(++count));
    }
    @Override
    public void onCreate() {
      super.onCreate();
      mComponent=DaggerCrimeComponent.builder().crimeModule(new CrimeModule(getApplicationContext())).build();
  }

}
