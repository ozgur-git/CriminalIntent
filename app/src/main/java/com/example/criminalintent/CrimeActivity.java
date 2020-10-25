package com.example.criminalintent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

public class CrimeActivity extends SingleFragmentActivity{


    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }


}