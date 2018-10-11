package com.example.android.bakingtime;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingtime.utils.Recipe;

import java.util.List;

/**
 * Created by ayomide on 9/26/18.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {
    private Recipe [] allRecipes;

    private final Context mContent;

    private final RecipeAdapterOnClickListener mClickListener;

    public interface RecipeAdapterOnClickListener{
        void onClick(Recipe recipe);
    }

    public RecipeAdapter(@NonNull Context mContent, RecipeAdapterOnClickListener mClickListener) {
        this.mContent = mContent;
        this.mClickListener = mClickListener;
    }

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutID = R.layout.recipes_list;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutID, parent, false);
        return new RecipeAdapterViewHolder(view);
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView recipeTV;

        private RecipeAdapterViewHolder(View view){
            super(view);
            recipeTV = view.findViewById(R.id.recipeTV);
            recipeTV.setOnClickListener(this);
        }

        public void onClick(View v){
            int adapterPos = getAdapterPosition();
            mClickListener.onClick(allRecipes[adapterPos]);
        }
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder recipeHolder, int position) {
        recipeHolder.recipeTV.setText(allRecipes[position].getmName());
        recipeHolder.recipeTV.setBackgroundResource(R.color.colorAccent);
        recipeHolder.recipeTV.setId(position);

    }

    @Override
    public int getItemCount() {
        if(allRecipes == null)
            return 0;
        else
            return allRecipes.length;
    }

    public void setRecipeData(Recipe [] recipes){
        allRecipes = recipes;
        notifyDataSetChanged();
    }
}
