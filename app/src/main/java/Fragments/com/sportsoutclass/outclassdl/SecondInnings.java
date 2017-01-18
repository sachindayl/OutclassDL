package com.sportsoutclass.outclassdl;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.List;
import java.util.Map;

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
    @BindView(R.id.second_innings_calc_button)
    Button second_innings_calc_button;

    DataMap overData;
    View view;
    StateClass state;
    DataMap firstInningsOverData;
    InterruptionSetup fix;
    AlertDialog.Builder t2WinTarget;
    AlertDialog.Builder usrErrAlert;
    boolean allFieldsFilled;
    int totalWickets, inter1Wickets, inter2Wickets, inter3Wickets, inter1total, inter2total, inter3total;
    double inter1Over, inter2Over, inter3Over, inter1OversAtEnd, inter2OversAtEnd, inter3OversAtEnd;
    public SecondInnings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_innings, container, false);
        ButterKnife.bind(this, view);

        totalWickets = 10;
        overData = new DataMap();
        state = (StateClass) getActivity().getApplication();
        usrErrAlert = new AlertDialog.Builder(getActivity());
        t2WinTarget = new AlertDialog.Builder(getActivity());
        firstInningsOverData = new DataMap();
        fix = new InterruptionSetup();
        allFieldsFilled = false;
        second_innings_calc_button.setOnClickListener(this);
        Tracking analyticsTracker = new Tracking("SecondInnings", state);
        analyticsTracker.doTracking();

        ArrayAdapter<CharSequence> interruptions_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.interruptions_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        interruptions_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        second_innings_interruptions_spinner.setAdapter(interruptions_adapter);
        second_innings_interruptions_spinner.setOnItemSelectedListener(this);

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

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.second_innings_interruptions_spinner:
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
            second_innings_interruption_1_container.setVisibility(View.VISIBLE);
            second_innings_interruption_2_container.setVisibility(View.GONE);
            second_innings_interruption_3_container.setVisibility(View.GONE);
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

}
