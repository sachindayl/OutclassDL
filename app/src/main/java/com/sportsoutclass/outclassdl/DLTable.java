package com.sportsoutclass.outclassdl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DLTable extends AppCompatActivity {

    @BindView(R.id.app_bar)
    Toolbar toolbar;
    @BindView(R.id.dl_grid_layout)
    GridLayout dlGridLayout;
    StateClass state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dltable);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        state = (StateClass) getApplication();
        Tracking analyticsTracking = new Tracking("TableActivity", state);
        analyticsTracking.doTracking();
    }


    void generateTable(){

        int wicketsLost = 5;
        int columns;
        int rows = 5;

        if(wicketsLost < 6){
            columns = wicketsLost + 4;
        }else{
            columns = 10 - wicketsLost;
        }
        //TODO: Create Grid Layout Dynamically, use Asynctask to calculate values
        for (int i = 0; i < 5; i++) {

        }

    }

}
