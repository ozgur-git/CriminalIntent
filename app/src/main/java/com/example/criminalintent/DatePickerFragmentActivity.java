package com.example.criminalintent;

import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.Fragment;

import java.util.Date;

public class DatePickerFragmentActivity extends SingleFragmentActivity{

    private static final String DATE_EXTRA="date";


    public static Intent newIntent(Context context, Date date){

        Intent intent=new Intent(context,DatePickerFragmentActivity.class);

        intent.putExtra(DATE_EXTRA,date);

        return intent;
    }

    @Override
    protected Fragment createFragment() {

        return  DatePickerFragment.newInstance((Date)getIntent().getSerializableExtra(DATE_EXTRA));

    }
}
