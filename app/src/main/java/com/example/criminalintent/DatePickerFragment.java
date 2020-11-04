package com.example.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import static com.example.criminalintent.CrimeFragment.REQUEST_DATE;

public class DatePickerFragment extends DialogFragment {

    static Logger mLogger=Logger.getLogger("DatePickerFragment");

    public static final String DATE_EXTRA="date_extra";

    private static final String ARG_DATE="date";

    private Date mDate;

    public static DatePickerFragment newInstance(Date date){

        Bundle args=new Bundle();

        mLogger.info("received date is "+date);
        args.putSerializable(ARG_DATE,date);

        mLogger.info("received args is "+args.getSerializable(ARG_DATE));

        DatePickerFragment fragment=new DatePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        mLogger.info("oncreatedialog received is "+(Date)getArguments().getSerializable(ARG_DATE));

        mDate=(Date)getArguments().getSerializable(ARG_DATE);

        mLogger.info("oncreatedialog "+mDate);

        View view=getActivity().getLayoutInflater().inflate(R.layout.dialog_date,null);

        DatePicker datePicker=(DatePicker)view;

        datePicker.init(mDate.getYear(),mDate.getMonth(),mDate.getDay(),null);

//        View view=getActivity().getLayoutInflater().inflate(R.layout.dialog_date,null);
//        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date,null);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picker_title)
//                .setView(R.layout.dialog_date)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDate=new GregorianCalendar(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth()).getTime();
//                        Intent intent=new Intent(getActivity(),CrimePagerActivity.class);
                        Intent intent=new Intent();
                        intent.putExtra(DATE_EXTRA,mDate);
                        getTargetFragment().onActivityResult(REQUEST_DATE, Activity.RESULT_OK,intent);

                    }
                }).create();

    }

}
