package com.sportsoutclass.outclassdl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
public class FirstInnings extends BaseFragment implements AdapterView.OnItemSelectedListener {
    public FirstInnings() {
        // Required empty public constructor
    }

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
    @BindView(R.id.first_innings_team2_total_interruption_1_tv)
    TextView first_innings_team2_total_interruption_1_tv;
    @BindView(R.id.first_innings_team2_total_interruption_2_tv)
    TextView first_innings_team2_total_interruption_2_tv;
    @BindView(R.id.first_innings_team2_total_interruption_3_tv)
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
    double overs;
    boolean allFieldsFilled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first_innings, container, false);
        ButterKnife.bind(this, view);
        items = new ArrayList<>();
        state = (StateClass) getActivity().getApplication();
        usrErrAlert = new AlertDialog.Builder(getActivity());
        t1WinTarget = new AlertDialog.Builder(getActivity());
        firstInningsOverData = new DataMap();
        fix = new InterruptionSetup();
        allFieldsFilled = false;

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
                if(position == 0){
                    InterruptionsAmountVisibilitySetup(1);
                    state.setInterruptionsSc2(1);
                }
                else if(position == 1){
                    InterruptionsAmountVisibilitySetup(2);
                    state.setInterruptionsSc2(2);
                }
                else if(position == 2){
                    InterruptionsAmountVisibilitySetup(3);
                    state.setInterruptionsSc2(3);
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
            first_innings_overs_remaining_interruption_1_et.setImeOptions(EditorInfo.IME_ACTION_DONE);

        } else if (i == 2) {
            InterruptionsAmountVisibilitySetup(1);
            first_innings_overs_remaining_interruption_1_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            first_innings_interruption_2_container.setVisibility(View.VISIBLE);
            first_innings_interruption_3_container.setVisibility(View.GONE);
            first_innings_overs_remaining_interruption_2_et.setImeOptions(EditorInfo.IME_ACTION_DONE);

        } else if (i == 3) {
            InterruptionsAmountVisibilitySetup(2);
            first_innings_overs_remaining_interruption_2_et.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            first_innings_interruption_3_container.setVisibility(View.VISIBLE);
            first_innings_overs_remaining_interruption_3_et.setImeOptions(EditorInfo.IME_ACTION_DONE);

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
                    InterruptionSetup.interruptionErrors(usrErrAlert, -10012, "Error", "");

                }
                state.setOvers(overs);
                Log.v("TotalOvers: ", numberOfOversToS);
            }
        });
    }

}
