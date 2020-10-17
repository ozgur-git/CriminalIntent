package com.example.criminalintent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimes;

    private CrimeLab() {
        mCrimes = new ArrayList<>();
        for(int i=0;i<100;i++)
        {
            Crime crime=new Crime();
            crime.setTitle("Crime #"+i);
            crime.setSolved(1==(i%2));
            mCrimes.add(crime);
        }
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
}
