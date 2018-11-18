package co.za.bakingapp.Features.GetAllRecipes;

import android.os.Bundle;
import co.za.bakingapp.Features.common.BaseActivity;
import co.za.bakingapp.R;


public class GetAllRecipesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            addFragment(new GetAllRecipesFragment());
        }

    }

        }



