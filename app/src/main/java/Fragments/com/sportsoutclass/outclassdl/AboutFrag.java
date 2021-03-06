package com.sportsoutclass.outclassdl;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFrag extends BaseFragment implements FragmentContract{


    public AboutFrag() {
        // Required empty public constructor
    }

    String[] titleValues, subTitleValues;
    @BindView(R.id.about_recycler)
    RecyclerView aboutRecycler;
    final Context context = (StateClass) StateClass.getContext();
    View view;
    private AdView mAdView;
    StateClass state;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, view);
        actionBarImplementation();
        state = (StateClass) getActivity().getApplication();
        adMobImplementation();
        Tracking analyticsTracker = new Tracking("AboutPage", state);
        analyticsTracker.doTracking();

        String version = BuildConfig.VERSION_NAME;
        titleValues = new String[]{"Developer", "Version", "Feedback", "Rate", "Licences"};
        subTitleValues = new String[]{"Sachinda Liyanaarachchi", version, "Give your feedback at dlcalculatorapp@gmail.com", "Please rate this app on store", "Open source licences used"};
        aboutRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        aboutRecycler.setLayoutManager(llm);

        AboutPageRVAdapter adapter = new AboutPageRVAdapter(context, titleValues, subTitleValues);
        aboutRecycler.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //set title on action bar
        getActionBar().setTitle("About");
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
