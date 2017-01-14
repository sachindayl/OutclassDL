package com.sportsoutclass.outclassdl;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.test.internal.runner.TestLoader;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class TeamPick extends BaseFragment {
    public TeamPick(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_pick, container, false);
        // Set title bar
        TextView t1 = (TextView)view.findViewById(R.id.text_v1);
        Button b1 = (Button) view.findViewById(R.id.frag_btn1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FirstInnings();
                replaceFragment(fragment, R.id.fragment_container);
            }
        });
        //for app bar
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActionBar().setTitle("Team Pick");
    }


}
