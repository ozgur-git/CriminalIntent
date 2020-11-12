package com.example.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

public class CrimeListFragment extends Fragment {

    @Inject
    CrimeList mCrimeList;
    @Inject
    Crime mCrime;

    CrimeComponent mComponent;
    private RecyclerView mRecyclerView;
    private CrimeAdapter mAdapter;
    private Integer crimeItemPosition;

    public static final String CRIME_ID="KEY_CRIME_ID";
    public static final String CRIME_POSITION_KEY="KEY_CRIME_ID";

    Logger mLogger=Logger.getLogger(getClass().getName());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        GlobalVariables globalVariables=(GlobalVariables)getActivity().getApplicationContext();
        mComponent=globalVariables.getComponent();
        mComponent.inject(this);
//        mComponent.inject();


        View view=inflater.inflate(R.layout.fragment_crime_list,container,false);

        setHasOptionsMenu(true);

        mRecyclerView=view.findViewById(R.id.crime_recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mLogger.info("onCreateView is called!");

        updateUI();

        return view;
    }

    private void updateUI(){

       mAdapter=new CrimeAdapter(mCrimeList.getCrimes());
//        mAdapter=new CrimeAdapter(CrimeLab.getCrimeLab().getCrimes());

       mRecyclerView.setAdapter(mAdapter);
    }

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
            mDateTextView.setText(mCrime.getDate());
            mTitleTextView.setText(mCrime.getTitle());
            mImageSolveView.setVisibility(mCrime.isSolved()?View.VISIBLE:View.INVISIBLE);
        }

        @Override
        public void onClick(View v) {

            mLogger.info("click position is "+getAbsoluteAdapterPosition());
            crimeItemPosition=getAbsoluteAdapterPosition();
//            CrimePagerActivity.newIntent(getActivity(),crimeItemPosition);
//            Intent intent=new Intent(getActivity(),CrimeActivity.class);
//            intent.putExtra(CRIME_ID, crimeItemPosition);
//            intent.putExtra(CRIME_ID, mCrime.getId());
//            startActivity(intent);
            startActivity(CrimePagerActivity.newIntent(getActivity(),crimeItemPosition));
        }
    }

    private void updateSubtitle(){

        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(getString(R.string.subtitle_format,mCrimeList.getCrimes().size()));
    }

    private class CrimeAdapter extends RecyclerView.Adapter<MainHolder>{

        private List<Crime> mCrimes;
        private int crimePosition;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
            mLogger.info("adapter cons is called!");
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

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mLogger.info("onResume is called!"+crimeItemPosition);
        if (crimeItemPosition!=null)
            mAdapter.notifyItemChanged(crimeItemPosition);
//        mAdapter.notifyDataSetChanged();
//        updateUI();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mLogger.info("onSaveInstanceState is called! "+crimeItemPosition);
//        outState.putInt(CRIME_POSITION_KEY,crimeItemPosition);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.new_crime:
                                 mCrimeList.addCrime(mCrime);
                                 startActivity(CrimePagerActivity.newIntent(getActivity().getBaseContext(),mCrimeList.getCrimes().size()+1));
                                 return true;
            case R.id.show_subtitle:
                                  updateSubtitle();
            default:
                                return super.onOptionsItemSelected(item);
        }

    }
}
