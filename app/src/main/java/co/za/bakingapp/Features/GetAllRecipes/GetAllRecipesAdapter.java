package co.za.bakingapp.Features.GetAllRecipes;

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
import co.za.bakingapp.R;

public class GetAllRecipesAdapter extends RecyclerView.Adapter<GetAllRecipesAdapter.GetAllRecipesViewHolder> {


    List<RecipeDatum> getRecipesCollection;
    private Context mContext;
    public GetAllRecipesAdapter.OnRecipeClicked onItemClickListener;

    public GetAllRecipesAdapter(Context mContext) {
        this.getRecipesCollection = Collections.emptyList();
        this.mContext = mContext;
    }

    public interface OnRecipeClicked {
        void onRecipeClicked(RecipeDatum entity);
    }

    @NonNull
    @Override
    public GetAllRecipesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.cell_get_all_recipes;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        GetAllRecipesViewHolder viewHolder = new GetAllRecipesViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllRecipesViewHolder getAllRecipesViewHolder, int i) {
        final RecipeDatum recipeDatum = this.getRecipesCollection.get(i);

        getAllRecipesViewHolder.tv_recipe_title.setText(recipeDatum.getName());

        getAllRecipesViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) onItemClickListener.onRecipeClicked(recipeDatum);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (this.getRecipesCollection != null) ? this.getRecipesCollection.size() : 0;
    }


    static class GetAllRecipesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_recipe_title)
        TextView tv_recipe_title;

        public GetAllRecipesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void setInfoCollection(List<RecipeDatum> INFO) {
        this.validateCollection(INFO);
        this.getRecipesCollection = INFO;
        this.notifyDataSetChanged();
    }


    private void validateCollection(Collection<RecipeDatum> infoCollection) {
        if (infoCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public void setOnItemClickListener(GetAllRecipesAdapter.OnRecipeClicked onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
