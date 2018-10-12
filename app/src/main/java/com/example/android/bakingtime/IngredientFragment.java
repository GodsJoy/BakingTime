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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ayomide on 10/2/18.
 */
public class IngredientFragment extends Fragment {
    private final String ING_DATA = "ing_data";
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
        if(savedInstanceState != null) {
            ArrayList<Ingredient> ingList = savedInstanceState.getParcelableArrayList(ING_DATA);
            ingredients = new Ingredient[ingList.size()];
            for(int i=0;i<ingList.size();i++)
                ingredients[i] = ingList.get(i);

        }
        final View rootView = inflater.inflate(R.layout.activity_ingredient, container, false);
        DetailsActivity parentActivity = (DetailsActivity) getActivity();
        populateIng(rootView, parentActivity);
        return rootView;
    }

    public void populateIng(View view, DetailsActivity activity){
        ingRecyclerView = view.findViewById(R.id.recyclerview_ingredients);
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(activity, 1);
        ingRecyclerView.setLayoutManager(layoutManager);
        ingRecyclerView.setHasFixedSize(true);
        ingAdapter = new IngredientAdapter(activity);
        ingAdapter.setIngredientData(ingredients);
        ingRecyclerView.setAdapter(ingAdapter);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelableArray(ING_DATA, ingredients);
        ArrayList<Ingredient> ingList = new ArrayList<>(Arrays.asList(ingredients));
        outState.putParcelableArrayList(ING_DATA, ingList);
    }
}
