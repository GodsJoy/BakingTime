package com.example.android.bakingtime.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ayomide on 9/24/18.
 * A Recipe POJO
 */
public class Recipe implements Parcelable {
    private int mID;
    private String mName;
    private Ingredient [] mIngredients;
    private BakingStep [] mSteps;
    private String mServings;
    private String mImage;
    public static String idLabel = "id";
    public static String nameLabel = "name";
    public static String ingrLabel = "ingredients";
    public static String stepsLabel = "steps";
    public static String servLabel = "servings";
    public static String imageLabel = "image";

    public Recipe(int mID, String mName, Ingredient[] ingredients, BakingStep[] steps, String servings, String image) {
        this.mID = mID;
        this.mName = mName;
        this.mIngredients = ingredients;
        this.mSteps = steps;
        this.mServings = servings;
        this.mImage = image;
    }

    protected Recipe(Parcel in){
        this.mID = in.readInt();
        this.mName = in.readString();
        this.mIngredients = in.createTypedArray(Ingredient.CREATOR);
        //in.readTypedArray(this.mIngredients, Ingredient.CREATOR);
        this.mSteps = in.createTypedArray(BakingStep.CREATOR);
        //in.readTypedArray(this.mSteps, BakingStep.CREATOR);
        this.mServings = in.readString();
        this.mImage = in.readString();
    }

    /*private Recipe(Parcel in) {
        mData = in.readInt();
    }*/

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Ingredient[] getIngredients() {
        return mIngredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.mIngredients = ingredients;
    }

    public BakingStep[] getSteps() {
        return mSteps;
    }

    public void setSteps(BakingStep[] steps) {
        this.mSteps = steps;
    }

    public String getmServings() {
        return mServings;
    }

    public void setmServings(String mServings) {
        this.mServings = mServings;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mID);
        parcel.writeString(mName);
        parcel.writeTypedArray(mIngredients, 0);
        parcel.writeTypedArray(mSteps, 0);
        parcel.writeString(mServings);
        parcel.writeString(mImage);
    }

    public static final Parcelable.Creator<Recipe> CREATOR
            = new Parcelable.Creator<Recipe>() {
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };


}
