package com.example.criminalintent;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class CrimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

//        FragmentManager fragmentManager=getSupportFragmentManager();
//        Fragment fragment=fragmentManager.findFragmentById(R.id.fragment_container);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new CrimeFragment()).commit();

    }
}