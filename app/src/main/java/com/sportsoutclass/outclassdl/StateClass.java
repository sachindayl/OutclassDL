package com.sportsoutclass.outclassdl;

import android.app.Application;

/**
 * Created by Sachinda on 12/24/2015.
 */


public class StateClass extends Application {
    public double overs, inter1StartOver, inter2StartOver, inter3StartOver, inter1EndOver, inter2EndOver, inter3EndOver;
    public int wickets, totalT1, totalT2int1, totalT2int2, totalT2int3, inter1Wickets, inter2Wickets, inter3Wickets, interruptions;


    /**
     * Getters
     */
    public int getInterruptions() {
        return this.interruptions;
    }


    public double getOvers() {
        return this.overs;
    }

    public int getTotalT1() {
        return this.totalT1;
    }

    public int getTotalT2int1() {
        return this.totalT2int1;
    }

    public int getTotalT2int2() {
        return this.totalT2int2;
    }

    public int getTotalT2int3() {
        return this.totalT2int3;
    }

    public int getWickets() {
        return this.wickets;
    }

    public double getInter1StartOver() {
        return this.inter1StartOver;
    }

    public double getInter2StartOver() {
        return this.inter2StartOver;
    }

    public double getInter3StartOver() {
        return this.inter3StartOver;
    }

    public int getInter1Wickets() {
        return this.inter1Wickets;
    }

    public int getInter2Wickets() {
        return this.inter2Wickets;
    }

    public int getInter3Wickets() {
        return this.inter3Wickets;
    }

    public double getInter1EndOver() {
        return this.inter1EndOver;
    }

    public double getInter2EndOver() {
        return this.inter2EndOver;
    }

    public double getInter3EndOver() {
        return this.inter3EndOver;
    }

    /**
     * setters
     */
    public void setInterruptions(int value) {
        this.interruptions = value;
    }

    public void setOvers(double value) {
        this.overs = value;
    }

    public void setWickets(int value) {
        this.wickets = value;
    }

    public void setTotalT1(int value) {
        this.totalT1 = value;
    }

    public void setTotalT2int1(int value) {
        this.totalT2int1 = value;
    }

    public void setTotalT2int2(int value) {
        this.totalT2int2 = value;
    }

    public void setTotalT2int3(int value) {
        this.totalT2int3 = value;
    }

    public void setInter1StartOver(double value) {
        this.inter1StartOver = value;
    }

    public void setInter2StartOver(double value) {
        this.inter2StartOver = value;
    }

    public void setInter3StartOver(double value) {
        this.inter3StartOver = value;
    }

    public void setInter1Wickets(int value) {
        this.inter1Wickets = value;
    }

    public void setInter2Wickets(int value) {
        this.inter2Wickets = value;
    }

    public void setInter3Wickets(int value) {
        this.inter3Wickets = value;
    }

    public void setInter1EndOver(double value) {
        this.inter1EndOver = value;
    }

    public void setInter2EndOver(double value) {
        this.inter2EndOver = value;
    }

    public void setInter3EndOver(double value) {
        this.inter3EndOver = value;
    }
}
