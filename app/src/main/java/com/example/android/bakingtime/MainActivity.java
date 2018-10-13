package com.example.android.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingtime.IdlingResource.SimpleIdlingResource;
import com.example.android.bakingtime.networking.RecipeServiceGenerator;
import com.example.android.bakingtime.networking.Service;
import com.example.android.bakingtime.utils.CreateRecipeFromJSON;
import com.example.android.bakingtime.utils.Recipe;
import com.example.android.bakingtime.utils.RecipeString;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements RecipeAdapter.RecipeAdapterOnClickListener,
        RecipeDownloader.DelayerCallback{
    public static final int default_saved_recipe = -1;

    private Recipe [] allRecipe;

    @Nullable
    SimpleIdlingResource mIdlingResource;

    @BindView(R.id.recyclerview_recipe) RecyclerView recyclerView;
    private RecipeAdapter adapter;
    @BindView(R.id.errorTV) TextView errorText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create shared preference
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_preference),Context.MODE_PRIVATE);
        int saved_recipe = sharedPref.getInt(getString(R.string.recipe_pos), default_saved_recipe);

        if(saved_recipe == default_saved_recipe) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.shared_preference), 0);
            editor.commit();
        }

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
        getmIdlingResource();


        //getRecipeData();
        //allRecipe = CreateRecipeFromJSON.getEachRecipe(RecipeString.mRecipeString);
        //adapter.setRecipeData(allRecipe);
        /*if(allRecipe == null)
            showErrorMessage();
        else {

            showRecipeData();
        }*/
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getmIdlingResource() {
        if(mIdlingResource == null){
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onStart() {
        super.onStart();
        RecipeDownloader.downloadRecipeString(this, MainActivity.this, mIdlingResource);
    }

    @Override
    public void onCompleteDownload(String recipeString) {
        allRecipe = CreateRecipeFromJSON.getEachRecipe(recipeString);
        adapter.setRecipeData(allRecipe);
        if(allRecipe == null)
            showErrorMessage();
        else {

            showRecipeData();
        }
    }

    private void getRecipeData(){
        Recipe [] recipes1;
        Service service = RecipeServiceGenerator.createRecipeService(Service.class);
        Call<JsonArray> call = service.fetchBakingData();
        call.enqueue(new Callback<JsonArray>() {
            Recipe [] recipes;
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        String arrayString = response.body().toString();
                        /*allRecipe = CreateRecipeFromJSON.getEachRecipe(arrayString);
                        adapter.setRecipeData(allRecipe);
                        if(allRecipe == null)
                            showErrorMessage();
                        else {

                            showRecipeData();
                        }*/
                        recipes = CreateRecipeFromJSON.getEachRecipe(arrayString);
                        adapter.setRecipeData(recipes);
                        if(recipes == null)
                            showErrorMessage();
                        else {

                            showRecipeData();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }


        });

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
