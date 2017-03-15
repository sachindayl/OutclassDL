package com.sportsoutclass.outclassdl;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by Sachinda on 12/24/2015.
 * Contains all the states that will be saved from edit texts
 */


public class StateClass extends Application {
    private Tracker mTracker;

    public double overs, inter1StartOver, inter2StartOver, inter3StartOver,
            inter1EndOver, inter2EndOver, inter3EndOver, resAtEndInter;
    public double overs_sc2, inter1StartOver_sc2, inter2StartOver_sc2, inter3StartOver_sc2,
            inter1EndOver_sc2, inter2EndOver_sc2, inter3EndOver_sc2, resAtEndInter_sc2, team1StartResources;
    public int wickets, totalT1, totalT2int1, totalT2int2,
            totalT2int3, inter1Wickets, inter2Wickets, inter3Wickets, interruptions, parScoreTarget;
    public int totalT1_sc2, totalT1int1_sc2, totalT1int2_sc2,
            totalT1int3_sc2, inter1Wickets_sc2, inter2Wickets_sc2, inter3Wickets_sc2, interruptions_sc2, g50;
    public String errorMsgValue, errorMsgTitle;

    private static Context mContext;

    static final String admob_AppId = "ca-app-pub-7942440273858612~6758605083";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

    final String getAdmobAppId(){
        return admob_AppId;
    }

    public static Context getContext() {
        return mContext;
    }


    public String getErrorMessageValue() {
        return this.errorMsgValue;
    }

    public void setErrorMessageValue(String value) {
        this.errorMsgValue = value;
    }

    public String getErrorMessageTitle() {
        return this.errorMsgTitle;
    }

    public void setErrorMessageTitle(String value) {
        this.errorMsgTitle = value;
    }

    /*
     * First Innings
     */
    public int getG50() {
        return this.g50;
    }

    public void setG50(int value) {
        this.g50 = value;
        Log.v("G50", "G50 Set to " + value);
    }

    //overs for team 2 given when recalculated
    public void setOversT2StartFI(double value) {
        this.overs_sc2 = value;
    }

    public double getOversT2StartFI() {
        return this.overs_sc2;
    }

    public int getInterruptionsFI() {
        return this.interruptions_sc2;
    }

    public void setInterruptionsFI(int value) {
        this.interruptions_sc2 = value;
    }

    public int getTotalT1FI() {
        return this.totalT1_sc2;
    }

    public void setTotalT1FI(int value) {
        this.totalT1_sc2 = value;
    }

    public double getEndInterResFI() {
        return this.resAtEndInter_sc2;
    }

    public void setEndInterResFI(double value) {
        this.resAtEndInter_sc2 = value;
    }
    /*
        Interruption 1 First Innings
     */

    public int getTotalT1int1FI() {
        return this.totalT1int1_sc2;
    }

    public void setTotalT1int1FI(int value) {
        this.totalT1int1_sc2 = value;
    }

    public double getInter1StartOverFI() {
        return this.inter1StartOver_sc2;
    }

    public void setInter1StartOverFI(double value) {
        this.inter1StartOver_sc2 = value;
    }

    public int getInter1WicketsFI() {
        return this.inter1Wickets_sc2;
    }

    public void setInter1WicketsFI(int value) {
        this.inter1Wickets_sc2 = value;
    }

    public double getInter1EndOverFI() {
        return this.inter1EndOver_sc2;
    }

    public void setInter1EndOverFI(double value) {
        this.inter1EndOver_sc2 = value;
    }

    /*
        Interruption 2 First Innings
     */

    public int getTotalT1int2FI() {
        return this.totalT1int2_sc2;
    }

    public void setTotalT1int2FI(int value) {
        this.totalT1int2_sc2 = value;
    }

    public double getInter2StartOverFI() {
        return this.inter2StartOver_sc2;
    }

    public void setInter2StartOverFI(double value) {
        this.inter2StartOver_sc2 = value;
    }

    public int getInter2WicketsFI() {
        return this.inter2Wickets_sc2;
    }

    public void setInter2WicketsFI(int value) {
        this.inter2Wickets_sc2 = value;
    }

    public double getInter2EndOverFI() {
        return this.inter2EndOver_sc2;
    }

    public void setInter2EndOverFI(double value) {
        this.inter2EndOver_sc2 = value;
    }

