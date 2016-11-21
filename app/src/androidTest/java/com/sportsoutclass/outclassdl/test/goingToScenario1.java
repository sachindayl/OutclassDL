package com.sportsoutclass.outclassdl.test;

import com.sportsoutclass.outclassdl.MainActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;


public class goingToScenario1 extends ActivityInstrumentationTestCase2<MainActivity> {
  	private Solo solo;
  	
  	public goingToScenario1() {
		super(MainActivity.class);
  	}

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}
  
   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
  	}
  
	public void testRun() {
        //Wait for activity: 'com.sportsoutclass.outclassdl.MainActivity'
		solo.waitForActivity(com.sportsoutclass.outclassdl.MainActivity.class, 2000);
        //Set default small timeout to 26621 milliseconds
		Timeout.setSmallTimeout(3000);
        //Enter the text: '50'
		solo.clearEditText((android.widget.EditText) solo.getView(com.sportsoutclass.outclassdl.R.id.number_overs_edit_text));
		solo.enterText((android.widget.EditText) solo.getView(com.sportsoutclass.outclassdl.R.id.number_overs_edit_text), "50");
        //Click on Yes
		solo.clickOnView(solo.getView(com.sportsoutclass.outclassdl.R.id.did_team_1_bat_spinner));
        //Click on Yes
		solo.clickOnView(solo.getView(TextView.class, 0));
        //Click on Empty Text View
		solo.clickOnView(solo.getView(com.sportsoutclass.outclassdl.R.id.team_1_score_edit));
        //Enter the text: '250'
		solo.clearEditText((android.widget.EditText) solo.getView(com.sportsoutclass.outclassdl.R.id.team_1_score_edit));
		solo.enterText((android.widget.EditText) solo.getView(com.sportsoutclass.outclassdl.R.id.team_1_score_edit), "250");
        //Enter the text: '5'
		solo.clearEditText((android.widget.EditText) solo.getView(com.sportsoutclass.outclassdl.R.id.team_1_wickets_edit));
		solo.enterText((android.widget.EditText) solo.getView(com.sportsoutclass.outclassdl.R.id.team_1_wickets_edit), "5");
        //Press next button
		solo.pressSoftKeyboardNextButton();
        //Click on next
		solo.clickOnView(solo.getView(com.sportsoutclass.outclassdl.R.id.next_button));
        //Wait for activity: 'com.sportsoutclass.outclassdl.Scenario1'
		assertTrue("com.sportsoutclass.outclassdl.Scenario1 is not found!", solo.waitForActivity(com.sportsoutclass.outclassdl.Scenario1.class));
        //Click on ImageView
		solo.clickOnView(solo.getView(android.widget.ImageButton.class, 0));
        //Wait for activity: 'com.sportsoutclass.outclassdl.MainActivity'
		assertTrue("com.sportsoutclass.outclassdl.MainActivity is not found!", solo.waitForActivity(com.sportsoutclass.outclassdl.MainActivity.class));
	}
}
