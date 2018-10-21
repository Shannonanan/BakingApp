package co.za.bakingapp.Features.GetAllRecipes;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.za.bakingapp.Data.Repository;
import co.za.bakingapp.Data.models.RecipeDatum;
import co.za.bakingapp.Features.RecipeSteps.RecipeStepsActivity;
import co.za.bakingapp.R;

public class GetAllRecipesFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private  GetAllRecipesAdapter getAllRecipesAdapter;
    private GetAllRecipesViewModel getAllRecipesViewModel;


    public GetAllRecipesFragment() {
        setRetainInstance(true); }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_recipes, container, false);

        setupViews(view);
        return view;
    }

    private void setupViews(View v) {
        mRecyclerView = v.findViewById(R.id.recyclerview_AllRecipes);
        LinearLayoutManager gridLayoutManager =
                new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        getAllRecipesAdapter= new GetAllRecipesAdapter(getContext());
        mRecyclerView.setAdapter(getAllRecipesAdapter);

        Repository repository = new Repository(getContext());

        GetAllRecipesViewModelFactory factory =
                new GetAllRecipesViewModelFactory(repository, getContext());



        getAllRecipesViewModel = ViewModelProviders.of(this, factory).get(GetAllRecipesViewModel.class);

        GetAllRecipesViewModel.SetCollection setOnItemClickListener = new GetAllRecipesViewModel.SetCollection() {
            @Override
            public void setInfoCollection(List<RecipeDatum> newList) {
                getAllRecipesAdapter.setInfoCollection(newList);
            }
        };

        getAllRecipesViewModel.setOnItemClickListener(setOnItemClickListener);

        GetAllRecipesAdapter.OnRecipeClicked recipeClicked = new GetAllRecipesAdapter.OnRecipeClicked() {
            @Override
            public void onRecipeClicked(RecipeDatum entity) {
               //pass to the next activity
                startActivity(RecipeStepsActivity.getCallingIntent(getContext(), entity));
            }
        };

        getAllRecipesAdapter.setOnItemClickListener(recipeClicked);
         getAllRecipesViewModel.doSomeWork();
    }

}
