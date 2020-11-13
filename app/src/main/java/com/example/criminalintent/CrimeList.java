package com.example.criminalintent;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CrimeList {

    @Inject
    Crime newCrime;

    CrimeComponent mComponent;
    List<Crime> mCrimes = new ArrayList<>();

    static int count=0;

    public CrimeList() {

        System.out.println("count is "+(++count));
        GlobalVariables globalVariables=getApplicationContext();
        mComponent=globalVariables.getComponent();
        mComponent.inject(this);
/
        for(int i=0;i<7;i++)
        {
            Crime crime=new Crime();
            crime.setTitle("Crime #"+(i+1));
            crime.setRequiresPolice((1==(i%3)));
            crime.setSolved(1==(i%2));
            mCrimes.add(crime);
        }
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public void addCrime(Crime crime){

        mCrimes.add(crime);
    }
}
