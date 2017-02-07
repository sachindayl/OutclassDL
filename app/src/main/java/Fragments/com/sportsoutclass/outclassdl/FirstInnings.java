package com.sportsoutclass.outclassdl;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstInnings extends BaseFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    public FirstInnings() {
        // Required empty public constructor
    }

    @BindView(R.id.first_innings_calc_button)
    Button calculate_Btn;
    //Layout Binding
    @BindView(R.id.first_innings_interruption_1_container)
    LinearLayout first_innings_interruption_1_container;
    @BindView(R.id.first_innings_interruption_2_container)
    LinearLayout first_innings_interruption_2_container;
    @BindView(R.id.first_innings_interruption_3_container)
    LinearLayout first_innings_interruption_3_container;

    //TextView Bindings
    @BindView(R.id.first_innings_interruption_1_tv)
    TextView first_innings_interruption_1_tv;
    @BindView(R.id.first_innings_total_interruption_1_tv)
    TextView first_innings_team2_total_interruption_1_tv;
    @BindView(R.id.first_innings_total_interruption_2_tv)
    TextView first_innings_team2_total_interruption_2_tv;
    @BindView(R.id.first_innings_total_interruption_3_tv)
    TextView first_innings_team2_total_interruption_3_tv;
    @BindView(R.id.first_innings_interruption_2_tv)
    TextView first_innings_interruption_2_tv;
    @BindView(R.id.first_innings_interruption_3_tv)
    TextView first_innings_interruption_3_tv;
    @BindView(R.id.first_innings_which_over_interruption_1_tv)
    TextView first_innings_which_over_interruption_1_tv;
    @BindView(R.id.first_innings_which_over_interruption_2_tv)
    TextView first_innings_which_over_interruption_2_tv;
    @BindView(R.id.first_innings_which_over_interruption_3_tv)
    TextView first_innings_which_over_interruption_3_tv;
    @BindView(R.id.first_innings_wickets_lost_interruption_1_tv)
    TextView first_innings_wickets_lost_interruption_1_tv;
    @BindView(R.id.first_innings_wickets_lost_interruption_2_tv)
    TextView first_innings_wickets_lost_interruption_2_tv;
    @BindView(R.id.first_innings_wickets_lost_interruption_3_tv)
    TextView first_innings_wickets_lost_interruption_3_tv;
    @BindView(R.id.first_innings_overs_remaining_interruption_1_tv)
    TextView first_innings_overs_remaining_interruption_1_tv;
    @BindView(R.id.first_innings_overs_remaining_interruption_2_tv)
    TextView first_innings_overs_remaining_interruption_2_tv;
    @BindView(R.id.first_innings_overs_remaining_interruption_3_tv)
    TextView first_innings_overs_remaining_interruption_3_tv;
    //EditText Bindings
    @BindView(R.id.first_innings_total_interruption_1_et)
    EditText first_innings_total_interruption_1_et;
    @BindView(R.id.first_innings_total_interruption_2_et)
    EditText first_innings_total_interruption_2_et;
    @BindView(R.id.first_innings_total_interruption_3_et)
    EditText first_innings_total_interruption_3_et;
    @BindView(R.id.first_innings_which_over_interruption_1_et)
    EditText first_innings_which_over_interruption_1_et;
    @BindView(R.id.first_innings_which_over_interruption_2_et)
    EditText first_innings_which_over_interruption_2_et;
    @BindView(R.id.first_innings_which_over_interruption_3_et)
    EditText first_innings_which_over_interruption_3_et;
    @BindView(R.id.first_innings_wickets_lost_interruption_1_et)
    EditText first_innings_wickets_lost_interruption_1_et;
    @BindView(R.id.first_innings_wickets_lost_interruption_2_et)
    EditText first_innings_wickets_lost_interruption_2_et;
    @BindView(R.id.first_innings_wickets_lost_interruption_3_et)
    EditText first_innings_wickets_lost_interruption_3_et;
    @BindView(R.id.first_innings_overs_remaining_interruption_1_et)
    EditText first_innings_overs_remaining_interruption_1_et;
    @BindView(R.id.first_innings_overs_remaining_interruption_2_et)
    EditText first_innings_overs_remaining_interruption_2_et;
    @BindView(R.id.first_innings_overs_remaining_interruption_3_et)
    EditText first_innings_overs_remaining_interruption_3_et;
    @BindView(R.id.first_innings_team2_overs_et)
    EditText first_innings_team2_overs_et;
    @BindView(R.id.first_innings_team1_final_total_et)
    EditText first_innings_team1_final_total_et;
    @BindView(R.id.first_innings_interruptions_spinner)
    Spinner first_innings_interruptions_spinner;

    @BindView(R.id.first_innings_number_overs_edit_text)
    EditText first_innings_number_overs_edit_text;
    @BindView(R.id.g50_spinner)
    Spinner g50_Spinner;
    View view;
    StateClass state;
    DataMap firstInningsOverData;
    InterruptionSetup fix;
    List<Map<String, String>> items;
    AlertDialog.Builder t1WinTarget;
    AlertDialog.Builder usrErrAlert;
    private int totalWickets, inter1WicketsFirstInnings, inter2WicketsFirstInnings, inter3WicketsFirstInnings, inter1totalFirstInnings, inter2totalFirstInnings, inter3totalFirstInnings, team1finalTotB4rev;
    double overs, inter1OversFirstInnings, inter2OversFirstInnings, inter3OversFirstInnings, inter1OversAtEndFirstInnings, inter2OversAtEndFirstInnings, inter3OversAtEndFirstInnings, team2OversAtStartFirstInnings;
    boolean allFieldsFilled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first_innings, container, false);
        ButterKnife.bind(this, view);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        totalWickets = 10;
        items = new ArrayList<>();
        state = (StateClass) getActivity().getApplication();
        usrErrAlert = new AlertDialog.Builder(getActivity());
        t1WinTarget = new AlertDialog.Builder(getActivity());
        firstInningsOverData = new DataMap();
        fix = new InterruptionSetup();
        allFieldsFilled = false;


        calculate_Btn.setOnClickListener(this);
        Tracking analyticsTracker = new Tracking("FirstInnings", state);
        analyticsTracker.doTracking();

        Map<String, String> item0 = new HashMap<>(2);
        item0.put("text", "200");
        item0.put("subText", "U-19, U-15, Associate Nations and Below");
        items.add(item0);

        Map<String, String> item1 = new HashMap<>(2);
        item1.put("text", "245");
        item1.put("subText", "First Class Cricket and Above");
        items.add(item1);
