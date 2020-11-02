package com.example.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Date;
import java.util.logging.Logger;

import static com.example.criminalintent.DatePickerFragment.DATE_EXTRA;


public class CrimeFragment extends Fragment {

    Logger mLogger=Logger.getLogger("LOGGER_KEY");

    private static final String CRIME_ID_KEY="crime_id";
    private static final String DIALOG_DATE="dialog_date";
    public static final int REQUEST_DATE=0;


    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckbox;
    private int crimeIndex;

    public static CrimeFragment newInstance(int crimeIndex) {

        Bundle args = new Bundle();

        args.putInt(CRIME_ID_KEY,crimeIndex);
//        args.putSerializable(CRIME_ID_KEY,crimeIndex);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        crimeIndex= (int)getArguments().get(CRIME_ID_KEY);
//        crimeID=(UUID) getArguments().get(CRIME_ID_KEY);
//        mLogger.log(Level.INFO,"id is "+crimeID.toString());

        mCrime=CrimeLab.getCrimeLab().getCrime(crimeIndex);
//        mCrime=CrimeLab.getCrimeLab().getCrime(crimeID);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
//        View v=inflater.inflate(R.layout.fragment_crime,container,false);
       View v=View.inflate(getContext(),R.layout.fragment_crime,null);

        mSolvedCheckbox=v.findViewById(R.id.crime_solved);
        mDateButton=v.findViewById(R.id.crime_date);
        mTitleField=v.findViewById(R.id.crime_title);


        mTitleField.setText(mCrime.getTitle());
        mDateButton.setText(mCrime.getDate());
        mDateButton.setEnabled(true);
        mDateButton.setOnClickListener((view)->{
//            DialogFragment dialogFragment=new DatePickerFragment();
            DatePickerFragment dialog=DatePickerFragment.newInstance(mCrime.getCrimeDate());
            dialog.show(getFragmentManager(),DIALOG_DATE);
            dialog.setTargetFragment(this,REQUEST_DATE);
//            dialogFragment.show(getfragmentmanager(),dialog_date);

        });

        mSolvedCheckbox.setChecked(mCrime.isSolved());

        mSolvedCheckbox.setOnCheckedChangeListener((buttonView, isChecked)->mCrime.setSolved(isChecked));

        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_DATE&&resultCode== Activity.RESULT_OK) {
            mCrime.setDate((Date)data.getSerializableExtra(DATE_EXTRA));
            mDateButton.setText(mCrime.getDate());
        }
    }
}
