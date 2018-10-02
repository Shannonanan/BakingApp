package co.za.bakingapp.Features.GetAllRecipes;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import co.za.bakingapp.Data.Repository;

public class GetAllRecipesViewModelFactory extends ViewModelProvider.NewInstanceFactory  {

    private final Repository mRepository;


    public GetAllRecipesViewModelFactory(Repository repository) {
        this.mRepository = repository;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new GetAllRecipesViewModel(mRepository);
    }

}