//        TeamSelection teamSelect = (TeamSelection) view.getContext();
        SimpleAdapter adapter = new SimpleAdapter(getContext(), items,
                android.R.layout.simple_spinner_item,
                new String[]{"text", "subText"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_2);
        g50_Spinner.setAdapter(adapter);
        g50_Spinner.setOnItemSelectedListener(this);
        state.setG50(0);

        ArrayAdapter<CharSequence> interruptions_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.interruptions_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        interruptions_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        first_innings_interruptions_spinner.setAdapter(interruptions_adapter);
        first_innings_interruptions_spinner.setOnItemSelectedListener(this);

        editTextWatcher();


        //for app bar
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //set title on action bar
        getActionBar().setTitle("First Innings Calculator");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.g50_spinner:
                if (position == 0) state.setG50(0);
                else if (position == 1) state.setG50(1);
                break;
            case R.id.first_innings_interruptions_spinner:
                if (position == 0) {
                    InterruptionsAmountVisibilitySetup(1);
                    state.setInterruptionsFI(1);
                } else if (position == 1) {
                    InterruptionsAmountVisibilitySetup(2);
                    state.setInterruptionsFI(2);
                } else if (position == 2) {
                    InterruptionsAmountVisibilitySetup(3);
                    state.setInterruptionsFI(3);
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //this enables disables visibility of number of interruptions available to edit
    private void InterruptionsAmountVisibilitySetup(int i) {
        if (i == 1) {
            first_innings_interruption_1_container.setVisibility(View.VISIBLE);
            first_innings_interruption_2_container.setVisibility(View.GONE);
            first_innings_interruption_3_container.setVisibility(View.GONE);
            first_innings_which_over_interruption_1_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            first_innings_total_interruption_1_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            first_innings_wickets_lost_interruption_1_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            first_innings_overs_remaining_interruption_1_et.setImeOptions(EditorInfo.IME_ACTION_DONE);

        } else if (i == 2) {
            InterruptionsAmountVisibilitySetup(1);

            first_innings_interruption_2_container.setVisibility(View.VISIBLE);
            first_innings_interruption_3_container.setVisibility(View.GONE);
            first_innings_which_over_interruption_2_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            first_innings_total_interruption_2_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            first_innings_wickets_lost_interruption_2_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            first_innings_overs_remaining_interruption_2_et.setImeOptions(EditorInfo.IME_ACTION_DONE);
            first_innings_overs_remaining_interruption_1_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        } else if (i == 3) {
            InterruptionsAmountVisibilitySetup(2);

            first_innings_interruption_3_container.setVisibility(View.VISIBLE);
            first_innings_which_over_interruption_3_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            first_innings_total_interruption_3_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            first_innings_wickets_lost_interruption_3_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            first_innings_overs_remaining_interruption_3_et.setImeOptions(EditorInfo.IME_ACTION_DONE);
            first_innings_overs_remaining_interruption_2_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        }

    }

    private void editTextWatcher() {
        first_innings_number_overs_edit_text.addTextChangedListener(new TextWatcher() {
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
                overs = Double.parseDouble(numberOfOversToS);
                if (overs > 50.0) {
                    InterruptionSetup.interruptionErrors(usrErrAlert, -10013, "Error", "");

                }
                state.setOvers(overs);
                Log.v("TotalOvers: ", numberOfOversToS);
            }
        });

        //overs at the start of interruption
        first_innings_which_over_interruption_1_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter1OversFirstInnings = 0;
                String inter1overToS = s.toString();
                if (inter1overToS.equals("")) {
                    inter1overToS = "0";
                }
                inter1OversFirstInnings = Double.parseDouble(inter1overToS);
                state.setInter1StartOverFI(inter1OversFirstInnings);
            }
        });
        first_innings_which_over_interruption_2_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter2OversFirstInnings = 0;
                String inter2overToS = s.toString();
                if (inter2overToS.equals("")) {
                    inter2overToS = "0";
                }
                inter2OversFirstInnings = Double.parseDouble(inter2overToS);
                state.setInter2StartOverFI(inter2OversFirstInnings);
            }
        });
        first_innings_which_over_interruption_3_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter3OversFirstInnings = 0;
                String inter3overToS = s.toString();
                if (inter3overToS.equals("")) {
                    inter3overToS = "0";
                }
                inter3OversFirstInnings = Double.parseDouble(inter3overToS);
                state.setInter3StartOverFI(inter3OversFirstInnings);
            }
        });
        //wickets at the start of interruption
        first_innings_wickets_lost_interruption_1_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter1WicketsFirstInnings = 0;
                String inter1WicketsToS = s.toString();
                if (inter1WicketsToS.equals("")) {
                    inter1WicketsToS = "0";
                }
                inter1WicketsFirstInnings = Integer.parseInt(inter1WicketsToS);
                if (inter1WicketsFirstInnings > totalWickets) {
                    InterruptionSetup.interruptionErrors(usrErrAlert, -10003, "Error", inter1WicketsToS);
                }
                state.setInter1WicketsFI(inter1WicketsFirstInnings);
            }
        });
        first_innings_wickets_lost_interruption_2_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter2WicketsFirstInnings = 0;
                String inter2WicketsToS = s.toString();
                if (inter2WicketsToS.equals("")) {
                    inter2WicketsToS = "0";
                }
                inter2WicketsFirstInnings = Integer.parseInt(inter2WicketsToS);
                if (inter2WicketsFirstInnings > totalWickets) {
                    InterruptionSetup.interruptionErrors(usrErrAlert, -10004, "Error", inter2WicketsToS);
                }
                state.setInter2WicketsFI(inter2WicketsFirstInnings);
            }
        });
        first_innings_wickets_lost_interruption_3_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter3WicketsFirstInnings = 0;
                String inter3WicketsToS = s.toString();
                if (inter3WicketsToS.equals("")) {
                    inter3WicketsToS = "0";
                }
                inter3WicketsFirstInnings = Integer.parseInt(inter3WicketsToS);
                if (inter3WicketsFirstInnings > totalWickets) {
                    InterruptionSetup.interruptionErrors(usrErrAlert, -10005, "Error", inter3WicketsToS);
                }
                state.setInter3WicketsFI(inter3WicketsFirstInnings);
            }
        });
        //overs remaining till end of play
        first_innings_overs_remaining_interruption_1_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter1OversAtEndFirstInnings = 0;
                String inter1OversAtEndToS = s.toString();
                if (inter1OversAtEndToS.equals("")) {
                    inter1OversAtEndToS = "0";
                }
                inter1OversAtEndFirstInnings = Double.parseDouble(inter1OversAtEndToS);
                state.setInter1EndOverFI(inter1OversAtEndFirstInnings);
            }
        });
        first_innings_overs_remaining_interruption_2_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter2OversAtEndFirstInnings = 0;
                String inter2OversAtEndToS = s.toString();
                if (inter2OversAtEndToS.equals("")) {
                    inter2OversAtEndToS = "0";
                }
                inter2OversAtEndFirstInnings = Double.parseDouble(inter2OversAtEndToS);
                state.setInter2EndOverFI(inter2OversAtEndFirstInnings);
            }
        });
        first_innings_overs_remaining_interruption_3_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter3OversAtEndFirstInnings = 0;
                String inter3OversAtEndToS = s.toString();
                if (inter3OversAtEndToS.equals("")) {
                    inter3OversAtEndToS = "0";
                }
                inter3OversAtEndFirstInnings = Double.parseDouble(inter3OversAtEndToS);
                state.setInter3EndOverFI(inter3OversAtEndFirstInnings);
            }
        });
        //total at the interruption
        first_innings_total_interruption_1_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter1totalFirstInnings = 0;
                String inter1OversAtEndToS = s.toString();
                if (inter1OversAtEndToS.equals("")) {
                    inter1OversAtEndToS = "0";
                }
                inter1totalFirstInnings = Integer.parseInt(inter1OversAtEndToS);
                state.setTotalT1int1FI(inter1totalFirstInnings);
            }
        });
        first_innings_total_interruption_2_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter2totalFirstInnings = 0;
                String inter2OversAtEndToS = s.toString();
                if (inter2OversAtEndToS.equals("")) {
                    inter2OversAtEndToS = "0";
                }
                inter2totalFirstInnings = Integer.parseInt(inter2OversAtEndToS);
                state.setTotalT1int2FI(inter2totalFirstInnings);
            }
        });
        first_innings_total_interruption_3_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter3totalFirstInnings = 0;
                String inter3OversAtEndToS = s.toString();
                if (inter3OversAtEndToS.equals("")) {
                    inter3OversAtEndToS = "0";
                }
                inter3totalFirstInnings = Integer.parseInt(inter3OversAtEndToS);
                state.setTotalT1int3FI(inter3totalFirstInnings);
            }
        });
        //overs given for team 2
        first_innings_team2_overs_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                team2OversAtStartFirstInnings = 0;
                String inter3OversAtEndToS = s.toString();
                if (inter3OversAtEndToS.equals("")) {
                    inter3OversAtEndToS = "0";
                }
                team2OversAtStartFirstInnings = Double.parseDouble(inter3OversAtEndToS);
                state.setOversT2StartFI(team2OversAtStartFirstInnings);
            }
        });
        //team1 final total before is revised
        first_innings_team1_final_total_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                team1finalTotB4rev = 0;
                String inter3OversAtEndToS = s.toString();
                if (inter3OversAtEndToS.equals("")) {
                    inter3OversAtEndToS = "0";
                }
                team1finalTotB4rev = Integer.parseInt(inter3OversAtEndToS);
                state.setTotalT1FI(team1finalTotB4rev);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_innings_calc_button:
                int interrupt = state.getInterruptionsFI();
                allFieldsFilled = whichFieldsToCheck(interrupt);

                Log.v("allFieldsFilledBtn: ", String.valueOf(allFieldsFilled));
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
                break;
        }
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
                    target = interNew.one_Interruption_FirstInnings();
                } else if (interruption == 2) {
                    target = interNew.two_Interruptions_FirstInnings();
                } else if (interruption == 3) {
                    target = interNew.three_Interruptions_FirstInnings();
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
            team1ScoreCalc(result);
            pd.dismiss();
        }
    }

    /**
     * Shows the user the final target needed to be achieved
     *
     * @param target score found by calculation
     */
    public void team1ScoreCalc(int target) {
//        int inter = stateSc2.getInterruptionsFI();
        //values less than -10000 contains error codes
        Log.v("Error Code: ", String.valueOf(target));
        if (target > -10000) {

            SharedPreferences pref = state.getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putFloat("firstInningsResource", (float) state.getEndInterResFI());
            editor.apply();

            t1WinTarget.setTitle("Target");
            t1WinTarget.setMessage("\nTeam 2 needs " + String.valueOf(target) + " runs to win.");
            t1WinTarget.setPositiveButton("OK", null);
            t1WinTarget.show();
        } else {
            String value = state.getErrorMessageValue();
            String title = state.getErrorMessageTitle();
            InterruptionSetup.interruptionErrors(usrErrAlert, target, title, value);
        }
    }

    /**
     * Checks if the edit text fields contain necessary data before calculation starts.
     *
     * @param interruptions number of interruptions user needs to check
     * @return true if the necessary fields are not empty.
     */
    public boolean whichFieldsToCheck(int interruptions) {
        boolean notEmpty = false;
        if (interruptions == 1) {
            boolean x1 = fix.editTextFieldCheck(first_innings_total_interruption_1_et);
            boolean x2 = fix.editTextFieldCheck(first_innings_which_over_interruption_1_et);
            boolean x3 = fix.editTextFieldCheck(first_innings_wickets_lost_interruption_1_et);
            boolean x4 = fix.editTextFieldCheck(first_innings_team1_final_total_et);
            boolean x5 = fix.editTextFieldCheck(first_innings_team2_overs_et);
            boolean x6 = fix.editTextFieldCheck(first_innings_number_overs_edit_text);
            boolean x7 = fix.editTextFieldCheck(first_innings_overs_remaining_interruption_1_et);

            if (x1 && x2 && x3 && x4 && x5 && x6 && x7) {
                notEmpty = true;
            }

        } else if (interruptions == 2) {
            boolean x1 = whichFieldsToCheck(1);
            boolean x2 = fix.editTextFieldCheck(first_innings_total_interruption_2_et);
            boolean x3 = fix.editTextFieldCheck(first_innings_which_over_interruption_2_et);
            boolean x4 = fix.editTextFieldCheck(first_innings_wickets_lost_interruption_2_et);
            boolean x5 = fix.editTextFieldCheck(first_innings_overs_remaining_interruption_2_et);
            if (x1 && x2 && x3 && x4 && x5) {
                notEmpty = true;
            }

        } else if (interruptions == 3) {
            boolean x1 = whichFieldsToCheck(2);
            boolean x2 = fix.editTextFieldCheck(first_innings_total_interruption_3_et);
            boolean x3 = fix.editTextFieldCheck(first_innings_which_over_interruption_3_et);
            boolean x4 = fix.editTextFieldCheck(first_innings_wickets_lost_interruption_3_et);
            boolean x5 = fix.editTextFieldCheck(first_innings_overs_remaining_interruption_3_et);
            if (x1 && x2 && x3 && x4 && x5) {
                notEmpty = true;
            }
        }
        return notEmpty;
    }
}
