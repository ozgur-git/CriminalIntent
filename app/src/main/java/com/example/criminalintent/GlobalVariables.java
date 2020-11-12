package com.example.criminalintent;

import android.app.Application;

public class GlobalVariables extends Application {


    CrimeComponent mComponent=DaggerCrimeComponent.builder().crimeModule(new CrimeModule()).build();

    public CrimeComponent getComponent() {
        return mComponent;
    }
}
