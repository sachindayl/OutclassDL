package com.sportsoutclass.outclassdl;

import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * This page contains the process that works for Scenario 1 which is the DL Method for 2nd team.
 * This page also converts decimal values to over values.
 */
public class InterruptionSetup {

    StateClass state;
    DataMap overData;
    int target, t1TotalScore, startOfInnsOversWickets;
    double startOfInnsOvers, resAtStartOfMatch;

    public int one_interruption() {
        init();
        double t1Overs = state.getOvers();
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
        if (oversAtInter1Start > t1Overs) {
            target = -10001;
            return target;
        }
        //Team 2 Wickets at the start of the interruption 1
        int wicketsAtInter1Start = state.getInter1Wickets();
        if (wicketsAtInter1Start > 10) {
            target = -10002;
            return target;
        }
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
        target = -1000;
        double oversAtInter1Start = state.getInter1StartOver();
        double oversRemainingInterEnd = state.getInter1EndOver();
        double oversAtInter2Start = state.getInter2StartOver();
        //Team 2 Wickets at the start of the interruption 1
        int wicketsAtInter2Start = state.getInter2Wickets();
        if (wicketsAtInter2Start > 10) {
            target = -10003;
            return target;
        }
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
        target = -1000;
        double oversAtInter3Start = state.getInter3StartOver();
        Log.v("oversAtInter3Start: ", String.valueOf(oversAtInter3Start));
        int wicketsAtInter3Start = state.getInter3Wickets();
        Log.v("wicketsAtInter3Start: ", String.valueOf(wicketsAtInter3Start));
        if (wicketsAtInter3Start > 10) {
            target = -10004;
            return target;
        }
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

    /**
     * This method contains code when there is only 1 interruption for Scenario 2
     */
    public int one_Interruption_Sc2() {
        init();
        double t1Overs = state.getOvers();
        Log.v("ResourcesStartofMatch: ", String.valueOf(resAtStartOfMatch));
        int team1FinalTotalb4Rev = state.getTotalT1Sc2();
        int team1TotalatInt1 = state.getTotalT1int1Sc2();
        //Team 1 Overs at the start of the interruption 1
        double oversAtInter1StartSc2 = state.getInter1StartOverSc2();
        Log.v("oversAtInter1Start: ", String.valueOf(oversAtInter1StartSc2));
        //Error message if overs at the interruption is greater than the overall overs
        if (oversAtInter1StartSc2 > t1Overs) {
            target = -10001;
            return target;
        }
        //Team 2 Wickets at the start of the interruption 1
        int wicketsAtInter1StartSc2 = state.getInter1WicketsSc2();
        //Error message if wickets are greater than 10
        if (wicketsAtInter1StartSc2 > 10) {
            target = -10002;
            return target;
//            sc2.team1ScoreCalc(target);
        }
        //overs remaining at the start of the interruption
        double remainingOversAtInterStartSc2 = overCalculations(startOfInnsOvers, oversAtInter1StartSc2, "minus");
        //Team 2 Overs and wickets together when the interruption happened to set up key to find resource percentage
        int oversAndWicketsInt1StartTogetherSc2 = (int) ((remainingOversAtInterStartSc2 * 100) + wicketsAtInter1StartSc2);
        Log.v("WktsAndOvrs2gthrStart: ", String.valueOf(oversAndWicketsInt1StartTogetherSc2));
        //resource percentage at the start of the interruption
        double percentageResourcesAtInterStartSc2 = overData.DataSet(oversAndWicketsInt1StartTogetherSc2);
        Log.v("resources@interStart: ", String.valueOf(percentageResourcesAtInterStartSc2));

        //overs remaining at the end of the interruption
        double oversRemainingInterEndSc2 = state.getInter1EndOverSc2();
        Log.v("netOvers: ", String.valueOf(remainingOversAtInterStartSc2));
        //overs remaining plus wickets to get rid of decimal
        int totalOversRemainingPlusWicketsSc2 = (int) ((oversRemainingInterEndSc2 * 100) + wicketsAtInter1StartSc2);
        Log.v("TotalOversAndWkts: ", String.valueOf(totalOversRemainingPlusWicketsSc2));
        //resources left gathered from dataSet at the end of the interruption
        double resourceCheckForRemainderOversSc2 = overData.DataSet(totalOversRemainingPlusWicketsSc2);
        Log.v("resForRemainder: ", String.valueOf(resourceCheckForRemainderOversSc2));
        //resources lost at the end of the interruption
        double resourcesLost = percentageResourcesAtInterStartSc2 - resourceCheckForRemainderOversSc2;
        Log.v("resourcesLost: ", String.valueOf(resourcesLost));
        //resources left at end of interruption
        double resourcesLeftAtEndInterSc2 = resAtStartOfMatch - resourcesLost;
        state.setEndInterResSc2(resourcesLeftAtEndInterSc2);
        Log.v("resourcesAvailable: ", String.valueOf(resourcesLeftAtEndInterSc2));

        double oversForT2AtStartSc2 = state.getOversT2startSc2();
        double resForT2AtStartSc2 = overData.DataSet((int) oversForT2AtStartSc2 * 100);

        if (resForT2AtStartSc2 > resourcesLeftAtEndInterSc2) {
            target = (int) (team1FinalTotalb4Rev + 200 * (resForT2AtStartSc2 - resourcesLeftAtEndInterSc2) / 100 + 1.5);
        } else {
            target = (int) (team1FinalTotalb4Rev * resForT2AtStartSc2 / resourcesLeftAtEndInterSc2 + 1.5);
        }
        return target;
    }

    public int two_Interruptions_Sc2() {
        init();
        one_Interruption_Sc2();
        target = -1000;
        int team1FinalTotalb4Rev = state.getTotalT1Sc2();
        double oversAtInter1StartSc2 = state.getInter1StartOverSc2();
        double oversRemainingInterEndSc2 = state.getInter1EndOverSc2();
        double oversAtInter2StartSc2 = state.getInter2StartOverSc2();
        //Team 2 Wickets at the start of the interruption 1
        int wicketsAtInter2StartSc2 = state.getInter2WicketsSc2();
        if (wicketsAtInter2StartSc2 > 10) {
            target = -10003;
            return target;
        }
        double oversFinalizedAtInter1EndSc2 = overCalculations(oversAtInter1StartSc2, oversRemainingInterEndSc2, "plus");
        Log.v("oversFinalizedAtInter1End: ", String.valueOf(oversFinalizedAtInter1EndSc2));
        double oversRemainingAtInter2EndSc2 = state.getInter2EndOverSc2();
        /**
         * Make a check at button press for this.
         */
        double oversFinalizedAtInter2EndSc2 = overCalculations(oversAtInter2StartSc2, oversRemainingAtInter2EndSc2, "plus");
        Log.v("oversFinalizedAtInter2End: ", String.valueOf(oversFinalizedAtInter2EndSc2));
        double oversLeftAtInter2StartSc2 = overCalculations(oversFinalizedAtInter1EndSc2, oversAtInter2StartSc2, "minus");
        Log.v("oversLeftAtInter2Start: ", String.valueOf(oversLeftAtInter2StartSc2));
        int oversWktsLeftAtInter2StartSc2 = (int) ((oversLeftAtInter2StartSc2 * 100) + wicketsAtInter2StartSc2);
        Log.v("resLeftAtInter2Start: ", String.valueOf(oversWktsLeftAtInter2StartSc2));
        double resLeftAtInter2StartSc2 = overData.DataSet(oversWktsLeftAtInter2StartSc2);
        double oversLeftAtInter2EndSc2 = overCalculations(oversFinalizedAtInter2EndSc2, oversAtInter2StartSc2, "minus");
        Log.v("oversLeftAtInter2End: ", String.valueOf(oversLeftAtInter2EndSc2));
        int oversWktsLeftAtInter2EndSc2 = (int) ((oversLeftAtInter2EndSc2 * 100) + wicketsAtInter2StartSc2);
        Log.v("resLeftAtInter2End: ", String.valueOf(oversWktsLeftAtInter2EndSc2));
        double resLeftAtInter2EndSc2 = overData.DataSet(oversWktsLeftAtInter2EndSc2);
        double resLostAtInt2Sc2 = resLeftAtInter2StartSc2 - resLeftAtInter2EndSc2;
        double resourcesLeftAtEndInterSc2 = state.getEndInterResSc2();
        double resLeftAtInt2Sc2 = resourcesLeftAtEndInterSc2 - resLostAtInt2Sc2;
        state.setEndInterResSc2(resLeftAtInt2Sc2);

        double oversForT2AtStartSc2 = state.getOversT2startSc2();
        double resForT2AtStartSc2 = overData.DataSet((int) oversForT2AtStartSc2 * 100);


        if (resForT2AtStartSc2 > resourcesLeftAtEndInterSc2) {
            target = (int) (team1FinalTotalb4Rev + 200 * (resForT2AtStartSc2 - resourcesLeftAtEndInterSc2) / 100 + 1.5);
        } else {
            target = (int) (team1FinalTotalb4Rev * resForT2AtStartSc2 / resourcesLeftAtEndInterSc2 + 1.5);
        }


        return target;
    }

    public int three_Interruptions_Sc2() {
        init();
        one_Interruption_Sc2();
        two_Interruptions_Sc2();
        target = -1000;
        double oversAtInter3StartSc2 = state.getInter3StartOverSc2();
        Log.v("oversAtInter3Start: ", String.valueOf(oversAtInter3StartSc2));
        int wicketsAtInter3StartSc2 = state.getInter3WicketsSc2();
        int team1FinalTotalb4Rev = state.getTotalT1Sc2();
        Log.v("wicketsAtInter3Start: ", String.valueOf(wicketsAtInter3StartSc2));
        if (wicketsAtInter3StartSc2 > 10) {
            target = -10004;
            return target;
        }
        double oversFinalizedAtInter2EndSc2 = overCalculations(state.getInter2StartOverSc2(), state.getInter2EndOverSc2(), "plus");
        Log.v("oversFinalizedAtInter2End: ", String.valueOf(oversFinalizedAtInter2EndSc2));
        double oversLeftAtInter3StartSc2 = overCalculations(oversFinalizedAtInter2EndSc2, oversAtInter3StartSc2, "minus");
        Log.v("oversLeftAtInter3Start: ", String.valueOf(oversLeftAtInter3StartSc2));
        int oversWktsLeftAtInter3StartSc2 = (int) ((oversLeftAtInter3StartSc2 * 100) + wicketsAtInter3StartSc2);
        Log.v("oversWktsLeftAtInter3Start: ", String.valueOf(oversWktsLeftAtInter3StartSc2));

        double oversFinalizedAtInter3EndSc2 = overCalculations(oversAtInter3StartSc2, state.getInter3EndOverSc2(), "plus");
        double resLeftAtInter3StartSc2 = overData.DataSet(oversWktsLeftAtInter3StartSc2);
        double oversLeftAtInter3EndSc2 = overCalculations(oversFinalizedAtInter3EndSc2, oversAtInter3StartSc2, "minus");
        Log.v("oversLeftAtInter3End: ", String.valueOf(oversLeftAtInter3EndSc2));
        int oversWktsLeftAtInter3EndSc2 = (int) ((oversLeftAtInter3EndSc2 * 100) + wicketsAtInter3StartSc2);
        Log.v("oversWktsLeftAtInter3End: ", String.valueOf(oversWktsLeftAtInter3EndSc2));
        double resLeftAtInter3EndSc2 = overData.DataSet(oversWktsLeftAtInter3EndSc2);
        Log.v("resLeftAtInter3End: ", String.valueOf(resLeftAtInter3EndSc2));
        double resLostAtInt3Sc2 = resLeftAtInter3StartSc2 - resLeftAtInter3EndSc2;
        Log.v("resLostAtInt3: ", String.valueOf(resLostAtInt3Sc2));
        double resLeftAtInt2Sc2 = state.getEndInterResSc2();
        Log.v("resLeftAtInt2: ", String.valueOf(resLeftAtInt2Sc2));

        double resLeftAtInt3Sc2 = resLeftAtInt2Sc2 - resLostAtInt3Sc2;
        Log.v("resourcesAvailable: ", String.valueOf(resLeftAtInt3Sc2));
        state.setEndInterResSc2(resLeftAtInt3Sc2);

        double oversForT2AtStartSc2 = state.getOversT2startSc2();
        double resForT2AtStartSc2 = overData.DataSet((int) oversForT2AtStartSc2 * 100);

        if (resForT2AtStartSc2 > resLeftAtInt2Sc2) {
            target = (int) (team1FinalTotalb4Rev + 200 * (resForT2AtStartSc2 - resLeftAtInt2Sc2) / 100 + 1.5);
        } else {
            target = (int) (team1FinalTotalb4Rev * resForT2AtStartSc2 / resLeftAtInt2Sc2 + 1.5);
        }

        Log.v("target: ", String.valueOf(target));
        return target;
    }

    /**
     * This method contains all the error that Alert builder will show the user.
     *
     * @param usrErr    alertBuilder for different scenarios
     * @param errorCode error code
     */
    public static void interruptionErrors(AlertDialog.Builder usrErr, int errorCode) {

        usrErr.setTitle("Invalid Information");
        if (errorCode == -10001) {
            usrErr.setMessage("Interruption 1: Overs should be between 0 and 50");
        } else if (errorCode == -10002) {
            usrErr.setMessage("Interruption 1: Wickets should be between 0 and 10");
        } else if (errorCode == -10003) {
            usrErr.setMessage("Interruption 2: Wickets should be between 0 and 10");
        } else if (errorCode == -10004) {
            usrErr.setMessage("Interruption 3: Wickets should be between 0 and 10");
        }
        usrErr.setPositiveButton("OK", null);
        usrErr.show();
    }


    private void init() {
        state = (StateClass) StateClass.getContext();
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
