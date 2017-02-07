package com.sportsoutclass.outclassdl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondInnings extends BaseFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    @BindView(R.id.second_innings_interruption_1_container)
    LinearLayout second_innings_interruption_1_container;
    @BindView(R.id.second_innings_interruption_2_container)
    LinearLayout second_innings_interruption_2_container;
    @BindView(R.id.second_innings_interruption_3_container)
    LinearLayout second_innings_interruption_3_container;

    @BindView(R.id.second_innings_number_overs_et)
    EditText second_innings_number_overs_et;
    @BindView(R.id.second_innings_team_1_score_et)
    EditText second_innings_team_1_score_et;
    @BindView(R.id.second_innings_team_1_wickets_et)
    EditText second_innings_team_1_wickets_et;


    @BindView(R.id.second_innings_which_over_interruption_1_et)
    EditText second_innings_which_over_interruption_1_et;
    @BindView(R.id.second_innings_total_interruption_1_et)
    EditText second_innings_total_interruption_1_et;
    @BindView(R.id.second_innings_wickets_lost_interruption_1_et)
    EditText second_innings_wickets_lost_interruption_1_et;
    @BindView(R.id.second_innings_overs_remaining_interruption_1_et)
    EditText second_innings_overs_remaining_interruption_1_et;

    @BindView(R.id.second_innings_which_over_interruption_2_et)
    EditText second_innings_which_over_interruption_2_et;
    @BindView(R.id.second_innings_total_interruption_2_et)
    EditText second_innings_total_interruption_2_et;
    @BindView(R.id.second_innings_wickets_lost_interruption_2_et)
    EditText second_innings_wickets_lost_interruption_2_et;
    @BindView(R.id.second_innings_overs_remaining_interruption_2_et)
    EditText second_innings_overs_remaining_interruption_2_et;

    @BindView(R.id.second_innings_which_over_interruption_3_et)
    EditText second_innings_which_over_interruption_3_et;
    @BindView(R.id.second_innings_total_interruption_3_et)
    EditText second_innings_total_interruption_3_et;
    @BindView(R.id.second_innings_wickets_lost_interruption_3_et)
    EditText second_innings_wickets_lost_interruption_3_et;
    @BindView(R.id.second_innings_overs_remaining_interruption_3_et)
    EditText second_innings_overs_remaining_interruption_3_et;

    @BindView(R.id.second_innings_interruptions_spinner)
    Spinner second_innings_interruptions_spinner;
    @BindView(R.id.first_innings_interruption_check_spinner)
    Spinner first_innings_interruption_check_spinner;
    @BindView(R.id.second_innings_calc_button)
    Button second_innings_calc_button;

    DataMap overData;
    View view;
    StateClass state;
    DataMap firstInningsOverData;
    InterruptionSetup interruptionSetup;
    AlertDialog.Builder t2WinTarget;
    AlertDialog.Builder usrErrAlert;
    boolean allFieldsFilled, firstInningsHadInterCheck;
    int team1FinalTotal, totalWicketsSI, team1TotalWicketsOfMatch, inter1WicketsSI, inter2WicketsSI, inter3WicketsSI, inter1totalSI, inter2totalSI, inter3totalSI;
    double totalOversOfMatch, inter1OversSI, inter2OversSI, inter3OversSI, inter1OversAtEndSI, inter2OversAtEndSI, inter3OversAtEndSI, team1Resources;

    public SecondInnings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_innings, container, false);
        ButterKnife.bind(this, view);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        totalWicketsSI = 10;
        overData = new DataMap();
        state = (StateClass) getActivity().getApplication();
        usrErrAlert = new AlertDialog.Builder(getActivity());
        t2WinTarget = new AlertDialog.Builder(getActivity());
        firstInningsOverData = new DataMap();
        interruptionSetup = new InterruptionSetup();
        allFieldsFilled = false;
        second_innings_calc_button.setOnClickListener(this);
        Tracking analyticsTracker = new Tracking("SecondInnings", state);
        analyticsTracker.doTracking();

        ArrayAdapter<CharSequence> check_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.yes_no_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        check_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        first_innings_interruption_check_spinner.setAdapter(check_adapter);
        first_innings_interruption_check_spinner.setOnItemSelectedListener(this);
        first_innings_interruption_check_spinner.setSelection(1);

        ArrayAdapter<CharSequence> interruptions_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.interruptions_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        interruptions_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        second_innings_interruptions_spinner.setAdapter(interruptions_adapter);
        second_innings_interruptions_spinner.setOnItemSelectedListener(this);

        editTextWatcher();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.second_innings_calc_button:
                //gets the number of interruptions and checks if all the necessary edit texts are filled
                int interrupt = state.getInterruptionsSI();
                boolean allFieldsFilled = whichFieldsToCheck(interrupt);

                if (allFieldsFilled) {

                    new AsyncCalculation().execute(interrupt);
                    InputMethodManager iMM = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    iMM.hideSoftInputFromWindow(v.getWindowToken(), 0);

                } else {
                    usrErrAlert.setTitle("Incomplete Information");
                    usrErrAlert.setMessage("Please fill all the blanks");
                    usrErrAlert.setPositiveButton("OK", null);
                    usrErrAlert.show();

                }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.second_innings_interruptions_spinner:
                if (position == 0) {
                    InterruptionsAmountVisibilitySetup(1);
                    state.setInterruptionsSI(1);
                } else if (position == 1) {
                    InterruptionsAmountVisibilitySetup(2);
                    state.setInterruptionsSI(2);
                } else if (position == 2) {
                    InterruptionsAmountVisibilitySetup(3);
                    state.setInterruptionsSI(3);
                }
                break;
            case R.id.first_innings_interruption_check_spinner:
                if (position == 0) {
                    //get shared pref value if there was an interruption in the first innings
                    firstInningsHadInterCheck = true;
                    SharedPreferences pref = state.getApplicationContext().getSharedPreferences("MyPref", 0);
                    team1Resources = pref.getFloat("firstInningsResource", 0); // getting Float
                    state.setTeam1StartResourcesForSI(team1Resources);
                    Toast.makeText(getContext(), "Last First Innings Calculation Data has been Loaded", Toast.LENGTH_SHORT).show();
                    Log.v("team1Resources@pref: ", String.valueOf(team1Resources));
                } else if (position == 1) {
                    firstInningsHadInterCheck = false;
                    team1Resources = overData.DataSet((int) totalOversOfMatch * 100);
                    Log.v("team1Resources@noPref: ", String.valueOf(team1Resources));
                    state.setTeam1StartResourcesForSI(team1Resources);
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class AsyncCalculation extends AsyncTask<Integer, Void, Integer> {
        ProgressDialog pd = new ProgressDialog(getActivity());
        InterruptionSetup interNew = new InterruptionSetup();
        String response = "";
        int target = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("\tCalculating...");
            pd.show();
        }

        @Override
        protected Integer doInBackground(Integer... interruptions) {
            int interruption = interruptions[0];
            try {
                if (interruption == 1) {
                    target = interNew.one_interruption_SecondInnings();
                } else if (interruption == 2) {
                    target = interNew.two_interruptions_SecondInnings();
                } else if (interruption == 3) {
                    target = interNew.three_interruptions_SecondInnings();
                }
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


    //this enables disables visibility of number of interruptions available to edit
    private void InterruptionsAmountVisibilitySetup(int i) {
        if (i == 1) {
            second_innings_interruption_1_container.setVisibility(View.VISIBLE);
            second_innings_interruption_2_container.setVisibility(View.GONE);
            second_innings_interruption_3_container.setVisibility(View.GONE);
            second_innings_number_overs_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            second_innings_team_1_score_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            second_innings_team_1_wickets_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            second_innings_which_over_interruption_1_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            second_innings_total_interruption_1_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            second_innings_wickets_lost_interruption_1_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            second_innings_overs_remaining_interruption_1_et.setImeOptions(EditorInfo.IME_ACTION_DONE);

        } else if (i == 2) {
            InterruptionsAmountVisibilitySetup(1);

            second_innings_interruption_2_container.setVisibility(View.VISIBLE);
            second_innings_interruption_3_container.setVisibility(View.GONE);
            second_innings_which_over_interruption_2_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            second_innings_total_interruption_2_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            second_innings_wickets_lost_interruption_2_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            second_innings_overs_remaining_interruption_2_et.setImeOptions(EditorInfo.IME_ACTION_DONE);
            second_innings_overs_remaining_interruption_1_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        } else if (i == 3) {
            InterruptionsAmountVisibilitySetup(2);

            second_innings_interruption_3_container.setVisibility(View.VISIBLE);
            second_innings_which_over_interruption_3_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            second_innings_total_interruption_3_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            second_innings_wickets_lost_interruption_3_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            second_innings_overs_remaining_interruption_3_et.setImeOptions(EditorInfo.IME_ACTION_DONE);
            second_innings_overs_remaining_interruption_2_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        }

    }

    public boolean whichFieldsToCheck(int inter) {
        boolean notEmpty = false;
        if (inter == 1) {
            boolean x1 = interruptionSetup.editTextFieldCheck(second_innings_total_interruption_1_et);
            boolean x2 = interruptionSetup.editTextFieldCheck(second_innings_which_over_interruption_1_et);
            boolean x3 = interruptionSetup.editTextFieldCheck(second_innings_wickets_lost_interruption_1_et);
            boolean x4 = interruptionSetup.editTextFieldCheck(second_innings_overs_remaining_interruption_1_et);
            boolean x5 = interruptionSetup.editTextFieldCheck(second_innings_number_overs_et);
            boolean x6 = interruptionSetup.editTextFieldCheck(second_innings_team_1_score_et);
            boolean x7 = interruptionSetup.editTextFieldCheck(second_innings_team_1_wickets_et);

            if (x1 && x2 && x3 && x4 && x5 && x6 && x7) {
                notEmpty = true;
            }
        } else if (inter == 2) {
            boolean x1 = whichFieldsToCheck(1);
            boolean x2 = interruptionSetup.editTextFieldCheck(second_innings_total_interruption_2_et);
            boolean x3 = interruptionSetup.editTextFieldCheck(second_innings_which_over_interruption_2_et);
            boolean x4 = interruptionSetup.editTextFieldCheck(second_innings_wickets_lost_interruption_2_et);
            boolean x5 = interruptionSetup.editTextFieldCheck(second_innings_overs_remaining_interruption_2_et);
            if (x1 && x2 && x3 && x4 && x5) {
                notEmpty = true;
            }
        } else if (inter == 3) {
            boolean x1 = whichFieldsToCheck(2);
            boolean x2 = interruptionSetup.editTextFieldCheck(second_innings_total_interruption_3_et);
            boolean x3 = interruptionSetup.editTextFieldCheck(second_innings_which_over_interruption_3_et);
            boolean x4 = interruptionSetup.editTextFieldCheck(second_innings_wickets_lost_interruption_3_et);
            boolean x5 = interruptionSetup.editTextFieldCheck(second_innings_overs_remaining_interruption_3_et);
            if (x1 && x2 && x3 && x4 && x5) {
                notEmpty = true;
            }
        }
        return notEmpty;
    }

    private void editTextWatcher() {
        //Number of overs set at the start of the match.
        second_innings_number_overs_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String numberOfOversToS = s.toString();
                if (numberOfOversToS.equals("")) {
                    numberOfOversToS = "0";
                }
                totalOversOfMatch = Double.parseDouble(numberOfOversToS);
                if (totalOversOfMatch > 50.0) {
                    InterruptionSetup.interruptionErrors(usrErrAlert, -10013, "Error", "");
                }
                if (!firstInningsHadInterCheck) {
                    team1Resources = overData.DataSet((int) totalOversOfMatch * 100);
                    Log.v("team1Resources@ET: ", String.valueOf(team1Resources));
                    state.setTeam1StartResourcesForSI(team1Resources);
                }
                state.setOvers(totalOversOfMatch);
                Log.v("TotalOvers: ", numberOfOversToS);
            }
        });
        //Total team 1 final total
        second_innings_team_1_score_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String team1TotalToS = s.toString();
                if (team1TotalToS.equals("")) {
                    team1TotalToS = "0";
                }
                team1FinalTotal = Integer.parseInt(team1TotalToS);
                state.setTotalT1(team1FinalTotal);
                Log.v("Final Total: ", team1TotalToS);
            }
        });
        //Total team 1 wickets fallen.
        second_innings_team_1_wickets_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String team1NumberOfWicketsToS = s.toString();
                if (team1NumberOfWicketsToS.equals("")) {
                    team1NumberOfWicketsToS = "0";
                }
                team1TotalWicketsOfMatch = Integer.parseInt(team1NumberOfWicketsToS);
                if (team1TotalWicketsOfMatch > totalWicketsSI) {
                    InterruptionSetup.interruptionErrors(usrErrAlert, -10002, "Error", "");

                }
                state.setWickets(team1TotalWicketsOfMatch);
                Log.v("TotalWickets: ", team1NumberOfWicketsToS);
            }
        });
        //overs at the start of interruption
        second_innings_which_over_interruption_1_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter1OversSI = 0;
                String inter1overToS = s.toString();
                if (inter1overToS.equals("")) {
                    inter1overToS = "0";
                }
                inter1OversSI = Double.parseDouble(inter1overToS);
                state.setInter1StartOverSI(inter1OversSI);
                Log.v("int1oversSI: ", inter1overToS);
            }
        });
        second_innings_which_over_interruption_2_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter2OversSI = 0;
                String inter2overToS = s.toString();
                if (inter2overToS.equals("")) {
                    inter2overToS = "0";
                }
                inter2OversSI = Double.parseDouble(inter2overToS);
                state.setInter2StartOverSI(inter2OversSI);
            }
        });
        second_innings_which_over_interruption_3_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter3OversSI = 0;
                String inter3overToS = s.toString();
                if (inter3overToS.equals("")) {
                    inter3overToS = "0";
                }
                inter3OversSI = Double.parseDouble(inter3overToS);
                state.setInter3StartOverSI(inter3OversSI);
            }
        });
        //wickets at the start of interruption
        second_innings_wickets_lost_interruption_1_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter1WicketsSI = 0;
                String inter1WicketsToS = s.toString();
                if (inter1WicketsToS.equals("")) {
                    inter1WicketsToS = "0";
                }
                inter1WicketsSI = Integer.parseInt(inter1WicketsToS);
                if (inter1WicketsSI > totalWicketsSI) {
                    InterruptionSetup.interruptionErrors(usrErrAlert, -10003, "Error", inter1WicketsToS);
                }
                state.setInter1WicketsSI(inter1WicketsSI);
                Log.v("int1wicketsSI: ", inter1WicketsToS);
            }
        });
        second_innings_wickets_lost_interruption_2_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter2WicketsSI = 0;
                String inter2WicketsToS = s.toString();
                if (inter2WicketsToS.equals("")) {
                    inter2WicketsToS = "0";
                }
                inter2WicketsSI = Integer.parseInt(inter2WicketsToS);
                if (inter2WicketsSI > totalWicketsSI) {
                    InterruptionSetup.interruptionErrors(usrErrAlert, -10004, "Error", inter2WicketsToS);
                }
                state.setInter2WicketsSI(inter2WicketsSI);
            }

        });
        second_innings_wickets_lost_interruption_3_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter3WicketsSI = 0;
                String inter3WicketsToS = s.toString();
                if (inter3WicketsToS.equals("")) {
                    inter3WicketsToS = "0";
                }
                inter3WicketsSI = Integer.parseInt(inter3WicketsToS);
                if (inter3WicketsSI > totalWicketsSI) {
                    InterruptionSetup.interruptionErrors(usrErrAlert, -10005, "Error", inter3WicketsToS);
                }
                state.setInter3WicketsSI(inter3WicketsSI);
            }
        });
        //overs remaining till end of play
        second_innings_overs_remaining_interruption_1_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter1OversAtEndSI = 0;
                String inter1OversAtEndToS = s.toString();
                if (inter1OversAtEndToS.equals("")) {
                    inter1OversAtEndToS = "0";
                }
                inter1OversAtEndSI = Double.parseDouble(inter1OversAtEndToS);
                state.setInter1EndOverSI(inter1OversAtEndSI);
                Log.v("int1oversRemainSI: ", inter1OversAtEndToS);
            }
        });
        second_innings_overs_remaining_interruption_2_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter2OversAtEndSI = 0;
                String inter2OversAtEndToS = s.toString();
                if (inter2OversAtEndToS.equals("")) {
                    inter2OversAtEndToS = "0";
                }
                inter2OversAtEndSI = Double.parseDouble(inter2OversAtEndToS);
                state.setInter2EndOverSI(inter2OversAtEndSI);
            }
        });
        second_innings_overs_remaining_interruption_3_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter3OversAtEndSI = 0;
                String inter3OversAtEndToS = s.toString();
                if (inter3OversAtEndToS.equals("")) {
                    inter3OversAtEndToS = "0";
                }
                inter3OversAtEndSI = Double.parseDouble(inter3OversAtEndToS);
                state.setInter3EndOverSI(inter3OversAtEndSI);
            }
        });
        //total at the interruption
        second_innings_total_interruption_1_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter1totalSI = 0;
                String inter1OversAtEndToS = s.toString();
                if (inter1OversAtEndToS.equals("")) {
                    inter1OversAtEndToS = "0";
                }
                inter1totalSI = Integer.parseInt(inter1OversAtEndToS);
                state.setTotalT2int1SI(inter1totalSI);
                Log.v("int1TotalSI: ", inter1OversAtEndToS);
            }
        });
        second_innings_total_interruption_2_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter2totalSI = 0;
                String inter2OversAtEndToS = s.toString();
                if (inter2OversAtEndToS.equals("")) {
                    inter2OversAtEndToS = "0";
                }
                inter2totalSI = Integer.parseInt(inter2OversAtEndToS);
                state.setTotalT2int2SI(inter2totalSI);
            }
        });
        second_innings_total_interruption_3_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter3totalSI = 0;
                String inter3OversAtEndToS = s.toString();
                if (inter3OversAtEndToS.equals("")) {
                    inter3OversAtEndToS = "0";
                }
                inter3totalSI = Integer.parseInt(inter3OversAtEndToS);
                state.setTotalT2int3SI(inter3totalSI);
            }
        });
    }

    /**
     * Calculates the to win score and checks if there is a target to chase or if the
     * game is over. shows an Alert Dialog accordingly
     *
     * @param target the score team has to chase
     */
    private void toWinTarget(final int target) {
        int toWin;
        int team2score = 0;
        double remainingOvers = 0.0;
        if (target > -10000) {
            int interruptions = state.getInterruptionsSI();
            if (interruptions == 1) {
                team2score = state.getTotalT2int1SI();
                remainingOvers = state.getInter1EndOverSI();
            } else if (interruptions == 2) {
                team2score = state.getTotalT2int2SI();
                remainingOvers = state.getInter2EndOverSI();
            } else if (interruptions == 3) {
                team2score = state.getTotalT2int3SI();
                Log.v("team2scoreInt3: ", String.valueOf(team2score));
                remainingOvers = state.getInter3EndOverSI();
            }
            toWin = target - team2score;
            String toWinToS = String.valueOf(toWin);

            Log.v("Need to win: ", String.valueOf(toWin));

            if (remainingOvers == 0.0) {
                if (toWin <= 0) {
                    toWin = Math.abs(toWin);
                    toWinToS = String.valueOf(toWin);
                    t2WinTarget.setTitle("Final Result");
                    t2WinTarget.setMessage("\nTarget was " + target + ". \n\nTeam 2 has won the match by " + toWinToS + " run(s).");
                } else {
                    Log.v("Need to win: ", String.valueOf(toWin));
                    //when team 1 wins 1 run less is counted
                    toWinToS = String.valueOf(toWin - 1);
                    t2WinTarget.setTitle("Final Result");
                    t2WinTarget.setMessage("\nTarget was " + target + ". \n\nTeam 1 has won the match by " + toWinToS + " run(s).");
                }

            } else if (toWin <= 0) {
                toWin = Math.abs(toWin);
                toWinToS = String.valueOf(toWin);
                t2WinTarget.setTitle("Final Result");
                t2WinTarget.setMessage("\nTarget was " + target + ". \n\nTeam 2 has won the match by " + toWinToS + " run(s).");
            } else {
                Log.v("Need to win: ", String.valueOf(toWin));
                t2WinTarget.setTitle("Target");
                t2WinTarget.setMessage("\nTarget is " + target + ". \n\nTeam 2 needs " + toWinToS + " run(s) to Win.");
                t2WinTarget.setNeutralButton("Par Score Table", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        state.setParScoreTarget(target);
                        Fragment rateTable = new RateTable();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, rateTable, "rateTable").addToBackStack("inningsPick").commit();
                    }
                });
            }
            t2WinTarget.setPositiveButton("OK", null);
            t2WinTarget.show();
        } else {
            String errMsgValue = state.getErrorMessageValue();
            String errMsgTitle = state.getErrorMessageTitle();
            InterruptionSetup.interruptionErrors(usrErrAlert, target, errMsgTitle, errMsgValue);
        }
    }

}
