package com.example.android.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * Created by ayomide on 10/13/18.
 */
public class GridViewWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new GridRemoteViewFactory(this.getApplicationContext());
    }
}

class GridRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory{
    private Context mContext;
    private String [] ingredients;

    public GridRemoteViewFactory(Context context){
        mContext = context;
        /*int i = 0;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences
                (mContext.getString(R.string.shared_preference), Context.MODE_PRIVATE);

        Set<String> set1 = new HashSet<>(Arrays.asList(new String [0]));
        Set<String> set2 = sharedPreferences
                .getStringSet(mContext.getString(R.string.selected_ingredients), set1);
        //int position = intent.getIntExtra(BakingTimeWidgetProvider.WIDGET_POSITION_EXTRA, -1);
        ingredients = new String[set2.size()];
        for(String s:set2){
            ingredients[i] = s;
            i++;
        }*/

        //mPosition = position;
        //recipe = CreateRecipeFromJSON.getEachRecipe(recipeString)[position];
        //mIngredients = recipe.getIngredients();
        //getRecipeData();
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        int i = 0;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences
                (mContext.getString(R.string.shared_preference), Context.MODE_PRIVATE);

        //Set<String> set1 = new HashSet<>(Arrays.asList(new String [0]));
        String ingString = sharedPreferences
                .getString(mContext.getString(R.string.selected_ingredients), "");
        //int position = intent.getIntExtra(BakingTimeWidgetProvider.WIDGET_POSITION_EXTRA, -1);
        ingredients = ingString.split("////");
        //Log.d("Ondatachanged", ingredients.length+" leng");
        /*for(String s:set2){
            ingredients[i] = s;
            i++;
        }*/
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(ingredients == null)
            return 0;
        return ingredients.length;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        //Ingredient ing = mIngredients[position];
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient);
        String ingText = (position+1)+". "+ingredients[position];
        views.setTextViewText(R.id.ingredientTV, ingText);

        /*Bundle extras = new Bundle();
        extras.putParcelable(DetailsActivity.RECIPE_EXTRA, recipe);
        //extras.putLong(PlantDetailActivity.EXTRA_PLANT_ID, id);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.ingredientTV, fillInIntent);
        //Log.d("getViewsAt", "in getvies");*/
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
