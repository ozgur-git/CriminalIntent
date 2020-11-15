package com.example.criminalintent;

import java.util.ArrayList;
import java.util.List;

class CrimeList {

    List<Crime> mCrimes = new ArrayList<>();

    static int count=0;

    CrimeList() {

       System.out.println("count is "+(++count));

        for(int i=0;i<7;i++)
        {

            Crime newCrime=new Crime();
            newCrime.setTitle("Crime #"+(i+1));
            newCrime.setRequiresPolice((1==(i%3)));
            newCrime.setSolved(1==(i%2));
            mCrimes.add(newCrime);
        }
    }

    List<Crime> getCrimes() {
        return mCrimes;
    }

    void addCrime(){

        mCrimes.add(new Crime());
    }

    void removeCrime(int index){

        mCrimes.remove(index);
    }
}
