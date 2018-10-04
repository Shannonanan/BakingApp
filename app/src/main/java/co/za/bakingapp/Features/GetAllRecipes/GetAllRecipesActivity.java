package co.za.bakingapp.Features.GetAllRecipes;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.za.bakingapp.R;


public class GetAllRecipesActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_recipes);

        GetAllRecipesFragment getAllRecipesFragment = new GetAllRecipesFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.recyclerview_AllRecipes_container, getAllRecipesFragment)
                .commit();


    }
}
