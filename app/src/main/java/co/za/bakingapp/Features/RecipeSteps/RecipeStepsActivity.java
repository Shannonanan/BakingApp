package co.za.bakingapp.Features.RecipeSteps;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.za.bakingapp.Data.models.RecipeDatum;
import co.za.bakingapp.Features.RecipeDetails.RecipeDetailsFragment;
import co.za.bakingapp.Features.common.BaseActivity;
import co.za.bakingapp.Features.common.BaseActivitySteps;
import co.za.bakingapp.R;

public class RecipeStepsActivity extends BaseActivitySteps implements RecipeStepsFragment.OnIngredientClickListener{

    private static final String STEP_DATA = "step";
    private static final String RECIPE_DATA = "recipeSteps";
    private static final String TWO_PANE_LAYOUT = "two_pane_layout";
    private boolean mTwoPane;
    RecipeDatum recipeDatum;
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";

    public static Intent getCallingIntent(Context context, RecipeDatum entity) {
        Intent intent = new Intent(context, RecipeStepsActivity.class);
        intent.putExtra("entity", entity);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
             recipeDatum = (RecipeDatum) getIntent().getSerializableExtra("entity");
            initializeActivity(recipeDatum);
        }



    }

    private void initializeActivity(RecipeDatum recipeDatum) {


        if(findViewById(R.id.android_me_linear_layout) != null){
            mTwoPane = true;

            Bundle bundle = new Bundle();
            bundle.putSerializable("entity", recipeDatum);
            bundle.putBoolean(TWO_PANE_LAYOUT, mTwoPane);

            RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
            recipeStepsFragment.setArguments(bundle);
            addFragment(recipeStepsFragment);



            Bundle bundle2 = new Bundle();
            bundle2.putSerializable(STEP_DATA, 0);
            bundle2.putSerializable(RECIPE_DATA, recipeDatum);
            bundle2.putBoolean(TWO_PANE_LAYOUT, mTwoPane);

            RecipeDetailsFragment fragment1 = new RecipeDetailsFragment();
            fragment1.setArguments(bundle2);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_detail_fragment, fragment1)
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        }else{
            mTwoPane = false;

            Bundle bundle = new Bundle();
            bundle.putSerializable("entity", recipeDatum);
            bundle.putBoolean(TWO_PANE_LAYOUT, mTwoPane);

            RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
            recipeStepsFragment.setArguments(bundle);
            addFragment(recipeStepsFragment);
        }
    }


    @Override
    public void onIngredientSelected(int position) {

        Bundle bundle2 = new Bundle();
        bundle2.putSerializable(STEP_DATA, position);
        bundle2.putSerializable(RECIPE_DATA, recipeDatum);
        bundle2.putBoolean(TWO_PANE_LAYOUT, mTwoPane);

        RecipeDetailsFragment fragment1 = new RecipeDetailsFragment();
        fragment1.setArguments(bundle2);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_detail_fragment, fragment1)
                .addToBackStack(BACK_STACK_ROOT_TAG)
                .commit();
    }
}
