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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

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

    @BindView(R.id.number_overs_edit_text)
    EditText totalOvers;
    @BindView(R.id.g50_spinner)
    Spinner g50_Spinner;
    View view;
    StateClass state;
    List<Map<String, String>> items;
    AlertDialog.Builder usrErrAlert;
    Double overs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first_innings, container, false);
        ButterKnife.bind(this, view);
        items = new ArrayList<>();
        state = (StateClass) getActivity().getApplication();
        usrErrAlert = new AlertDialog.Builder(getActivity());
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

        editTextWatcher();
//        TextView t1 = (TextView)view.findViewById(R.id.text_v2);
//        Button b1 = (Button) view.findViewById(R.id.frag_btn2);
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment fragment = new InningsPick();
//                replaceFragment(fragment, R.id.fragment_container);
//            }
//        });


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
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void editTextWatcher(){
        totalOvers.addTextChangedListener(new TextWatcher() {
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
