package com.example.android.bakingtime;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.example.android.bakingtime.IdlingResource.SimpleIdlingResource;
import com.example.android.bakingtime.networking.RecipeServiceGenerator;
import com.example.android.bakingtime.networking.Service;
import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ayomide on 10/13/18.
 * Got idea from Tea Time lesson - Udacity
 */
public class RecipeDownloader {
    private static final int DELAY_MILLIS = 3000;

    static String recipeJsonString;

    interface DelayerCallback{
        void onCompleteDownload(String recipeString);
    }

    static void downloadRecipeString(Context context, final DelayerCallback callback,
                              @Nullable final SimpleIdlingResource idlingResource) {
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        getRecipeData();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onCompleteDownload(recipeJsonString);
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                }
            }
        }, DELAY_MILLIS);

    }

    private static void getRecipeData(){
        Service service = RecipeServiceGenerator.createRecipeService(Service.class);
        Call<JsonArray> call = service.fetchBakingData();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        recipeJsonString = response.body().toString();

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }


        });

    }


}
