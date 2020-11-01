package com.example.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

//        View v=getLayoutInflater().inflate(R.layout.dialog_date,null);
//        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date,null);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picker_title)
                .setView(R.layout.dialog_date)
//                .setView(view)
                .setPositiveButton(android.R.string.ok,null).create();

    }
}
