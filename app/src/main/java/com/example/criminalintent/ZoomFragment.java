package com.example.criminalintent;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.logging.Logger;

public class ZoomFragment extends DialogFragment {

    static  Logger mLogger=Logger.getLogger("abc");

    private final static String IMAGE_PATH = "path";

    public static ZoomFragment newInstance(String path) {

        mLogger.info("zoom fragment new instance is called!");

        Bundle bundle=new Bundle();

        bundle.putSerializable(IMAGE_PATH,path);

        ZoomFragment zoomFragment=new ZoomFragment();

        zoomFragment.setArguments(bundle);

        return zoomFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mLogger.info("zoom fragment oncreateview is called!");

        View view = inflater.inflate(R.layout.fragment_zoom, container, false);

        String path=(String) getArguments().getSerializable(IMAGE_PATH);

        ImageView imageView = view.findViewById(R.id.zoom_view);

        imageView.setImageBitmap(BitmapFactory.decodeFile(path));


        return view;
    }
}

