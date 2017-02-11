package com.sportsoutclass.outclassdl;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ApplicationTest {

    @Rule
    public final ActivityTestRule<TeamSelection> mActivityRule = new ActivityTestRule<>(TeamSelection.class);

    @Test
    public void firstInningsInterruption1Test1() {
        onView(withText("First Innings")).check(matches(isDisplayed()));
        onView(withText("First Innings")).perform(click());
        onView(withId(R.id.first_innings_number_overs_edit_text)).perform(scrollTo(),clearText(), typeText("20"), closeSoftKeyboard());
        onView(withId(R.id.first_innings_which_over_interruption_1_et)).perform(scrollTo(),clearText(), typeText("12"), closeSoftKeyboard());
        onView(withId(R.id.first_innings_total_interruption_1_et)).perform(scrollTo(),clearText(), typeText("85"), closeSoftKeyboard());
        onView(withId(R.id.first_innings_wickets_lost_interruption_1_et)).perform(scrollTo(),clearText(), typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.first_innings_overs_remaining_interruption_1_et)).perform(scrollTo(),clearText(), typeText("3"),closeSoftKeyboard());
        onView(withId(R.id.first_innings_team1_final_total_et)).perform(clearText(), typeText("120"), closeSoftKeyboard());
        onView(withId(R.id.first_innings_team2_overs_et)).perform(clearText(), typeText("15"), closeSoftKeyboard());
        onView(withId(R.id.first_innings_calc_button)).perform(click());
        onView(withText("Target")).check(matches(isDisplayed()));
//        onView(withText("Target")).inRoot(isDialog()).check(matches(withText("Team 2 needs 128 runs to win.")));
        pauseTestFor(500);

    }

    @Test
    public void checkAllFragmentFunctionality() {
        onView(withText("First Innings")).check(matches(isDisplayed()));
        onView(withText("Second Innings")).check(matches(isDisplayed()));
        onView(withText("First Innings")).perform(click());
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withText("Second Innings")).perform(click());
        onView(withContentDescription("Navigate up")).perform(click());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("About")).perform(click());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Instructions")).perform(click());
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withContentDescription("Navigate up")).perform(click()); //main page

    }

    private void pauseTestFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
