package com.example.android.bakingtime;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android.bakingtime.utils.BakingStep;
import com.example.android.bakingtime.utils.Ingredient;
import com.example.android.bakingtime.utils.Recipe;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

public class DetailsActivity extends AppCompatActivity{

    public static final String RECIPE_EXTRA = "recipe_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        //close app if no Recipe intent was passed
        if(intent == null) {
            closeAndReportError();
            return;
        }

        //get recipe as a parcelableExtra
        Recipe thisRecipe = intent.getParcelableExtra(RECIPE_EXTRA);

        if(thisRecipe == null){
            closeAndReportError();
            return;
        }

        boolean twoPane = false;

        //set twoPane to true if linear_layout is not null
        if(findViewById(R.id.linear_layout) != null) {
            twoPane = true;
            Log.d("IStwopane", "istwopane");
        }
        else{
            Log.d("IStwopane", "false istwopane");
        }


        DetailsListFragment detailsListFragment = new DetailsListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        //pass recipe to Fragment
        detailsListFragment.setRecipeData(thisRecipe);
        detailsListFragment.setIsTwoPane(twoPane);

        fragmentManager.beginTransaction()
                .add(R.id.list_container, detailsListFragment)
                .commit();


    }

    private void closeAndReportError(){
        finish();
        Toast.makeText(this, "Error encountered", Toast.LENGTH_SHORT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