    /*
        Interruption 3 First Innings
     */
    public int getTotalT1int3FI() {
        return this.totalT1int3_sc2;
    }

    public void setTotalT1int3FI(int value) {
        this.totalT1int3_sc2 = value;
    }

    public double getInter3StartOverFI() {
        double answer = this.inter3StartOver_sc2;
        Log.v("getInter3StartOverSI: ", String.valueOf(answer));
        return answer;
    }

    public void setInter3StartOverFI(double value) {
        this.inter3StartOver_sc2 = value;
        Log.v("inter3StartOverVal: ", String.valueOf(value));
    }

    public int getInter3WicketsFI() {
        return this.inter3Wickets_sc2;
    }

    public void setInter3WicketsFI(int value) {
        this.inter3Wickets_sc2 = value;
    }

    public double getInter3EndOverFI() {
        return this.inter3EndOver_sc2;
    }

    public void setInter3EndOverFI(double value) {
        this.inter3EndOver_sc2 = value;
    }

    /**
     * Second Innings
     */

    //Team 1 wickets at the end of their innings
    public int getWickets() {
        return this.wickets;
    }

    public void setWickets(int value) {
        this.wickets = value;
    }

    public int getInterruptionsSI() {
        return this.interruptions;
    }

    public void setInterruptionsSI(int value) {
        this.interruptions = value;
    }

    public double getOvers() {
        return this.overs;
    }

    public void setOvers(double value) {
        this.overs = value;
    }

    public int getTotalT1() {
        return this.totalT1;
    }

    public void setTotalT1(int value) {
        this.totalT1 = value;
    }

    public double getTeam1StartResourcesForSI() {
        return this.team1StartResources;
    }

    public void setTeam1StartResourcesForSI(double value) {
        this.team1StartResources = value;
    }

    public double getEndInterResSI() {
        return this.resAtEndInter;
    }

    public void setEndInterResSI(double value) {
        this.resAtEndInter = value;
    }

    /*
        Interruption 1 Second Innings
     */
    public int getTotalT2int1SI() {
        return this.totalT2int1;
    }

    public void setTotalT2int1SI(int value) {
        this.totalT2int1 = value;
    }

    public double getInter1StartOverSI() {
        return this.inter1StartOver;
    }

    public void setInter1StartOverSI(double value) {
        this.inter1StartOver = value;
    }

    public int getInter1WicketsSI() {
        return this.inter1Wickets;
    }

    public void setInter1WicketsSI(int value) {
        this.inter1Wickets = value;
    }

    public double getInter1EndOverSI() {
        return this.inter1EndOver;
    }

    public void setInter1EndOverSI(double value) {
        this.inter1EndOver = value;
    }

    /*
        Interruption 2 Second Innings
     */
    public int getTotalT2int2SI() {
        return this.totalT2int2;
    }

    public void setTotalT2int2SI(int value) {
        this.totalT2int2 = value;
    }

    public double getInter2StartOverSI() {
        return this.inter2StartOver;
    }

    public void setInter2StartOverSI(double value) {
        this.inter2StartOver = value;
    }

    public int getInter2WicketsSI() {
        return this.inter2Wickets;
    }

    public void setInter2WicketsSI(int value) {
        this.inter2Wickets = value;
    }

    public double getInter2EndOverSI() {
        return this.inter2EndOver;
    }

    public void setInter2EndOverSI(double value) {
        this.inter2EndOver = value;
    }

    /*
        Interruption 3 Second Innings
     */
    public int getTotalT2int3SI() {
        return this.totalT2int3;
    }

    public void setTotalT2int3SI(int value) {
        this.totalT2int3 = value;
    }

    public double getInter3StartOverSI() {
        return this.inter3StartOver;
    }

    public void setInter3StartOverSI(double value) {
        this.inter3StartOver = value;
    }

    public int getInter3WicketsSI() {
        return this.inter3Wickets;
    }

    public void setInter3WicketsSI(int value) {
        this.inter3Wickets = value;
    }

    public double getInter3EndOverSI() {
        return this.inter3EndOver;
    }

    public void setInter3EndOverSI(double value) {
        this.inter3EndOver = value;
    }

    public int getParScoreTarget() {
        return this.parScoreTarget;
    }

    public void setParScoreTarget(int parScore) {
        this.parScoreTarget = parScore;
    }

}
