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
        for(int i=0;i<5;i++)
        {
            Crime crime=new Crime();
            crime.setTitle("Crime #"+(i+1));
            crime.setRequiresPolice((1==(i%3)));
            crime.setSolved(1==(i%2));
            mCrimes.add(crime);
        }
        mLogger.log(Level.INFO,"size of crimes is "+mCrimes.size());
    }

    public Crime getCrime(int index){

        return mCrimes.get(index);

    }

//    public Crime getCrime(UUID uuid){
//
//        mLogger.log(Level.INFO,"uuid is "+uuid);
//
//        for(Crime w:mCrimes)
//            if (w.getId().equals(uuid)) {
//
//                mLogger.log(Level.INFO,"found crime title is "+w.getTitle());
//                return w;
//            }
//
//            return null;
//
//    }

    public static CrimeLab getCrimeLab(){

        if (sCrimeLab==null) sCrimeLab=new CrimeLab();

        return sCrimeLab;
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    void addCrime(Crime crime){

        mCrimes.add(crime);

    }
}
