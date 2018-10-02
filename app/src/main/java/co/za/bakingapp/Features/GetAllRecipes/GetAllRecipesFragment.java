package co.za.bakingapp.Features.GetAllRecipes;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.za.bakingapp.Data.Repository;
import co.za.bakingapp.R;

public class GetAllRecipesFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private  GetAllRecipesAdapter getAllRecipesAdapter;
    private GetAllRecipesViewModel getAllRecipesViewModel;
    public GetAllRecipesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_recipes, container);
        setupViews(view);
        return view;
    }

    private void setupViews(View v) {
        mRecyclerView = v.findViewById(R.id.recyclerview_AllRecipes);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(getContext(),2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        getAllRecipesAdapter= new GetAllRecipesAdapter();
        mRecyclerView.setAdapter(getAllRecipesAdapter);

        Repository repository = new Repository();

        GetAllRecipesViewModelFactory factory =
                new GetAllRecipesViewModelFactory(repository);

        getAllRecipesViewModel = ViewModelProviders.of(this, factory).get(GetAllRecipesViewModel.class);
        getAllRecipesViewModel.getRecipes();
    }

}
