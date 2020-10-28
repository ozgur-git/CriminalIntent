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
import android.widget.*;

import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CrimeFragment extends Fragment {

    Logger mLogger=Logger.getLogger("LOGGER_KEY");

    public static final String CRIME_ID_KEY="crime_id";

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckbox;
    private UUID crimeID;

    public static CrimeFragment newInstance(UUID crimeID) {

        Bundle args = new Bundle();

        args.putSerializable(CRIME_ID_KEY,crimeID);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mCrime=new Crime();
//        UUID crimeID=(UUID)getActivity().getIntent().getSerializableExtra(CRIME_ID);
        crimeID=(UUID) getArguments().get(CRIME_ID_KEY);
        mLogger.log(Level.INFO,"id is "+crimeID.toString());

        mCrime=CrimeLab.getCrimeLab().getCrime(crimeID);
    }

    void giveFeedback(){
        Intent intent=new Intent(getActivity(),CrimeListActivity.class);
        intent.putExtra(CRIME_ID_KEY,crimeID);
        getActivity().setResult(Activity.RESULT_OK,intent);
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
        mDateButton.setEnabled(false);

        mSolvedCheckbox.setChecked(mCrime.isSolved());

        mSolvedCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mCrime.setSolved(isChecked);
            giveFeedback();
        });

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

}
