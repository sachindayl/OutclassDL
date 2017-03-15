package com.sportsoutclass.outclassdl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LicencesFrag extends BaseFragment implements FragmentContract {
    public LicencesFrag() {

    }
    @BindView(R.id.recipe_list_view)
    ListView mListView;
    private AdView mAdView;
    View view;
    StateClass state;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_licences, container, false);
        ButterKnife.bind(this, view);
        actionBarImplementation();
        state = (StateClass) getActivity().getApplication();
        adMobImplementation();
        //Licence array
        String[] listItems = {"Android Compatibility Library v4","Android Compatibility Library v7","Android Design Support Library","Android Support Library" , "Android SDK","Butter Knife", "Google Play Services: Analytics"};

        //Overriding simple view to get custom height of the list view
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listItems){
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);

                // Get the Layout Parameters for ListView Current Item View
                ViewGroup.LayoutParams params = view.getLayoutParams();

                // Set the height of the Item View
                params.height = 175;
                view.setLayoutParams(params);

                return view;
            }

        };
        mListView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //set title on action bar
        getActionBar().setTitle("Licences");
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

