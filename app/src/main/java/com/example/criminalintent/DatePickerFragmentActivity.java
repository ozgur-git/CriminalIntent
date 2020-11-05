package com.example.criminalintent;

import androidx.fragment.app.Fragment;

import java.util.Date;

public class DatePickerFragmentActivity extends SingleFragmentActivity{
    //TODO crime fragment dan intent extra ile date gelecek
    @Override
    protected Fragment createFragment() {

        return  DatePickerFragment.newInstance(new Date());

    }
}
