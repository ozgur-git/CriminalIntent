package com.example.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

public class CrimeListFragment extends Fragment {

    private Callbacks mCallbacks;
    private FragmentCallbacks mFragmentCallbacks;

    @Inject
    CrimeList mCrimeList;

    CrimeComponent mComponent;
    private RecyclerView mRecyclerView;
    private CrimeAdapter mAdapter;
    private Integer crimeItemPosition;
    private boolean mSubtitleVisible;

    public static final String CRIME_ID="KEY_CRIME_ID";
    public static final String CRIME_POSITION_KEY="KEY_CRIME_ID";
    public static final String MENU_VISIBILITY_KEY="KEY_VISIBILITY";

    Logger mLogger=Logger.getLogger(getClass().getName());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        GlobalVariables globalVariables=(GlobalVariables)getActivity().getApplicationContext();
        mComponent=globalVariables.getComponent();
        mComponent.inject(this);

        View view=inflater.inflate(R.layout.fragment_crime_list,container,false);

        LinearLayout placeholderLayout=view.findViewById(R.id.placeholder_layout);

        if (mCrimeList.getCrimes().size()==0){

            Button placeholderButton=view.findViewById(R.id.new_crime_button);

            placeholderButton.setOnClickListener((v)->{
                mCrimeList.addCrime();
                startActivity(CrimePagerActivity.newIntent(getActivity().getBaseContext(),mCrimeList.getCrimes().size()));
            });
        }

        else  placeholderLayout.setVisibility(View.GONE);

        setHasOptionsMenu(true);

        mRecyclerView=view.findViewById(R.id.crime_recycler_view);

        mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));

        ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                mLogger.info("onSwipe is called at "+viewHolder.getAbsoluteAdapterPosition());
                mCrimeList.removeCrime(viewHolder.getAbsoluteAdapterPosition(),mCrimeList.getCrimes().get(viewHolder.getAbsoluteAdapterPosition()).getId());//todo baska column gerekiyor
                mFragmentCallbacks.onCrimeDeleted(null);
                updateUI();

            }
        };

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(mRecyclerView);

        mLogger.info("onCreateView is called!");

        updateUI();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState!=null) mSubtitleVisible=savedInstanceState.getBoolean(MENU_VISIBILITY_KEY);
        mLogger.info("received visibility is "+mSubtitleVisible);
    }

    void updateUI(){

       mAdapter=new CrimeAdapter(mCrimeList.getCrimes());

       mRecyclerView.setAdapter(mAdapter);

       updateSubtitle();
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

//            mImageSolveView.setContentDescription(mCrime.isSolved()?getString(R.string.crime_solved_image_description):getString(R.string.crime_not_solved_image_description));
            mImageSolveView.setContentDescription(getString(R.string.crime_solved_image_description));
            mDateTextView.setContentDescription("date of the crime is "+mCrime.getDate());
            mTitleTextView.setContentDescription("title of the crime is "+mCrime.getTitle());
        }

        @Override
            public void onClick(View v) {

            mLogger.info("click position is "+getAbsoluteAdapterPosition());

            crimeItemPosition=getAbsoluteAdapterPosition();

            mCallbacks.onCrimeSelected(crimeItemPosition);

//            startActivity(CrimePagerActivity.newIntent(getActivity(),crimeItemPosition));
        }
    }

    private void updateSubtitle(){

        int crimeSize=mCrimeList.getCrimes().size();

        String subtitle=getResources().getQuantityString(R.plurals.subtitle_plural,crimeSize,crimeSize);

        if (!mSubtitleVisible) {

            subtitle = null;
        }

        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(subtitle);

    }

    private class CrimeAdapter extends RecyclerView.Adapter<MainHolder>{

        private List<Crime> mCrimes;
        private int crimePosition;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;

        }

        @NonNull
        @Override
        public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());

            return new CrimeHolder(layoutInflater,parent);

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
        public long getItemId(int position) {
            return super.getItemId(position);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        mLogger.info("onResume is called!"+crimeItemPosition);
        if (crimeItemPosition!=null)
            mAdapter.notifyItemChanged(crimeItemPosition);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mLogger.info("onSaveInstanceState is called visibility "+mSubtitleVisible);
        outState.putBoolean(MENU_VISIBILITY_KEY,mSubtitleVisible);
//        outState.putInt(CRIME_POSITION_KEY,crimeItemPosition);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_crime_list,menu);

        MenuItem subtitleItem=menu.findItem(R.id.show_subtitle);

        if (mSubtitleVisible) subtitleItem.setTitle(R.string.hide_subtitle);

        else subtitleItem.setTitle(R.string.show_subtitle);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.new_crime:
                                 mCrimeList.addCrime();
                                 updateUI();
                                 mCallbacks.onCrimeSelected(mCrimeList.getCrimes().size()-1);
//                                 startActivity(CrimePagerActivity.newIntent(getActivity().getBaseContext(),mCrimeList.getCrimes().size()+1));
                                 return true;
            case R.id.show_subtitle:
                                 mSubtitleVisible=!mSubtitleVisible;
                                 getActivity().invalidateOptionsMenu();
                                 updateSubtitle();
                                 return true;
            default:
                                 return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallbacks=(Callbacks) context;
        mFragmentCallbacks=(FragmentCallbacks) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks=null;
    }

}
