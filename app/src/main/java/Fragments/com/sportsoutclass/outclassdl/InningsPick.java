package com.sportsoutclass.outclassdl;

import android.os.Bundle;
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


public class InningsPick extends BaseFragment implements FragmentContract {
    public InningsPick() {
    }

    @BindView(R.id.team_pick_recycler)
    RecyclerView inningsPicker;
    private static final String TAG = "InningsPicker";
    private AdView mAdView;
    StateClass state;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_innings_pick, container, false);
        ButterKnife.bind(this, view);
        actionBarImplementation();
        state = (StateClass) getActivity().getApplication();
        adMobImplementation();

        AppRater.app_launched(getContext());
        String[] titleValues = new String[]{"First Innings", "Second Innings"};
        String[] subTitleValues = new String[]{"If Team 1 innings was interrupted", "If Team 2 was unable to bat the alotted overs"};
        inningsPicker.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        inningsPicker.setLayoutManager(llm);

        InningsPickerRVAdapter adapter = new InningsPickerRVAdapter(titleValues, subTitleValues);
        inningsPicker.setAdapter(adapter);

        //for app bar
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActionBar().setTitle("DL Calculator");
    }


    @Override
    public void actionBarImplementation() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void adMobImplementation() {
        //Admob Initialization
        MobileAds.initialize(getContext(), state.getAdmobAppId());
        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
