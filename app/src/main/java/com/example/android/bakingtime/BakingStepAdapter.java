package com.example.android.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingtime.utils.BakingStep;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ayomide on 9/28/18.
 */
public class BakingStepAdapter extends RecyclerView.Adapter<BakingStepAdapter.BakingStepAdapterViewHolder> {
    private BakingStep[] allSteps;

    private final Context mContext;

    private boolean mTwoPanes;

    //parent activity. Needed to create a fragment manager to display fragment in parent activity
    private DetailsActivity mParentActivity;

    public BakingStepAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public BakingStepAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutID = R.layout.steps;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutID, parent, false);
        return new BakingStepAdapterViewHolder(view, mContext);
    }

    public class BakingStepAdapterViewHolder extends RecyclerView.ViewHolder {
        private final TextView stepName; //Baking step name. e.g Step:1

        public BakingStepAdapterViewHolder(View view, Context context) {
            super(view);
            stepName = view.findViewById(R.id.stepTextTV);
        }


    }

    @Override
    public void onBindViewHolder(BakingStepAdapterViewHolder holder, final int position) {
        holder.stepName.setText(mContext.getString(R.string.step)+(position+1));
        holder.stepName.setBackgroundResource(R.color.colorAccent);

        //set the onclick listener for each baking step
        holder.stepName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if two pane, create the two fragments side by side
                if(mTwoPanes){
                    StepFragment stepFragment = new StepFragment();
                    FragmentManager fragmentManager = mParentActivity.getSupportFragmentManager();

                    stepFragment.setStepData(allSteps[position]);

                    fragmentManager.beginTransaction()
                            .replace(R.id.details_container, stepFragment)
                            .commit();
                }
                else {
                    //it's a single pane, so launch the steps activity
                    Intent intent = new Intent(mContext, StepActivity.class);
                    ArrayList<BakingStep> arrayList = new ArrayList<BakingStep>(Arrays.asList(allSteps));
                    intent.putParcelableArrayListExtra(StepActivity.STEP_EXTRA_STEP,
                            arrayList);
                    intent.putExtra(StepActivity.STEP_EXTRA_POS, position);

                    mContext.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        if(allSteps == null)
            return 0;
        else
            return allSteps.length;
    }

    public void setBakingStepsData(BakingStep [] stepsData){
        allSteps = stepsData;
        notifyDataSetChanged();
    }

    public void setIsTwoPanes(boolean twoPanes){
        mTwoPanes = twoPanes;
    }

    public void setParentActivity(DetailsActivity activity){
        mParentActivity = activity;
    }

}
