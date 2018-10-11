package com.example.android.bakingtime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingtime.utils.Ingredient;

/**
 * Created by ayomide on 10/2/18.
 */
public class IngredientFragment extends Fragment {
    private RecyclerView ingRecyclerView;
    private IngredientAdapter ingAdapter;
    private Ingredient [] ingredients;

    public IngredientFragment(){}

    public void setIngData(Ingredient [] ingData){
        ingredients = ingData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("IngFrag", "Got here");
        final View rootView = inflater.inflate(R.layout.activity_ingredient, container, false);
        DetailsActivity parentActivity = (DetailsActivity) getActivity();
        populateIng(ingredients, rootView, parentActivity);
        return rootView;
    }

    public void populateIng(Ingredient[] ingredients, View view, DetailsActivity activity){
        ingRecyclerView = view.findViewById(R.id.recyclerview_ingredients);
        Log.d("RecyclerView null", ""+(ingRecyclerView == null)+(view == null));
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(activity, 1);
        ingRecyclerView.setLayoutManager(layoutManager);
        ingRecyclerView.setHasFixedSize(true);
        ingAdapter = new IngredientAdapter(activity);
        ingRecyclerView.setAdapter(ingAdapter);
        ingAdapter.setIngredientData(ingredients);
    }
}
