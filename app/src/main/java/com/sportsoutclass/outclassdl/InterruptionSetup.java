package com.sportsoutclass.outclassdl;

import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * This page contains the process that works for Scenario 1 which is the DL Method for 2nd team.
 * This page also converts decimal values to over values.
 */
class InterruptionSetup {

    private StateClass state;
    private DataMap overData;
    private int target;
    private int t1TotalScore;
    private int g50_value;
    private double startOfInnsOvers, resAtStartOfMatch;

    //G50 Matters only when getting team 1 total after interruption
    private void g50_setup(){
        int g50_index = state.getG50();
        Log.d(" G50 Value" , String.valueOf(g50_index));
        if(g50_index == 0){
            g50_value = 200;
        }else if(g50_index == 1){
            g50_value = 245;
        }
    }

    int one_interruption() {
        init();
        //Overs T2 will be playing at start of their inning
        double oversForT2 = state.getOvers();
        Log.v("ResourcesStartofMatch: ", String.valueOf(resAtStartOfMatch));
        //Team 1 total score at the end of their innings
        int t1TotalScore = state.getTotalT1();
        Log.v("Team1 Total: ", String.valueOf(t1TotalScore));
        //Team 1 wickets at the end of their innings
        int t1Wickets = state.getWickets();
        Log.v("Team1 Wickets: ", String.valueOf(t1Wickets));
        //Team 2 Overs at the start of the interruption 1
        double oversAtInter1Start = state.getInter1StartOver();
        double oversCanPut = overCalculations(oversForT2, oversAtInter1Start, "minus");
        //overs remaining at the end of the interruption
        double oversRemainingInterEnd = state.getInter1EndOver();
        if (oversCanPut <= 0) {
            String wholeOversToS = String.valueOf(oversForT2);
            state.setErrorMessageValue(wholeOversToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10006;
        }
        if (oversCanPut <= oversRemainingInterEnd) {
            String oversCanPutToS = String.valueOf(oversCanPut);
            state.setErrorMessageValue(oversCanPutToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10007;
        }

        Log.v("oversAtInter1Start: ", String.valueOf(oversAtInter1Start));
        if (oversAtInter1Start > oversForT2) {
            return -10001;
        }

        //Team 2 Wickets at the start of the interruption 1
        int wicketsAtInter1Start = state.getInter1Wickets();
        if (wicketsAtInter1Start > 10) {
            return -10002;
        }
        //overs remaining at the start of the interruption
        double remainingOversAtInterStart = overCalculations(startOfInnsOvers, oversAtInter1Start, "minus");
        //Team 2 Overs and wickets together when the interruption happened to set up key to find resource percentage
        int oversAndWicketsStartTogether = (int) ((remainingOversAtInterStart * 100) + wicketsAtInter1Start);
        Log.v("WktsAndOvrs2gthrStart: ", String.valueOf(oversAndWicketsStartTogether));
        //resource percentage at the start of the interruption
        double percentageResourcesAtInterStart = overData.DataSet(oversAndWicketsStartTogether);
        Log.v("resources@interStart: ", String.valueOf(percentageResourcesAtInterStart));
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

    int two_interruptions() {
        init();
        one_interruption();
        target = -1000;
        double oversAtInter1Start = state.getInter1StartOver();
        double oversRemainingInter1End = state.getInter1EndOver();
        double oversAtInter2Start = state.getInter2StartOver();
        //Team 2 Wickets at the start of the interruption 1
        int wicketsAtInter1Start = state.getInter1Wickets();
        int wicketsAtInter2Start = state.getInter2Wickets();
        double oversFinalizedAtInter1End = overCalculations(oversAtInter1Start, oversRemainingInter1End, "plus");
        double oversCanPut = overCalculations(oversFinalizedAtInter1End, oversAtInter2Start, "minus");
        double oversRemainingAtInter2End = state.getInter2EndOver();
        if (oversCanPut <= 0) {
            String wholeOversToS = String.valueOf(oversFinalizedAtInter1End);
            state.setErrorMessageValue(wholeOversToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10016;
        }
        if (oversCanPut <= oversRemainingAtInter2End) {
            String oversCanPutToS = String.valueOf(oversCanPut);
            state.setErrorMessageValue(oversCanPutToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10018;
        }
        if (wicketsAtInter2Start > 10) {
            return -10003;
        }
        if (oversAtInter2Start < oversAtInter1Start) {
            String intOversStartToS = String.valueOf(oversAtInter1Start);
            state.setErrorMessageValue(intOversStartToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10005;
        }
        if (wicketsAtInter2Start < wicketsAtInter1Start) {
            String int1WicketsToS = String.valueOf(wicketsAtInter1Start);
            state.setErrorMessageValue(int1WicketsToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10014;
        }
        double oversFinalizedAtInter2End = overCalculations(oversAtInter2Start, oversRemainingAtInter2End, "plus");
        double oversLeftAtInter2Start = overCalculations(oversFinalizedAtInter1End, oversAtInter2Start, "minus");

        if (oversFinalizedAtInter2End >= oversFinalizedAtInter1End) {
            String oversLeftAtInter2StartToS = String.valueOf(oversLeftAtInter2Start);
            state.setErrorMessageValue(oversLeftAtInter2StartToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10018;
        }
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

    int three_interruptions() {
        init();
        one_interruption();
        two_interruptions();
        target = -1000;
        double oversAtInter2Start = state.getInter2StartOver();
        double oversAtInter3Start = state.getInter3StartOver();
        Log.v("oversAtInter3Start: ", String.valueOf(oversAtInter3Start));
        int wicketsAtInter2Start = state.getInter2Wickets();
        int wicketsAtInter3Start = state.getInter3Wickets();
        Log.v("wicketsAtInter3Start: ", String.valueOf(wicketsAtInter3Start));
        double oversFinalizedAtInter2End = overCalculations(state.getInter2StartOver(), state.getInter2EndOver(), "plus");
        double oversRemainingAtInter3End = state.getInter3EndOver();
        double oversCanPut = overCalculations(oversFinalizedAtInter2End, state.getInter3StartOver(), "minus");
        if (oversCanPut <= 0) {
            String wholeOversToS = String.valueOf(oversFinalizedAtInter2End);
            state.setErrorMessageValue(wholeOversToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10017;
        }
        if (oversAtInter3Start < oversAtInter2Start) {
            String int2OversStartToS = String.valueOf(oversAtInter2Start);
            state.setErrorMessageTitle("Invalid Information");
            state.setErrorMessageValue(int2OversStartToS);
            return -10013;
        }
        if (oversCanPut <= oversRemainingAtInter3End) {
            String oversCanPutToS = String.valueOf(oversCanPut);
            state.setErrorMessageValue(oversCanPutToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10019;
        }
        if (wicketsAtInter3Start > 10) {
            return -10004;
        }
        if (wicketsAtInter3Start < wicketsAtInter2Start) {
            String int2WicketsToS = String.valueOf(wicketsAtInter2Start);
            state.setErrorMessageTitle("Invalid Information");
            state.setErrorMessageValue(int2WicketsToS);
            return -10015;
        }
        double oversFinalizedAtInter3End = overCalculations(oversAtInter3Start, state.getInter3EndOver(), "plus");
        double oversLeftAtInter3Start = overCalculations(oversFinalizedAtInter2End, oversAtInter3Start, "minus");

        if (oversFinalizedAtInter3End >= oversFinalizedAtInter2End) {
            String oversLeftAtInter3StartTos = String.valueOf(oversLeftAtInter3Start);
            state.setErrorMessageValue(oversLeftAtInter3StartTos);
            state.setErrorMessageTitle("Invalid Information");
            return -10019;
        }
        int oversWktsLeftAtInter3Start = (int) ((oversLeftAtInter3Start * 100) + wicketsAtInter3Start);
        double resLeftAtInter3Start = overData.DataSet(oversWktsLeftAtInter3Start);
        double oversLeftAtInter3End = overCalculations(oversFinalizedAtInter3End, oversAtInter3Start, "minus");
        Log.v("oversLeftAtInter3End: ", String.valueOf(oversLeftAtInter3End));
        int oversWktsLeftAtInter3End = (int) ((oversLeftAtInter3End * 100) + wicketsAtInter3Start);
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
    private double overCalculations(double x, double y, String function) {
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
            if (z < 0) {
                z = -1;
            } else {
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

        }
        return z;
    }

    /**
     * This method contains code when there is only 1 interruption for Scenario 2
     */
    int one_Interruption_Sc2() {
        init();
        g50_setup();
        double oversForT2Sc2 = state.getOvers();
        Log.v("oversForT2Sc2: ", String.valueOf(oversForT2Sc2));
        int team1FinalTotalb4Rev = state.getTotalT1Sc2();
        //Team 1 Overs at the start of the interruption 1
        double oversAtInter1StartSc2 = state.getInter1StartOverSc2();
        Log.v("oversAtInter1Start: ", String.valueOf(oversAtInter1StartSc2));
        //Team 2 Wickets at the start of the interruption 1
        int wicketsAtInter1StartSc2 = state.getInter1WicketsSc2();
        //Error message if wickets are greater than 10
        //overs remaining at the start of the interruption
        double remainingOversAtInterStartSc2 = overCalculations(startOfInnsOvers, oversAtInter1StartSc2, "minus");
        //overs remaining at the end of the interruption
        double oversRemainingInterEndSc2 = state.getInter1EndOverSc2();
        double oversCanPut = overCalculations(oversForT2Sc2, oversAtInter1StartSc2, "minus");

        if (oversCanPut <= 0) {
            String wholeOversToS = String.valueOf(oversForT2Sc2);
            state.setErrorMessageValue(wholeOversToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10006;
        }
        if (oversCanPut <= oversRemainingInterEndSc2) {
            String oversCanPutToS = String.valueOf(oversCanPut);
            state.setErrorMessageValue(oversCanPutToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10007;
        }
        int TotalT1int1Sc2 = state.getTotalT1int1Sc2();
        if (oversRemainingInterEndSc2 == 0 && TotalT1int1Sc2 != team1FinalTotalb4Rev) {
            String TotalT1int1Sc2ToS = String.valueOf(TotalT1int1Sc2);
            state.setErrorMessageValue(TotalT1int1Sc2ToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10020;
        }


        if (oversAtInter1StartSc2 > oversForT2Sc2) {
            return -10001;
        }
        //Team 2 Wickets at the start of the interruption 1
        int wicketsAtInter1Start = state.getInter1Wickets();
        if (wicketsAtInter1Start > 10) {
            return -10002;
        }
        //Team 2 Overs and wickets together when the interruption happened to set up key to find resource percentage
        int oversAndWicketsInt1StartTogetherSc2 = (int) ((remainingOversAtInterStartSc2 * 100) + wicketsAtInter1StartSc2);
        Log.v("WktsAndOvrs2gthrStart: ", String.valueOf(oversAndWicketsInt1StartTogetherSc2));
        //resource percentage at the start of the interruption
        double percentageResourcesAtInterStartSc2 = overData.DataSet(oversAndWicketsInt1StartTogetherSc2);
        Log.v("resources@interStart: ", String.valueOf(percentageResourcesAtInterStartSc2));
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
            target = (int) (team1FinalTotalb4Rev + g50_value * (resForT2AtStartSc2 - resourcesLeftAtEndInterSc2) / 100 + 1.5);
        } else {
            target = (int) (team1FinalTotalb4Rev * resForT2AtStartSc2 / resourcesLeftAtEndInterSc2 + 1.5);
        }
        return target;
    }

    int two_Interruptions_Sc2() {
        init();
        g50_setup();
        one_Interruption_Sc2();
        target = -1000;
        int team1FinalTotalb4Rev = state.getTotalT1Sc2();
        double oversAtInter1StartSc2 = state.getInter1StartOverSc2();
        double oversRemainingInterEndSc2 = state.getInter1EndOverSc2();
        double oversAtInter2StartSc2 = state.getInter2StartOverSc2();
        //Team 2 Wickets at the start of the interruption 1
        int wicketsAtInter1StartSc2 = state.getInter1WicketsSc2();
        int wicketsAtInter2StartSc2 = state.getInter2WicketsSc2();
        if (wicketsAtInter2StartSc2 > 10) {
            target = -10003;
            return target;
        }
        double oversFinalizedAtInter1EndSc2 = overCalculations(oversAtInter1StartSc2, oversRemainingInterEndSc2, "plus");
        double oversRemainingAtInter2EndSc2 = state.getInter2EndOverSc2();
        double oversCanPut = overCalculations(oversFinalizedAtInter1EndSc2, oversAtInter2StartSc2, "minus");


        int TotalT1int2Sc2 = state.getTotalT1int2Sc2();
        if (oversRemainingAtInter2EndSc2 == 0 && TotalT1int2Sc2 != team1FinalTotalb4Rev) {
            String TotalT1int2Sc2ToS = String.valueOf(TotalT1int2Sc2);
            state.setErrorMessageValue(TotalT1int2Sc2ToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10020;
        }
        if (oversCanPut <= 0) {
            String wholeOversToS = String.valueOf(oversFinalizedAtInter1EndSc2);
            state.setErrorMessageValue(wholeOversToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10016;
        }
        if (oversCanPut <= oversRemainingAtInter2EndSc2) {
            String oversCanPutToS = String.valueOf(oversCanPut);
            state.setErrorMessageValue(oversCanPutToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10018;
        }
        if (wicketsAtInter2StartSc2 > 10) {
            return -10003;
        }
        if (oversAtInter2StartSc2 < oversAtInter1StartSc2) {
            String intOversStartToS = String.valueOf(oversAtInter1StartSc2);
            state.setErrorMessageValue(intOversStartToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10005;
        }
        if (wicketsAtInter2StartSc2 < wicketsAtInter1StartSc2) {
            String int1WicketsToS = String.valueOf(wicketsAtInter1StartSc2);
            state.setErrorMessageValue(int1WicketsToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10014;
        }
        /**
         * Make a check at button press for this.
         */
        double oversFinalizedAtInter2EndSc2 = overCalculations(oversAtInter2StartSc2, oversRemainingAtInter2EndSc2, "plus");
        double oversLeftAtInter2StartSc2 = overCalculations(oversFinalizedAtInter1EndSc2, oversAtInter2StartSc2, "minus");

        if (oversFinalizedAtInter2EndSc2 >= oversFinalizedAtInter1EndSc2) {
            String oversLeftAtInter2StartToS = String.valueOf(oversLeftAtInter2StartSc2);
            state.setErrorMessageValue(oversLeftAtInter2StartToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10018;
        }


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
            target = (int) (team1FinalTotalb4Rev + g50_value * (resForT2AtStartSc2 - resourcesLeftAtEndInterSc2) / 100 + 1.5);
        } else {
            target = (int) (team1FinalTotalb4Rev * resForT2AtStartSc2 / resourcesLeftAtEndInterSc2 + 1.5);
        }
        return target;
    }

    int three_Interruptions_Sc2() {
        init();
        g50_setup();
        one_Interruption_Sc2();
        two_Interruptions_Sc2();
        target = -1000;
        double oversAtInter2StartSc2 = state.getInter2StartOverSc2();
        double oversAtInter3StartSc2 = state.getInter3StartOverSc2();
        double oversRemainingAtInter3EndSc2 = state.getInter3EndOverSc2();
        Log.v("oversAtInter3Start: ", String.valueOf(oversAtInter3StartSc2));
        int wicketsAtInter2StartSc2 = state.getInter2WicketsSc2();
        int wicketsAtInter3StartSc2 = state.getInter3WicketsSc2();
        int team1FinalTotalb4Rev = state.getTotalT1Sc2();
        Log.v("wicketsAtInter3Start: ", String.valueOf(wicketsAtInter3StartSc2));
        if (wicketsAtInter3StartSc2 > 10) {
            target = -10004;
            return target;
        }
        double oversFinalizedAtInter2EndSc2 = overCalculations(state.getInter2StartOverSc2(), state.getInter2EndOverSc2(), "plus");
        double oversLeftAtInter3StartSc2 = overCalculations(oversFinalizedAtInter2EndSc2, oversAtInter3StartSc2, "minus");
        int oversWktsLeftAtInter3StartSc2 = (int) ((oversLeftAtInter3StartSc2 * 100) + wicketsAtInter3StartSc2);
        double oversFinalizedAtInter3EndSc2 = overCalculations(oversAtInter3StartSc2, state.getInter3EndOverSc2(), "plus");

        double oversCanPut = overCalculations(oversFinalizedAtInter2EndSc2, state.getInter3StartOver(), "minus");
        if (oversCanPut <= 0) {
            String wholeOversToS = String.valueOf(oversFinalizedAtInter2EndSc2);
            state.setErrorMessageValue(wholeOversToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10017;
        }
        if (oversAtInter3StartSc2 < oversAtInter2StartSc2) {
            String int2OversStartToS = String.valueOf(oversAtInter2StartSc2);
            state.setErrorMessageTitle("Invalid Information");
            state.setErrorMessageValue(int2OversStartToS);
            return -10013;
        }
        if (oversCanPut <= oversRemainingAtInter3EndSc2) {
            String oversCanPutToS = String.valueOf(oversCanPut);
            state.setErrorMessageValue(oversCanPutToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10019;
        }

        int TotalT1int3Sc2 = state.getTotalT1int3Sc2();
        if (oversRemainingAtInter3EndSc2 == 0 && TotalT1int3Sc2 != team1FinalTotalb4Rev) {
            String TotalT1int3Sc2ToS = String.valueOf(TotalT1int3Sc2);
            state.setErrorMessageValue(TotalT1int3Sc2ToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10020;
        }

        if (wicketsAtInter3StartSc2 > 10) {
            return -10004;
        }
        if (wicketsAtInter3StartSc2 < wicketsAtInter2StartSc2) {
            String int2WicketsToS = String.valueOf(wicketsAtInter2StartSc2);
            state.setErrorMessageTitle("Invalid Information");
            state.setErrorMessageValue(int2WicketsToS);
            return -10015;
        }

        if (oversFinalizedAtInter3EndSc2 >= oversFinalizedAtInter2EndSc2) {
            String oversLeftAtInter3StartTos = String.valueOf(oversLeftAtInter3StartSc2);
            state.setErrorMessageValue(oversLeftAtInter3StartTos);
            state.setErrorMessageTitle("Invalid Information");
            return -10019;
        }

        double resLeftAtInter3StartSc2 = overData.DataSet(oversWktsLeftAtInter3StartSc2);
        double oversLeftAtInter3EndSc2 = overCalculations(oversFinalizedAtInter3EndSc2, oversAtInter3StartSc2, "minus");
        Log.v("oversLeftAtInter3End: ", String.valueOf(oversLeftAtInter3EndSc2));
        int oversWktsLeftAtInter3EndSc2 = (int) ((oversLeftAtInter3EndSc2 * 100) + wicketsAtInter3StartSc2);
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
            target = (int) (team1FinalTotalb4Rev + g50_value * (resForT2AtStartSc2 - resLeftAtInt2Sc2) / 100 + 1.5);
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
    static void interruptionErrors(AlertDialog.Builder usrErr, int errorCode, String title, String value) {

        usrErr.setTitle(title);
        if (errorCode == -10001) {
            usrErr.setMessage("Interruption 1: Overs should be between 0 and 50");
        } else if (errorCode == -10002) {
            usrErr.setMessage("Interruption 1: Wickets should be between 0 and 10");
        } else if (errorCode == -10003) {
            usrErr.setMessage("Interruption 2: Wickets should be between 0 and 10");
        } else if (errorCode == -10004) {
            usrErr.setMessage("Interruption 3: Wickets should be between 0 and 10");
        } else if (errorCode == -10005) {
            usrErr.setMessage("Over that the interruption 2 occurred must be greater than " + value + " overs");
        } else if (errorCode == -10006) {
            usrErr.setMessage("Over that the interruption 1 occurred must be less than " + value + " overs");
        } else if (errorCode == -10007) {
            usrErr.setMessage("Interruption 1: Overs remaining must be less than " + value + " overs");
        } else if (errorCode == -10008) {
            usrErr.setMessage("Please enter valid information");
        } else if (errorCode == -10009) {
            usrErr.setMessage("Overs and team 1 total must be entered");
        } else if (errorCode == -10010) {
            usrErr.setMessage("Team 1 total must be entered");
        } else if (errorCode == -10011) {
            usrErr.setMessage("Number of overs Team 2 will be playing must be entered");
        } else if (errorCode == -10012) {
            usrErr.setMessage("Overs should be between 0 and 50");
        } else if (errorCode == -10013) {
            usrErr.setMessage("Over that the interruption 3 occurred must be greater than " + value + " overs");
        } else if (errorCode == -10014) {
            usrErr.setMessage("Interruption 2: fallen wickets cannot be less than " + value + " wickets");
        } else if (errorCode == -10015) {
            usrErr.setMessage("Interruption 3: fallen wickets cannot be less than " + value + " wickets");
        } else if (errorCode == -10016) {
            usrErr.setMessage("Over that the interruption 2 occurred must be less than " + value + " overs");
        } else if (errorCode == -10017) {
            usrErr.setMessage("Over that the interruption 3 occurred must be less than " + value + " overs");
        } else if (errorCode == -10018) {
            usrErr.setMessage("Interruption 2: Overs remaining must be less than " + value + " overs");
        } else if (errorCode == -10019) {
            usrErr.setMessage("Interruption 3: Overs remaining must be less than " + value + " overs");
        } else if (errorCode == -10020) {
            usrErr.setMessage("Team 1 final total must be " + value + " runs");
        } else if (errorCode == -10021) {
            usrErr.setMessage("Please enter the number of overs");
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
        int startOfInnsOversWickets = (int) (startOfInnsOvers * 100);
        //resources percentage at the start of the match
        resAtStartOfMatch = overData.DataSet(startOfInnsOversWickets);
    }


}
