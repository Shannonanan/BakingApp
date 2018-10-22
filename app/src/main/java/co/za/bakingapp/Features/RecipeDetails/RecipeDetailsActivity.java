package co.za.bakingapp.Features.RecipeDetails;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.za.bakingapp.Data.models.Step;
import co.za.bakingapp.R;

public class RecipeDetailsActivity extends AppCompatActivity {



    public static Intent getCallingIntent(Context context, Step step) {
        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        intent.putExtra("step", step);
        return  intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        getSupportActionBar().hide();
        Step step = (Step)getIntent().getSerializableExtra("step");

        if(savedInstanceState == null){

            Bundle bundle = new Bundle();
            bundle.putSerializable("step", step);

        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        recipeDetailsFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
        .add(R.id.fragment_container_recipe_detail, recipeDetailsFragment)
        .commit();}
    }


}
