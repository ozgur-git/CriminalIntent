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
import java.util.logging.Logger;

class CrimeList {

    Logger mLogger=Logger.getLogger(getClass().getName());

    List<Crime> mCrimes;

//    Context mContext;
    private SQLiteDatabase mDatabase;

    static int count=0;

    CrimeList(Context context) {

       mCrimes = new ArrayList<>();

       mDatabase=new CrimeBaseHelper(context).getWritableDatabase();

   }

    List<Crime> getCrimes() {

       Cursor cursor=mDatabase.query(CrimeTable.NAME,null,null,null,null,null,null);

//       Cursor cursor=mDatabase.rawQuery("select * from "+CrimeTable.NAME,null);

       while (cursor.moveToNext()){

        mLogger.info("database title is "+cursor.getString(cursor.getColumnIndex(Cols.TITLE)));


       }

        return mCrimes;
    }

    void addCrime(){

        Crime c=new Crime();
        c.setTitle("dummy");

        mDatabase.execSQL("insert into "+CrimeTable.NAME+" values('"+c.getTitle()+"','"+c.getDate()+"','"+c.isSolved()+"');");
//        mDatabase.execSQL("insert into "+CrimeTable.NAME+" values("+c.getId()+","+c.getTitle()+","+c.getDate()+","+c.isSolved()+")");
//        mDatabase.insert(CrimeTable.NAME,null,getContentValues(new Crime()));

        mCrimes.add(new Crime());
    }

    void updateCrime(Crime crime){

         mDatabase.execSQL("update "+CrimeTable.NAME+
//                             " set "+Cols.UUID+"="+crime.getId()+","+
                             " set "+Cols.TITLE+"="+crime.getTitle()+","+
                             " set "+Cols.DATE+"="+crime.getDate()+","+
                             " set "+Cols.SOLVED+"="+crime.isSolved()+" where "+
                             Cols.UUID+"="+crime.getId());

    }

    void removeCrime(int index){

        mCrimes.remove(index);
    }

    private static ContentValues getContentValues(Crime crime){

        ContentValues values=new ContentValues();

//        values.put(Cols.UUID,crime.getId());
        values.put(Cols.TITLE,crime.getTitle());
        values.put(Cols.DATE,crime.getDate());
        values.put(Cols.SOLVED,crime.isSolved());

        return values;

    }
}
