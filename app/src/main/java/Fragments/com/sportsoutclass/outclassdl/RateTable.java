package com.sportsoutclass.outclassdl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class RateTable extends BaseFragment {


    public RateTable() {
        // Required empty public constructor
    }

    View view;
    @BindView(R.id.rate_table_container)
    TableLayout rateTableContainer;
    StateClass state;
    InterruptionSetup interruptionSetup;
    DataMap dataMap;
    TableRow tr;
    TextView tview;
    int showColumns;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_rate_table, container, false);
        ButterKnife.bind(this, view);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        state = (StateClass) getActivity().getApplication();
        interruptionSetup = new InterruptionSetup();
        dataMap = new DataMap();
        addWicketsRow();
        return view;
    }

    private double[] getRateData() {
        double[] data = new double[3];
        int wickets = 0;
        double interStartOver = -1.0;
        double interEndOver = -1.0;
        Log.v("interValueRT", String.valueOf(state.getInterruptionsSI()));
        switch (state.getInterruptionsSI()) {
            case 1:
                wickets = state.getInter1WicketsSI();
                interStartOver = state.getInter1StartOverSI();
                interEndOver = state.getInter1EndOverSI();
                break;
            case 2:
                wickets = state.getInter2WicketsSI();
                interStartOver = state.getInter2StartOverSI();
                interEndOver = state.getInter2EndOverSI();
                break;
            case 3:
                wickets = state.getInter3WicketsSI();
                interStartOver = state.getInter3StartOverSI();
                interEndOver = state.getInter3EndOverSI();
                break;
        }
        Log.v("interEndOver" + state.getInterruptionsSI(), String.valueOf(interEndOver));
        Log.v("interStartOver" + state.getInterruptionsSI(), String.valueOf(interStartOver));
        data[0] = wickets;
        data[1] = interEndOver;
        data[2] = interStartOver;
        return data;
    }

    private void addWicketsRow() {
        double[] data = getRateData();
        int wickets = (int) data[0];
        Log.v("wicketsRT", String.valueOf(wickets));
        TableRow wicketsRow = new TableRow(getActivity());
        wicketsRow.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.tableRowGrey));
        wicketsRow.setPadding(20, 20, 20, 20);
        TextView tv = new TextView(getActivity());
        tv.setText("");
        wicketsRow.addView(tv);
//        if(wickets > 5) showColumns = 10;
//        else showColumns = wickets + 6;
        showColumns = 10;
        //adding number of wickets to first row
        for (int i = wickets; i < showColumns; i++) {
            tv = new TextView(getActivity());
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(20,0,20,0);
            tv.setText(String.valueOf(i));
            tv.setTextColor(ContextCompat.getColor(getContext(),R.color.accentColor));
            wicketsRow.addView(tv);
        }
        rateTableContainer.addView(wicketsRow);

    }

    /**
     * This method dynamically creates rows and shows par scores for second innings targets
     */
    private void rowCreator() {
        double[] data = getRateData();
        double numOfRows = data[1];
        //adding one because we don't need to check first over its already shown
        double startOver = data[2] + 1;
        double resourcesAtStartOver = dataMap.DataSet((int)state.getOvers()*100);
        Log.v("startOverBefore: ", String.valueOf(startOver));
        double oversLeft = data[1] - 1;
        Log.v("oversLeftBefore: ", String.valueOf(oversLeft));
        int resourceKey;
        int numOfWickets = (int) data[0];
        boolean rowColor = false;
        int numOfRowsToInt;
        //if there are decimals in remaining overs, adding another row
        if (numOfRows % 1 != 0) numOfRowsToInt = (int) numOfRows + 1;
        else numOfRowsToInt = (int) numOfRows;
        Log.v("numOfRowsToInt: ", String.valueOf(numOfRowsToInt));
        double team1Resources = state.getTeam1StartResourcesForSI();
        Log.v("team1 resources: ", String.valueOf(team1Resources));
        for (int i = 0; i < numOfRowsToInt; i++) {
            //creating rows and adding color
            tr = new TableRow(getActivity());
            tr.setPadding(20, 20, 20, 20);
            if (rowColor) {
                tr.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.tableRowGrey));
                rowColor = false;
            } else {
                tr.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                rowColor = true;
            }
            //adding over number to row
            TextView tv = new TextView(getActivity());

            DecimalFormat df = new DecimalFormat("#.0");
            String startOverToS = df.format(startOver);
            tv.setText(startOverToS);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(ContextCompat.getColor(getContext(),R.color.accentColor));
            tr.addView(tv);

            //adding the par scores to every over according to the number of overs
            for (int j = numOfWickets; j < showColumns; j++) {
                resourceKey = (int) ((oversLeft * 100) + j);
                Log.v("resourceKey: ", String.valueOf(resourceKey));
                double resourceValue = dataMap.DataSet(resourceKey);
                Log.v("resourceValue: ", String.valueOf(resourceValue));
                double resourcesForRemainingOvers = team1Resources - resourceValue;
                Log.v("resForRemainingOvers: ", String.valueOf(resourcesForRemainingOvers));
                //dividing team2 resources by team 1 for par score
                double parScoreDouble = state.getParScoreTarget() * (resourcesForRemainingOvers / team1Resources);
                Log.v("parScoreEquation: ", String.valueOf(parScoreDouble));
                double parScore = Math.abs(parScoreDouble) ;
                Log.v("pScore b4 decimal fix: ", String.valueOf(parScore));
                int parScoreFinal = (int) parScore;
                Log.v("final par score: ", String.valueOf(parScoreFinal));
                createTextView(String.valueOf(parScoreFinal));

            }
            oversLeft--;
            Log.v("oversLeftAfter: ", String.valueOf(oversLeft));
//            lessThanOver = checkBallsLeft(oversLeft);
//            if(lessThanOver) startOver = startOver + oversLeft;
//            else startOver++;
            startOver++;
            Log.v("startOverAfter: ", String.valueOf(startOver));


            rateTableContainer.addView(tr);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //set title on action bar
        getActionBar().setTitle("Par Score Table");
        rowCreator();
    }

//    private class RowCalculation extends AsyncTask<String, Double, String> {
//        ProgressDialog pd = new ProgressDialog(getActivity());
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pd.setMessage("\tCalculating...");
//            pd.show();
//        }
//
//        @Override
//        final protected String doInBackground(String... resourceKeys) {
//            rowCreator();
//            return null;
//        }
//
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            pd.dismiss();
//        }
//    }

    private void createTextView(String result){
        tview = new TextView(getActivity());
        tview.setGravity(Gravity.CENTER);
        tview.setPadding(20,0,20,0);
        tview.setText(result);
        tr.addView(tview);
    }

    private boolean checkBallsLeft(double x){
        boolean onlyBallsLeft = false;
        if(x > 0 && x < 0.6) onlyBallsLeft = true;
        return onlyBallsLeft;
    }
}
