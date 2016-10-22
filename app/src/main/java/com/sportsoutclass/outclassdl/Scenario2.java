package com.sportsoutclass.outclassdl;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * In Scenario 1: The team 1 has finished batting their allotted overs and the interruption(s) only
 * occurs to the second team.
 */
public class Scenario2 extends AppCompatActivity {
    @BindView(R.id.app_bar) Toolbar toolbar;
    Intent aboutPg, insPg;
    @BindView(R.id.next_button3)
    Button calcBtn;
    //Textview Bindings
    @BindView(R.id.interruption_1_text_view_sc2)
    TextView interruption1TextViewSc2;
    @BindView(R.id.team2_total_interruption_1_text_view_sc2)
    TextView totalInter1TextViewSc2;
    @BindView(R.id.team2_total_interruption_2_text_view_sc2)
    TextView totalInter2TextViewSc2;
    @BindView(R.id.team2_total_interruption_3_text_view_sc2)
    TextView totalInter3TextViewSc2;
    @BindView(R.id.interruption_2_text_view_sc2)
    TextView interruption2TextViewSc2;
    @BindView(R.id.interruption_3_text_view_sc2)
    TextView interruption3TextViewSc2;
    @BindView(R.id.which_over_interruption_1_text_view_sc2)
    TextView whichOverInterruption1TextViewSc2;
    @BindView(R.id.which_over_interruption_2_text_view_sc2)
    TextView whichOverInterruption2TextViewSc2;
    @BindView(R.id.which_over_interruption_3_text_view_sc2)
    TextView whichOverInterruption3TextViewSc2;
    @BindView(R.id.wickets_lost_interruption_1_text_view_sc2)
    TextView wicketsLostInterruption1TextViewSc2;
    @BindView(R.id.wickets_lost_interruption_2_text_view_sc2)
    TextView wicketsLostInterruption2TextViewSc2;
    @BindView(R.id.wickets_lost_interruption_3_text_view_sc2)
    TextView wicketsLostInterruption3TextViewSc2;
    @BindView(R.id.overs_remaining_interruption_1_text_view_sc2)
    TextView oversRemainingInterruption1TextViewSc2;
    @BindView(R.id.overs_remaining_interruption_2_text_view_sc2)
    TextView oversRemainingInterruption2TextViewSc2;
    @BindView(R.id.overs_remaining_interruption_3_text_view_sc2)
    TextView oversRemainingInterruption3TextViewSc2;
    //EditText Bindings
    @BindView(R.id.total_interruption_1_edit_text_sc2)
    EditText totalInter1EditTextSc2;
    @BindView(R.id.total_interruption_2_edit_text_sc2)
    EditText totalInter2EditTextSc2;
    @BindView(R.id.total_interruption_3_edit_text_sc2)
    EditText totalInter3EditTextSc2;
    @BindView(R.id.interruptions_edit_text_sc2)
    EditText team2InterruptionsEditSc2;
    @BindView(R.id.which_over_interruption_1_edit_text_sc2)
    EditText whichOverInterruption1EditTextSc2;
    @BindView(R.id.which_over_interruption_2_edit_text_sc2)
    EditText whichOverInterruption2EditTextSc2;
    @BindView(R.id.which_over_interruption_3_edit_text_sc2)
    EditText whichOverInterruption3EditTextSc2;
    @BindView(R.id.wickets_lost_interruption_1_edit_text_sc2)
    EditText wicketsLostInterruption1EditTextSc2;
    @BindView(R.id.wickets_lost_interruption_2_edit_text_sc2)
    EditText wicketsLostInterruption2EditTextSc2;
    @BindView(R.id.wickets_lost_interruption_3_edit_text_sc2)
    EditText wicketsLostInterruption3EditTextSc2;
    @BindView(R.id.overs_remaining_interruption_1_edit_text_sc2)
    EditText oversRemainingInterruption1EditTextSc2;
    @BindView(R.id.overs_remaining_interruption_2_edit_text_sc2)
    EditText oversRemainingInterruption2EditTextSc2;
    @BindView(R.id.overs_remaining_interruption_3_edit_text_sc2)
    EditText oversRemainingInterruption3EditTextSc2;
    @BindView(R.id.team2_overs_sc2_editText)
    EditText team2OversStartOfInnsSc2;
    @BindView(R.id.team1_final_totalEditTextsc2)
    EditText team1FinalTotalAfterRevSc2;

