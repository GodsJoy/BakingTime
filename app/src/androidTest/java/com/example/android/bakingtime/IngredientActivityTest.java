package com.example.android.bakingtime;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by ayomide on 10/12/18.
 */
@RunWith(AndroidJUnit4.class)
public class IngredientActivityTest {
    public static final String INGREDIENT_STR = "Ingredients";
    public static final String ALL_INGREDIENTS = "List of all ingredients";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    //@Rule
    //public ActivityTestRule<DetailsActivity> mActivityTestRules2 =
            //new ActivityTestRule<>(DetailsActivity.class);

    @Test
    public void clickIngredient_showListOfIngredients(){

        //Got idea from here : https://guides.codepath.com/android/UI-Testing-with-Espresso
        onView(withId(R.id.recyclerview_recipe)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.ingredientStr)).check(matches(withText(INGREDIENT_STR)));

        onView(withId(R.id.ingredientStr)).perform(click());

        onView(withId(R.id.all_ingredients)).check(matches(withText(ALL_INGREDIENTS)));

    }
}
