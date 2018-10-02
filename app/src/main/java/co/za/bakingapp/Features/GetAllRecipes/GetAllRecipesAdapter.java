package co.za.bakingapp.Features.GetAllRecipes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class GetAllRecipesAdapter extends RecyclerView.Adapter<GetAllRecipesAdapter.GetAllRecipesViewHolder> {


    @NonNull
    @Override
    public GetAllRecipesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllRecipesViewHolder getAllRecipesViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class GetAllRecipesViewHolder extends RecyclerView.ViewHolder{

        public GetAllRecipesViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
