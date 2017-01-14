package com.sportsoutclass.outclassdl;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstInnings extends BaseFragment {


    public FirstInnings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_innings, container, false);

        // Set title bar
        TextView t1 = (TextView)view.findViewById(R.id.text_v2);
        Button b1 = (Button) view.findViewById(R.id.frag_btn2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new TeamPick();
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
        getActionBar().setTitle("First Innings");
    }

}
