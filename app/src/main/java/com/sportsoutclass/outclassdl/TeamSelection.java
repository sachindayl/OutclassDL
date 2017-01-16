package com.sportsoutclass.outclassdl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * This is the main activity which contains inning selection fragments.
 */
public final class TeamSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            InningsPick inningsPick = new InningsPick();
            inningsPick.setArguments(getIntent().getExtras());
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, inningsPick);
            fragmentTransaction.commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent aboutPg, insPg;

        //noinspection SimplifiableIfStatement.
        if (id == R.id.action_about) {
            aboutPg = new Intent(this, AboutPage.class);
            startActivity(aboutPg);
        } else if (id == R.id.action_instructions) {
            insPg = new Intent(this, HowToPage.class);
            startActivity(insPg);
        }

        return super.onOptionsItemSelected(item);
    }

}
