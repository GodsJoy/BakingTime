package com.example.android.bakingtime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;


/**
 * Implementation of App Widget functionality.
 */
public class BakingTimeWidgetProvider extends AppWidgetProvider {

    public static final String WIDGET_RECIPE_EXTRA = "widget_recipe";
    public static final String WIDGET_POSITION_EXTRA = "widget_position";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.baking_time_widget_provider);

        SharedPreferences sharedPreferences = context.getSharedPreferences
                (context.getString(R.string.shared_preference), Context.MODE_PRIVATE);
        int saved_recipe = sharedPreferences
                .getInt(context.getString(R.string.recipe_pos), MainActivity.default_saved_recipe);
        Log.d("saved pos", saved_recipe+" saved");
        if(saved_recipe == MainActivity.default_saved_recipe){
            //RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.ingredient);
            //Launch MainActivity when the app is launched for the first time, since shared preference
            //is not yet set.
            views.setViewVisibility(R.id.recipe_widget_grid_view, View.GONE);
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            //views.setTextViewText(R.id.ingredientTV, "Click for recipe");
            views.setOnClickPendingIntent(R.id.cake_image, pendingIntent);
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        else{
            views.setViewVisibility(R.id.cake_image, View.GONE);

            //Log.d("ingsize", set2.size()+"ing size");
            //Log.d("ing pos", saved_recipe+" ing size");
            Intent intent = new Intent(context, GridViewWidgetService.class);

            //intent.putExtra(WIDGET_RECIPE_EXTRA, saved_recipe);
            //intent.putExtra(WIDGET_RECIPE_EXTRA, allRecipe[saved_recipe]);
            //intent.putExtra(WIDGET_RECIPE_EXTRA, arrayString);
            //intent.putExtra(WIDGET_POSITION_EXTRA, saved_recipe);
            views.setRemoteAdapter(R.id.recipe_widget_grid_view, intent);

            //Log.d("WidgetProvide", saved_recipe+" saved pos");
            Intent intent2 = new Intent(context, DetailsActivity.class);
            //intent2.putExtra(DetailsActivity.RECIPE_EXTRA, allRecipe[saved_recipe]);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.recipe_widget_grid_view, pendingIntent);
            views.setEmptyView(R.id.recipe_widget_grid_view, R.id.cake_image);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);

            //getRecipeData(context, appWidgetManager, appWidgetId, views, saved_recipe);
            /*intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(DetailsActivity.RECIPE_EXTRA, allRecipe[saved_recipe]);*/
        }

        //intent = new Intent(context, MainActivity.class);

        /*PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.cake_image, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);*/
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        FavoriteRecipeService.startActionUpdateRecipeWidget(context);
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

