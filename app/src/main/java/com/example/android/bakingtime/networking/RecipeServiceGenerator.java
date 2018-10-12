package com.example.android.bakingtime.networking;

import com.example.android.bakingtime.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ayomide on 10/11/18.
 */
public class RecipeServiceGenerator {
    public static <S> S createRecipeService(Class<S> recipeServiceClass){
        String token = "";
        Gson gson =new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setDateFormat(DateFormat.LONG)
                .setPrettyPrinting()
                .setVersion(1.0)
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(
                        GsonConverterFactory.create(gson))
                .baseUrl(Route.BASE_URL);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .readTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .cache(null);

        /*if(BuildConfig.DEBUG){
            HttpLoggingInterceptor
        }*/

        builder.client(httpClientBuilder.build());
        Retrofit retrofit = builder.build();
        return retrofit.create(recipeServiceClass);
    }
}
