package com.example.criminalintent;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        mViewPager=findViewById(R.id.crime_view_pager);

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
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


    }
}