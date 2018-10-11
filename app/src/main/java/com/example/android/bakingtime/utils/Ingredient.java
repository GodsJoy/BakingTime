package com.example.android.bakingtime.utils;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ayomide on 9/24/18.
 * An Ingredient POJO
 */
public class Ingredient implements Parcelable {
    private int mQty;
    private String mMeasure;
    private String nName;
    private static String qtyLabel = "quantity";
    private static String measureLabel = "measure";
    private static String nameLabel = "ingredient";

    public Ingredient(int mQty, String mMeasure, String nName) {
        this.mQty = mQty;
        this.mMeasure = mMeasure;
        this.nName = nName;
    }

    private Ingredient(Parcel in){
        this.mQty = in.readInt();
        this.mMeasure = in.readString();
        this.nName = in.readString();
    }

    public static final Parcelable.Creator<Ingredient> CREATOR
            = new Parcelable.Creator<Ingredient>() {
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public int getmQty() {
        return mQty;
    }

    public void setmQty(int mQty) {
        this.mQty = mQty;
    }

    public String getmMeasure() {
        return mMeasure;
    }

    public void setmMeasure(String mMeasure) {
        this.mMeasure = mMeasure;
    }

    public String getnName() {
        return nName;
    }

    public void setnName(String nName) {
        this.nName = nName;
    }

    public static Ingredient [] createIngredientFromJSON(JSONArray ingr){
        Ingredient [] allIng = new Ingredient[ingr.length()];
        try {
            for(int i=0; i<ingr.length();i++){

                JSONObject anIngr = ingr.getJSONObject(i);
                allIng[i] = new Ingredient(anIngr.getInt(qtyLabel), anIngr.getString(measureLabel),
                        anIngr.getString(nameLabel));
            }
            return allIng;
        }
        catch (Exception e){
            return new Ingredient[0];
        }

    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mQty);
        parcel.writeString(mMeasure);
        parcel.writeString(nName);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
