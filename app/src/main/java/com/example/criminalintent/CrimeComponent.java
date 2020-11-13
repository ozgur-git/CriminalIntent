package com.example.criminalintent;

import dagger.Component;

import javax.inject.Singleton;

@Component(modules = CrimeModule.class)
@Singleton
public interface CrimeComponent {

    void inject(CrimeListFragment crimeListFragment);
    void inject(CrimePagerActivity crimePagerActivity);
    void inject(CrimeFragment crimeragment);
    void inject(CrimeList crimeList);
//    void inject();

}
