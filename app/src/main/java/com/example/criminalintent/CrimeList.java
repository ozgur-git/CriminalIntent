package com.example.criminalintent;

import java.util.ArrayList;
import java.util.List;

public class CrimeList {

    List<Crime> mCrimes = new ArrayList<>();

    static int count=0;

    public CrimeList() {

        System.out.println("count is "+(++count));

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
}
