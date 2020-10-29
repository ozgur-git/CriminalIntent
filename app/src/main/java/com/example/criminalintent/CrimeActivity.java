package com.example.criminalintent;

import androidx.fragment.app.Fragment;

import static com.example.criminalintent.CrimeListFragment.CRIME_ID;

public class CrimeActivity extends SingleFragmentActivity{


    @Override
    protected Fragment createFragment() {

//        return CrimeFragment.newInstance((UUID)this.getIntent().getSerializableExtra(CRIME_ID));
        return CrimeFragment.newInstance(this.getIntent().getIntExtra(CRIME_ID,0));

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}