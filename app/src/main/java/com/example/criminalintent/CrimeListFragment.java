package com.example.criminalintent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CrimeListFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_crime_list,container,false);

        mRecyclerView=view.findViewById(R.id.crime_recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private class CrimeHolder extends RecyclerView.ViewHolder{

        public CrimeHolder(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
            super(inflater.inflate(R.layout.list_item_crime,container,false));
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeListFragment.CrimeHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
