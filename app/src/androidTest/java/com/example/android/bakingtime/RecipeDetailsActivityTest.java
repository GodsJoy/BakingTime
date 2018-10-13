package com.example.android.bakingtime;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * Created by ayomide on 10/10/18.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityTest {
    private static final String INGREDIENT_STR = "Ingredients";

    private IdlingResource mIdlingResource;

    @Rule public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource(){
        mIdlingResource = mActivityTestRule.getActivity().getmIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void clickMenu_showDetails(){

        //Got idea from here : https://guides.codepath.com/android/UI-Testing-with-Espresso
        onView(withId(R.id.recyclerview_recipe)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.ingredientStr)).check(matches(withText(INGREDIENT_STR)));

    }

    @After
    public void unRegisteridlingResource(){
        if(mIdlingResource != null){
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