    int totalWicketsSc2, inter1WicketsSc2, inter2WicketsSc2, inter3WicketsSc2, inter1totalSc2, inter2totalSc2, inter3totalSc2, team1finalTotB4rev;
    double inter1OverSc2, inter2OverSc2, inter3OverSc2, inter1OversAtEndSc2, inter2OversAtEndSc2, inter3OversAtEndSc2, team2OversAtStartSc2;
    boolean allFieldsFilled;
    DataMap overDataSc2;
    StateClass stateSc2;
    Tracker mTracker;
    AlertDialog.Builder t1WinTarget, usrErrAlertSc2;
    InterruptionSetup fix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario2);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        stateSc2 = (StateClass) getApplication();
        mTracker = stateSc2.getDefaultTracker();
        Log.i("TAG", "Setting screen name: Scenario2");
        mTracker.setScreenName("Scenario2");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher); // Initialize this to whatever you want
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = getTheme();
            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
            int color = typedValue.data;
            ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(null, bm, color);
            setTaskDescription(description);
            bm.recycle();
        }
        calcBtn.setBackgroundResource(R.color.primaryColor);
        calcBtn.setTextColor(Color.WHITE);

        init();
        InterruptionsValueEdit();
        editTextData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            aboutPg = new Intent(this, AboutPage.class);
            startActivity(aboutPg);
        } else if (id == R.id.action_instructions) {
            insPg = new Intent(this, HowToPage.class);
            startActivity(insPg);
        }
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    //this edits the number for interruptions
    private void InterruptionsValueEdit() {
        team2InterruptionsEditSc2.addTextChangedListener(new TextWatcher() {
            int interToInt;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String interruptionsToS = s.toString();
                if (interruptionsToS.equals("")) {
                    interruptionsToS = "0";
                }
                Log.v("interruptionsToS value", interruptionsToS);
                interToInt = Integer.parseInt(interruptionsToS);
                stateSc2.setInterruptionsSc2(interToInt);
                InterruptionsAmountVisibilitySetup(interToInt);

                if (interToInt > 0 && interToInt < 4) {
                    team2InterruptionsEditSc2.setNextFocusForwardId(R.id.which_over_interruption_1_edit_text_sc2);
                }
            }
        });

    }

    //this enables disables visibility of number of interruptions available to edit
    private void InterruptionsAmountVisibilitySetup(int i) {
        if (i == 1) {
            interruption1TextViewSc2.setVisibility(View.VISIBLE);
            whichOverInterruption1TextViewSc2.setVisibility(View.VISIBLE);
            wicketsLostInterruption1TextViewSc2.setVisibility(View.VISIBLE);
            oversRemainingInterruption1TextViewSc2.setVisibility(View.VISIBLE);
            whichOverInterruption1EditTextSc2.setVisibility(View.VISIBLE);
            wicketsLostInterruption1EditTextSc2.setVisibility(View.VISIBLE);
            oversRemainingInterruption1EditTextSc2.setVisibility(View.VISIBLE);
            totalInter1TextViewSc2.setVisibility(View.VISIBLE);
            totalInter1EditTextSc2.setVisibility(View.VISIBLE);

            interruption2TextViewSc2.setVisibility(View.INVISIBLE);
            whichOverInterruption2TextViewSc2.setVisibility(View.INVISIBLE);
            wicketsLostInterruption2TextViewSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption2TextViewSc2.setVisibility(View.INVISIBLE);
            whichOverInterruption2EditTextSc2.setVisibility(View.INVISIBLE);
            wicketsLostInterruption2EditTextSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption2EditTextSc2.setVisibility(View.INVISIBLE);
            totalInter2TextViewSc2.setVisibility(View.INVISIBLE);
            totalInter2EditTextSc2.setVisibility(View.INVISIBLE);
            interruption3TextViewSc2.setVisibility(View.INVISIBLE);
            whichOverInterruption3TextViewSc2.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3TextViewSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3TextViewSc2.setVisibility(View.INVISIBLE);
            whichOverInterruption3EditTextSc2.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3EditTextSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3EditTextSc2.setVisibility(View.INVISIBLE);
            totalInter3EditTextSc2.setVisibility(View.INVISIBLE);
            totalInter3TextViewSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption1EditTextSc2.setImeOptions(EditorInfo.IME_ACTION_DONE);


        } else if (i == 2) {
            InterruptionsAmountVisibilitySetup(1);
            oversRemainingInterruption1EditTextSc2.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            interruption2TextViewSc2.setVisibility(View.VISIBLE);
            whichOverInterruption2TextViewSc2.setVisibility(View.VISIBLE);
            wicketsLostInterruption2TextViewSc2.setVisibility(View.VISIBLE);
            oversRemainingInterruption2TextViewSc2.setVisibility(View.VISIBLE);
            whichOverInterruption2EditTextSc2.setVisibility(View.VISIBLE);
            wicketsLostInterruption2EditTextSc2.setVisibility(View.VISIBLE);
            oversRemainingInterruption2EditTextSc2.setVisibility(View.VISIBLE);
            totalInter2TextViewSc2.setVisibility(View.VISIBLE);
            totalInter2EditTextSc2.setVisibility(View.VISIBLE);

            interruption3TextViewSc2.setVisibility(View.INVISIBLE);
            whichOverInterruption3TextViewSc2.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3TextViewSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3TextViewSc2.setVisibility(View.INVISIBLE);
            whichOverInterruption3EditTextSc2.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3EditTextSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3EditTextSc2.setVisibility(View.INVISIBLE);
            totalInter3EditTextSc2.setVisibility(View.INVISIBLE);
            totalInter3TextViewSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption2EditTextSc2.setImeOptions(EditorInfo.IME_ACTION_DONE);

        } else if (i == 3) {
            InterruptionsAmountVisibilitySetup(2);
            oversRemainingInterruption2EditTextSc2.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            interruption3TextViewSc2.setVisibility(View.VISIBLE);
            whichOverInterruption3TextViewSc2.setVisibility(View.VISIBLE);
            wicketsLostInterruption3TextViewSc2.setVisibility(View.VISIBLE);
            oversRemainingInterruption3TextViewSc2.setVisibility(View.VISIBLE);
            whichOverInterruption3EditTextSc2.setVisibility(View.VISIBLE);
            wicketsLostInterruption3EditTextSc2.setVisibility(View.VISIBLE);
            oversRemainingInterruption3EditTextSc2.setVisibility(View.VISIBLE);
            totalInter3EditTextSc2.setVisibility(View.VISIBLE);
            totalInter3TextViewSc2.setVisibility(View.VISIBLE);
            oversRemainingInterruption3EditTextSc2.setImeOptions(EditorInfo.IME_ACTION_DONE);


        } else if (i == 0) {


            interruption1TextViewSc2.setVisibility(View.INVISIBLE);
            whichOverInterruption1TextViewSc2.setVisibility(View.INVISIBLE);
            wicketsLostInterruption1TextViewSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption1TextViewSc2.setVisibility(View.INVISIBLE);
            whichOverInterruption1EditTextSc2.setVisibility(View.INVISIBLE);
            wicketsLostInterruption1EditTextSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption1EditTextSc2.setVisibility(View.INVISIBLE);
            totalInter1TextViewSc2.setVisibility(View.INVISIBLE);
            totalInter1EditTextSc2.setVisibility(View.INVISIBLE);
            interruption2TextViewSc2.setVisibility(View.INVISIBLE);
            whichOverInterruption2TextViewSc2.setVisibility(View.INVISIBLE);
            wicketsLostInterruption2TextViewSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption2TextViewSc2.setVisibility(View.INVISIBLE);
            whichOverInterruption2EditTextSc2.setVisibility(View.INVISIBLE);
            wicketsLostInterruption2EditTextSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption2EditTextSc2.setVisibility(View.INVISIBLE);
            totalInter2TextViewSc2.setVisibility(View.INVISIBLE);
            totalInter2EditTextSc2.setVisibility(View.INVISIBLE);
            interruption3TextViewSc2.setVisibility(View.INVISIBLE);
            whichOverInterruption3TextViewSc2.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3TextViewSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3TextViewSc2.setVisibility(View.INVISIBLE);
            whichOverInterruption3EditTextSc2.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3EditTextSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3EditTextSc2.setVisibility(View.INVISIBLE);
            totalInter3EditTextSc2.setVisibility(View.INVISIBLE);
            totalInter3TextViewSc2.setVisibility(View.INVISIBLE);

        } else {

            interruption1TextViewSc2.setVisibility(View.INVISIBLE);
            whichOverInterruption1TextViewSc2.setVisibility(View.INVISIBLE);
            wicketsLostInterruption1TextViewSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption1TextViewSc2.setVisibility(View.INVISIBLE);
            whichOverInterruption1EditTextSc2.setVisibility(View.INVISIBLE);
            wicketsLostInterruption1EditTextSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption1EditTextSc2.setVisibility(View.INVISIBLE);
            totalInter1TextViewSc2.setVisibility(View.INVISIBLE);
            totalInter1EditTextSc2.setVisibility(View.INVISIBLE);
            interruption2TextViewSc2.setVisibility(View.INVISIBLE);
            whichOverInterruption2TextViewSc2.setVisibility(View.INVISIBLE);
            wicketsLostInterruption2TextViewSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption2TextViewSc2.setVisibility(View.INVISIBLE);
            whichOverInterruption2EditTextSc2.setVisibility(View.INVISIBLE);
            wicketsLostInterruption2EditTextSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption2EditTextSc2.setVisibility(View.INVISIBLE);
            totalInter2TextViewSc2.setVisibility(View.INVISIBLE);
            totalInter2EditTextSc2.setVisibility(View.INVISIBLE);
            interruption3TextViewSc2.setVisibility(View.INVISIBLE);
            whichOverInterruption3TextViewSc2.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3TextViewSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3TextViewSc2.setVisibility(View.INVISIBLE);
            whichOverInterruption3EditTextSc2.setVisibility(View.INVISIBLE);
            wicketsLostInterruption3EditTextSc2.setVisibility(View.INVISIBLE);
            oversRemainingInterruption3EditTextSc2.setVisibility(View.INVISIBLE);
            totalInter3EditTextSc2.setVisibility(View.INVISIBLE);
            totalInter3TextViewSc2.setVisibility(View.INVISIBLE);
        }

    }

    //activating calculate button
    public void activateNextBtnSc2(View v) {

        int interrupt = stateSc2.getInterruptionsSc2();
        allFieldsFilled = whichFieldsTocheck(interrupt);

        Log.v("allFieldsFilledBtn: ", String.valueOf(allFieldsFilled));
        if (allFieldsFilled) {

            AsyncCalculation calc = new AsyncCalculation();
            calc.execute(interrupt);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        } else {
            usrErrAlertSc2.setTitle("Incomplete Information");
            usrErrAlertSc2.setMessage("Please fill all the blanks");
            usrErrAlertSc2.setPositiveButton("OK", null);
            usrErrAlertSc2.show();
        }
    }

    private void editTextData() {
        //overs at the start of interruption
        whichOverInterruption1EditTextSc2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter1OverSc2 = 0;
                String inter1overToS = s.toString();
                if (inter1overToS.equals("")) {
                    inter1overToS = "0";
                }
                inter1OverSc2 = Double.parseDouble(inter1overToS);
                stateSc2.setInter1StartOverSc2(inter1OverSc2);
            }
        });
        whichOverInterruption2EditTextSc2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter2OverSc2 = 0;
                String inter2overToS = s.toString();
                if (inter2overToS.equals("")) {
                    inter2overToS = "0";
                }
                inter2OverSc2 = Double.parseDouble(inter2overToS);
                stateSc2.setInter2StartOverSc2(inter2OverSc2);
            }
        });
        whichOverInterruption3EditTextSc2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter3OverSc2 = 0;
                String inter3overToS = s.toString();
                if (inter3overToS.equals("")) {
                    inter3overToS = "0";
                }
                inter3OverSc2 = Double.parseDouble(inter3overToS);
                stateSc2.setInter3StartOverSc2(inter3OverSc2);
            }
        });
        //wickets at the start of interruption
        wicketsLostInterruption1EditTextSc2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter1WicketsSc2 = 0;
                String inter1WicketsToS = s.toString();
                if (inter1WicketsToS.equals("")) {
                    inter1WicketsToS = "0";
                }
                inter1WicketsSc2 = Integer.parseInt(inter1WicketsToS);
                if (inter1WicketsSc2 > 10) {
                    InterruptionSetup.interruptionErrors(usrErrAlertSc2, -10002, "Error", inter1WicketsToS);
                }
                stateSc2.setInter1WicketsSc2(inter1WicketsSc2);
            }
        });
        wicketsLostInterruption2EditTextSc2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter2WicketsSc2 = 0;
                String inter2WicketsToS = s.toString();
                if (inter2WicketsToS.equals("")) {
                    inter2WicketsToS = "0";
                }
                inter2WicketsSc2 = Integer.parseInt(inter2WicketsToS);
                if (inter2WicketsSc2 > 10) {
                    InterruptionSetup.interruptionErrors(usrErrAlertSc2, -10003, "Error", inter2WicketsToS);
                }
                stateSc2.setInter2WicketsSc2(inter2WicketsSc2);
            }
        });
        wicketsLostInterruption3EditTextSc2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter3WicketsSc2 = 0;
                String inter3WicketsToS = s.toString();
                if (inter3WicketsToS.equals("")) {
                    inter3WicketsToS = "0";
                }
                inter3WicketsSc2 = Integer.parseInt(inter3WicketsToS);
                if (inter3WicketsSc2 > 10) {
                    InterruptionSetup.interruptionErrors(usrErrAlertSc2, -10004, "Error", inter3WicketsToS);
                }
                stateSc2.setInter3WicketsSc2(inter3WicketsSc2);
            }
        });
        //overs remaining till end of play
        oversRemainingInterruption1EditTextSc2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter1OversAtEndSc2 = 0;
                String inter1OversAtEndToS = s.toString();
                if (inter1OversAtEndToS.equals("")) {
                    inter1OversAtEndToS = "0";
                }
                inter1OversAtEndSc2 = Double.parseDouble(inter1OversAtEndToS);
                stateSc2.setInter1EndOverSc2(inter1OversAtEndSc2);
            }
        });
        oversRemainingInterruption2EditTextSc2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter2OversAtEndSc2 = 0;
                String inter2OversAtEndToS = s.toString();
                if (inter2OversAtEndToS.equals("")) {
                    inter2OversAtEndToS = "0";
                }
                inter2OversAtEndSc2 = Double.parseDouble(inter2OversAtEndToS);
                stateSc2.setInter2EndOverSc2(inter2OversAtEndSc2);
            }
        });
        oversRemainingInterruption3EditTextSc2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter3OversAtEndSc2 = 0;
                String inter3OversAtEndToS = s.toString();
                if (inter3OversAtEndToS.equals("")) {
                    inter3OversAtEndToS = "0";
                }
                inter3OversAtEndSc2 = Double.parseDouble(inter3OversAtEndToS);
                stateSc2.setInter3EndOverSc2(inter3OversAtEndSc2);
            }
        });
        //total at the interruption
        totalInter1EditTextSc2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter1totalSc2 = 0;
                String inter1OversAtEndToS = s.toString();
                if (inter1OversAtEndToS.equals("")) {
                    inter1OversAtEndToS = "0";
                }
                inter1totalSc2 = Integer.parseInt(inter1OversAtEndToS);
                stateSc2.setTotalT1int1Sc2(inter1totalSc2);
            }
        });
        totalInter2EditTextSc2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter2totalSc2 = 0;
                String inter2OversAtEndToS = s.toString();
                if (inter2OversAtEndToS.equals("")) {
                    inter2OversAtEndToS = "0";
                }
                inter2totalSc2 = Integer.parseInt(inter2OversAtEndToS);
                stateSc2.setTotalT1int2Sc2(inter2totalSc2);
            }
        });
        totalInter3EditTextSc2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inter3totalSc2 = 0;
                String inter3OversAtEndToS = s.toString();
                if (inter3OversAtEndToS.equals("")) {
                    inter3OversAtEndToS = "0";
                }
                inter3totalSc2 = Integer.parseInt(inter3OversAtEndToS);
                stateSc2.setTotalT1int3Sc2(inter3totalSc2);
            }
        });
        //overs given for team 2
        team2OversStartOfInnsSc2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                team2OversAtStartSc2 = 0;
                String inter3OversAtEndToS = s.toString();
                if (inter3OversAtEndToS.equals("")) {
                    inter3OversAtEndToS = "0";
                }
                team2OversAtStartSc2 = Double.parseDouble(inter3OversAtEndToS);
                stateSc2.setOversT2StartSc2(team2OversAtStartSc2);
            }
        });
        //team1 final total before is revised
        team1FinalTotalAfterRevSc2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                team1finalTotB4rev = 0;
                String inter3OversAtEndToS = s.toString();
                if (inter3OversAtEndToS.equals("")) {
                    inter3OversAtEndToS = "0";
                }
                team1finalTotB4rev = Integer.parseInt(inter3OversAtEndToS);
                stateSc2.setTotalT1Sc2(team1finalTotB4rev);
            }
        });
    }

    public void team1ScoreCalc(int target) {
//        int inter = stateSc2.getInterruptionsSc2();
        //values less than -10000 contains error codes
        if (target > -10000) {
            t1WinTarget.setTitle("Par Score");
            t1WinTarget.setMessage("Team 2 needs " + String.valueOf(target) + " runs to win.");
            t1WinTarget.setPositiveButton("OK", null);
            t1WinTarget.show();
        } else {
            String value = stateSc2.getErrorMessageValue();
            String title = stateSc2.getErrorMessageTitle();
            InterruptionSetup.interruptionErrors(usrErrAlertSc2, target, title, value);
        }
    }


    public boolean whichFieldsTocheck(int inter) {
        boolean notEmpty = false;
        if (inter == 1) {
            boolean x1 = editTextFieldCheck(totalInter1EditTextSc2);
            boolean x2 = editTextFieldCheck(whichOverInterruption1EditTextSc2);
            boolean x3 = editTextFieldCheck(wicketsLostInterruption1EditTextSc2);
            boolean x4 = editTextFieldCheck(team1FinalTotalAfterRevSc2);
            boolean x5 = editTextFieldCheck(team2OversStartOfInnsSc2);

            if (x1 && x2 && x3 && x4 && x5) {
                notEmpty = true;
            }

        } else if (inter == 2) {
            boolean x1 = whichFieldsTocheck(1);
            boolean x2 = editTextFieldCheck(totalInter2EditTextSc2);
            boolean x3 = editTextFieldCheck(whichOverInterruption2EditTextSc2);
            boolean x4 = editTextFieldCheck(wicketsLostInterruption2EditTextSc2);
            if (x1 && x2 && x3 && x4) {
                notEmpty = true;
            }

        } else if (inter == 3) {
            boolean x1 = whichFieldsTocheck(2);
            boolean x2 = editTextFieldCheck(totalInter3EditTextSc2);
            boolean x3 = editTextFieldCheck(whichOverInterruption3EditTextSc2);
            boolean x4 = editTextFieldCheck(wicketsLostInterruption3EditTextSc2);
            if (x1 && x2 && x3 && x4) {
                notEmpty = true;
            }
        }
        return notEmpty;
    }

    public boolean editTextFieldCheck(EditText x) {
        boolean fieldNotEmpty = false;
        int len = x.getText().toString().length();
        Log.v("length: ", String.valueOf(len));
        if (len != 0) {
            fieldNotEmpty = true;
        }
        Log.v("fieldNotEmpty: ", String.valueOf(fieldNotEmpty));
        return fieldNotEmpty;
    }

    private class AsyncCalculation extends AsyncTask<Integer, Void, Integer> {
        ProgressDialog pd = new ProgressDialog(Scenario2.this);
        InterruptionSetup interNew = new InterruptionSetup();
        String response = "";
        int target = 0;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("\tCalculating...");
            pd.show();
        }

        @Override
        protected Integer doInBackground(Integer... interruptions) {
            int interruption = interruptions[0];
            try {
                if (interruption == 1) {
                    target = interNew.one_Interruption_Sc2();
                } else if (interruption == 2) {
                    target = interNew.two_Interruptions_Sc2();
                } else if (interruption == 3) {
                    target = interNew.three_Interruptions_Sc2();
                }

                Log.v("theCalculatedTarget: ", String.valueOf(target));

            } catch (Exception e) {
                e.printStackTrace();
                response = e.getMessage();
            }
            return target;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            Log.v("theCalculatedTrgtPE: ", String.valueOf(result));
            team1ScoreCalc(result);
            pd.dismiss();
        }
    }

    private void init() {

        //TextView Assignments
        //Edit Text Assignments

        totalWicketsSc2 = 10;
        overDataSc2 = new DataMap();
        fix = new InterruptionSetup();
        t1WinTarget = new AlertDialog.Builder(Scenario2.this);
        usrErrAlertSc2 = new AlertDialog.Builder(Scenario2.this);
        allFieldsFilled = false;
    }
}
