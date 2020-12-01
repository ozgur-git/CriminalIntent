package com.example.criminalintent;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.criminalintent.database.CrimeBaseHelper;
import com.example.criminalintent.database.CrimeDbSchema.CrimeTable;
import com.example.criminalintent.database.CrimeDbSchema.CrimeTable.Cols;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

class CrimeList {

    Logger mLogger=Logger.getLogger(getClass().getName());

    List<Crime> mCrimes;

    private final SQLiteDatabase mDatabase;

    SimpleDateFormat mSimpleDateFormat;

    CrimeList(Context context) {

        mDatabase=new CrimeBaseHelper(context).getWritableDatabase();

        Cursor cursor=mDatabase.rawQuery("select * from "+CrimeTable.NAME,null);

        mCrimes=new ArrayList<>();

        mSimpleDateFormat=new SimpleDateFormat("EEE. MMM dd, yyy");

        while (cursor.moveToNext()) {

            Crime c = new Crime();

            c.setTitle(cursor.getString(cursor.getColumnIndex(Cols.TITLE)));

            c.setId(UUID.fromString(cursor.getString(cursor.getColumnIndex(Cols.UUID))));

            c.getSuspect().setSuspectName(cursor.getString(cursor.getColumnIndex(Cols.SUSPECT)));

            try {

                String dateString=cursor.getString(cursor.getColumnIndex(Cols.DATE));
                mLogger.info("database read date is "+dateString);
                c.setDate(mSimpleDateFormat.parse(dateString));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            c.setSolved(cursor.getString(cursor.getColumnIndex(Cols.SOLVED)).equals("true"));

            mCrimes.add(c);

            mLogger.info("mCrimes array size is"+mCrimes.size());
        }

        for(Crime c:mCrimes)
            mLogger.info("title is "+c.getTitle()+" and is solved is "+c.isSolved());

        cursor.close();

   }

    List<Crime> getCrimes() {

        return mCrimes;
    }

    void addCrime(){

        Crime c=new Crime();

        mDatabase.execSQL("insert into "+CrimeTable.NAME+" values('"+c.getId()+"','"+c.getTitle()+"','"+c.getDate()+"','"+c.isSolved()+"','"+c.getSuspect()+"')");

        mCrimes.add(c);
    }

    void updateCrime(Crime crime){

        String sql="update "+CrimeTable.NAME+
//                             " set "+Cols.UUID+"='"+crime.getId()+"',"+
                " set "+Cols.TITLE+"='"+crime.getTitle()+"', "
                +Cols.DATE+"='"+crime.getDate()+"', "
                +Cols.SOLVED+"='"+crime.isSolved()+"',"
                +Cols.SUSPECT+"='"+crime.getSuspect()+"'"+
                " where "+Cols.UUID+"='"+crime.getId()+"'";

        mLogger.info("sql is "+sql);

         mDatabase.execSQL(sql);

    }

    void removeCrime(int index, UUID crimeID){

        String sql="delete from "+CrimeTable.NAME+" where "+Cols.UUID+"='"+crimeID+"'";

        mLogger.info("index is "+sql);

        mDatabase.execSQL(sql);

        mCrimes.remove(index);
    }

}
