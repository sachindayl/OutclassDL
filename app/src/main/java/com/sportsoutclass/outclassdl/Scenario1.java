package com.sportsoutclass.outclassdl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * In Scenario 1: The team 1 has finished batting their allotted overs and the interruption(s) only
 * occurs to the second team.
 */
public class Scenario1 extends AppCompatActivity {
    TextView interruption1TextView, totalInter1TextView, totalInter2TextView, totalInter3TextView,
            interruption2TextView, interruption3TextView,
            whichOverInterruption1TextView, whichOverInterruption2TextView,
            whichOverInterruption3TextView, wicketsLostInterruption1TextView,
            wicketsLostInterruption2TextView, wicketsLostInterruption3TextView,
            oversRemainingInterruption1TextView, oversRemainingInterruption2TextView,
            oversRemainingInterruption3TextView;
    EditText totalInter1EditText, totalInter2EditText, totalInter3EditText, team2InterruptionsEdit, whichOverInterruption1EditText, whichOverInterruption2EditText,
            whichOverInterruption3EditText, wicketsLostInterruption1EditText,
            wicketsLostInterruption2EditText, wicketsLostInterruption3EditText,
            oversRemainingInterruption1EditText, oversRemainingInterruption2EditText,
            oversRemainingInterruption3EditText;
    int totalWickets, inter1Wickets, inter2Wickets, inter3Wickets, inter1total, inter2total, inter3total, interuptns;
    double inter1Over, inter2Over, inter3Over, inter1OversAtEnd, inter2OversAtEnd, inter3OversAtEnd;
    DataMap overData;
    StateClass state;
    AlertDialog.Builder t2WinScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario1);
        init();
        InterruptionsValueEdit();
        editTextData();

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
        team2InterruptionsEdit.addTextChangedListener(new TextWatcher() {
            int interToInt;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String interruptionsToS = s.toString();
                if (interruptionsToS.equals("")) {
                    interruptionsToS = "0";
                }
                Log.v("interruptionsToS value", interruptionsToS);
                interToInt = Integer.parseInt(interruptionsToS);
                state.setInterruptions(interToInt);
                InterruptionsAmountSetup(interToInt);

                if (interToInt > 0 && interToInt < 4) {
                    team2InterruptionsEdit.setNextFocusForwardId(R.id.which_over_interruption_1_edit_text);
                }
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
            totalInter1TextView.setVisibility(View.VISIBLE);
            totalInter1EditText.setVisibility(View.VISIBLE);

            interruption2TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption2TextView.setVisibility(View.INVISIBLE);
            wicketsLostInterruption2TextView.setVisibility(View.INVISIBLE);
            oversRemainingInterruption2TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption2EditText.setVisibility(View.INVISIBLE);
            wicketsLostInterruption2EditText.setVisibility(View.INVISIBLE);
            oversRemainingInterruption2EditText.setVisibility(View.INVISIBLE);
            totalInter2TextView.setVisibility(View.INVISIBLE);
            totalInter2EditText.setVisibility(View.INVISIBLE);
            interruption3TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption3TextView.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3TextView.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption3EditText.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3EditText.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3EditText.setVisibility(View.INVISIBLE);
            totalInter3EditText.setVisibility(View.INVISIBLE);
            totalInter3TextView.setVisibility(View.INVISIBLE);
            oversRemainingInterruption1EditText.setImeOptions(EditorInfo.IME_ACTION_DONE);


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
            totalInter2TextView.setVisibility(View.VISIBLE);
            totalInter2EditText.setVisibility(View.VISIBLE);

            interruption3TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption3TextView.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3TextView.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption3EditText.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3EditText.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3EditText.setVisibility(View.INVISIBLE);
            totalInter3EditText.setVisibility(View.INVISIBLE);
            totalInter3TextView.setVisibility(View.INVISIBLE);
            oversRemainingInterruption2EditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

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
            totalInter3EditText.setVisibility(View.VISIBLE);
            totalInter3TextView.setVisibility(View.VISIBLE);
            oversRemainingInterruption3EditText.setImeOptions(EditorInfo.IME_ACTION_DONE);


        } else if (i == 0) {
            Toast.makeText(getApplicationContext(), "You need to have an interruption!",
                    Toast.LENGTH_SHORT).show();

            interruption1TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption1TextView.setVisibility(View.INVISIBLE);
            wicketsLostInterruption1TextView.setVisibility(View.INVISIBLE);
            oversRemainingInterruption1TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption1EditText.setVisibility(View.INVISIBLE);
            wicketsLostInterruption1EditText.setVisibility(View.INVISIBLE);
            oversRemainingInterruption1EditText.setVisibility(View.INVISIBLE);
            totalInter1TextView.setVisibility(View.INVISIBLE);
            totalInter1EditText.setVisibility(View.INVISIBLE);
            interruption2TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption2TextView.setVisibility(View.INVISIBLE);
            wicketsLostInterruption2TextView.setVisibility(View.INVISIBLE);
            oversRemainingInterruption2TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption2EditText.setVisibility(View.INVISIBLE);
            wicketsLostInterruption2EditText.setVisibility(View.INVISIBLE);
            oversRemainingInterruption2EditText.setVisibility(View.INVISIBLE);
            totalInter2TextView.setVisibility(View.INVISIBLE);
            totalInter2EditText.setVisibility(View.INVISIBLE);
            interruption3TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption3TextView.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3TextView.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption3EditText.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3EditText.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3EditText.setVisibility(View.INVISIBLE);
            totalInter3EditText.setVisibility(View.INVISIBLE);
            totalInter3TextView.setVisibility(View.INVISIBLE);

        } else {
            Toast.makeText(getApplicationContext(), "Maximum 3 Interruptions",
                    Toast.LENGTH_SHORT).show();

            interruption1TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption1TextView.setVisibility(View.INVISIBLE);
            wicketsLostInterruption1TextView.setVisibility(View.INVISIBLE);
            oversRemainingInterruption1TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption1EditText.setVisibility(View.INVISIBLE);
            wicketsLostInterruption1EditText.setVisibility(View.INVISIBLE);
            oversRemainingInterruption1EditText.setVisibility(View.INVISIBLE);
            totalInter1TextView.setVisibility(View.INVISIBLE);
            totalInter1EditText.setVisibility(View.INVISIBLE);
            interruption2TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption2TextView.setVisibility(View.INVISIBLE);
            wicketsLostInterruption2TextView.setVisibility(View.INVISIBLE);
            oversRemainingInterruption2TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption2EditText.setVisibility(View.INVISIBLE);
            wicketsLostInterruption2EditText.setVisibility(View.INVISIBLE);
            oversRemainingInterruption2EditText.setVisibility(View.INVISIBLE);
            totalInter2TextView.setVisibility(View.INVISIBLE);
            totalInter2EditText.setVisibility(View.INVISIBLE);
            interruption3TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption3TextView.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3TextView.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3TextView.setVisibility(View.INVISIBLE);
            whichOverInterruption3EditText.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3EditText.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3EditText.setVisibility(View.INVISIBLE);
            totalInter3EditText.setVisibility(View.INVISIBLE);
            totalInter3TextView.setVisibility(View.INVISIBLE);
        }

    }

    //activating calculate button
    public void activateCalcBtn(View v) {

        int interrupt = state.getInterruptions();
        double intOverStart;
        double intOverEnd = 0.0;
        double wholeOvers;
        double oversCanPut = 0.0;
        if (interrupt == 1) {
            intOverStart = state.getInter1StartOver();
            intOverEnd = state.getInter1EndOver();
            wholeOvers = state.getOvers();
            oversCanPut = wholeOvers - intOverStart;
        } else if (interrupt == 2) {
            intOverStart = state.getInter2StartOver();
            Log.v("intOverStart: ", String.valueOf(intOverStart));
            intOverEnd = state.getInter2EndOver();
            Log.v("intOverEnd: ", String.valueOf(intOverEnd));
            wholeOvers = state.getInter1StartOver() + state.getInter1EndOver();
            Log.v("wholeOvers: ", String.valueOf(wholeOvers));
            oversCanPut = wholeOvers - intOverStart;
            Log.v("oversCanPut: ", String.valueOf(oversCanPut));
        }
        if (oversCanPut < intOverEnd) {
            String oversCanPutToS = String.valueOf(oversCanPut);
            Toast.makeText(getApplicationContext(), "Overs remaining cannot be greater than " + oversCanPutToS + " overs!", Toast.LENGTH_SHORT).show();
        } else {
            AsyncCalculation calc = new AsyncCalculation();
            calc.execute();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void editTextData() {

        whichOverInterruption1EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter1Over = 0;
                String inter1overToS = s.toString();
                if (inter1overToS.equals("")) {
                    inter1overToS = "0";
                }
                inter1Over = Double.parseDouble(inter1overToS);
                state.setInter1StartOver(inter1Over);
            }
        });
        whichOverInterruption2EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter2Over = 0;
                String inter2overToS = s.toString();
                if (inter2overToS.equals("")) {
                    inter2overToS = "0";
                }
                inter2Over = Double.parseDouble(inter2overToS);
                state.setInter2StartOver(inter2Over);
            }
        });
        whichOverInterruption3EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter3Over = 0;
                String inter3overToS = s.toString();
                if (inter3overToS.equals("")) {
                    inter3overToS = "0";
                }
                inter3Over = Double.parseDouble(inter3overToS);
                state.setInter3StartOver(inter3Over);
            }
        });
        wicketsLostInterruption1EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter1Wickets = 0;
                String inter1WicketsToS = s.toString();
                if (inter1WicketsToS.equals("")) {
                    inter1WicketsToS = "0";
                }
                inter1Wickets = Integer.parseInt(inter1WicketsToS);
                state.setInter1Wickets(inter1Wickets);
            }
        });
        wicketsLostInterruption2EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter2Wickets = 0;
                String inter2WicketsToS = s.toString();
                if (inter2WicketsToS.equals("")) {
                    inter2WicketsToS = "0";
                }
                inter2Wickets = Integer.parseInt(inter2WicketsToS);
                state.setInter2Wickets(inter2Wickets);
            }
        });
        wicketsLostInterruption3EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter3Wickets = 0;
                String inter3WicketsToS = s.toString();
                if (inter3WicketsToS.equals("")) {
                    inter3WicketsToS = "0";
                }
                inter3Wickets = Integer.parseInt(inter3WicketsToS);
                state.setInter3Wickets(inter3Wickets);
            }
        });
        oversRemainingInterruption1EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter1OversAtEnd = 0;
                String inter1OversAtEndToS = s.toString();
                if (inter1OversAtEndToS.equals("")) {
                    inter1OversAtEndToS = "0";
                }
                inter1OversAtEnd = Double.parseDouble(inter1OversAtEndToS);
                state.setInter1EndOver(inter1OversAtEnd);
            }
        });
        oversRemainingInterruption2EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter2OversAtEnd = 0;
                String inter2OversAtEndToS = s.toString();
                if (inter2OversAtEndToS.equals("")) {
                    inter2OversAtEndToS = "0";
                }
                inter2OversAtEnd = Double.parseDouble(inter2OversAtEndToS);
                state.setInter2EndOver(inter2OversAtEnd);
            }
        });
        oversRemainingInterruption3EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter3OversAtEnd = 0;
                String inter3OversAtEndToS = s.toString();
                if (inter3OversAtEndToS.equals("")) {
                    inter3OversAtEndToS = "0";
                }
                inter3OversAtEnd = Double.parseDouble(inter3OversAtEndToS);
                state.setInter3EndOver(inter3OversAtEnd);
            }
        });

        totalInter1EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter1total = 0;
                String inter1OversAtEndToS = s.toString();
                if (inter1OversAtEndToS.equals("")) {
                    inter1OversAtEndToS = "0";
                }
                inter1total = Integer.parseInt(inter1OversAtEndToS);
                state.setTotalT2int1(inter1total);
            }
        });
        totalInter2EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter2total = 0;
                String inter2OversAtEndToS = s.toString();
                if (inter2OversAtEndToS.equals("")) {
                    inter2OversAtEndToS = "0";
                }
                inter2total = Integer.parseInt(inter2OversAtEndToS);
                state.setTotalT2int2(inter2total);
            }
        });
        totalInter3EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter3total = 0;
                String inter3OversAtEndToS = s.toString();
                if (inter3OversAtEndToS.equals("")) {
                    inter3OversAtEndToS = "0";
                }
                inter3total = Integer.parseInt(inter3OversAtEndToS);
                state.setTotalT2int3(inter3total);
            }
        });

    }

    private int calculations() {
        int target = 0;
        //getting the number of interruptions
        interuptns = state.getInterruptions();
        //Overs both teams has at the start of the match
        double startOfInnsOvers = state.getOvers();
        Log.v("overs in Scenario1: ", String.valueOf(startOfInnsOvers));
        //Setting up the key to grab the resource percentage at the start of the match
        int startOfInnsOversWickets = (int) (startOfInnsOvers * 100);
        //resources percentage at the start of the match
        double resAtStartofMatch = overData.DataSet(startOfInnsOversWickets);
        Log.v("ResourcesStartofMatch: ", String.valueOf(resAtStartofMatch));
        //Team 1 total score at the end of their innings
        int t1TotalScore = state.getTotalT1();
        Log.v("Team1 Total: ", String.valueOf(t1TotalScore));
        //Team 1 wickets at the end of their innings
        int t1Wickets = state.getWickets();
        Log.v("Team1 Wickets: ", String.valueOf(t1Wickets));
        //Team 2 Overs at the start of the interruption 1
        double oversAtInter1Start = state.getInter1StartOver();
        Log.v("oversAtInter1Start: ", String.valueOf(oversAtInter1Start));
        //Team 2 Wickets at the start of the interruption 1
        int wicketsAtInter1Start = state.getInter1Wickets();
        //overs remaining at the start of the interruption
        double remainingOversAtInterStart = startOfInnsOvers - oversAtInter1Start;
        //Team 2 Overs and wickets together when the interruption happened to set up key to find resource percentage
        int oversAndWicketsStartTogether = (int) ((remainingOversAtInterStart * 100) + wicketsAtInter1Start);
        Log.v("WktsAndOvrs2gthrStart: ", String.valueOf(oversAndWicketsStartTogether));
        //resource percentage at the start of the interruption
        double percentageResourcesAtInterStart = overData.DataSet(oversAndWicketsStartTogether);
        Log.v("resources@interStart: ", String.valueOf(percentageResourcesAtInterStart));

        //overs remaining at the end of the interruption
        double oversRemainingInterEnd = state.getInter1EndOver();
        Log.v("netOvers: ", String.valueOf(remainingOversAtInterStart));
        //overs remaining *10 to get rid of decimal
        int totalOversRemainingPlusWickets = (int) ((oversRemainingInterEnd * 100) + wicketsAtInter1Start);
        Log.v("TotalOversAndWkts: ", String.valueOf(totalOversRemainingPlusWickets));
        //resources left gathered from dataSet at the end of the interruption
        double resourceCheckForRemainderOvers = overData.DataSet(totalOversRemainingPlusWickets);
        Log.v("resForRemainder: ", String.valueOf(resourceCheckForRemainderOvers));
        //resources lost at the end of the interruption
        double resourcesLost = percentageResourcesAtInterStart - resourceCheckForRemainderOvers;
        Log.v("resourcesLost: ", String.valueOf(resourcesLost));
        //resources left at end of interruption
        double resourcesLeftAtEndInter = resAtStartofMatch - resourcesLost;

        if (interuptns == 1) {
//            //Team 2 Overs at the start of the interruption 1
//            double oversAtInter1Start = state.getInter1StartOver();
//            Log.v("oversAtInter1Start: ", String.valueOf(oversAtInter1Start));
//            //Team 2 Wickets at the start of the interruption 1
//            int wicketsAtInter1Start = state.getInter1Wickets();
//            //overs remaining at the start of the interruption
//            double remainingOversAtInterStart = startOfInnsOvers - oversAtInter1Start;
//            //Team 2 Overs and wickets together when the interruption happened to set up key to find resource percentage
//            int oversAndWicketsStartTogether = (int) ((remainingOversAtInterStart * 100) + wicketsAtInter1Start);
//            Log.v("WktsAndOvrs2gthrStart: ", String.valueOf(oversAndWicketsStartTogether));
//            //resource percentage at the start of the interruption
//            double percentageResourcesAtInterStart = overData.DataSet(oversAndWicketsStartTogether);
//            Log.v("resources@interStart: ", String.valueOf(percentageResourcesAtInterStart));
//
//            //overs remaining at the end of the interruption
//            double oversRemainingInterEnd = state.getInter1EndOver();
//            Log.v("netOvers: ", String.valueOf(remainingOversAtInterStart));
//            //overs remaining *10 to get rid of decimal
//            int totalOversRemainingPlusWickets = (int) ((oversRemainingInterEnd * 100) + wicketsAtInter1Start);
//            Log.v("TotalOversAndWkts: ", String.valueOf(totalOversRemainingPlusWickets));
//            //resources left gathered from dataSet at the end of the interruption
//            double resourceCheckForRemainderOvers = overData.DataSet(totalOversRemainingPlusWickets);
//            Log.v("resForRemainder: ", String.valueOf(resourceCheckForRemainderOvers));
//            //resources lost at the end of the interruption
//            double resourcesLost = percentageResourcesAtInterStart - resourceCheckForRemainderOvers;
//            Log.v("resourcesLost: ", String.valueOf(resourcesLost));
//            //resources left at end of interruption
//            double resourcesLeftAtEndInter = resAtStartofMatch - resourcesLost;
            Log.v("resourcesAvailable: ", String.valueOf(resourcesLeftAtEndInter));
            //setting up target
            target = (int) ((t1TotalScore * (resourcesLeftAtEndInter / resAtStartofMatch)) + 1.5);
            Log.v("Target@calculations: ", String.valueOf(target));
        } else if (interuptns == 2) {
            double oversAtInter2Start = state.getInter2StartOver();
            //Team 2 Wickets at the start of the interruption 1
            int wicketsAtInter2Start = state.getInter2Wickets();
            double oversFinalizedAtInter1End = oversAtInter1Start + oversRemainingInterEnd;
            Log.v("oversFinalizedAtInter1End: ", String.valueOf(oversFinalizedAtInter1End));
            double oversRemainingAtInter2End = state.getInter2EndOver();
            /**
             * Make a check at button press for this.
             */
            double oversFinalizedAtInter2End = oversAtInter2Start + oversRemainingAtInter2End;
            Log.v("oversFinalizedAtInter2End: ", String.valueOf(oversFinalizedAtInter2End));
            double oversLeftAtInter2Start = oversFinalizedAtInter1End - oversAtInter2Start;
            Log.v("oversLeftAtInter2Start: ", String.valueOf(oversLeftAtInter2Start));
            int oversWktsLeftAtInter2Start = (int) ((oversLeftAtInter2Start * 100) + wicketsAtInter2Start);
            Log.v("resLeftAtInter2Start: ", String.valueOf(oversWktsLeftAtInter2Start));
            double resLeftAtInter2Start = overData.DataSet(oversWktsLeftAtInter2Start);
            double oversLeftAtInter2End = oversFinalizedAtInter2End - oversAtInter2Start;
            Log.v("oversLeftAtInter2End: ", String.valueOf(oversLeftAtInter2End));
            int oversWktsLeftAtInter2End = (int) ((oversLeftAtInter2End * 100) + wicketsAtInter2Start);
            Log.v("resLeftAtInter2End: ", String.valueOf(oversWktsLeftAtInter2End));
            double resLeftAtInter2End = overData.DataSet(oversWktsLeftAtInter2End);
            double resLostAtInt2 = resLeftAtInter2Start - resLeftAtInter2End;
            double resLeftAtInt2 = resourcesLeftAtEndInter - resLostAtInt2;
            Log.v("resLeftAtInt2: ", String.valueOf(resLeftAtInt2));
            target = (int) ((t1TotalScore * (resLeftAtInt2 / resAtStartofMatch)) + 1.5);
            Log.v("targetInt2: ", String.valueOf(target));
        }

        return target;
    }

    private void toWinTarget(int target) {
        int toWin;
        int team2score = 0;
        double remainingOvers = 0.0;
        if (interuptns == 1) {
            team2score = state.getTotalT2int1();
            remainingOvers = state.getInter1EndOver();
        } else if (interuptns == 2) {
            team2score = state.getTotalT2int2();
            remainingOvers = state.getInter2EndOver();
        }
        toWin = target - team2score;
        String toWinToS = String.valueOf(toWin);

        Log.v("Need to win: ", String.valueOf(toWin));


        t2WinScore.setTitle("Target");
        if (remainingOvers == 0.0) {
            if (toWin <= 0) {
                toWin = Math.abs(toWin);
                toWinToS = String.valueOf(toWin);
                t2WinScore.setMessage("Team 2 has won the match by " + toWinToS + " runs.");
            } else {
                toWin = toWin--;
                toWinToS = String.valueOf(toWin);
                t2WinScore.setMessage("Team 1 has won the match by " + toWinToS + " runs.");
            }

        } else if (toWin <= 0) {
            toWin = Math.abs(toWin);
            toWinToS = String.valueOf(toWin);
            t2WinScore.setMessage("Team 2 has won the match by " + toWinToS + " runs.");
        } else {
            t2WinScore.setMessage("Team 2 needs " + toWinToS + " run(s) to Win.");
        }
        t2WinScore.setPositiveButton("OK", null);
        t2WinScore.show();
    }

    private void init() {
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
        totalInter1TextView = (TextView) findViewById(R.id.team2_total_interruption_1_text_view);
        totalInter2TextView = (TextView) findViewById(R.id.team2_total_interruption_2_text_view);
        totalInter3TextView = (TextView) findViewById(R.id.team2_total_interruption_3_text_view);
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
        totalInter1EditText = (EditText) findViewById(R.id.total_interruption_1_edit_text);
        totalInter2EditText = (EditText) findViewById(R.id.total_interruption_2_edit_text);
        totalInter3EditText = (EditText) findViewById(R.id.total_interruption_3_edit_text);

        totalWickets = 10;
        overData = new DataMap();
        state = (StateClass) getApplicationContext();
        t2WinScore = new AlertDialog.Builder(Scenario1.this);
    }

    private class AsyncCalculation extends AsyncTask<Integer, Void, Integer> {
        ProgressDialog pd = new ProgressDialog(Scenario1.this);
        String response = "";
        int target = 0;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("\tCalculating...");
            pd.show();
        }

        @Override
        protected Integer doInBackground(Integer... params) {

            try {
                target = calculations();
                Log.v("theCalculatedTarget: ", String.valueOf(target));

            } catch (Exception e) {
                e.printStackTrace();
                response = e.getMessage();
            }
            return target;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            Log.v("theCalculatedTrgtPE: ", String.valueOf(result));
            toWinTarget(result);
            pd.dismiss();
        }
    }
}
