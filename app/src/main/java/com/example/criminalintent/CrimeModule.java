package com.example.criminalintent;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class CrimeModule {

//    Context context;

//    public CrimeModule(Context context) {
//        this.context = context;
//    }
//
    @Provides
    Crime provideCrime(){

        return new Crime();
    }

    @Provides
    @Singleton
    CrimeList providesCrimeList(){

        CrimeList mCrimes=new CrimeList();

        return mCrimes;
    }

}
