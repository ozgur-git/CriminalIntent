package com.example.criminalintent;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ArrayList;
import java.util.List;

public class CrimeList {

    @Inject
    Provider<Crime> obj;

    List<Crime> mCrimes = new ArrayList<>();

    static int count=0;

    public CrimeList() {

       System.out.println("count is "+(++count));

        for(int i=0;i<7;i++)
        {

            Crime newCrime=obj.get();
//            Crime newCrime=new Crime();
            newCrime.setTitle("Crime #"+(i+1));
            newCrime.setRequiresPolice((1==(i%3)));
            newCrime.setSolved(1==(i%2));
            mCrimes.add(newCrime);
        }
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public void addCrime(){

        mCrimes.add(new Crime());
    }
}
