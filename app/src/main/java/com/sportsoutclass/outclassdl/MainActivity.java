package com.sportsoutclass.outclassdl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    Intent nextPage;
    ListView teamsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oversInfo();
//        spinnerInfo();
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

//    private void spinnerInfo() {
//        Spinner spinner = (Spinner) findViewById(R.id.yes_no_spinner);
//// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.yes_no, android.R.layout.simple_spinner_item);
//// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
//    }

    public void scenarioBtn(View v) {
        nextPage = new Intent(this, Scenario1.class);
        startActivity(nextPage);
    }

    private void oversInfo() {
        teamsListView = (ListView) findViewById(R.id.teams_list);
        String[] teams = {"Number of overs available at the start of the innings", "Did Team 1 bat the full amount of overs?"};
        ArrayAdapter<String> teamsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, teams);
        teamsListView.setAdapter(teamsAdapter);
    }
}

