package co.za.bakingapp.Features.GetAllRecipes;

import android.arch.lifecycle.ViewModel;

import co.za.bakingapp.Data.Repository;

public class GetAllRecipesViewModel extends ViewModel{

    private final Repository mRepository;

    public GetAllRecipesViewModel(Repository repository) {
        this.mRepository = repository;
    }

    public void getRecipes() {
        //callbacks
        mRepository.getAllRecipeData();
    }
}
