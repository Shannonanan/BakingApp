package co.za.bakingapp.Features.RecipeSteps;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.za.bakingapp.Data.models.RecipeDatum;
import co.za.bakingapp.Features.common.BaseActivity;
import co.za.bakingapp.R;

public class RecipeStepsActivity extends BaseActivity {


    public static Intent getCallingIntent(Context context, RecipeDatum entity) {
        Intent intent = new Intent(context, RecipeStepsActivity.class);
        intent.putExtra("entity", entity);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            RecipeDatum recipeDatum = (RecipeDatum) getIntent().getSerializableExtra("entity");
            initializeActivity(recipeDatum);
        }
    }

    private void initializeActivity(RecipeDatum recipeDatum) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", recipeDatum);

        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
        recipeStepsFragment.setArguments(bundle);
        addFragment(recipeStepsFragment);
    }



}
