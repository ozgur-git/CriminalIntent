package com.example.criminalintent;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Module
public class CrimeModule {

    @Provides
    Crime provideCrime(){

        return new Crime();
    }

    @Provides
    @Singleton
    List<Crime> providesCrimeList(){

        List<Crime> mCrimes = new ArrayList<>();
        for(int i=0;i<7;i++)
        {
            Crime crime=new Crime();
            crime.setTitle("Crime #"+(i+1));
            crime.setRequiresPolice((1==(i%3)));
            crime.setSolved(1==(i%2));
            mCrimes.add(crime);
        }

        return mCrimes;
    }

}
