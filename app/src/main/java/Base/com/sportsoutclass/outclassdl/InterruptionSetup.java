package com.sportsoutclass.outclassdl;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.DecimalFormat;

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
    private void g50_setup() {
        int g50_index = state.getG50();
        Log.d(" G50 Value", String.valueOf(g50_index));
        if (g50_index == 0) {
            g50_value = 200;
        } else if (g50_index == 1) {
            g50_value = 245;
        }
    }

    int one_interruption_SecondInnings() {
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
        double oversAtInter1Start = state.getInter1StartOverSI();
        double oversCanPut = overCalculations(oversForT2, oversAtInter1Start, "minus");
        //overs remaining at the end of the interruption
        double oversRemainingInterEnd = state.getInter1EndOverSI();
        if (oversCanPut <= 0) {
            String wholeOversToS = String.valueOf(oversForT2);
            state.setErrorMessageValue(wholeOversToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10007;
        }
        if (oversCanPut <= oversRemainingInterEnd) {
            String oversCanPutToS = String.valueOf(oversCanPut);
            state.setErrorMessageValue(oversCanPutToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10008;
        }

        Log.v("oversAtInter1Start: ", String.valueOf(oversAtInter1Start));
        if (oversAtInter1Start > oversForT2) {
            return -10001;
        }

        //Team 2 Wickets at the start of the interruption 1
        int wicketsAtInter1Start = state.getInter1WicketsSI();
        Log.v("WktsInnings1: ", String.valueOf(wicketsAtInter1Start));
        if (wicketsAtInter1Start > 10) {
            return -10003;
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
        state.setEndInterResSI(resourcesLeftAtEndInter);
        Log.v("resourcesAvailable: ", String.valueOf(resourcesLeftAtEndInter));
        //setting up target
        target = (int) ((t1TotalScore * (resourcesLeftAtEndInter / resAtStartOfMatch)) + 1);
        Log.v("Target@calculations: ", String.valueOf(target));

        return target;
    }

    int two_interruptions_SecondInnings() {
        init();
        one_interruption_SecondInnings();
        target = -1000;
        double oversAtInter1Start = state.getInter1StartOverSI();
        double oversRemainingInter1End = state.getInter1EndOverSI();
        double oversAtInter2Start = state.getInter2StartOverSI();
        //Team 2 Wickets at the start of the interruption 1
        int wicketsAtInter1Start = state.getInter1WicketsSI();
        int wicketsAtInter2Start = state.getInter2WicketsSI();
        double oversFinalizedAtInter1End = overCalculations(oversAtInter1Start, oversRemainingInter1End, "plus");
        double oversCanPut = overCalculations(oversFinalizedAtInter1End, oversAtInter2Start, "minus");
        double oversRemainingAtInter2End = state.getInter2EndOverSI();
        if (oversCanPut <= 0) {
            String wholeOversToS = String.valueOf(oversFinalizedAtInter1End);
            state.setErrorMessageValue(wholeOversToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10017;
        }
        /*  if overs available to bowl is less than user entered number
            of overs remaining */
        if (oversCanPut <= oversRemainingAtInter2End) {
            String oversCanPutToS = String.valueOf(oversCanPut);
            state.setErrorMessageValue(oversCanPutToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10019;
        }
        //checking if user entered wickets at interruption 2 is correct
        if (wicketsAtInter2Start > 10) {
            return -10004;
        }

        //checks if the over that inter 2 happened is less than inter 1
        if (oversAtInter2Start < oversAtInter1Start) {
            String intOversStartToS = String.valueOf(oversAtInter1Start);
            state.setErrorMessageValue(intOversStartToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10006;
        }
        //checks if the wickets at inter 2 is less than inter 1
        if (wicketsAtInter2Start < wicketsAtInter1Start) {
            String int1WicketsToS = String.valueOf(wicketsAtInter1Start);
            state.setErrorMessageValue(int1WicketsToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10015;
        }
        double oversFinalizedAtInter2End = overCalculations(oversAtInter2Start, oversRemainingAtInter2End, "plus");
        double oversLeftAtInter2Start = overCalculations(oversFinalizedAtInter1End, oversAtInter2Start, "minus");

        if (oversFinalizedAtInter2End >= oversFinalizedAtInter1End) {
            String oversLeftAtInter2StartToS = String.valueOf(oversLeftAtInter2Start);
            state.setErrorMessageValue(oversLeftAtInter2StartToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10019;
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
        double resourcesLeftAtEndInter = state.getEndInterResSI();
        double resLeftAtInt2 = resourcesLeftAtEndInter - resLostAtInt2;
        state.setEndInterResSI(resLeftAtInt2);
        Log.v("resLeftAtInt2: ", String.valueOf(resLeftAtInt2));
        target = (int) ((t1TotalScore * (resLeftAtInt2 / resAtStartOfMatch)) + 1);
        Log.v("targetInt2: ", String.valueOf(target));

        return target;
    }

    int three_interruptions_SecondInnings() {
        init();
        one_interruption_SecondInnings();
        two_interruptions_SecondInnings();
        target = -1000;
        double oversAtInter2Start = state.getInter2StartOverSI();
        double oversAtInter3Start = state.getInter3StartOverSI();
        Log.v("oversAtInter3Start: ", String.valueOf(oversAtInter3Start));
        int wicketsAtInter2Start = state.getInter2WicketsSI();
        int wicketsAtInter3Start = state.getInter3WicketsSI();
        Log.v("wicketsAtInter3Start: ", String.valueOf(wicketsAtInter3Start));
        double oversFinalizedAtInter2End = overCalculations(state.getInter2StartOverSI(), state.getInter2EndOverSI(), "plus");
        double oversRemainingAtInter3End = state.getInter3EndOverSI();
        double oversCanPut = overCalculations(oversFinalizedAtInter2End, state.getInter3StartOverSI(), "minus");
        if (oversCanPut <= 0) {
            String wholeOversToS = String.valueOf(oversFinalizedAtInter2End);
            state.setErrorMessageValue(wholeOversToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10018;
        }
        if (oversAtInter3Start < oversAtInter2Start) {
            String int2OversStartToS = String.valueOf(oversAtInter2Start);
            state.setErrorMessageTitle("Invalid Information");
            state.setErrorMessageValue(int2OversStartToS);
            return -10014;
        }
        if (oversCanPut <= oversRemainingAtInter3End) {
            String oversCanPutToS = String.valueOf(oversCanPut);
            state.setErrorMessageValue(oversCanPutToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10020;
        }
        if (wicketsAtInter3Start > 10) {
            return -10005;
        }
        if (wicketsAtInter3Start < wicketsAtInter2Start) {
            String int2WicketsToS = String.valueOf(wicketsAtInter2Start);
            state.setErrorMessageTitle("Invalid Information");
            state.setErrorMessageValue(int2WicketsToS);
            return -10016;
        }
        double oversFinalizedAtInter3End = overCalculations(oversAtInter3Start, state.getInter3EndOverSI(), "plus");
        double oversLeftAtInter3Start = overCalculations(oversFinalizedAtInter2End, oversAtInter3Start, "minus");

        if (oversFinalizedAtInter3End >= oversFinalizedAtInter2End) {
            String oversLeftAtInter3StartTos = String.valueOf(oversLeftAtInter3Start);
            state.setErrorMessageValue(oversLeftAtInter3StartTos);
            state.setErrorMessageTitle("Invalid Information");
            return -10020;
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
        double resLeftAtInt2 = state.getEndInterResSI();
        Log.v("resLeftAtInt2: ", String.valueOf(resLeftAtInt2));
        double resLeftAtInt3 = resLeftAtInt2 - resLostAtInt3;
        Log.v("resourcesAvailable: ", String.valueOf(resLeftAtInt3));
        target = (int) ((t1TotalScore * (resLeftAtInt3 / resAtStartOfMatch)) + 1);
        Log.v("target: ", String.valueOf(target));
        return target;
    }

    /**
     * This method is used to calculate overs since there's only have 6 balls per over
     *
     * @param x start value
     * @param y end value
     * @param function plus or minus
     * @return number of overs
     */
    double overCalculations(double x, double y, String function) {
        double z = 0.0;
        DecimalFormat df = new DecimalFormat("#.0");
        if (function.equals("plus")) {
            double zRaw = x + y;
            z = Double.parseDouble(df.format(zRaw));
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
            double zRaw = x - y;
            z = Double.parseDouble(df.format(zRaw));
            Log.v("z in minus: ", String.valueOf(z));
            String doubleToS = String.valueOf(z);
            int digitCheck = (int) (z + 0.5);
            Log.v("z after digitCheck: ", String.valueOf(digitCheck));
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
    int one_Interruption_FirstInnings() {
        init();
        g50_setup();
        //overs team1 will play
        double oversForT1FI = state.getOvers();
        Log.v("oversForT2FI: ", String.valueOf(oversForT1FI));
        int team1FinalTotalb4Rev = state.getTotalT1FI();
        //Team 1 Overs at the start of the interruption 1
        double oversAtInter1StartFI = state.getInter1StartOverFI();
        Log.v("oversAtInter1Start: ", String.valueOf(oversAtInter1StartFI));
        //Team 2 Wickets at the start of the interruption 1
        int wicketsAtInter1StartFI = state.getInter1WicketsFI();
        Log.v("wicketsatInter1FI: ", String.valueOf(wicketsAtInter1StartFI));
        //Error message if wickets are greater than 10
        //overs remaining at the start of the interruption
        double remainingOversAtInterStartFI = overCalculations(startOfInnsOvers, oversAtInter1StartFI, "minus");
        //overs remaining at the end of the interruption
        double oversRemainingInterEndFI = state.getInter1EndOverFI();
        double oversCanPut = overCalculations(oversForT1FI, oversAtInter1StartFI, "minus");
        Log.v("oversCanPut: ", String.valueOf(oversCanPut));
        //if overs available to bowl are less than 0 at interruption 1
        if (oversCanPut <= 0) {
            String wholeOversToS = String.valueOf(oversForT1FI);
            state.setErrorMessageValue(wholeOversToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10007;
        }
        //if the number of available to put is less than the number
        //of remaining at this interruption
        if (oversCanPut <= oversRemainingInterEndFI) {
            String oversCanPutToS = String.valueOf(oversCanPut);
            state.setErrorMessageValue(oversCanPutToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10008;
        }
        int TotalT1int1FI = state.getTotalT1int1FI();

        /*
            if overs remaining is 0 at inter 1, total team 1 made should be equal to
            team 1 final total
         */
        if (oversRemainingInterEndFI == 0 && TotalT1int1FI != team1FinalTotalb4Rev) {
            String TotalT1int1FIToS = String.valueOf(TotalT1int1FI);
            state.setErrorMessageValue(TotalT1int1FIToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10021;
        }


        if (oversAtInter1StartFI > oversForT1FI) {
            return -10001;
        }
        //Team 2 Wickets at the start of the interruption 1
        int wicketsAtInter1Start = state.getInter1WicketsSI();
        if (wicketsAtInter1Start > 10) {
            return -10003;
        }
        //Team 2 Overs and wickets together when the interruption happened to set up key to find resource percentage
        int oversAndWicketsInt1StartTogetherFI = (int) ((remainingOversAtInterStartFI * 100) + wicketsAtInter1StartFI);
        Log.v("WktsAndOvrs2gthrStart: ", String.valueOf(oversAndWicketsInt1StartTogetherFI));
        //resource percentage at the start of the interruption
        double percentageResourcesAtInterStartFI = overData.DataSet(oversAndWicketsInt1StartTogetherFI);
        Log.v("resources@interStart: ", String.valueOf(percentageResourcesAtInterStartFI));
        Log.v("netOvers: ", String.valueOf(remainingOversAtInterStartFI));
        //overs remaining plus wickets to get rid of decimal
        int totalOversRemainingPlusWicketsFI = (int) ((oversRemainingInterEndFI * 100) + wicketsAtInter1StartFI);
        Log.v("TotalOversAndWkts: ", String.valueOf(totalOversRemainingPlusWicketsFI));
        //resources left gathered from dataSet at the end of the interruption
        double resourceCheckForRemainderOversFI = overData.DataSet(totalOversRemainingPlusWicketsFI);
        Log.v("resForRemainder: ", String.valueOf(resourceCheckForRemainderOversFI));
        //resources lost at the end of the interruption
        double resourcesLost = percentageResourcesAtInterStartFI - resourceCheckForRemainderOversFI;
        Log.v("resourcesLost: ", String.valueOf(resourcesLost));
        //resources left at end of interruption
        double resourcesLeftAtEndInterFI = resAtStartOfMatch - resourcesLost;
        state.setEndInterResFI(resourcesLeftAtEndInterFI);
        Log.v("resourcesAvailable: ", String.valueOf(resourcesLeftAtEndInterFI));

        double oversForT2AtStartFI = state.getOversT2StartFI();
        double resForT2AtStartFI = overData.DataSet((int) oversForT2AtStartFI * 100);

        if (resForT2AtStartFI > resourcesLeftAtEndInterFI) {
            target = (int) (team1FinalTotalb4Rev + g50_value * (resForT2AtStartFI - resourcesLeftAtEndInterFI) / 100 + 1);
        } else {
            target = (int) (team1FinalTotalb4Rev * resForT2AtStartFI / resourcesLeftAtEndInterFI + 1);
        }
        return target;
    }

    int two_Interruptions_FirstInnings() {
        init();
        g50_setup();
        one_Interruption_FirstInnings();
        target = -1000;
        int team1FinalTotalb4Rev = state.getTotalT1FI();
        double oversAtInter1StartFI = state.getInter1StartOverFI();
        double oversRemainingInterEndFI = state.getInter1EndOverFI();
        double oversAtInter2StartFI = state.getInter2StartOverFI();
        //Team 2 Wickets at the start of the interruption 1
        int wicketsAtInter1StartFI = state.getInter1WicketsFI();
        int wicketsAtInter2StartFI = state.getInter2WicketsFI();
        //checking if wickets at start of int 2 is greater than 10
        if (wicketsAtInter2StartFI > 10) {
            target = -10004;
            return target;
        }
        double oversFinalizedAtInter1EndFI = overCalculations(oversAtInter1StartFI, oversRemainingInterEndFI, "plus");
        double oversRemainingAtInter2EndFI = state.getInter2EndOverFI();
        double oversCanPut = overCalculations(oversFinalizedAtInter1EndFI, oversAtInter2StartFI, "minus");


        int TotalT1int2FI = state.getTotalT1int2FI();
        if (oversRemainingAtInter2EndFI == 0 && TotalT1int2FI != team1FinalTotalb4Rev) {
            String TotalT1int2FIToS = String.valueOf(TotalT1int2FI);
            state.setErrorMessageValue(TotalT1int2FIToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10021;
        }
        if (oversCanPut <= 0) {
            String wholeOversToS = String.valueOf(oversFinalizedAtInter1EndFI);
            state.setErrorMessageValue(wholeOversToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10017;
        }
        if (oversCanPut <= oversRemainingAtInter2EndFI) {
            String oversCanPutToS = String.valueOf(oversCanPut);
            state.setErrorMessageValue(oversCanPutToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10019;
        }
        if (wicketsAtInter2StartFI > 10) {
            return -10004;
        }
        if (oversAtInter2StartFI < oversAtInter1StartFI) {
            String intOversStartToS = String.valueOf(oversAtInter1StartFI);
            state.setErrorMessageValue(intOversStartToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10006;
        }
        if (wicketsAtInter2StartFI < wicketsAtInter1StartFI) {
            String int1WicketsToS = String.valueOf(wicketsAtInter1StartFI);
            state.setErrorMessageValue(int1WicketsToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10015;
        }

        /*
         * Make a check at button press for this.
         */
        double oversFinalizedAtInter2EndFI = overCalculations(oversAtInter2StartFI, oversRemainingAtInter2EndFI, "plus");
        double oversLeftAtInter2StartFI = overCalculations(oversFinalizedAtInter1EndFI, oversAtInter2StartFI, "minus");

        if (oversFinalizedAtInter2EndFI >= oversFinalizedAtInter1EndFI) {
            String oversLeftAtInter2StartToS = String.valueOf(oversLeftAtInter2StartFI);
            state.setErrorMessageValue(oversLeftAtInter2StartToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10019;
        }


        int oversWktsLeftAtInter2StartFI = (int) ((oversLeftAtInter2StartFI * 100) + wicketsAtInter2StartFI);
        Log.v("resLeftAtInter2Start: ", String.valueOf(oversWktsLeftAtInter2StartFI));
        double resLeftAtInter2StartFI = overData.DataSet(oversWktsLeftAtInter2StartFI);
        double oversLeftAtInter2EndFI = overCalculations(oversFinalizedAtInter2EndFI, oversAtInter2StartFI, "minus");
        Log.v("oversLeftAtInter2End: ", String.valueOf(oversLeftAtInter2EndFI));
        int oversWktsLeftAtInter2EndFI = (int) ((oversLeftAtInter2EndFI * 100) + wicketsAtInter2StartFI);
        Log.v("resLeftAtInter2End: ", String.valueOf(oversWktsLeftAtInter2EndFI));
        double resLeftAtInter2EndFI = overData.DataSet(oversWktsLeftAtInter2EndFI);
        double resLostAtInt2FI = resLeftAtInter2StartFI - resLeftAtInter2EndFI;
        double resourcesLeftAtEndInterFI = state.getEndInterResFI();
        double resLeftAtInt2FI = resourcesLeftAtEndInterFI - resLostAtInt2FI;
        state.setEndInterResFI(resLeftAtInt2FI);
        double oversForT2AtStartFI = state.getOversT2StartFI();
        double resForT2AtStartFI = overData.DataSet((int) oversForT2AtStartFI * 100);
        if (resForT2AtStartFI > resourcesLeftAtEndInterFI) {
            target = (int) (team1FinalTotalb4Rev + g50_value * (resForT2AtStartFI - resourcesLeftAtEndInterFI) / 100 + 1);
        } else {
            target = (int) (team1FinalTotalb4Rev * resForT2AtStartFI / resourcesLeftAtEndInterFI + 1);
        }
        return target;
    }

    int three_Interruptions_FirstInnings() {
        init();
        g50_setup();
        one_Interruption_FirstInnings();
        two_Interruptions_FirstInnings();
        target = -1000;
        double oversAtInter2StartFI = state.getInter2StartOverFI();
        double oversAtInter3StartFI = state.getInter3StartOverFI();
        double oversRemainingAtInter3EndFI = state.getInter3EndOverFI();
        Log.v("oversAtInter3Start: ", String.valueOf(oversAtInter3StartFI));
        int wicketsAtInter2StartFI = state.getInter2WicketsFI();
        int wicketsAtInter3StartFI = state.getInter3WicketsFI();
        int team1FinalTotalb4Rev = state.getTotalT1FI();
        Log.v("wicketsAtInter3Start: ", String.valueOf(wicketsAtInter3StartFI));
        //checks if user entered a wrong number of wickets
        if (wicketsAtInter3StartFI > 10) {
            target = -10005;
            return target;
        }
        double oversFinalizedAtInter2EndFI = overCalculations(state.getInter2StartOverFI(), state.getInter2EndOverFI(), "plus");
        double oversLeftAtInter3StartFI = overCalculations(oversFinalizedAtInter2EndFI, oversAtInter3StartFI, "minus");
        int oversWktsLeftAtInter3StartFI = (int) ((oversLeftAtInter3StartFI * 100) + wicketsAtInter3StartFI);
        double oversFinalizedAtInter3EndFI = overCalculations(oversAtInter3StartFI, state.getInter3EndOverFI(), "plus");

        double oversCanPut = overCalculations(oversFinalizedAtInter2EndFI, state.getInter3StartOverSI(), "minus");
        if (oversCanPut <= 0) {
            String wholeOversToS = String.valueOf(oversFinalizedAtInter2EndFI);
            state.setErrorMessageValue(wholeOversToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10018;
        }
        if (oversAtInter3StartFI < oversAtInter2StartFI) {
            String int2OversStartToS = String.valueOf(oversAtInter2StartFI);
            state.setErrorMessageTitle("Invalid Information");
            state.setErrorMessageValue(int2OversStartToS);
            return -10014;
        }
        if (oversCanPut <= oversRemainingAtInter3EndFI) {
            String oversCanPutToS = String.valueOf(oversCanPut);
            state.setErrorMessageValue(oversCanPutToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10020;
        }

        int TotalT1int3FI = state.getTotalT1int3FI();
        /*
            if overs remaining is 0 at inter 3, total team 1 made should be equal to
            team 1 final total
         */
        if (oversRemainingAtInter3EndFI == 0 && TotalT1int3FI != team1FinalTotalb4Rev) {
            String TotalT1int3Sc2ToS = String.valueOf(TotalT1int3FI);
            state.setErrorMessageValue(TotalT1int3Sc2ToS);
            state.setErrorMessageTitle("Invalid Information");
            return -10021;
        }

        if (wicketsAtInter3StartFI > 10) {
            return -10005;
        }
        if (wicketsAtInter3StartFI < wicketsAtInter2StartFI) {
            String int2WicketsToS = String.valueOf(wicketsAtInter2StartFI);
            state.setErrorMessageTitle("Invalid Information");
            state.setErrorMessageValue(int2WicketsToS);
            return -10016;
        }

        if (oversFinalizedAtInter3EndFI >= oversFinalizedAtInter2EndFI) {
            String oversLeftAtInter3StartTos = String.valueOf(oversLeftAtInter3StartFI);
            state.setErrorMessageValue(oversLeftAtInter3StartTos);
            state.setErrorMessageTitle("Invalid Information");
            return -10020;
        }

        double resLeftAtInter3StartFI = overData.DataSet(oversWktsLeftAtInter3StartFI);
        double oversLeftAtInter3EndFI = overCalculations(oversFinalizedAtInter3EndFI, oversAtInter3StartFI, "minus");
        Log.v("oversLeftAtInter3End: ", String.valueOf(oversLeftAtInter3EndFI));
        int oversWktsLeftAtInter3EndFI = (int) ((oversLeftAtInter3EndFI * 100) + wicketsAtInter3StartFI);
        double resLeftAtInter3EndFI = overData.DataSet(oversWktsLeftAtInter3EndFI);
        Log.v("resLeftAtInter3End: ", String.valueOf(resLeftAtInter3EndFI));
        double resLostAtInt3FI = resLeftAtInter3StartFI - resLeftAtInter3EndFI;
        Log.v("resLostAtInt3: ", String.valueOf(resLostAtInt3FI));
        double resLeftAtInt2FI = state.getEndInterResFI();
        Log.v("resLeftAtInt2: ", String.valueOf(resLeftAtInt2FI));
        double resLeftAtInt3FI = resLeftAtInt2FI - resLostAtInt3FI;
        Log.v("resourcesAvailable: ", String.valueOf(resLeftAtInt3FI));
        state.setEndInterResFI(resLeftAtInt3FI);
        double oversForT2AtStartFI = state.getOversT2StartFI();
        double resForT2AtStartFI = overData.DataSet((int) oversForT2AtStartFI * 100);
        if (resForT2AtStartFI > resLeftAtInt2FI) {
            target = (int) (team1FinalTotalb4Rev + g50_value * (resForT2AtStartFI - resLeftAtInt2FI) / 100 + 1);
        } else {
            target = (int) (team1FinalTotalb4Rev * resForT2AtStartFI / resLeftAtInt2FI + 1);
        }
        Log.v("target: ", String.valueOf(target));
        return target;
    }

    /**
     * This method contains all the error that Alert builder will show the user.
     *
     * @param usrErr    alertBuilder for different scenarios
     * @param errorCode error code
     * @param title     Title that will be used in alert builder
     * @param value     passing any value that needs to be shown in the builder
     */
    static void interruptionErrors(AlertDialog.Builder usrErr, int errorCode, String title, String value) {

        usrErr.setTitle(title);
        switch (errorCode) {
            case -10001:
                usrErr.setMessage("Interruption 1: Overs should be between 0 and 50");
                break;
            case -10002:
                usrErr.setMessage("Team 1 Wickets: Should be between 0 and 10");
                break;
            case -10003:
                usrErr.setMessage("Interruption 1: Wickets should be between 0 and 10");
                break;
            case -10004:
                usrErr.setMessage("Interruption 2: Wickets should be between 0 and 10");
                break;
            case -10005:
                usrErr.setMessage("Interruption 3: Wickets should be between 0 and 10");
                break;
            case -10006:
                usrErr.setMessage("Over that the interruption 2 occurred must be greater than " + value + " overs");
                break;
            case -10007:
                usrErr.setMessage("Over that the interruption 1 occurred must be less than " + value + " overs");
                break;
            case -10008:
                usrErr.setMessage("Interruption 1: Overs remaining must be less than " + value + " overs");
                break;
            case -10009:
                usrErr.setMessage("Please enter valid information");
                break;
            case -10010:
                usrErr.setMessage("Overs and team 1 total must be entered");
                break;
            case -10011:
                usrErr.setMessage("Team 1 total must be entered");
                break;
            case -10012:
                usrErr.setMessage("Initial Number of overs Team 2 will be playing must be entered");
                break;
            case -10013:
                usrErr.setMessage("Overs should be between 0 and 50");
                break;
            case -10014:
                usrErr.setMessage("Over that the interruption 3 occurred must be greater than " + value + " overs");
                break;
            case -10015:
                usrErr.setMessage("Interruption 2: fallen wickets cannot be less than " + value + " wickets");
                break;
            case -10016:
                usrErr.setMessage("Interruption 3: fallen wickets cannot be less than " + value + " wickets");
                break;
            case -10017:
                usrErr.setMessage("Over that the interruption 2 occurred must be less than " + value + " overs");
                break;
            case -10018:
                usrErr.setMessage("Over that the interruption 3 occurred must be less than " + value + " overs");
                break;
            case -10019:
                usrErr.setMessage("Interruption 2: Overs remaining must be less than " + value + " overs");
                break;
            case -10020:
                usrErr.setMessage("Interruption 3: Overs remaining must be less than " + value + " overs");
                break;
            case -10021:
                usrErr.setMessage("Team 1 final total must be " + value + " runs");
                break;
            case -10022:
                usrErr.setMessage("Please enter the number of overs");
                break;
        }
        usrErr.setPositiveButton("OK", null);
        usrErr.show();
    }

    /**
     * Checks if the selected edit text is empty or not
     *
     * @param x EditText that will be checked
     * @return true if not empty
     */
    boolean editTextFieldCheck(EditText x) {
        boolean fieldNotEmpty = false;
        int len = x.getText().toString().length();
        if (len != 0) {
            fieldNotEmpty = true;
        } else if (x.getVisibility() == View.GONE || x.getVisibility() == View.INVISIBLE) {
            fieldNotEmpty = true;
        }
        return fieldNotEmpty;
    }

    private void init() {
        state = (StateClass) StateClass.getContext();
        overData = new DataMap();
        target = 0;
        //Team1 total
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
