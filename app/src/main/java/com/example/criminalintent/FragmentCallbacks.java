package com.example.criminalintent;

public interface FragmentCallbacks {

    void onCrimeUpdated(int crimeIndex);
    void onCrimeDeleted(CrimeFragment crimeFragment);
}
