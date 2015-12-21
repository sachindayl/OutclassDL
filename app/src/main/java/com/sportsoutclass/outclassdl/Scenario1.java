package com.sportsoutclass.outclassdl;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Scenario1 extends AppCompatActivity {
    TextView interruption1TextView, interruption2TextView, interruption3TextView,
            whichOverInterruption1TextView, whichOverInterruption2TextView,
            whichOverInterruption3TextView, wicketsLostInterruption1TextView,
            wicketsLostInterruption2TextView, wicketsLostInterruption3TextView,
            oversRemainingInterruption1TextView, oversRemainingInterruption2TextView,
            oversRemainingInterruption3TextView;
    EditText team2InterruptionsEdit, whichOverInterruption1EditText, whichOverInterruption2EditText,
            whichOverInterruption3EditText, wicketsLostInterruption1EditText,
            wicketsLostInterruption2EditText, wicketsLostInterruption3EditText,
            oversRemainingInterruption1EditText, oversRemainingInterruption2EditText,
            oversRemainingInterruption3EditText;
    String whichOverInterruption1EditTextValue, wicketsLostInterruption1EditTextValue,
            oversRemainingInterruption1EditTextValue,whichOverInterruption2EditTextValue,
            wicketsLostInterruption2EditTextValue, oversRemainingInterruption2EditTextValue,
            whichOverInterruption3EditTextValue, wicketsLostInterruption3EditTextValue,
            oversRemainingInterruption3EditTextValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario1);

        variableAssignments();
        InterruptionsValueEdit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //this edits the number for interruptions
    private void InterruptionsValueEdit() {
        team2InterruptionsEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    String interruptionsToS = team2InterruptionsEdit.getText().toString();
                    Log.v("interruptionsToS value", interruptionsToS);
                    int interToInt = 0;
                    if(interToInt > 0 && interToInt < 4 ){
                        interToInt = Integer.parseInt(interruptionsToS);
                    }

                    InterruptionsAmountSetup(interToInt);
                    if (interToInt > 0 && interToInt < 4) {
                        team2InterruptionsEdit.setNextFocusForwardId(R.id.which_over_interruption_1_edit_text);
                    }
                    handled = true;
                } else if (actionId == EditorInfo.IME_ACTION_NONE) {
                    team2InterruptionsEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                }
                return handled;
            }
        });
    }

    //this enables disables visibility of number of interruptions available to edit
    private void InterruptionsAmountSetup(int i) {
        if (i == 1) {
            interruption1TextView.setVisibility(View.VISIBLE);
            whichOverInterruption1TextView.setVisibility(View.VISIBLE);
            wicketsLostInterruption1TextView.setVisibility(View.VISIBLE);
            oversRemainingInterruption1TextView.setVisibility(View.VISIBLE);
            whichOverInterruption1EditText.setVisibility(View.VISIBLE);
            wicketsLostInterruption1EditText.setVisibility(View.VISIBLE);
            oversRemainingInterruption1EditText.setVisibility(View.VISIBLE);

            interruption2TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption2TextView.setVisibility(View.INVISIBLE);
            wicketsLostInterruption2TextView.setVisibility(View.INVISIBLE);
            oversRemainingInterruption2TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption2EditText.setVisibility(View.INVISIBLE);
            wicketsLostInterruption2EditText.setVisibility(View.INVISIBLE);
            oversRemainingInterruption2EditText.setVisibility(View.INVISIBLE);
            interruption3TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption3TextView.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3TextView.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption3EditText.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3EditText.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3EditText.setVisibility(View.INVISIBLE);
            oversRemainingInterruption1EditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

            whichOverInterruption1EditTextValue = whichOverInterruption1EditText.getText().toString();
            wicketsLostInterruption1EditTextValue = wicketsLostInterruption1EditText.getText().toString();
            oversRemainingInterruption1EditTextValue = oversRemainingInterruption1EditText.getText().toString();
        } else if (i == 2) {
            InterruptionsAmountSetup(1);
            oversRemainingInterruption1EditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            interruption2TextView.setVisibility(View.VISIBLE);
            whichOverInterruption2TextView.setVisibility(View.VISIBLE);
            wicketsLostInterruption2TextView.setVisibility(View.VISIBLE);
            oversRemainingInterruption2TextView.setVisibility(View.VISIBLE);
            whichOverInterruption2EditText.setVisibility(View.VISIBLE);
            wicketsLostInterruption2EditText.setVisibility(View.VISIBLE);
            oversRemainingInterruption2EditText.setVisibility(View.VISIBLE);

            interruption3TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption3TextView.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3TextView.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption3EditText.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3EditText.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3EditText.setVisibility(View.INVISIBLE);
            oversRemainingInterruption2EditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

            whichOverInterruption2EditTextValue = whichOverInterruption2EditText.getText().toString();
            wicketsLostInterruption2EditTextValue = wicketsLostInterruption2EditText.getText().toString();
            oversRemainingInterruption2EditTextValue = oversRemainingInterruption2EditText.getText().toString();
        } else if (i == 3) {
            InterruptionsAmountSetup(2);
            oversRemainingInterruption2EditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            interruption3TextView.setVisibility(View.VISIBLE);
            whichOverInterruption3TextView.setVisibility(View.VISIBLE);
            wicketsLostInterruption3TextView.setVisibility(View.VISIBLE);
            oversRemainingInterruption3TextView.setVisibility(View.VISIBLE);
            whichOverInterruption3EditText.setVisibility(View.VISIBLE);
            wicketsLostInterruption3EditText.setVisibility(View.VISIBLE);
            oversRemainingInterruption3EditText.setVisibility(View.VISIBLE);
            oversRemainingInterruption3EditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

            whichOverInterruption3EditTextValue = whichOverInterruption3EditText.getText().toString();
            wicketsLostInterruption3EditTextValue = wicketsLostInterruption3EditText.getText().toString();
            oversRemainingInterruption3EditTextValue = oversRemainingInterruption3EditText.getText().toString();
        } else if (i == 0) {
            Toast.makeText(getApplicationContext(), "You need to have an interruption!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Maximum 3 Interruptions",
                    Toast.LENGTH_SHORT).show();
        }

    }

    //activating next button
    public void ActivateNextBtn(int i){

    }


    private class AsyncTaskCalculation extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }

    private void calculations(){
        MainActivity oversValue = new MainActivity();
        DataMap overData = new DataMap();
        int mainOvers = oversValue.getOvers();
        double percentageRes = 0.0;
        double resourcesLeft = 0.0;

        int whichOverInterruption1ToInt = Integer.parseInt(whichOverInterruption1EditTextValue);
        String oversAtInterAndWickets = whichOverInterruption1EditTextValue.replaceAll(".","") + wicketsLostInterruption1EditTextValue;
        int oversAtInterAndWicketsToInt = Integer.parseInt(oversAtInterAndWickets);
        if(oversAtInterAndWicketsToInt <= 5009 && oversAtInterAndWicketsToInt >= 4010){
           percentageRes = overData.fiftyFortyOneData(oversAtInterAndWicketsToInt);
        }

        int oversRemainingInterruption1toInt = Integer.parseInt(oversRemainingInterruption1EditTextValue);
        int remainingOversAtInterrStart = mainOvers - whichOverInterruption1ToInt;
        double team2StartResources = 100;
        double resourcesAtInterrStart = percentageRes;

        boolean playAbandoned = false;
        if(oversRemainingInterruption1toInt == 0){
            playAbandoned = true;
        }
        if(playAbandoned){
            resourcesLeft = team2StartResources - resourcesAtInterrStart;
        }

        //team1 score * (resourcesLeft / team2StartResources)
        //need to get team1 score from MainActivity
    }

    private void variableAssignments(){
        //TextView Assignments
        interruption1TextView = (TextView) findViewById(R.id.interruption_1_text_view);
        interruption2TextView = (TextView) findViewById(R.id.interruption_2_text_view);
        interruption3TextView = (TextView) findViewById(R.id.interruption_3_text_view);
        whichOverInterruption1TextView = (TextView) findViewById(R.id.which_over_interruption_1_text_view);
        whichOverInterruption2TextView = (TextView) findViewById(R.id.which_over_interruption_2_text_view);
        whichOverInterruption3TextView = (TextView) findViewById(R.id.which_over_interruption_3_text_view);
        wicketsLostInterruption1TextView = (TextView) findViewById(R.id.wickets_lost_interruption_1_text_view);
        wicketsLostInterruption2TextView = (TextView) findViewById(R.id.wickets_lost_interruption_2_text_view);
        wicketsLostInterruption3TextView = (TextView) findViewById(R.id.wickets_lost_interruption_3_text_view);
        oversRemainingInterruption1TextView = (TextView) findViewById(R.id.overs_remaining_interruption_1_text_view);
        oversRemainingInterruption2TextView = (TextView) findViewById(R.id.overs_remaining_interruption_2_text_view);
        oversRemainingInterruption3TextView = (TextView) findViewById(R.id.overs_remaining_interruption_3_text_view);
        //Edit Text Assignments
        team2InterruptionsEdit = (EditText) findViewById(R.id.interruptions_edit_text);
        whichOverInterruption1EditText = (EditText) findViewById(R.id.which_over_interruption_1_edit_text);
        whichOverInterruption2EditText = (EditText) findViewById(R.id.which_over_interruption_2_edit_text);
        whichOverInterruption3EditText = (EditText) findViewById(R.id.which_over_interruption_3_edit_text);
        wicketsLostInterruption1EditText = (EditText) findViewById(R.id.wickets_lost_interruption_1_edit_text);
        wicketsLostInterruption2EditText = (EditText) findViewById(R.id.wickets_lost_interruption_2_edit_text);
        wicketsLostInterruption3EditText = (EditText) findViewById(R.id.wickets_lost_interruption_3_edit_text);
        oversRemainingInterruption1EditText = (EditText) findViewById(R.id.overs_remaining_interruption_1_edit_text);
        oversRemainingInterruption2EditText = (EditText) findViewById(R.id.overs_remaining_interruption_2_edit_text);
        oversRemainingInterruption3EditText = (EditText) findViewById(R.id.overs_remaining_interruption_3_edit_text);


    }
}
