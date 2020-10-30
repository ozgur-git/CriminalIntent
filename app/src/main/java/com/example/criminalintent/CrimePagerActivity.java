package com.example.criminalintent;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class CrimePagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    private static final String EXTRA_CRIME_ID="crime_id";

    public static Intent newIntent(Context context,int crimeID){

        Intent intent=new Intent(context,CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

//        int crimeID=(int)getIntent().getIntExtra(EXTRA_CRIME_ID,0);
        mCrimes=CrimeLab.getCrimeLab().getCrimes();

        mViewPager=findViewById(R.id.crime_view_pager);


        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager(),0) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
//                Crime crime=mCrimes.get(position);
                return CrimeFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        mViewPager.setCurrentItem((int)getIntent().getIntExtra(EXTRA_CRIME_ID,0));

    }
}