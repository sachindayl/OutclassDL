package com.sportsoutclass.outclassdl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AboutPage extends AppCompatActivity {

    private Toolbar toolbar;
    ListView about_ListView;
    ArrayList<String> list;
    ListAdapter listViewAdapter;
    String[] values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_page, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    private void init() {
        about_ListView = (ListView) findViewById(R.id.aboutListView);
        values = new String[]{"Version", "Feedback"};
        list = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            list.add(values[i]);
        }
        listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        about_ListView.setAdapter(listViewAdapter);
    }
}
