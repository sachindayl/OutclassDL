package com.sportsoutclass.outclassdl;

import android.app.ProgressDialog;
import android.os.AsyncTask;
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
import java.util.LinkedList;
import java.util.Queue;

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
        if(wickets > 5) showColumns = 10;
        else showColumns = wickets + 5;
        //adding number of wickets to first row
        for (int i = wickets; i < showColumns; i++) {
            tv = new TextView(getActivity());
            tv.setGravity(Gravity.CENTER);
            tv.setText(String.valueOf(i));
            wicketsRow.addView(tv);
        }
        rateTableContainer.addView(wicketsRow);

    }

    private void rowCreator() {
        double[] data = getRateData();
        double numOfRows = data[1];
        //adding one because we don't need to check first over its already shown
        double startOver = data[2] + 1;
        //calculation start after one later
        double oversLeft = data[1] - 1;
        int resourceKey;
        int numOfWickets = (int) data[0];
        boolean rowColor = false;
        int numOfRowsToInt;
        //if there are decimals in remaining overs, adding another row
        if ((numOfRows * 10) % 10 > 0) numOfRowsToInt = (int) numOfRows + 1;
        else numOfRowsToInt = (int) numOfRows;

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
            tv.setText(String.valueOf(startOver));
            tr.addView(tv);

            for (int j = numOfWickets; j < showColumns; j++) {
                resourceKey = (int) ((oversLeft * 100) + j);
                double resourceValue = dataMap.DataSet(resourceKey);
                double parScoreDouble = (state.getTotalT1() * resourceValue) /100;
                int parScoreToInt = (int) parScoreDouble;
                int parScoreFinal = state.getParScoreTarget() - parScoreToInt;
                createTextView(String.valueOf(parScoreFinal));
//                if(parScoreFinal == state.getParScoreTarget()) break;
                Log.v("resourceKey: ", String.valueOf(resourceKey));
            }
            //noinspection unchecked

            Log.v("RowCalculation: ", String.valueOf(startOver));

//            InputMethodManager iMM = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//            iMM.hideSoftInputFromWindow(view.getWindowToken(), 0);
            startOver++;
            oversLeft--;
            rateTableContainer.addView(tr);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //set title on action bar
        getActionBar().setTitle("DL Table");
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
        tview.setText(result);
        tr.addView(tview);
    }
}
