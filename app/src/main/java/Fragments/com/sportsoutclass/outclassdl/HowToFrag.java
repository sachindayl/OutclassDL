package com.sportsoutclass.outclassdl;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HowToFrag extends BaseFragment implements FragmentContract{

    private AdView mAdView;
    View view;
    StateClass state;
    public HowToFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_how_to, container, false);
        ButterKnife.bind(this, view);
        actionBarImplementation();
        state = (StateClass) getActivity().getApplication();
        adMobImplementation();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //set title on action bar
        getActionBar().setTitle("Instructions");
    }

    @Override
    public void adMobImplementation() {
        MobileAds.initialize(getContext(), state.getAdmobAppId());
        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void actionBarImplementation() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
