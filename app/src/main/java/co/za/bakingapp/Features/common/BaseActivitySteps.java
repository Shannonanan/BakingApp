package co.za.bakingapp.Features.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.za.bakingapp.Features.RecipeDetails.RecipeDetailsFragment;
import co.za.bakingapp.R;

public class BaseActivitySteps extends AppCompatActivity {

    private static final String BACK_STACK_ROOT_TAG = "root_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);
    }

    public void addFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        fragmentManager.beginTransaction()
                .add(R.id.recyclerview_container_steps, fragment)
            //    .addToBackStack(BACK_STACK_ROOT_TAG)
                .commit();
    }

    /**
     * Add a fragment on top of the current tab
     */
    public void addFragmentOnTop(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.recipe_detail_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

}