package com.example.android.bakingtime.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by ayomide on 9/24/18.
 */
public class CreateRecipeFromJSON {

    //converts each recipe JSON Object to an actual Recipe
    public static Recipe [] getEachRecipe(String JsonString){
        try{
            JSONArray recipeJson = new JSONArray(JsonString);
            //JSONArray re = new JSONArray(JsonString);

            Recipe [] allRecipes = new Recipe[recipeJson.length()];
            JSONObject recipeStr;
            for(int i=0; i< recipeJson.length(); i++){
                recipeStr = recipeJson.getJSONObject(i);
                allRecipes[i] = new Recipe(recipeStr.getInt(Recipe.idLabel),
                        recipeStr.getString(Recipe.nameLabel),
                        Ingredient.createIngredientFromJSON(recipeStr.getJSONArray(Recipe.ingrLabel)),
                        BakingStep.createStepsFromJSON(recipeStr.getJSONArray(Recipe.stepsLabel)),
                        recipeStr.getString(Recipe.servLabel),
                        recipeStr.getString(Recipe.imageLabel));

                //Log.d("EachRecipe", recipeStr.length()+" recipe");

            }
            Log.d("Result", allRecipes.length+"");
            return allRecipes;
        }
        catch (Exception e){
            Log.d("ResultError", "Error");
            return new Recipe[0];
        }
    }

}
