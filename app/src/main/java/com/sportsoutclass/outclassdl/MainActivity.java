package com.sportsoutclass.outclassdl;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    //If the variable contains Two it is regarding scenario 2
    Intent scenario1, scenario2, aboutPg, insPg;
    //Using butter knife to bind views
    @BindView(R.id.team_1_score) TextView team1TotalScoreText;
    @BindView(R.id.team_1_wickets) TextView team1WicketsText;
    @BindView(R.id.team_1_scoreTwo) TextView team1TotalScoreDL;
    @BindView(R.id.team_1_wicketsTwo) TextView team1WicketsDL;
    @BindView(R.id.team_1_oversTwo) TextView team1OversDL;
    @BindView(R.id.number_overs_edit_text) EditText numberOfOversEditText;
    @BindView(R.id.team_1_score_edit) EditText team1TotalScoreEditText;
    @BindView(R.id.team_1_wickets_edit) EditText team1WicketsEditText;
    @BindView(R.id.team_1_score_editTwo) EditText team1TotalScoreDLEditText;
    @BindView(R.id.team_1_wickets_editTwo) EditText team1WicketsDLEditText;
    @BindView(R.id.team_1_overs_editTwo) EditText team2OversDLEditText;

    double overs, oversTwo;
    int total, totalT1AfterRevised, wickets, wicketsAfterRevised;
    StateClass state;
    Button nextBtn;
    InterruptionSetup setup;
    AlertDialog.Builder usrErrAlert;
    Toolbar toolbar;
    Spinner g50_Spinner, did_team_1_bat_spinner,team_1_revised_total_spinner;
    List<Map<String, String>> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher); // Initialize this to whatever you want
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = getTheme();
            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
            int color = typedValue.data;
            ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(null, bm, color);
            setTaskDescription(description);
            bm.recycle();
        }
        init();

        SimpleAdapter adapter = new SimpleAdapter(this, items,
                android.R.layout.simple_spinner_item,
                new String[]{"text", "subText"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_2);

        g50_Spinner.setAdapter(adapter);
        g50_Spinner.setOnItemSelectedListener(this);
        state.setG50(0);

        did_team_1_bat_spinner = (Spinner) findViewById(R.id.did_team_1_bat_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> did_team_1_bat_adapter = ArrayAdapter.createFromResource(this,
                R.array.did_team_1_bat_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        did_team_1_bat_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        did_team_1_bat_spinner.setAdapter(did_team_1_bat_adapter);
        did_team_1_bat_spinner.setOnItemSelectedListener(this);

        team_1_revised_total_spinner = (Spinner) findViewById(R.id.team_1_revised_total_spinner);
        ArrayAdapter<CharSequence> team_1_revised_total_adapter = ArrayAdapter.createFromResource(this,
                R.array.team_1_revised_total_array, android.R.layout.simple_spinner_item);
        did_team_1_bat_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        team_1_revised_total_spinner.setAdapter(team_1_revised_total_adapter);
        team_1_revised_total_spinner.setOnItemSelectedListener(this);

        //At start team 1 bat the whole amount of overs spinner is set to yes and team revised total is disabled
        did_team_1_bat_spinner.setSelection(0);
        team_1_revised_total_spinner.setSelection(1);
        team_1_revised_total_spinner.setEnabled(false);

        OversInfo();
        TotalAndWicketsInfo();

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
        if (id == R.id.action_about) {
            aboutPg = new Intent(this, AboutPage.class);
            startActivity(aboutPg);
        } else if (id == R.id.action_instructions) {
            insPg = new Intent(this, HowToPage.class);
            startActivity(insPg);
        }

        return super.onOptionsItemSelected(item);
    }

    public void scenarioBtn(View v) {
        double innStartOvers = state.getOvers();
        int innStartWickets = state.getWickets();
        int innStartTotal = state.getTotalT1();
        int revisedT1Total = state.getTotalT1();
        int revisedT1Wickets = state.getWickets();
        double revisedT1Overs = state.getOvers();
            if (g50_Spinner.getSelectedItemPosition() == 0) {
                if (innStartOvers != 0 && innStartTotal != 0) {

                    if (innStartOvers > 0.0 && innStartOvers <= 50.0 && innStartWickets <= 10) {
                        scenario1 = new Intent(this, Scenario1.class);
                        startActivity(scenario1);
                    } else {
                        InterruptionSetup.interruptionErrors(usrErrAlert, -10008, "Error", "");
                    }
                } else {
                    if (innStartOvers == 0 && innStartTotal == 0) {
                        InterruptionSetup.interruptionErrors(usrErrAlert, -10009, "Error", "");
                    } else if (innStartTotal == 0) {
                        InterruptionSetup.interruptionErrors(usrErrAlert, -10010, "Error", "");
                    } else {
                        InterruptionSetup.interruptionErrors(usrErrAlert, -10011, "Error", "");
                    }
                }
            } else {
                if (team_1_revised_total_spinner.getSelectedItemPosition() == 0) {
                    if (innStartOvers > 0.0 && innStartOvers <= 50.0 && revisedT1Total > 0 && revisedT1Overs > 0.0 && revisedT1Wickets <= 10) {
                        scenario1 = new Intent(this, Scenario1.class);
                        startActivity(scenario1);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error: Please enter valid information",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (innStartOvers > 0.0 && innStartOvers <= 50.0) {
                        scenario2 = new Intent(this, Scenario2.class);
                        startActivity(scenario2);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error: Please enter valid information",
                                Toast.LENGTH_SHORT).show();
                    }

                }

            }



    }

    private void OversInfo() {
        numberOfOversEditText.addTextChangedListener(new TextWatcher() {
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

            }
        });

        overs = state.getOvers();
        team2OversDLEditText.addTextChangedListener(new TextWatcher() {
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
                oversTwo = Double.parseDouble(numberOfOversToS);
                if (oversTwo > overs) {
                    Toast.makeText(getApplicationContext(), "Overs should be between 0 and " + overs,
                            Toast.LENGTH_SHORT).show();
                }
                state.setOvers(oversTwo);

            }
        });

    }

    //captures team 1 total and wickets
    private void TotalAndWicketsInfo() {
        team1TotalScoreEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                total = 0;
                String totalToS = s.toString();
                if (totalToS.equals("")) {
                    totalToS = "0";
                }
                total = Integer.parseInt(totalToS);
                state.setTotalT1(total);
            }
        });
        team1TotalScoreDLEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                totalT1AfterRevised = 0;
                String totalToS = s.toString();
                if (totalToS.equals("")) {
                    totalToS = "0";
                }
                totalT1AfterRevised = Integer.parseInt(totalToS);
                state.setTotalT1(totalT1AfterRevised);
            }
        });

        team1WicketsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                wickets = 0;
                String wicketsToS = s.toString();
                Log.v("wicketsToS value", wicketsToS);
                if (wicketsToS.equals("")) {
                    wicketsToS = "0";
                }
                wickets = Integer.parseInt(wicketsToS);
                if (wickets > 10) {
                    Toast.makeText(getApplicationContext(), "Wickets should be between 0 and 10",
                            Toast.LENGTH_SHORT).show();
                }
                state.setWickets(wickets);
            }
        });
        team1WicketsDLEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                wicketsAfterRevised = 0;
                String wicketsToS = s.toString();
                Log.v("wicketsToS value", wicketsToS);
                if (wicketsToS.equals("")) {
                    wicketsToS = "0";
                }
                wicketsAfterRevised = Integer.parseInt(wicketsToS);
                if (wicketsAfterRevised > 10) {
                    Toast.makeText(getApplicationContext(), "Wickets should be between 0 and 10",
                            Toast.LENGTH_SHORT).show();
                }
                state.setWickets(wicketsAfterRevised);
            }
        });
    }

    private void init() {

        g50_Spinner = (Spinner) findViewById(R.id.g50_spinner);
        items = new ArrayList<>();

        Map<String, String> item0 = new HashMap<>(2);
        item0.put("text", "200");
        item0.put("subText", "U-19, U-15, Associate Nations");
        items.add(item0);

        Map<String, String> item1 = new HashMap<>(2);
        item1.put("text", "245");
        item1.put("subText", "First Class Cricket and Above");
        items.add(item1);

        //Two means revised info for team1
        //Switch to see if the team 1 completed all the overs or not and total runs and wickets
        state = (StateClass) getApplicationContext();
        setup = new InterruptionSetup();
        usrErrAlert = new AlertDialog.Builder(MainActivity.this);
        nextBtn = (Button) findViewById(R.id.next_button);
        nextBtn.setBackgroundResource(R.color.primaryColor);
        nextBtn.setTextColor(Color.WHITE);

        retrieveData();

    }

    /**
     * Saving state to use on back press
     */
    private void retrieveData() {
        double retrieveOvers = state.getOvers();
        if (retrieveOvers == 0.0) {
            numberOfOversEditText.setHint("Overs");
        } else {
            numberOfOversEditText.setText(String.valueOf(retrieveOvers));
        }
        int retrieveT1Total = state.getTotalT1();
        if (retrieveT1Total == 0) {
            team1TotalScoreEditText.setHint("Total");
        } else {
            team1TotalScoreEditText.setText(String.valueOf(retrieveT1Total));
        }
        int retrieveT1Wickets = state.getWickets();
        if (retrieveT1Wickets == 0) {
            team1WicketsEditText.setHint("");
        } else {
            team1WicketsEditText.setText(String.valueOf(retrieveT1Wickets));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        switch (adapterView.getId()){
            case R.id.g50_spinner:
                if (pos == 0) state.setG50(0);
                else if (pos == 1) state.setG50(1);
                break;
            case R.id.did_team_1_bat_spinner:
                if(pos == 0){
                    team_1_revised_total_spinner.setSelection(1);
                    team_1_revised_total_spinner.setEnabled(false);

                    team1TotalScoreDL.setVisibility(View.INVISIBLE);
                    team1WicketsDL.setVisibility(View.INVISIBLE);
                    team1OversDL.setVisibility(View.INVISIBLE);
                    team2OversDLEditText.setVisibility(View.INVISIBLE);
                    team1TotalScoreDLEditText.setVisibility(View.INVISIBLE);
                    team1WicketsDLEditText.setVisibility(View.INVISIBLE);
                    team1TotalScoreText.setVisibility(View.VISIBLE);
                    team1TotalScoreEditText.setVisibility(View.VISIBLE);
                    team1WicketsText.setVisibility(View.VISIBLE);
                    team1WicketsEditText.setVisibility(View.VISIBLE);
                }else if(pos == 1){
                    team_1_revised_total_spinner.setSelection(0);
                    team_1_revised_total_spinner.setEnabled(true);

                    team1TotalScoreDL.setVisibility(View.VISIBLE);
                    team1WicketsDL.setVisibility(View.VISIBLE);
                    team1OversDL.setVisibility(View.VISIBLE);
                    team2OversDLEditText.setVisibility(View.VISIBLE);
                    team1TotalScoreDLEditText.setVisibility(View.VISIBLE);
                    team1WicketsDLEditText.setVisibility(View.VISIBLE);
                    team1TotalScoreText.setVisibility(View.INVISIBLE);
                    team1TotalScoreEditText.setVisibility(View.INVISIBLE);
                    team1WicketsText.setVisibility(View.INVISIBLE);
                    team1WicketsEditText.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.team_1_revised_total_spinner:
                if(pos == 0){
                    team1TotalScoreDL.setVisibility(View.VISIBLE);
                    team1WicketsDL.setVisibility(View.VISIBLE);
                    team1TotalScoreDLEditText.setVisibility(View.VISIBLE);
                    team1WicketsDLEditText.setVisibility(View.VISIBLE);
                    team1OversDL.setVisibility(View.VISIBLE);
                    team2OversDLEditText.setVisibility(View.VISIBLE);
                }else if(pos == 1){
                    team1TotalScoreDL.setVisibility(View.INVISIBLE);
                    team1OversDL.setVisibility(View.INVISIBLE);
                    team2OversDLEditText.setVisibility(View.INVISIBLE);
                    team1WicketsDL.setVisibility(View.INVISIBLE);
                    team1TotalScoreDLEditText.setVisibility(View.INVISIBLE);
                    team1WicketsDLEditText.setVisibility(View.INVISIBLE);
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

