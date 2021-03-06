package com.example.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import javax.inject.Inject;
import java.util.logging.Logger;

public class CrimePagerActivity extends AppCompatActivity implements FragmentCallbacks{

    Logger mLogger= Logger.getLogger(getClass().getName());

    private ViewPager mViewPager;

    @Inject
    CrimeList mCrimes;

    CrimeComponent mComponent;

    private Button mFirstButton,mLastButton;

    private static final String EXTRA_CRIME_ID="crime_id";

    public static Intent newIntent(Context context,int crimeID){

        System.out.println("inside static intent is "+crimeID);
        Intent intent=new Intent(context,CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GlobalVariables globalVariables=(GlobalVariables)getApplicationContext();
        mComponent=globalVariables.getComponent();
        mComponent.inject(this);

        setContentView(R.layout.activity_crime_pager);

        mFirstButton=findViewById(R.id.firstPage);
        mLastButton=findViewById(R.id.lastPage);
        mViewPager=findViewById(R.id.crime_view_pager);

        mFirstButton.setOnClickListener((v)->{
            mViewPager.setCurrentItem(0);
            v.setEnabled(false);
        });

        mLastButton.setOnClickListener((v)->{
            mViewPager.setCurrentItem(mCrimes.getCrimes().size());
            v.setEnabled(false);
        });

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager(),0) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
//                Crime crime=mCrimes.get(position);
                if (position!=0) mFirstButton.setEnabled(true);

                if (position!=mCrimes.getCrimes().size()) mLastButton.setEnabled(true);

                return CrimeFragment.newInstance(position);
            }

            @Override
            public int getItemPosition(@NonNull Object object) {

//                return super.getItemPosition(object);
                return POSITION_NONE;

            }

            @Override
            public int getCount() {
                return mCrimes.getCrimes().size();
            }
        });


        mLogger.info("crime pager actvivity intent is "+getIntent().getIntExtra(EXTRA_CRIME_ID,0));
        mViewPager.setCurrentItem(getIntent().getIntExtra(EXTRA_CRIME_ID,0));

    }

    @Override
    public void onCrimeUpdated(int crimeIndex) {

    }

    @Override
    public void onCrimeDeleted(CrimeFragment crimeFragment) {

        finish();

    }

}