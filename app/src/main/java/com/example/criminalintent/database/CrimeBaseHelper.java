package com.example.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.example.criminalintent.database.CrimeDbSchema.CrimeTable.*;
import com.example.criminalintent.database.CrimeDbSchema.*;

import java.util.logging.Logger;

public class CrimeBaseHelper extends SQLiteOpenHelper {

    public static final int version=1;
    public static final String DATABASE_NAME="crimeBase.db";

    Logger mLogger=Logger.getLogger(getClass().getName());

    public CrimeBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, version);
        mLogger.info("helper is called");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        mLogger.info("helper oncreate is called");
        db.execSQL("create table " + CrimeTable.NAME + " (" + Cols.UUID + "," + Cols.TITLE + "," + Cols.DATE + "," + Cols.SOLVED+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
