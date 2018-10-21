package co.za.bakingapp.Features.RecipeSteps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.za.bakingapp.Data.models.RecipeDatum;
import co.za.bakingapp.Data.models.Step;
import co.za.bakingapp.R;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsAdapterViewHolder> {

    private List<Step> getStepsCollection;
    private Context context;
    public RecipeStepsAdapter.onStepClickedListener onStepClicked;

    public RecipeStepsAdapter(Context context) {
        getStepsCollection = Collections.emptyList();
        this.context = context;
    }


    public interface onStepClickedListener{
        void onStepClicked(Step step);
    }

    @NonNull
    @Override
    public RecipeStepsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.cell_recipe_steps;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        RecipeStepsAdapterViewHolder viewHolder = new RecipeStepsAdapterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsAdapterViewHolder holder, int i) {
        final Step step = getStepsCollection.get(i);

        holder.stepTitle.setText(step.getShortDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStepClicked.onStepClicked(step);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (this.getStepsCollection != null) ? getStepsCollection.size(): 0;
    }

    static class RecipeStepsAdapterViewHolder extends RecyclerView.ViewHolder{

            @BindView(R.id.tv_step_title) TextView stepTitle;
        public RecipeStepsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void setInfoCollection(List<Step> INFO) {
        this.validateCollection(INFO);
        this.getStepsCollection = INFO;
        this.notifyDataSetChanged();
    }


    private void validateCollection(Collection<Step> infoCollection) {
        if (infoCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public void setClickListener(RecipeStepsAdapter.onStepClickedListener listener){
        this.onStepClicked = listener;
    }
}
