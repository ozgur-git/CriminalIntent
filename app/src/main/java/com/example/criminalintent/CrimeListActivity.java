package com.example.criminalintent;

import android.content.Intent;
import androidx.fragment.app.Fragment;

import java.util.logging.Logger;

public class CrimeListActivity extends SingleFragmentActivity implements Callbacks,FragmentCallbacks{

    Logger mLogger=Logger.getLogger(getClass().getName());

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    public void onCrimeSelected(int crimeIndex) {

       if (findViewById(R.id.detail_fragment_container)==null){

           Intent intent=CrimePagerActivity.newIntent(this,crimeIndex);

           startActivity(intent);
       }

       else {

           mLogger.info("two pane is selected!");

           getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment_container,CrimeFragment.newInstance(crimeIndex)).commit();
       }

    }

    @Override
    public void onCrimeUpdated(int crimeIndex) {

    }
}
