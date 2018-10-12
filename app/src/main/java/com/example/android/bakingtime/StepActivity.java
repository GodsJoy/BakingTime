package com.example.android.bakingtime;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity {

    public static final String STEP_EXTRA_STEP = "step_extra_steps";
    public static final String STEP_EXTRA_POS = "step_extra_pos";
    public static final int DEFAULT_POS = -1;

    private final String PLAYER_POSITION = "player_position";
    private final String PLAYER_STATE = "player_state";
    private final String STEP_DATA = "step_data";
    private final long default_position = -1;
    private long playerPosition = default_position;
    private boolean playWhenReady = true;

    private SimpleExoPlayer mExoPlayer;

    @BindView(R.id.playerView) SimpleExoPlayerView playerView;
    @BindView(R.id.shortDescTV) TextView shortDescTV;
    @BindView(R.id.descTV) TextView descTV;
    @BindView(R.id.nextBTN) Button nextBTN;
    @BindView(R.id.prevBTN) Button prevBTN;
    private boolean hasVideo = false;
    private ArrayList<BakingStep> steps;
    private int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if(savedInstanceState != null){
            playerPosition = savedInstanceState.getLong(PLAYER_POSITION, default_position);
            playWhenReady = savedInstanceState.getBoolean(PLAYER_STATE);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent ==null){
            closeAndReportError();
            return;
        }

        /*In order to be able to move to a step video from another person
        * I passed all steps and position of current step through the intent that launched
        * this activity*/
        steps = intent.getParcelableArrayListExtra(STEP_EXTRA_STEP);
        currentPos = intent.getIntExtra(STEP_EXTRA_POS, DEFAULT_POS);
        if(steps == null || currentPos == DEFAULT_POS){
            closeAndReportError();
            return;
        }

        setTitle(getString(R.string.step)+(currentPos+1));
        BakingStep [] allSteps = new BakingStep[steps.size()];
        for (int i =0; i< steps.size();i++)
            allSteps[i] = steps.get(i);

        BakingStep thisStep = steps.get(currentPos);

        shortDescTV.setText(thisStep.getmShortDesc());
        descTV.setText(thisStep.getmDesc());


        //decide whether to hide or display next and previous buttons
        hideOrDisplayButtons();

        playerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.question_mark));
        String url = thisStep.getmVideoURL();
        if(url.length() >0){
            hasVideo = true;
            intializePlayer(Uri.parse(url));
        }
        else{
            url = thisStep.getmThumbnailURL();
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
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            playerView.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(this, "bakingtime");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            if(playerPosition != default_position)
                mExoPlayer.seekTo(playerPosition);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(playWhenReady);
        }
    }

    private void releasePlayer(){
        if(hasVideo && mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private void closeAndReportError(){
        finish();
        Toast.makeText(this, "Error encountered", Toast.LENGTH_SHORT);
    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        if(hasVideo)
            releasePlayer();
    }*/

    public void onClickNext(View view){
        Log.d("Next", "got to next");
        Intent intent = new Intent(this, StepActivity.class);
        intent.putParcelableArrayListExtra(StepActivity.STEP_EXTRA_STEP,
                steps);
        intent.putExtra(StepActivity.STEP_EXTRA_POS, currentPos+1);
        finish();
        startActivity(intent);
    }

    public void onClickPrev(View view){
        Log.d("Prev", "got to prev");
        Intent intent = new Intent(StepActivity.this, StepActivity.class);
        intent.putParcelableArrayListExtra(StepActivity.STEP_EXTRA_STEP,
                steps);
        intent.putExtra(StepActivity.STEP_EXTRA_POS, currentPos-1);
        finish();
        startActivity(intent);
    }

    public void hideOrDisplayButtons(){
        //hide or show next and prev buttons based on the current step.
        if(currentPos ==  steps.size()- 1){
            nextBTN.setVisibility(View.INVISIBLE);
            prevBTN.setVisibility(View.VISIBLE);
        }
        else if(currentPos == 0){
            prevBTN.setVisibility(View.INVISIBLE);
            nextBTN.setVisibility(View.VISIBLE);
        }
        else{
            nextBTN.setVisibility(View.VISIBLE);
            prevBTN.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(hasVideo && mExoPlayer != null) {
            playerPosition = mExoPlayer.getCurrentPosition();
            playWhenReady = mExoPlayer.getPlayWhenReady();
        }
        if(Util.SDK_INT <= 23){
            releasePlayer();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYER_POSITION, playerPosition);
        outState.putBoolean(PLAYER_STATE, playWhenReady);

    }
}
