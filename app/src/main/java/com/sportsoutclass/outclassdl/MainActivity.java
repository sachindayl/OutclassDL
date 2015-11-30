package com.sportsoutclass.outclassdl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Intent nextPage;
    Switch team1OversSwitch, team1RevisedSwitch;
    TextView team1RevisedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oversInfo();
        team1RevisedText = (TextView) findViewById(R.id.team_1_revised_text_view);
        //Switch to see if the team 1 completed all the overs or not
        team1OversSwitch = (Switch) findViewById(R.id.team_1_overs_switch);
        team1RevisedSwitch = (Switch) findViewById(R.id.team_1_revised_switch);
        team1OversSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    team1RevisedText.setVisibility(View.INVISIBLE);
                    team1RevisedSwitch.setVisibility(View.INVISIBLE);
                }else{
                    team1RevisedText.setVisibility(View.VISIBLE);
                    team1RevisedSwitch.setVisibility(View.VISIBLE);
                }
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


    public void scenarioBtn(View v) {
        nextPage = new Intent(this, Scenario1.class);
        startActivity(nextPage);
    }

    private void oversInfo() {

    }


}



