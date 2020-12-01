package com.example.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import static com.example.criminalintent.DatePickerFragment.DATE_EXTRA;

public class CrimeFragment extends Fragment {

    Logger mLogger=Logger.getLogger("LOGGER_KEY");

    private static final String CRIME_ID_KEY="crime_id";
    private static final String DIALOG_DATE="dialog_date";
    public static final int REQUEST_DATE=0;
    private static final int REQUEST_CONTACT=1;

    @Inject
    CrimeList mCrimeList;

    private Crime mCrime;
    CrimeComponent mComponent;

    private EditText mTitleField;

    private Button mDateButton;
    private Button mReportButton;
    private Button mSuspectButton;
    private Button mCallSuspectButton;

    private CheckBox mSolvedCheckbox;

    private int crimeIndex;


    public static CrimeFragment newInstance(int crimeIndex) {

        Bundle args = new Bundle();

        args.putInt(CRIME_ID_KEY,crimeIndex);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GlobalVariables globalVariables=(GlobalVariables)getActivity().getApplicationContext();
        mComponent=globalVariables.getComponent();
        mComponent.inject(this);
        crimeIndex= (int)getArguments().get(CRIME_ID_KEY);

        mCrime=mCrimeList.getCrimes().get(crimeIndex);

    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
       View v=View.inflate(getContext(),R.layout.fragment_crime,null);

       setHasOptionsMenu(true);

        mSolvedCheckbox=v.findViewById(R.id.crime_solved);
        mDateButton=v.findViewById(R.id.crime_date);
        mTitleField=v.findViewById(R.id.crime_title);
        mReportButton=v.findViewById(R.id.crime_report);


        mTitleField.setText(mCrime.getTitle());
        mDateButton.setText(mCrime.getDate());
        mDateButton.setEnabled(true);
        mDateButton.setOnClickListener((view)-> startActivityForResult(DatePickerFragmentActivity.newIntent(getActivity(),mCrime.getCrimeDate()),0));

        mSolvedCheckbox.setChecked(mCrime.isSolved());

        mSolvedCheckbox.setOnCheckedChangeListener((buttonView, isChecked)->mCrime.setSolved(isChecked));

        PackageManager packageManager=getActivity().getPackageManager();


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

        mReportButton.setOnClickListener((view)->{

            Intent intent=ShareCompat.IntentBuilder.from(getActivity()).getIntent();

            intent.setType("text/plain");

            //Intent intent=new Intent(Intent.ACTION_SEND);
            //intent.setType("text/plain");

            intent.putExtra(Intent.EXTRA_TEXT,getCrimeReport());
            intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.crime_report_suspect));
            intent=Intent.createChooser(intent,getString(R.string.send_report));
            startActivity(intent);

        });

        Intent pickContact=new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

        mSuspectButton=v.findViewById(R.id.crime_suspect);
        mSuspectButton.setOnClickListener((view)-> startActivityForResult(pickContact,REQUEST_CONTACT));

        if (mCrime.getSuspect() != null){

            mSuspectButton.setText(mCrime.getSuspect().getSuspectName());
        }

        if (packageManager.resolveActivity(pickContact,PackageManager.MATCH_DEFAULT_ONLY)==null)
            mSuspectButton.setEnabled(false);

        mCallSuspectButton.findViewById(R.id.call_suspect);
        mCallSuspectButton.setOnClickListener((view)->{

            Uri uri=ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

            String[] columns={ContactsContract.CommonDataKinds.Phone.NUMBER};

            String where=ContactsContract.Data.CONTACT_ID+"=?";

            String[] selectionArgs={((Integer)mCrime.getSuspect().getSuspectContactsID()).toString()};//todo String.valueof

            Cursor cursor=getActivity().getContentResolver().query(uri,columns,where,selectionArgs,null);




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

        else if (requestCode==REQUEST_CONTACT && data!=null){

            Uri contactUri=data.getData();

            String[] queryFields=new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME,ContactsContract.Contacts._ID
            };

            Cursor cursor=getActivity().getContentResolver().query(contactUri,queryFields,null,null,null);

            cursor.moveToFirst();

            mCrime.getSuspect().setSuspectName(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

            mCrime.getSuspect().setSuspectContactsID(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID);

            mSuspectButton.setText(mCrime.getSuspect().getSuspectName());

        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime,menu);

   }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.delete_crime:
                                    mCrimeList.removeCrime(crimeIndex,mCrime.getId());//todo baska column gerekiyor
                                    getActivity().finish();
//                                    return true;
            default:
                                    return super.onOptionsItemSelected(item);

        }
    }

    private String getCrimeReport(){

        String solvedString=null;

        if (mCrime.isSolved()){

            solvedString=getString(R.string.crime_report_solved);
        }

        else {

            solvedString = getString(R.string.crime_report_unsolved);
        }

        SimpleDateFormat dateFormat=new SimpleDateFormat("EEEE, MMM dd");

        String dateString=dateFormat.format(mCrime.getCrimeDate());

        String suspect=mCrime.getSuspect().getSuspectName();

        if (suspect==null) {

            suspect = getString(R.string.crime_report_no_suspect);
        }

        else {
            suspect = getString(R.string.crime_report_suspect);
        }

        String report=getString(R.string.crime_report,mCrime.getTitle(),dateString,solvedString,suspect);

        return report;

    }
    @Override
    public void onPause() {
        super.onPause();
        mCrimeList.updateCrime(mCrime);

    }
    
}
