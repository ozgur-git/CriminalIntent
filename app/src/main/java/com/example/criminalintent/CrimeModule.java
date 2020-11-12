package com.example.criminalintent;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class CrimeModule {

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
