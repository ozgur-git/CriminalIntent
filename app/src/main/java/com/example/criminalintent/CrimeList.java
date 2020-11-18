package com.example.criminalintent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.criminalintent.database.CrimeBaseHelper;

import java.util.ArrayList;
import java.util.List;

class CrimeList {

    List<Crime> mCrimes;

    Context mContext;
    private SQLiteDatabase mDatabase;

    static int count=0;

    CrimeList(Context context) {

       mCrimes = new ArrayList<>();

       if (context==null) System.out.println("context is null");

       mContext=context;

       mDatabase=new CrimeBaseHelper(context).getWritableDatabase();

       System.out.println("count is "+(++count));

//        for(int i=0;i<7;i++)
//        {
//
//            Crime newCrime=new Crime();
//            newCrime.setTitle("Crime #"+(i+1));
//            newCrime.setRequiresPolice((1==(i%3)));
//            newCrime.setSolved(1==(i%2));
//            mCrimes.add(newCrime);
//        }
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
