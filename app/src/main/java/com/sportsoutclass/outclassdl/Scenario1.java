package com.sportsoutclass.outclassdl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Scenario1 extends AppCompatActivity {
    TextView interruption1TextView, interruption2TextView, interruption3TextView,
            whichOverInterruption1TextView, whichOverInterruption2TextView,
            whichOverInterruption3TextView, wicketsLostInterruption1TextView,
            wicketsLostInterruption2TextView, wicketsLostInterruption3TextView,
            oversRemainingInterruption1TextView, oversRemainingInterruption2TextView,
            oversRemainingInterruption3TextView;
    EditText team1InterruptionsEdit, whichOverInterruption1EditText, whichOverInterruption2EditText,
            whichOverInterruption3EditText,wicketsLostInterruption1EditText,
            wicketsLostInterruption2EditText, wicketsLostInterruption3EditText,
            oversRemainingInterruption1EditText, oversRemainingInterruption2EditText,
            oversRemainingInterruption3EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario1);

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
        team1InterruptionsEdit = (EditText) findViewById(R.id.interruptions_edit_text);
        whichOverInterruption1EditText = (EditText) findViewById(R.id.which_over_interruption_1_edit_text);
        whichOverInterruption2EditText = (EditText) findViewById(R.id.which_over_interruption_2_edit_text);
        whichOverInterruption3EditText = (EditText) findViewById(R.id.which_over_interruption_3_edit_text);
        wicketsLostInterruption1EditText = (EditText) findViewById(R.id.wickets_lost_interruption_1_edit_text);
        wicketsLostInterruption2EditText = (EditText) findViewById(R.id.wickets_lost_interruption_2_edit_text);
        wicketsLostInterruption3EditText = (EditText) findViewById(R.id.wickets_lost_interruption_3_edit_text);
        oversRemainingInterruption1EditText = (EditText) findViewById(R.id.overs_remaining_interruption_1_edit_text);
        oversRemainingInterruption2EditText = (EditText) findViewById(R.id.overs_remaining_interruption_2_edit_text);
        oversRemainingInterruption3EditText = (EditText) findViewById(R.id.overs_remaining_interruption_3_edit_text);

        team1InterruptionsEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    String interruptionsToS = team1InterruptionsEdit.getText().toString();
//                    if(interruptionsToS.matches("")){
//                        Toast.makeText(getApplicationContext(), "You need to have an interruption!",
//                                Toast.LENGTH_LONG).show();
//
//                    }
//                    if(TextUtils.isEmpty(team1InterruptionsEdit.getText())){
//                        team1InterruptionsEdit.setError("You need to have an interruption!");
//
//                    }
                    int interToInt = Integer.parseInt(interruptionsToS);
                    interruptions(interToInt);
                    if(interToInt > 0 && interToInt < 4){
                        team1InterruptionsEdit.setNextFocusForwardId(R.id.which_over_interruption_1_edit_text);
                    }
                    handled = true;

                }
                else if(actionId == EditorInfo.IME_ACTION_NONE){
                    team1InterruptionsEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                }
                return handled;
            }
        });
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

    private void interruptions(int i) {
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
        }
        else if(i == 2){
            interruptions(1);
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
        }
        else if(i == 3){
            interruptions(2);
            oversRemainingInterruption2EditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            interruption3TextView.setVisibility(View.VISIBLE);
            whichOverInterruption3TextView.setVisibility(View.VISIBLE);
            wicketsLostInterruption3TextView.setVisibility(View.VISIBLE);
            oversRemainingInterruption3TextView.setVisibility(View.VISIBLE);
            whichOverInterruption3EditText.setVisibility(View.VISIBLE);
            wicketsLostInterruption3EditText.setVisibility(View.VISIBLE);
            oversRemainingInterruption3EditText.setVisibility(View.VISIBLE);
            oversRemainingInterruption3EditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }
        else if(i == 0){
            Toast.makeText(getApplicationContext(), "You need to have an interruption!",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Maximum 3 Interruptions",
                    Toast.LENGTH_LONG).show();
        }
    }
}
