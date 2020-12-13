package com.example.criminalintent;

import android.content.Intent;
import androidx.fragment.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity implements Callbacks{

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    public void onCrimeUpdated(int crimeIndex) {

       if (findViewById(R.id.detail_fragment_container)==null){

           Intent intent=CrimePagerActivity.newIntent(this,crimeIndex);
           startActivity(intent);
       }

       else {



       }

    }
}
