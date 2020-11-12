package com.example.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import javax.inject.Inject;
import java.util.List;

public class CrimePagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Inject
    List<Crime> mCrimes;

    CrimeComponent mComponent;

//    private List<Crime> mCrimes;
    private Button mFirstButton,mLastButton;

    private static final String EXTRA_CRIME_ID="crime_id";

    public static Intent newIntent(Context context,int crimeID){

        Intent intent=new Intent(context,CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mComponent=DaggerCrimeComponent.builder().crimeModule(new CrimeModule()).build();
        mComponent.inject(this);

        setContentView(R.layout.activity_crime_pager);

        mFirstButton=findViewById(R.id.firstPage);
        mLastButton=findViewById(R.id.lastPage);

//        int crimeID=(int)getIntent().getIntExtra(EXTRA_CRIME_ID,0);
        mViewPager=findViewById(R.id.crime_view_pager);

        mFirstButton.setOnClickListener((v)->{
            mViewPager.setCurrentItem(0);
            v.setEnabled(false);
        });

        mLastButton.setOnClickListener((v)->{
            mViewPager.setCurrentItem(mCrimes.size());
            v.setEnabled(false);
        });

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager(),0) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
//                Crime crime=mCrimes.get(position);
                if (position!=0) mFirstButton.setEnabled(true);

                if (position!=mCrimes.size()) mLastButton.setEnabled(true);

                return CrimeFragment.newInstance(position);
            }

            @Override
            public int getItemPosition(@NonNull Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        mViewPager.setCurrentItem((int)getIntent().getIntExtra(EXTRA_CRIME_ID,0));

    }
}