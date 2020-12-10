package com.example.criminalintent;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import javax.inject.Inject;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static com.example.criminalintent.DatePickerFragment.DATE_EXTRA;

public class CrimeFragment extends Fragment {

    Logger mLogger=Logger.getLogger("LOGGER_KEY");

    private static final String CRIME_ID_KEY="crime_id";
    public static final int REQUEST_DATE=0;
    private static final int REQUEST_CONTACT=1;
    private static final int REQUEST_PHOTO=2;
    private int crimeIndex;

    private File mPhotoFile;

    @Inject
    CrimeList mCrimeList;

    private Crime mCrime;
    CrimeComponent mComponent;

    private EditText mTitleField;

    private Button mDateButton;
    private Button mReportButton;
    private Button mSuspectButton;
    private Button mCallSuspectButton;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private CheckBox mSolvedCheckbox;

    Intent captureImage;

    int photoViewHeight,photoViewWidth;

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

        mPhotoFile=mCrimeList.getPhotoFile(mCrime);

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
        mSuspectButton=v.findViewById(R.id.crime_suspect);
        mCallSuspectButton=v.findViewById(R.id.call_suspect);
        mPhotoButton=v.findViewById(R.id.crime_camera);
        mPhotoView=v.findViewById(R.id.crime_photo);
        mPhotoView.setRotation(90);

//        ViewTreeObserver observer=mPhotoView.getViewTreeObserver();
//
//        observer.addOnGlobalLayoutListener(()->{
//
//            photoViewHeight=mPhotoView.getLayoutParams().height;
//
//            photoViewWidth=mPhotoView.getLayoutParams().width;
//
//            updatePhotoView();
//        });
        updatePhotoView();

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

        mSuspectButton.setOnClickListener((view)-> startActivityForResult(pickContact,REQUEST_CONTACT));

//        if (mCrime.getSuspect().getSuspectName() != null){
//
//            mSuspectButton.setText(mCrime.getSuspect().getSuspectName());
//        }

        if (packageManager.resolveActivity(pickContact,PackageManager.MATCH_DEFAULT_ONLY)==null) {

            mSuspectButton.setEnabled(false);
        }

        mCallSuspectButton.setEnabled(false);

        mCallSuspectButton.setOnClickListener((view)->{

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CONTACTS},1);
            }

            else {
                Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

                String[] columns = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                String where = ContactsContract.Data.CONTACT_ID + "=?";

                String[] selectionArgs = {(mCrime.getSuspect().getSuspectContactsID())};

                Cursor cursor = getActivity().getContentResolver().query(uri, columns, where, selectionArgs, null);

                cursor.moveToFirst();

                mLogger.info("phone number of the suspect is " + cursor.getString(0));

                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +cursor.getString(0)));

                startActivity(callIntent);
            }

        });

        mPhotoView.setOnClickListener((view)-> {

            mLogger.info("photo view is clicked!");
            ZoomFragment fragment=ZoomFragment.newInstance(mPhotoFile.getPath());
            fragment.show(getFragmentManager(),"zoom");

        });


        captureImage=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto=mPhotoFile!=null&&captureImage.resolveActivity(getActivity().getPackageManager())!=null;

        mPhotoButton.setEnabled(canTakePhoto);

        mPhotoButton.setOnClickListener((view)->{

            Uri uri= FileProvider.getUriForFile(getActivity(),"com.example.criminalintent.myprovider",mPhotoFile);

            captureImage.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_READ_URI_PERMISSION);

            captureImage.putExtra(MediaStore.EXTRA_OUTPUT,uri);

            List<ResolveInfo> cameraActivities=getActivity().getPackageManager().queryIntentActivities(captureImage,PackageManager.MATCH_DEFAULT_ONLY);
            for(ResolveInfo activity:cameraActivities){
                getActivity().grantUriPermission(activity.activityInfo.packageName,uri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
          }
         startActivityForResult(captureImage,REQUEST_PHOTO);
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

            mLogger.info("contact id received from uri is "+cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)));

            mCrime.getSuspect().setSuspectName(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

            mCrime.getSuspect().setSuspectContactsID(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)));

            mSuspectButton.setText(mCrime.getSuspect().getSuspectName());

            mCallSuspectButton.setEnabled(true);

        }

        else if (requestCode==REQUEST_PHOTO){

            Uri uri= FileProvider.getUriForFile(getActivity(),"com.example.criminalintent.myprovider",mPhotoFile);

            getActivity().revokeUriPermission(uri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhotoView();
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

        String solvedString;

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

        return getString(R.string.crime_report,mCrime.getTitle(),dateString,solvedString,suspect);

    }

    private void updatePhotoView(){

        if (mPhotoFile==null||!mPhotoFile.exists()){
            mPhotoView.setImageDrawable(null);
            mLogger.info("onactivity result width and heigt is ");

        } else{

            ViewTreeObserver observer=mPhotoView.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(()->{
                photoViewHeight=mPhotoView.getLayoutParams().height;
                photoViewWidth=mPhotoView.getLayoutParams().width;
            mLogger.info("onactivity result width and heigt is "+photoViewWidth+", "+photoViewHeight);
                Bitmap bitmap=PictureUtils.getScaledBitmap(mPhotoFile.getPath(),photoViewWidth,photoViewHeight);
                mPhotoView.setImageBitmap(bitmap);
            });

//            Bitmap bitmap=PictureUtils.getScaledBitmap(mPhotoFile.getPath(),getActivity());

        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mCrimeList.updateCrime(mCrime);

    }
    
}
