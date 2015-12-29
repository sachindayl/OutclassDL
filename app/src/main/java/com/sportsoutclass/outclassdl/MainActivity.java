package com.sportsoutclass.outclassdl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    Intent nextPage;
    Switch team1OversSwitch, team1RevisedSwitch;
    TextView team1RevisedText, team1TotalScoreText, team1WicketsText;
    EditText numberOfOversEditText, team1TotalScoreEditText, team1WicketsEditText;
    double overs;
    int total, wickets;
    StateClass state;
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        double correctOvers = state.getOvers();
        int correctWickets = state.getWickets();
        int correctTotal = state.getTotalT1();
        if (correctOvers != 0 && correctTotal != 0) {

            if (correctOvers > 0.0 && correctOvers <= 50.0 && correctWickets <= 10) {
                nextPage = new Intent(this, Scenario1.class);
                startActivity(nextPage);
            } else {
                Toast.makeText(getApplicationContext(), "Error: Please enter valid information",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            if (correctOvers == 0 && correctTotal == 0) {
                Toast.makeText(getApplicationContext(), "Error: Overs and team 1 total must be entered",
                        Toast.LENGTH_SHORT).show();
            } else if (correctTotal == 0) {
                Toast.makeText(getApplicationContext(), "Error: Team 1 total must be entered",
                        Toast.LENGTH_SHORT).show();
            } else if (correctOvers == 0) {
                Toast.makeText(getApplicationContext(), "Error: Team 1 overs must be entered",
                        Toast.LENGTH_SHORT).show();
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
    }


    //visibility of elements on create of this activity
    private void visibilityOnCreate() {
        team1OversSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    team1RevisedText.setVisibility(View.INVISIBLE);
                    team1RevisedSwitch.setVisibility(View.INVISIBLE);
                    team1TotalScoreText.setVisibility(View.VISIBLE);
                    team1TotalScoreEditText.setVisibility(View.VISIBLE);
                    team1WicketsText.setVisibility(View.VISIBLE);
                    team1WicketsEditText.setVisibility(View.VISIBLE);
                } else {
                    team1RevisedText.setVisibility(View.VISIBLE);
                    team1RevisedSwitch.setVisibility(View.VISIBLE);
                    team1TotalScoreText.setVisibility(View.INVISIBLE);
                    team1TotalScoreEditText.setVisibility(View.INVISIBLE);
                    team1WicketsText.setVisibility(View.INVISIBLE);
                    team1WicketsEditText.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void init() {
        team1RevisedText = (TextView) findViewById(R.id.team_1_revised_text_view);
        numberOfOversEditText = (EditText) findViewById(R.id.number_overs_edit_text);
        team1TotalScoreText = (TextView) findViewById(R.id.team_1_score);
        team1TotalScoreEditText = (EditText) findViewById(R.id.team_1_score_edit);
        team1WicketsText = (TextView) findViewById(R.id.team_1_wickets);
        team1WicketsEditText = (EditText) findViewById(R.id.team_1_wickets_edit);
        //Switch to see if the team 1 completed all the overs or not and total runs and wickets
        team1OversSwitch = (Switch) findViewById(R.id.team_1_overs_switch);
        team1RevisedSwitch = (Switch) findViewById(R.id.team_1_revised_switch);
        state = (StateClass) getApplicationContext();
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
            team1WicketsEditText.setHint("Wickets");
        } else {
            team1WicketsEditText.setText(String.valueOf(retrieveT1Wickets));
        }
    }
}



