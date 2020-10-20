package com.example.criminalintent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CrimeAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_crime_list,container,false);

        mRecyclerView=view.findViewById(R.id.crime_recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI(){

       mAdapter=new CrimeAdapter(CrimeLab.getCrimeLab().getCrimes());
       mRecyclerView.setAdapter(mAdapter);
    }

    private class CrimeHolder extends RecyclerView.ViewHolder{

        private TextView mTitleTextView;
        private TextView mDateTextView;

        private Crime mCrime;


        public CrimeHolder(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime,parent,false));

            mTitleTextView=itemView.findViewById(R.id.crime_title);
            mDateTextView=itemView.findViewById(R.id.crime_date);
        }

        public void setCrime(Crime crime) {
            mCrime = crime;
            mDateTextView.setText(mCrime.getDate().toString());
            mTitleTextView.setText(mCrime.getTitle());

        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());

            return new CrimeHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeListFragment.CrimeHolder holder, int position) {

            Crime crime=mCrimes.get(position);
            holder.setCrime(crime);

        }

        @Override
        public int getItemCount() {

            return mCrimes.size();
        }
    }
}
