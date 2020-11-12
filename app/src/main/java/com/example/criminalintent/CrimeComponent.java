package com.example.criminalintent;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = CrimeModule.class)
public interface CrimeComponent {

    void inject(CrimeListFragment crimeListFragment);
    void inject(CrimePagerActivity crimePagerActivity);
    void inject(CrimeFragment crimeragment);


}
