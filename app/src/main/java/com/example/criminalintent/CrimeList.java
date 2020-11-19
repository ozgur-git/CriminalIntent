package com.example.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.criminalintent.database.CrimeBaseHelper;
import com.example.criminalintent.database.CrimeDbSchema;
import com.example.criminalintent.database.CrimeDbSchema.CrimeTable;
import com.example.criminalintent.database.CrimeDbSchema.CrimeTable.Cols;

import java.util.ArrayList;
import java.util.List;

class CrimeList {

    List<Crime> mCrimes;

//    Context mContext;
    private SQLiteDatabase mDatabase;

    static int count=0;

    CrimeList(Context context) {

       mCrimes = new ArrayList<>();

       mDatabase=new CrimeBaseHelper(context).getWritableDatabase();

   }

    List<Crime> getCrimes() {

       Cursor cursor=mDatabase.query(true, CrimeTable.NAME,null,null,null,null,,null,null);

        return mCrimes;
    }

    void addCrime(){

//        mDatabase.insert(CrimeTable.NAME,null,getContentValues(new Crime()));

        mDatabase.execSQL("insert into "+CrimeTable.NAME+getContentValues(new Crime()));

//        mCrimes.add(new Crime());
    }

    void updateCrime(Crime crime){

         mDatabase.execSQL("update "+CrimeTable.NAME+" set "+Cols.UUID+"="+crime.getId().toString()+" where ");
    }

    void removeCrime(int index){

        mCrimes.remove(index);
    }

    private static ContentValues getContentValues(Crime crime){

        ContentValues values=new ContentValues();

        values.put(Cols.UUID,crime.getId().toString());
        values.put(Cols.DATE,crime.getDate());
        values.put(Cols.TITLE,crime.getTitle());
        values.put(Cols.SOLVED,crime.isSolved());

        return values;

    }
}
