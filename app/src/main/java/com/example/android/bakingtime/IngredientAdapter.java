package com.example.android.bakingtime;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingtime.utils.Ingredient;

import java.util.List;

/**
 * Created by ayomide on 9/28/18.
 */
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientAdaptorViewHolder> {
    private Ingredient [] allIngredients;

    private final Context mContext;

    public IngredientAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public IngredientAdaptorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutID = R.layout.ingredient;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutID, parent, false);
        return new IngredientAdaptorViewHolder(view);
    }

    public class IngredientAdaptorViewHolder extends RecyclerView.ViewHolder{
        private final TextView ingredientTV;

        private IngredientAdaptorViewHolder(View view){
            super(view);
            ingredientTV = view.findViewById(R.id.ingredientTV);
        }

    }

    @Override
    public void onBindViewHolder(IngredientAdaptorViewHolder ingHolder, int position) {
        Ingredient ing = allIngredients[position];
        String ingText = (position+1)+". "+ing.getnName()+" : " + ing.getmQty() + " " + ing.getmMeasure();
        ingHolder.ingredientTV.setText(ingText);
    }

    @Override
    public int getItemCount() {
        if(allIngredients == null)
            return 0;
        else
            return allIngredients.length;
    }

    public void setIngredientData(Ingredient [] ingredients){
        allIngredients = ingredients;
        Log.d("ingdata", allIngredients.length+" length");
        notifyDataSetChanged();
    }


}
