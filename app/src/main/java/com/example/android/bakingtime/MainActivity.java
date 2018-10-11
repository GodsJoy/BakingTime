package com.example.android.bakingtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingtime.utils.CreateRecipeFromJSON;
import com.example.android.bakingtime.utils.Recipe;
import com.example.android.bakingtime.utils.RecipeString;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickListener {
    private Recipe [] allRecipe;

    @BindView(R.id.recyclerview_recipe) RecyclerView recyclerView;
    private RecipeAdapter adapter;
    @BindView(R.id.errorTV) TextView errorText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager;
        if(findViewById(R.id.layout_600_dp) != null)
            layoutManager =
                new GridLayoutManager(this, 3);
        else
            layoutManager =
                    new GridLayoutManager(this, 1);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        adapter = new RecipeAdapter(this, this);
        recyclerView.setAdapter(adapter);

        allRecipe = CreateRecipeFromJSON.getEachRecipe(RecipeString.mRecipeString);
        adapter.setRecipeData(allRecipe);
        if(allRecipe == null)
            showErrorMessage();
        else {

            showRecipeData();
        }


        Log.d("Test", "test");
    }

    //Launch DetailsActivity when recipe is clicked
    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.RECIPE_EXTRA, recipe);
        startActivity(intent);
    }

    private void showRecipeData(){
        errorText.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(){
        errorText.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }
}
