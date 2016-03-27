package com.sportsoutclass.outclassdl;

import android.app.Application;
import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;

/**
 * Created by Sachinda on 12/24/2015.
 * Contains all the states that will be saved from edit texts
 */


public class StateClass extends Application {
    public double overs, inter1StartOver, inter2StartOver, inter3StartOver,
            inter1EndOver, inter2EndOver, inter3EndOver, resAtEndInter;
    public double overs_sc2, inter1StartOver_sc2, inter2StartOver_sc2, inter3StartOver_sc2,
            inter1EndOver_sc2, inter2EndOver_sc2, inter3EndOver_sc2, resAtEndInter_sc2;
    public int wickets, wicketsTwo, totalT1, totalT1Two, totalT2int1, totalT2int2,
            totalT2int3, inter1Wickets, inter2Wickets, inter3Wickets, interruptions;
    public int totalT1_sc2, totalT1int1_sc2, totalT1int2_sc2,
            totalT1int3_sc2, inter1Wickets_sc2, inter2Wickets_sc2, inter3Wickets_sc2, interruptions_sc2;
    public String errorMsgValue, errorMsgTitle;
    public FragmentManager fm;

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
    /**
     * Getters Scenario 1
     */
    public FragmentManager getFragManager() {
        return this.fm;
    }

    public String getErrorMessageValue() {
        return this.errorMsgValue;
    }

    public String getErrorMessageTitle() {
        return this.errorMsgTitle;
    }

    public int getInterruptions() {
        return this.interruptions;
    }

    public double getOvers() {
        return this.overs;
    }

    public int getTotalT1() {
        return this.totalT1;
    }

    public int getTotalT1Two() {
        return this.totalT1Two;
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

    public int getWicketsTwo() {
        return this.wicketsTwo;
    }

    public double getInter1StartOver() {
        return this.inter1StartOver;
    }

    public double getInter2StartOver() {
        return this.inter2StartOver;
    }

    public double getInter3StartOver() {
        double answer = this.inter3StartOver;
        Log.v("getInter3StartOver: ", String.valueOf(answer));
        return answer;
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

    public double getEndInterRes() {
        return this.resAtEndInter;
    }

    /**
     * Getters for Scenario 2
     */
    public int getInterruptionsSc2() {
        return this.interruptions_sc2;
    }

    public double getOversT2startSc2() {
        return this.overs_sc2;
    }

    public int getTotalT1Sc2() {
        return this.totalT1_sc2;
    }

    public int getTotalT1int1Sc2() {
        return this.totalT1int1_sc2;
    }

    public int getTotalT1int2Sc2() {
        return this.totalT1int1_sc2;
    }

    public int getTotalT1int3Sc2() {
        return this.totalT1int1_sc2;
    }

    public double getInter1StartOverSc2() {
        return this.inter1StartOver_sc2;
    }

    public double getInter2StartOverSc2() {
        return this.inter2StartOver_sc2;
    }

    public double getInter3StartOverSc2() {
        double answer = this.inter3StartOver_sc2;
        Log.v("getInter3StartOver: ", String.valueOf(answer));
        return answer;
    }

    public int getInter1WicketsSc2() {
        return this.inter1Wickets_sc2;
    }

    public int getInter2WicketsSc2() {
        return this.inter2Wickets_sc2;
    }

    public int getInter3WicketsSc2() {
        return this.inter3Wickets_sc2;
    }

    public double getInter1EndOverSc2() {
        return this.inter1EndOver_sc2;
    }

    public double getInter2EndOverSc2() {
        return this.inter2EndOver_sc2;
    }

    public double getInter3EndOverSc2() {
        return this.inter3EndOver_sc2;
    }

    public double getEndInterResSc2() {
        return this.resAtEndInter_sc2;
    }

    /**
     * Setters for scenario 1
     */
    public void setFragManager(FragmentManager value){
        this.fm = value;
    }
    public void setErrorMessageValue(String value) {
        this.errorMsgValue = value;
    }

    public void setErrorMessageTitle(String value) {
        this.errorMsgTitle = value;
    }
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
        Log.v("inter3StartOverVal: ", String.valueOf(value));
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

    public void setEndInterRes(double value) {
        this.resAtEndInter = value;
    }

    /**
     * Setters for scenario 2
     */
    public void setInterruptionsSc2(int value) {
        this.interruptions_sc2 = value;
    }

    public void setOversT2StartSc2(double value) {
        this.overs_sc2 = value;
    }

    public void setTotalT1Sc2(int value) {
        this.totalT1_sc2 = value;
    }

    public void setTotalT1int1Sc2(int value) {
        this.totalT1int1_sc2 = value;
    }

    public void setTotalT1int2Sc2(int value) {
        this.totalT1int2_sc2 = value;
    }

    public void setTotalT1int3Sc2(int value) {
        this.totalT1int3_sc2 = value;
    }

    public void setInter1StartOverSc2(double value) {
        this.inter1StartOver_sc2 = value;
    }

    public void setInter2StartOverSc2(double value) {
        this.inter2StartOver_sc2 = value;
    }

    public void setInter3StartOverSc2(double value) {
        this.inter3StartOver_sc2 = value;
        Log.v("inter3StartOverVal: ", String.valueOf(value));
    }

    public void setInter1WicketsSc2(int value) {
        this.inter1Wickets_sc2 = value;
    }

    public void setInter2WicketsSc2(int value) {
        this.inter2Wickets_sc2 = value;
    }

    public void setInter3WicketsSc2(int value) {
        this.inter3Wickets_sc2 = value;
    }

    public void setInter1EndOverSc2(double value) {
        this.inter1EndOver_sc2 = value;
    }

    public void setInter2EndOverSc2(double value) {
        this.inter2EndOver_sc2 = value;
    }

    public void setInter3EndOverSc2(double value) {
        this.inter3EndOver_sc2 = value;
    }

    public void setEndInterResSc2(double value) {
        this.resAtEndInter_sc2 = value;
    }
}
