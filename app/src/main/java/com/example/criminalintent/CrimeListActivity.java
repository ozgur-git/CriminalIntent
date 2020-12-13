package com.example.criminalintent;

import androidx.fragment.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks{

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    public void onCrimeUpdated(Crime crime) {

        CrimeListFragment listFragment=(CrimeListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        listFragment.updateUI();

    }
}
