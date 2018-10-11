package com.example.android.bakingtime.utils;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ayomide on 9/24/18.
 * A BakingStep POJO. Describes each baking step
 */

public class BakingStep implements Parcelable {
    private int mID;
    private String mShortDesc;
    private String mDesc;
    private String mVideoURL;
    private String mThumbnailURL;
    private static String idLabel = "id";
    private static String shortDescLabel = "shortDescription";
    private static String descLabel = "description";
    private static String videoLabel = "videoURL";
    private static String thumbnailLabel = "thumbnailURL";

    public BakingStep(int mID, String mShortDesc, String mDesc, String mVideoURL, String mThumbnailURL) {
        this.mID = mID;
        this.mShortDesc = mShortDesc;
        this.mDesc = mDesc;
        this.mVideoURL = mVideoURL;
        this.mThumbnailURL = mThumbnailURL;
    }

    private BakingStep(Parcel in){
        this.mID = in.readInt();
        this.mShortDesc = in.readString();
        this.mDesc = in.readString();
        this.mVideoURL = in.readString();
        this.mThumbnailURL = in.readString();
    }

    public static final Parcelable.Creator<BakingStep> CREATOR
            = new Parcelable.Creator<BakingStep>() {
        public BakingStep createFromParcel(Parcel in) {
            return new BakingStep(in);
        }

        public BakingStep[] newArray(int size) {
            return new BakingStep[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mID);
        parcel.writeString(mShortDesc);
        parcel.writeString(mDesc);
        parcel.writeString(mVideoURL);
        parcel.writeString(mThumbnailURL);
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmShortDesc() {
        return mShortDesc;
    }

    public void setmShortDesc(String mShortDesc) {
        this.mShortDesc = mShortDesc;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getmVideoURL() {
        return mVideoURL;
    }

    public void setmVideoURL(String mVideoURL) {
        this.mVideoURL = mVideoURL;
    }

    public String getmThumbnailURL() {
        return mThumbnailURL;
    }

    public void setmThumbnailURL(String mThumbnailURL) {
        this.mThumbnailURL = mThumbnailURL;
    }

    public static BakingStep[] createStepsFromJSON(JSONArray steps){
        BakingStep[] allSteps = new BakingStep[steps.length()];
        try{
            for(int i=0;i<steps.length();i++){

                JSONObject aStep = steps.getJSONObject(i);
                allSteps[i] = new BakingStep(aStep.getInt(idLabel), aStep.getString(shortDescLabel),
                        aStep.getString(descLabel), aStep.getString(videoLabel),
                        aStep.getString(thumbnailLabel));
            }
            return allSteps;
        }
        catch (Exception e){
            return new BakingStep[0];
        }

    }
}
