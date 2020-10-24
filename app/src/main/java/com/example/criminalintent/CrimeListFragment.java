package com.example.criminalintent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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





//    private class MainHolder extends RecyclerView.ViewHolder{
//
//        public MainHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }


    private abstract class MainHolder extends RecyclerView.ViewHolder {

        public MainHolder(@NonNull View itemView) {
            super(itemView);
        }
        public abstract  void setCrime(Crime crime);
    }

    private class CrimeHolder extends MainHolder implements View.OnClickListener{

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mImageSolveView;

        private Crime mCrime;

        public CrimeHolder(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime,parent,false));

            mTitleTextView=itemView.findViewById(R.id.crime_title);
            mDateTextView=itemView.findViewById(R.id.crime_date);
            mImageSolveView=itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(this);

        }

        public void setCrime(Crime crime) {
            mCrime = crime;
            mDateTextView.setText(mCrime.getDate().toString());
            mTitleTextView.setText(mCrime.getTitle());
            mImageSolveView.setVisibility(mCrime.isSolved()?View.VISIBLE:View.INVISIBLE);
        }

        @Override
        public void onClick(View v) {

            Toast.makeText(getActivity(),mCrime.getTitle()+ " clicked!",Toast.LENGTH_SHORT).show();
        }
    }

    private class CrimePoliceHolder extends MainHolder implements View.OnClickListener{

        private TextView mTitleTextView;
        private TextView mDateTextView;

        private Crime mCrime;

        public CrimePoliceHolder(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent) {

            super(inflater.inflate(R.layout.list_item_crime_police,parent,false));

            mTitleTextView=itemView.findViewById(R.id.crime_title);
            mDateTextView=itemView.findViewById(R.id.crime_date);

            itemView.setOnClickListener(this);

        }

        public void setCrime(Crime crime) {
            mCrime = crime;
            mDateTextView.setText(mCrime.getDate().toString());
            mTitleTextView.setText(mCrime.getTitle());
        }

        @Override
        public void onClick(View v) {

            Toast.makeText(getActivity(),mCrime.getTitle()+ " clicked!",Toast.LENGTH_SHORT).show();
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<MainHolder>{

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @NonNull
        @Override
        public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());

            return new CrimeHolder(layoutInflater,parent);

           // if (viewType==0) return new CrimeHolder(layoutInflater,parent);

           // else return new CrimePoliceHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MainHolder holder, int position) {

            Crime crime=mCrimes.get(position);
            holder.setCrime(crime);
        }

        @Override
        public int getItemCount() {

            return mCrimes.size();
        }

        @Override
        public int getItemViewType(int position) {
//            return super.getItemViewType(position);
            return (mCrimes.get(position).isRequiresPolice()?1:0);
        }


    }
}