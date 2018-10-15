package com.example.android.bakingtime;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ayomide on 10/12/18.
 */
public class FavoriteRecipeService extends IntentService {

    public static final String ACTION_UPDATE_WIDGET = "update_widget";

    public FavoriteRecipeService(){
        super("FavoriteRecipeService");
    }

    public static void startActionUpdateRecipeWidget(Context context) {
        Intent intent = new Intent(context, FavoriteRecipeService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("Check broadcast2","got to onHandle");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingTimeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.recipe_widget_grid_view);

        BakingTimeWidgetProvider.updateRecipeWidgets(this, appWidgetManager, appWidgetIds);

    }
}
