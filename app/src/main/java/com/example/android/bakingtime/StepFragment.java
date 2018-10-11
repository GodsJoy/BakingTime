package com.example.android.bakingtime;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingtime.utils.BakingStep;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by ayomide on 10/2/18.
 */
public class StepFragment extends Fragment {

    private BakingStep mStep;
    private DetailsActivity mParentActivity;
    private SimpleExoPlayerView playerView;
    private SimpleExoPlayer mExoPlayer;

    private TextView shortDescTV;
    private TextView descTV;
    private Button nextBTN;
    private Button prevBTN;
    private boolean hasVideo = false;

    public StepFragment(){}

    public void setStepData(BakingStep stepData){
        mStep = stepData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("stepFrag", "Got here");
        final View rootView = inflater.inflate(R.layout.activity_step, container, false);

        shortDescTV = rootView.findViewById(R.id.shortDescTV);
        descTV = rootView.findViewById(R.id.descTV);
        nextBTN = rootView.findViewById(R.id.nextBTN);
        prevBTN = rootView.findViewById(R.id.prevBTN);
        playerView = rootView.findViewById(R.id.playerView);
        mParentActivity = (DetailsActivity) getActivity();
        populateView();
        return rootView;

    }

    private void populateView(){
        shortDescTV.setText(mStep.getmShortDesc());
        descTV.setText(mStep.getmDesc());


        playerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.question_mark));
        String url = mStep.getmVideoURL();
        if(url.length() >0){
            hasVideo = true;
            intializePlayer(Uri.parse(url));
        }
        else{
            url = mStep.getmThumbnailURL();
            if(url.length() > 0){
                hasVideo = true;
                intializePlayer(Uri.parse(url));
            }
        }
    }

    private void intializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(mParentActivity, trackSelector, loadControl);
            playerView.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(mParentActivity, "bakingtime");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    mParentActivity, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer(){
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(hasVideo)
            releasePlayer();
    }

}
