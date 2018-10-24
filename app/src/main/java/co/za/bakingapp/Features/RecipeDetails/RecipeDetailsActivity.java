package co.za.bakingapp.Features.RecipeDetails;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.Serializable;
import java.util.List;

import co.za.bakingapp.Data.models.RecipeDatum;
import co.za.bakingapp.Data.models.Step;
import co.za.bakingapp.R;

public class RecipeDetailsActivity extends AppCompatActivity {


    private static final String RECIPE_DATA = "recipeSteps" ;
    private static final String STEP_DATA = "step";
    private static final String TWO_PANE_LAYOUT = "two_pane_layout";

    public static Intent getCallingIntent(Context context, int position, RecipeDatum recipeDatum) {
        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        intent.putExtra(RECIPE_DATA, recipeDatum);
        intent.putExtra(STEP_DATA, position);
        return  intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        getSupportActionBar().hide();
        int position = getIntent().getIntExtra(STEP_DATA, 0);
        RecipeDatum recipeDatum = (RecipeDatum)getIntent().getSerializableExtra(RECIPE_DATA);

        if(savedInstanceState == null){

            Bundle bundle = new Bundle();
            bundle.putSerializable(STEP_DATA, position);
            bundle.putSerializable(RECIPE_DATA, recipeDatum);
            bundle.putBoolean(TWO_PANE_LAYOUT, false);

        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        recipeDetailsFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
        .add(R.id.fragment_container_recipe_detail, recipeDetailsFragment)
        .commit();}
    }


}
