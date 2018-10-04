package co.za.bakingapp.Features.GetAllRecipes;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import co.za.bakingapp.Data.Repository;

public class GetAllRecipesViewModelFactory extends ViewModelProvider.NewInstanceFactory  {

    private final Repository mRepository;
    private Context context;


    public GetAllRecipesViewModelFactory(Repository repository, Context context) {
        this.mRepository = repository;
        this.context = context;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new GetAllRecipesViewModel(mRepository,context);
    }

}
