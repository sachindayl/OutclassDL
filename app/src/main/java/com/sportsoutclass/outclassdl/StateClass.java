package com.sportsoutclass.outclassdl;

import android.app.Application;

/**
 * Created by Sachinda on 12/24/2015.
 */


public class StateClass extends Application {
    public double overs, inter1StartOver, inter1EndOver;
    public int wickets, total, inter1Wickets;


    /**
     * Getters
     */
    public double getOvers() {
        return this.overs;
    }

    public int getTotal() {
        return this.total;
    }

    public int getWickets() {
        return this.wickets;
    }

    public double getInter1StartOver() {
        return this.inter1StartOver;
    }

    public int getInter1Wickets() {
        return this.inter1Wickets;
    }

    public double getInter1EndOver() {
        return this.inter1EndOver;
    }

    /**
     * setters
     */
    public void setOvers(double value) {
        this.overs = value;
    }

    public void setWickets(int value) {
        this.wickets = value;
    }

    public void setTotal(int value) {
        this.total = value;
    }

    public void setInter1StartOver(double value) {
        this.inter1StartOver = value;
    }

    public void setInter1Wickets(int value) {
        this.inter1Wickets = value;
    }

    public void setInter1EndOver(double value) {
        this.inter1EndOver = value;
    }
}
