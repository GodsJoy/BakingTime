package com.example.android.bakingtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.android.bakingtime.utils.Ingredient;

import java.util.ArrayList;

public class IngredientActivity extends AppCompatActivity {
    public static final String INGREDIENT_EXTRA = "ingredient_extra";

    private RecyclerView ingRecyclerView;
    private IngredientAdapter ingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.ingredients));
        Intent intent = getIntent();
        if(intent ==null){
            closeAndReportError();
            return;
        }

        ArrayList<Ingredient> ingredients = intent.getParcelableArrayListExtra(INGREDIENT_EXTRA);
        Ingredient [] ingredients1 = new Ingredient[ingredients.size()];
        for(int i = 0; i< ingredients.size(); i++)
            ingredients1[i] = ingredients.get(i);
        populateIng(ingredients1);
    }

    public void populateIng(Ingredient[] ingredients){
        ingRecyclerView = findViewById(R.id.recyclerview_ingredients);
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(this, 1);
        ingRecyclerView.setLayoutManager(layoutManager);
        ingRecyclerView.setHasFixedSize(true);
        ingAdapter = new IngredientAdapter(this);
        ingRecyclerView.setAdapter(ingAdapter);
        ingAdapter.setIngredientData(ingredients);
    }

    private void closeAndReportError(){
        finish();
        Toast.makeText(this, "Error encountered", Toast.LENGTH_SHORT);
    }
}
