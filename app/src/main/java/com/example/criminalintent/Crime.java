package com.example.criminalintent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private boolean mRequiresPolice;

    public Crime() {
        mDate = new Date();
        mId=UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getDate() {

        SimpleDateFormat dateFormat=new SimpleDateFormat("E. MMM dd, YYYY");

        return dateFormat.format(mDate);

    }

    public Date getCrimeDate(){

        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setRequiresPolice(boolean requiresPolice) {
        mRequiresPolice = requiresPolice;
    }

    public boolean isRequiresPolice() {
        return mRequiresPolice;
    }
}
