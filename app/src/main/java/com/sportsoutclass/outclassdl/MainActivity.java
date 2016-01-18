package com.sportsoutclass.outclassdl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //If the variable contains Two it is regarding scenario 2
    Intent scenario1, scenario2;
    Switch team1OversSwitch, team1RevisedSwitch;
    TextView team1TotalScoreText, team1WicketsText, team1TotalScoreDL, team1WicketsDL, team1OversDL;
    EditText numberOfOversEditText, team1TotalScoreEditText, team1WicketsEditText, team1TotalScoreDLEditText, team1WicketsDLEditText, team1OversDLEditText;
    double overs, oversTwo;
    int total, totalT1AfterRevised, wickets, wicketsAfterRevised;
    StateClass state;
    Button nextBtn;
    InterruptionSetup setup;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        init();
        visibilityOnCreate();
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
        if (id == R.id.action_settings) {
            return true;
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

        if (team1OversSwitch.isChecked()) {
            if (innStartOvers != 0 && innStartTotal != 0) {

                if (innStartOvers > 0.0 && innStartOvers <= 50.0 && innStartWickets <= 10) {
                    scenario1 = new Intent(this, Scenario1.class);
                    startActivity(scenario1);
                } else {
                    Toast.makeText(getApplicationContext(), "Error: Please enter valid information",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                if (innStartOvers == 0 && innStartTotal == 0) {
                    Toast.makeText(getApplicationContext(), "Error: Overs and team 1 total must be entered",
                            Toast.LENGTH_SHORT).show();
                } else if (innStartTotal == 0) {
                    Toast.makeText(getApplicationContext(), "Error: Team 1 total must be entered",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error: Team 1 overs must be entered",
                            Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if (team1RevisedSwitch.isChecked()) {
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
                    Toast.makeText(getApplicationContext(), "Overs should be between 0 and 50",
                            Toast.LENGTH_SHORT).show();
                }
                state.setOvers(overs);

            }
        });

        overs = state.getOvers();
        team1OversDLEditText.addTextChangedListener(new TextWatcher() {
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


    //visibility of elements on create of this activity
    private void visibilityOnCreate() {
        team1OversSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    team1RevisedSwitch.setVisibility(View.INVISIBLE);
                    team1TotalScoreDL.setVisibility(View.INVISIBLE);
                    team1WicketsDL.setVisibility(View.INVISIBLE);
                    team1OversDL.setVisibility(View.INVISIBLE);
                    team1OversDLEditText.setVisibility(View.INVISIBLE);
                    team1TotalScoreDLEditText.setVisibility(View.INVISIBLE);
                    team1WicketsDLEditText.setVisibility(View.INVISIBLE);
                    team1TotalScoreText.setVisibility(View.VISIBLE);
                    team1TotalScoreEditText.setVisibility(View.VISIBLE);
                    team1WicketsText.setVisibility(View.VISIBLE);
                    team1WicketsEditText.setVisibility(View.VISIBLE);
                } else {
                    team1RevisedSwitch.setVisibility(View.VISIBLE);
                    team1RevisedSwitch.setChecked(true);
                    team1TotalScoreDL.setVisibility(View.VISIBLE);
                    team1WicketsDL.setVisibility(View.VISIBLE);
                    team1OversDL.setVisibility(View.VISIBLE);
                    team1OversDLEditText.setVisibility(View.VISIBLE);
                    team1TotalScoreDLEditText.setVisibility(View.VISIBLE);
                    team1WicketsDLEditText.setVisibility(View.VISIBLE);
                    team1TotalScoreText.setVisibility(View.INVISIBLE);
                    team1TotalScoreEditText.setVisibility(View.INVISIBLE);
                    team1WicketsText.setVisibility(View.INVISIBLE);
                    team1WicketsEditText.setVisibility(View.INVISIBLE);
                }
            }
        });

        team1RevisedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    team1TotalScoreDL.setVisibility(View.VISIBLE);
                    team1WicketsDL.setVisibility(View.VISIBLE);
                    team1TotalScoreDLEditText.setVisibility(View.VISIBLE);
                    team1WicketsDLEditText.setVisibility(View.VISIBLE);
                    team1OversDL.setVisibility(View.VISIBLE);
                    team1OversDLEditText.setVisibility(View.VISIBLE);
                } else {
                    team1TotalScoreDL.setVisibility(View.INVISIBLE);
                    team1OversDL.setVisibility(View.INVISIBLE);
                    team1OversDLEditText.setVisibility(View.INVISIBLE);
                    team1WicketsDL.setVisibility(View.INVISIBLE);
                    team1TotalScoreDLEditText.setVisibility(View.INVISIBLE);
                    team1WicketsDLEditText.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void init() {

        numberOfOversEditText = (EditText) findViewById(R.id.number_overs_edit_text);
        team1TotalScoreText = (TextView) findViewById(R.id.team_1_score);
        team1TotalScoreEditText = (EditText) findViewById(R.id.team_1_score_edit);
        team1WicketsText = (TextView) findViewById(R.id.team_1_wickets);
        team1WicketsEditText = (EditText) findViewById(R.id.team_1_wickets_edit);
        //Two means revised info for team1
        team1TotalScoreDL = (TextView) findViewById(R.id.team_1_scoreTwo);
        team1OversDL = (TextView) findViewById(R.id.team_1_oversTwo);
        team1TotalScoreDLEditText = (EditText) findViewById(R.id.team_1_score_editTwo);
        team1OversDLEditText = (EditText) findViewById(R.id.team_1_overs_editTwo);
        team1WicketsDL = (TextView) findViewById(R.id.team_1_wicketsTwo);
        team1WicketsDLEditText = (EditText) findViewById(R.id.team_1_wickets_editTwo);
        //Switch to see if the team 1 completed all the overs or not and total runs and wickets
        team1OversSwitch = (Switch) findViewById(R.id.team_1_overs_switch);
        team1RevisedSwitch = (Switch) findViewById(R.id.team_1_revised_switch);
        state = (StateClass) getApplicationContext();
        setup = new InterruptionSetup();
        nextBtn = (Button) findViewById(R.id.next_button);

        retrieveData();

    }

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
}

