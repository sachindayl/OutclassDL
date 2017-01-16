package com.sportsoutclass.outclassdl;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SecondInnings.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SecondInnings extends BaseFragment {

    public SecondInnings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_innings, container, false);

//        TextView t1 = (TextView)view.findViewById(R.id.text_v2);
//        Button b1 = (Button) view.findViewById(R.id.frag_btn2);
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment fragment = new InningsPick();
//                replaceFragment(fragment, R.id.fragment_container);
//            }
//        });

        //for app bar
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //set title on action bar
        getActionBar().setTitle("Second Innings Calculator");
    }


}
