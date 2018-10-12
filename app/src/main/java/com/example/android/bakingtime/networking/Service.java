package com.example.android.bakingtime.networking;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ayomide on 10/12/18.
 */
public interface Service {
    @GET(Route.END_POINT)
    Call<JsonArray> fetchBakingData();
}
