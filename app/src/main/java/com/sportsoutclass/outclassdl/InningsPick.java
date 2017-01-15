package com.sportsoutclass.outclassdl;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class InningsPick extends BaseFragment {
    public InningsPick(){

    }
    RecyclerView inningsPicker;
    final Context context = StateClass.getContext();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_innings_pick, container, false);
        TextView t1 = (TextView)view.findViewById(R.id.team_pick_headline);
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment fragment = new FirstInnings();
//                replaceFragment(fragment, R.id.fragment_container);
//            }
//        });
        inningsPicker = (RecyclerView)view.findViewById(R.id.team_pick_recycler);
        String[] titleValues = new String[]{"First Innings" , "Second Innings"};
        String[] subTitleValues = new String[]{"If Team 1 was unable to bat the alotted overs", "If Team 2 innings was interrupted"};
        inningsPicker.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        inningsPicker.setLayoutManager(llm);

        InningsPickerRVAdapter adapter = new InningsPickerRVAdapter(context, titleValues, subTitleValues);
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


}
