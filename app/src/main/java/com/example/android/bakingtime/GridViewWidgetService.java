package com.example.android.bakingtime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingtime.networking.RecipeServiceGenerator;
import com.example.android.bakingtime.networking.Service;
import com.example.android.bakingtime.utils.CreateRecipeFromJSON;
import com.example.android.bakingtime.utils.Ingredient;
import com.example.android.bakingtime.utils.Recipe;
import com.google.gson.JsonArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ayomide on 10/13/18.
 */
public class GridViewWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        String recipeString =
                intent.getStringExtra(BakingTimeWidgetProvider.WIDGET_RECIPE_EXTRA);
        int position = intent.getIntExtra(BakingTimeWidgetProvider.WIDGET_POSITION_EXTRA, -1);
        //Recipe recipe = intent.getParcelableExtra(BakingTimeWidgetProvider.WIDGET_RECIPE_EXTRA);
        Log.d("GridService", "values of "+(recipeString == null)+position);
        //return new GridRemoteViewFactory(this.getApplicationContext(), recipe.getIngredients());
        return new GridRemoteViewFactory(this.getApplicationContext(), recipeString, position);
    }
}

class GridRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory{
    private Context mContext;
    private Ingredient [] mIngredients;
    private Recipe recipe;

    public GridRemoteViewFactory(Context context, String recipeString, int position){
        mContext = context;
        //mPosition = position;
        recipe = CreateRecipeFromJSON.getEachRecipe(recipeString)[position];
        mIngredients = recipe.getIngredients();
        //getRecipeData();
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        //values = getValues();
        //getRecipeData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mIngredients == null)
            return 0;
        return mIngredients.length;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ing = mIngredients[position];
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient);
        String ingText = (position+1)+". "+ing.getnName()+" : " + ing.getmQty() + " " + ing.getmMeasure();
        views.setTextViewText(R.id.ingredientTV, ingText);

        Bundle extras = new Bundle();
        extras.putParcelable(DetailsActivity.RECIPE_EXTRA, recipe);
        //extras.putLong(PlantDetailActivity.EXTRA_PLANT_ID, id);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.ingredientTV, fillInIntent);
        //Log.d("getViewsAt", "in getvies");
        return views;

    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private String [] getValues(){
        String s [] = {"1", "2", "3"};
        return s;
    }
}
