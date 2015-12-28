package com.sportsoutclass.outclassdl;

import android.util.Log;

/**
 * Created by Sachinda on 12/28/2015.
 */
public class InterruptionSetup {

    StateClass state;
    DataMap overData;
    int target, t1TotalScore, startOfInnsOversWickets;
    double startOfInnsOvers, resAtStartOfMatch;

    public int one_interruption() {
        init();
        Log.v("ResourcesStartofMatch: ", String.valueOf(resAtStartOfMatch));
        //Team 1 total score at the end of their innings
        int t1TotalScore = state.getTotalT1();
        Log.v("Team1 Total: ", String.valueOf(t1TotalScore));
        //Team 1 wickets at the end of their innings
        int t1Wickets = state.getWickets();
        Log.v("Team1 Wickets: ", String.valueOf(t1Wickets));
        //Team 2 Overs at the start of the interruption 1
        double oversAtInter1Start = state.getInter1StartOver();
        Log.v("oversAtInter1Start: ", String.valueOf(oversAtInter1Start));
        //Team 2 Wickets at the start of the interruption 1
        int wicketsAtInter1Start = state.getInter1Wickets();
        //overs remaining at the start of the interruption
        double remainingOversAtInterStart = overCalculations(startOfInnsOvers, oversAtInter1Start, "minus");
        //Team 2 Overs and wickets together when the interruption happened to set up key to find resource percentage
        int oversAndWicketsStartTogether = (int) ((remainingOversAtInterStart * 100) + wicketsAtInter1Start);
        Log.v("WktsAndOvrs2gthrStart: ", String.valueOf(oversAndWicketsStartTogether));
        //resource percentage at the start of the interruption
        double percentageResourcesAtInterStart = overData.DataSet(oversAndWicketsStartTogether);
        Log.v("resources@interStart: ", String.valueOf(percentageResourcesAtInterStart));

        //overs remaining at the end of the interruption
        double oversRemainingInterEnd = state.getInter1EndOver();
        Log.v("netOvers: ", String.valueOf(remainingOversAtInterStart));
        //overs remaining *10 to get rid of decimal
        int totalOversRemainingPlusWickets = (int) ((oversRemainingInterEnd * 100) + wicketsAtInter1Start);
        Log.v("TotalOversAndWkts: ", String.valueOf(totalOversRemainingPlusWickets));
        //resources left gathered from dataSet at the end of the interruption
        double resourceCheckForRemainderOvers = overData.DataSet(totalOversRemainingPlusWickets);
        Log.v("resForRemainder: ", String.valueOf(resourceCheckForRemainderOvers));
        //resources lost at the end of the interruption
        double resourcesLost = percentageResourcesAtInterStart - resourceCheckForRemainderOvers;
        Log.v("resourcesLost: ", String.valueOf(resourcesLost));
        //resources left at end of interruption
        double resourcesLeftAtEndInter = resAtStartOfMatch - resourcesLost;
        state.setEndInterRes(resourcesLeftAtEndInter);
        Log.v("resourcesAvailable: ", String.valueOf(resourcesLeftAtEndInter));
        //setting up target
        target = (int) ((t1TotalScore * (resourcesLeftAtEndInter / resAtStartOfMatch)) + 1.5);
        Log.v("Target@calculations: ", String.valueOf(target));

        return target;
    }

    public int two_interruptions() {
        init();
        one_interruption();
        double oversAtInter1Start = state.getInter1StartOver();
        double oversRemainingInterEnd = state.getInter1EndOver();
        double oversAtInter2Start = state.getInter2StartOver();
        //Team 2 Wickets at the start of the interruption 1
        int wicketsAtInter2Start = state.getInter2Wickets();
        double oversFinalizedAtInter1End = overCalculations(oversAtInter1Start, oversRemainingInterEnd, "plus");
        Log.v("oversFinalizedAtInter1End: ", String.valueOf(oversFinalizedAtInter1End));
        double oversRemainingAtInter2End = state.getInter2EndOver();
        /**
         * Make a check at button press for this.
         */
        double oversFinalizedAtInter2End = overCalculations(oversAtInter2Start, oversRemainingAtInter2End, "plus");
        Log.v("oversFinalizedAtInter2End: ", String.valueOf(oversFinalizedAtInter2End));
        double oversLeftAtInter2Start = overCalculations(oversFinalizedAtInter1End, oversAtInter2Start, "minus");
        Log.v("oversLeftAtInter2Start: ", String.valueOf(oversLeftAtInter2Start));
        int oversWktsLeftAtInter2Start = (int) ((oversLeftAtInter2Start * 100) + wicketsAtInter2Start);
        Log.v("resLeftAtInter2Start: ", String.valueOf(oversWktsLeftAtInter2Start));
        double resLeftAtInter2Start = overData.DataSet(oversWktsLeftAtInter2Start);
        double oversLeftAtInter2End = overCalculations(oversFinalizedAtInter2End, oversAtInter2Start, "minus");
        Log.v("oversLeftAtInter2End: ", String.valueOf(oversLeftAtInter2End));
        int oversWktsLeftAtInter2End = (int) ((oversLeftAtInter2End * 100) + wicketsAtInter2Start);
        Log.v("resLeftAtInter2End: ", String.valueOf(oversWktsLeftAtInter2End));
        double resLeftAtInter2End = overData.DataSet(oversWktsLeftAtInter2End);
        double resLostAtInt2 = resLeftAtInter2Start - resLeftAtInter2End;
        double resourcesLeftAtEndInter = state.getEndInterRes();
        double resLeftAtInt2 = resourcesLeftAtEndInter - resLostAtInt2;
        state.setEndInterRes(resLeftAtInt2);
        Log.v("resLeftAtInt2: ", String.valueOf(resLeftAtInt2));
        target = (int) ((t1TotalScore * (resLeftAtInt2 / resAtStartOfMatch)) + 1.5);
        Log.v("targetInt2: ", String.valueOf(target));
        return target;
    }

