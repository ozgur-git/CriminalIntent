package com.example.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Date;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        if  (savedInstanceState==null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, createFragment()).commit();
        }

    }
}
