package com.example.android.watchlisterapp;

import android.support.design.internal.NavigationMenuItemView;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.fredrikstahl.watchlisterapp.R;
import com.fredrikstahl.watchlisterapp.StartActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by fredrikstahl on 14/11/16.
 */
@RunWith(AndroidJUnit4.class)
public class StartActivityTests {
    @Rule
    public ActivityTestRule<StartActivity> mActivityRule = new ActivityTestRule<StartActivity>(StartActivity.class);


    @Test
    public void testNavigationDrawer() {
        // Check that content layout is displayed
        Espresso.onView(withId(R.id.content_frame)).check(matches(isDisplayed()));

        // Make sure that navigationview is displayed when clicking on home button (burger icon)
        Espresso.onView(withContentDescription("Watchlister")).perform(click());
        Espresso.onView(withId(R.id.navigation_view)).check(matches(isDisplayed()));
        Espresso.onView(withText("Movies1")).perform(click());
    }

    @Test
    public void testGridView() {

    }

    @Test
    public void testSomething() {
        //Espresso.onView(withId(R.id.drawer_layout)).perform(ViewActions.nav());

        //Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        //Espresso.onView(withContentDescription("Watchlister")).perform(click());


        //Espresso.onView(withId(R.id.navigation_view)).check(matches(isDisplayed()));

        //Espresso.isD(withId(R.id.navigation_header_container)).check(isDisplayed());
    }
}