    public int three_interruptions() {
        init();
        one_interruption();
        two_interruptions();
        double oversAtInter3Start = state.getInter3StartOver();
        Log.v("oversAtInter3Start: ", String.valueOf(oversAtInter3Start));
        int wicketsAtInter3Start = state.getInter3Wickets();
        Log.v("wicketsAtInter3Start: ", String.valueOf(wicketsAtInter3Start));
        double oversFinalizedAtInter2End = overCalculations(state.getInter2StartOver(), state.getInter2EndOver(), "plus");
        Log.v("oversFinalizedAtInter2End: ", String.valueOf(oversFinalizedAtInter2End));
        double oversLeftAtInter3Start = overCalculations(oversFinalizedAtInter2End, oversAtInter3Start, "minus");
        Log.v("oversLeftAtInter3Start: ", String.valueOf(oversLeftAtInter3Start));
        int oversWktsLeftAtInter3Start = (int) ((oversLeftAtInter3Start * 100) + wicketsAtInter3Start);
        Log.v("oversWktsLeftAtInter3Start: ", String.valueOf(oversWktsLeftAtInter3Start));

        double oversFinalizedAtInter3End = overCalculations(oversAtInter3Start, state.getInter3EndOver(), "plus");
        double resLeftAtInter3Start = overData.DataSet(oversWktsLeftAtInter3Start);
        double oversLeftAtInter3End = overCalculations(oversFinalizedAtInter3End, oversAtInter3Start, "minus");
        Log.v("oversLeftAtInter3End: ", String.valueOf(oversLeftAtInter3End));
        int oversWktsLeftAtInter3End = (int) ((oversLeftAtInter3End * 100) + wicketsAtInter3Start);
        Log.v("oversWktsLeftAtInter3End: ", String.valueOf(oversWktsLeftAtInter3End));
        double resLeftAtInter3End = overData.DataSet(oversWktsLeftAtInter3End);
        Log.v("resLeftAtInter3End: ", String.valueOf(resLeftAtInter3End));
        double resLostAtInt3 = resLeftAtInter3Start - resLeftAtInter3End;
        Log.v("resLostAtInt3: ", String.valueOf(resLostAtInt3));
        double resLeftAtInt2 = state.getEndInterRes();
        Log.v("resLeftAtInt2: ", String.valueOf(resLeftAtInt2));
        double resLeftAtInt3 = resLeftAtInt2 - resLostAtInt3;
        Log.v("resourcesAvailable: ", String.valueOf(resLeftAtInt3));
        target = (int) ((t1TotalScore * (resLeftAtInt3 / resAtStartOfMatch)) + 1.5);
        Log.v("target: ", String.valueOf(target));
        return target;
    }

    //changing over decimal to base 6
    public double overCalculations(double x, double y, String function) {
        double z = 0.0;
        if (function.equals("plus")) {
            z = x + y;
            String doubleToS = String.valueOf(z);
            int digitCheck = (int) (z + 0.5);
            digitCheck = digitCheck / 10;
            char decimal;
            if (digitCheck == 0) {
                decimal = doubleToS.charAt(2);
            } else {
                decimal = doubleToS.charAt(3);
            }
            int decimalToInt = Integer.parseInt(String.valueOf(decimal));
            if (decimalToInt > 5) {
                z = z + 0.4;
            }
        } else if (function.equals("minus")) {
            z = x - y;
            String doubleToS = String.valueOf(z);
            int digitCheck = (int) (z + 0.5);
            digitCheck = digitCheck / 10;
            char decimal;
            if (digitCheck == 0) {
                decimal = doubleToS.charAt(2);
            } else {
                decimal = doubleToS.charAt(3);
            }
            int decimalToInt = Integer.parseInt(String.valueOf(decimal));
            if (decimalToInt > 4) {
                z = z - 0.4;
            }
        }
        return z;

    }

    private void init() {
        state = (StateClass) state.getContext();
        overData = new DataMap();
        target = 0;
        t1TotalScore = state.getTotalT1();
        Log.v("t1TotalScoreInISInit: ", String.valueOf(t1TotalScore));
        //Overs both teams has at the start of the match
        startOfInnsOvers = state.getOvers();
        //Setting up the key to grab the resource percentage at the start of the match
        startOfInnsOversWickets = (int) (startOfInnsOvers * 100);
        //resources percentage at the start of the match
        resAtStartOfMatch = overData.DataSet(startOfInnsOversWickets);
    }


}
