package com.example.criminalintent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import java.util.UUID;

import static com.example.criminalintent.CrimeListFragment.CRIME_ID;

public class CrimeActivity extends SingleFragmentActivity{


    @Override
    protected Fragment createFragment() {

        return CrimeFragment.newInstance((UUID)this.getIntent().getSerializableExtra(CRIME_ID));

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}