package com.sportsoutclass.outclassdl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class RateTable extends BaseFragment {


    public RateTable() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rate_table, container, false);
        ButterKnife.bind(this,view);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        addRows();
        return view;
    }

    private void addRows(){
//        LinearLayout newRow = new LinearLayout(getActivity());
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        newRow.setWeightSum(8);
//        newRow.setOrientation(LinearLayout.HORIZONTAL);
//        TextView test1 = new TextView(getActivity());
//        test1 = oversLeft(test1);
//        oversLeftContainer.addView(test1);
//        for(int i=0; i < 8; i++){
//            TextView wicket = new TextView(getActivity());
//            wicket = wicketsLost(wicket);
//            wicketsLostContainer.addView(wicket);
//        }

    }

    private TextView oversLeft(TextView over){

        over.setText("Hello");
        over.setGravity(Gravity.CENTER);
        over.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));

        return over;
    }

    private TextView wicketsLost(TextView wicket){
        wicket.setText("wicket");
        wicket.setGravity(Gravity.CENTER);
        wicket.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        return wicket;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //set title on action bar
        getActionBar().setTitle("DL Table");
    }
}
