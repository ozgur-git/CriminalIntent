package com.example.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.logging.Logger;

public class PictureUtils {

   static Logger mLogger=Logger.getLogger("pictureutils");

    public static Bitmap getScaledBitmap(String path,int destWidth,int destHeight){

        BitmapFactory.Options options=new BitmapFactory.Options();

        options.inJustDecodeBounds=true;

        BitmapFactory.decodeFile(path,options);

       float srcWidth=options.outWidth;
       float srcHeight=options.outHeight;

       int inSampleSize=1;

       if (srcHeight>destHeight||srcWidth>destWidth){

           float heightScale=srcHeight/destHeight;
           float widthScale=srcWidth/destWidth;

           inSampleSize=Math.round(heightScale>widthScale?heightScale:widthScale);
       }

       options=new BitmapFactory.Options();

       options.inSampleSize=inSampleSize;

       Bitmap bitmap=BitmapFactory.decodeFile(path,options);

       mLogger.info("PictureUtils destWidth and destHeight are "+destWidth+", "+destHeight);

       return Bitmap.createScaledBitmap(bitmap,destWidth,destHeight,false);

    }

    public static Bitmap getScaledBitmap(String path, Activity activity){

        Point size=new Point();


        activity.getWindowManager().getDefaultDisplay().getSize(size);

        return getScaledBitmap(path,size.x,size.y);




    }
}
