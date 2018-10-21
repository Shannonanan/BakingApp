package co.za.bakingapp.Features.RecipeSteps;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.za.bakingapp.Data.models.RecipeDatum;
import co.za.bakingapp.Data.models.Step;
import co.za.bakingapp.Features.RecipeDetails.RecipeDetailsActivity;
import co.za.bakingapp.R;

public class RecipeStepsFragment extends Fragment{

    private static final String RECIPE_DATA = "recipes";
    RecipeDatum recipeDatum;
    RecyclerView recyclerView;
    RecipeStepsAdapter recipeStepsAdapter;
    List<Step> steps;

    public RecipeStepsFragment() {setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
         recipeDatum = new RecipeDatum();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        steps= new ArrayList<>();
        //load the saved state of imageid's and listindex if there is any
        if(savedInstanceState != null)
        {
            recipeDatum = (RecipeDatum) savedInstanceState.getSerializable(RECIPE_DATA);

        }else{
            try {
                recipeDatum = (RecipeDatum) getArguments().getSerializable("entity");
            }catch (NullPointerException ex){
                String message = ex.getMessage();
            }
        }
        setupViews(view);
        return view;
    }

    private void setupViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerview_RecipeSteps);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recipeStepsAdapter= new RecipeStepsAdapter(getContext());
        recyclerView.setAdapter(recipeStepsAdapter);

        recipeStepsAdapter.setInfoCollection(getSteps(recipeDatum));

        RecipeStepsAdapter.onStepClickedListener recipeStepClicked = new RecipeStepsAdapter.onStepClickedListener() {
            @Override
            public void onStepClicked(Step step) {
                    startActivity(RecipeDetailsActivity.getCallingIntent(getContext(), step));
            }
        };

        recipeStepsAdapter.setClickListener(recipeStepClicked);
    }


    public List<Step> getSteps(RecipeDatum recipeDatumm){
        steps.addAll(recipeDatumm.getSteps());
        return steps;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public  void onSaveInstanceState(Bundle currentState)
    {
        currentState.putSerializable(RECIPE_DATA, recipeDatum);
    }
}
