package com.example.android.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingtime.utils.BakingStep;
import com.example.android.bakingtime.utils.Ingredient;
import com.example.android.bakingtime.utils.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ayomide on 10/1/18.
 */
public class DetailsListFragment extends Fragment{

    private TextView ingredientTV;

    private RecyclerView stepsRecyclerView;
    private BakingStepAdapter bakingStepAdapter;

    private Recipe curentRecipe;
    private DetailsActivity parentActivity;

    private boolean mTwoPane;

    public DetailsListFragment(){ }

    public void setRecipeData(Recipe recipeData){
        curentRecipe = recipeData;
    }

    public void setIsTwoPane(boolean twoPane){
        mTwoPane = twoPane;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        parentActivity = (DetailsActivity) getActivity();
        parentActivity.setTitle(curentRecipe.getmName());
        final View rootView = inflater.inflate(R.layout.fragment_details_list, container, false);
        ingredientTV = rootView.findViewById(R.id.ingredientStr);
        ingredientTV.setBackgroundResource(R.color.colorAccent);
        ingredientTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTwoPane){
                    //if two pane, display all ingredients in second pane
                    IngredientFragment ingredientFragment = new IngredientFragment();
                    FragmentManager fragmentManager = parentActivity.getSupportFragmentManager();

                    ingredientFragment.setIngData(curentRecipe.getIngredients());

                    fragmentManager.beginTransaction()
                            .replace(R.id.details_container, ingredientFragment)
                            .commit();
                }
                else {
                    //else launch ingredient activity
                    Intent intent = new Intent(parentActivity, IngredientActivity.class);
                    ArrayList<Ingredient> arrayList = new ArrayList<Ingredient>(Arrays.asList(curentRecipe.getIngredients()));
                    intent.putParcelableArrayListExtra(IngredientActivity.INGREDIENT_EXTRA,
                            arrayList);

                    parentActivity.startActivity(intent);
                }
            }
        });

        //populate baking steps using recyclerview and adapter
        populateBakingSteps(curentRecipe.getSteps(), rootView);
        return rootView;
    }



    public void populateBakingSteps(BakingStep[] steps, View root){
        stepsRecyclerView = root.findViewById(R.id.recyclerview_steps);
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(parentActivity, 1);
        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setHasFixedSize(true);
        bakingStepAdapter = new BakingStepAdapter(parentActivity);
        stepsRecyclerView.setAdapter(bakingStepAdapter);
        bakingStepAdapter.setBakingStepsData(steps);
        bakingStepAdapter.setIsTwoPanes(mTwoPane);
        bakingStepAdapter.setParentActivity(parentActivity);

    }

}