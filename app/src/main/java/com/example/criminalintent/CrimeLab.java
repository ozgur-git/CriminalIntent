package com.example.criminalintent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrimeLab {

    Logger mLogger=Logger.getLogger(getClass().getName());

    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimes;

    private CrimeLab() {
        mCrimes = new ArrayList<>();
        for(int i=0;i<100;i++)
        {
            Crime crime=new Crime();
            crime.setTitle("Crime #"+i);
            crime.setRequiresPolice((1==(i%3)));
            crime.setSolved(1==(i%2));
            mCrimes.add(crime);
        }
        mLogger.log(Level.INFO,"size of crimes is "+mCrimes.size());
    }

    public Crime getCrime(UUID uuid){

        for(Crime w:mCrimes)
            if (w.getId()==uuid) return w;

        return null;

    }

    public static CrimeLab getCrimeLab(){

        if (sCrimeLab==null) sCrimeLab=new CrimeLab();

        return sCrimeLab;
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }
}