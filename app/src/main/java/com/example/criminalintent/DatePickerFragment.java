package com.example.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Date;
import java.util.logging.Logger;

public class DatePickerFragment extends DialogFragment {

    static Logger mLogger=Logger.getLogger("DatePickerFragment");

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
                .setPositiveButton(android.R.string.ok,null).create();

    }
}
