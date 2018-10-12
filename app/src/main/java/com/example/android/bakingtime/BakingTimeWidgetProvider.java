package com.example.android.bakingtime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakingtime.networking.RecipeServiceGenerator;
import com.example.android.bakingtime.networking.Service;
import com.example.android.bakingtime.utils.CreateRecipeFromJSON;
import com.example.android.bakingtime.utils.Ingredient;
import com.example.android.bakingtime.utils.Recipe;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of App Widget functionality.
 */
public class BakingTimeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_time_widget_provider);

        SharedPreferences sharedPreferences = context.getSharedPreferences
                (context.getString(R.string.shared_preference), Context.MODE_PRIVATE);
        int saved_recipe = sharedPreferences
                .getInt(context.getString(R.string.recipe_pos), MainActivity.default_saved_recipe);
        if(saved_recipe == MainActivity.default_saved_recipe){
            //Launch MainActivity when the app is launched for the first time, since shared preference
            //is not yet set.
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.cake_image, pendingIntent);
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        else{
            Log.d("Service", "saved:"+saved_recipe);
            getRecipeData(context, appWidgetManager, appWidgetId, views, saved_recipe);
            /*intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(DetailsActivity.RECIPE_EXTRA, allRecipe[saved_recipe]);*/
        }

        //intent = new Intent(context, MainActivity.class);

        /*PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.cake_image, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);*/
    }

    private static void getRecipeData(final Context context, final AppWidgetManager appWidgetManager,
                                      final int appWidgetId, final RemoteViews views,
                                      final int saved_recipe){
        Service service = RecipeServiceGenerator.createRecipeService(Service.class);
        Call<JsonArray> call = service.fetchBakingData();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        String arrayString = response.body().toString();
                        Log.d("NetworkData", arrayString);
                        Recipe [] allRecipe = CreateRecipeFromJSON.getEachRecipe(arrayString);
                        Intent intent = new Intent(context, DetailsActivity.class);
                        intent.putExtra(DetailsActivity.RECIPE_EXTRA, allRecipe[saved_recipe]);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                        views.setOnClickPendingIntent(R.id.cake_image, pendingIntent);
                        // Instruct the widget manager to update the widget
                        appWidgetManager.updateAppWidget(appWidgetId, views);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }

        });

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        //FavoriteRecipeService.startActionUpdateRecipeWidget(context);
        //updateRecipeWidgets(context, appWidgetManager, appWidgetIds);
    }

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

