package com.sportsoutclass.outclassdl;

import android.app.Application;

/**
 * Created by Sachinda on 12/24/2015.
 */


public class StateClass extends Application {
    public double overs, inter1StartOver, inter1EndOver;
    public int wickets, totalT1, totalT2, inter1Wickets;


    /**
     * Getters
     */
    public double getOvers() {
        return this.overs;
    }

    public int getTotalT1() {
        return this.totalT1;
    }

    public int getTotalT2() {
        return this.totalT2;
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

    public void setTotalT1(int value) {
        this.totalT1 = value;
    }

    public void setTotalT2(int value) {
        this.totalT2 = value;
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
