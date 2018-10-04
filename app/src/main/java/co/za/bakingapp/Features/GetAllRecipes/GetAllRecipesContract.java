package co.za.bakingapp.Features.GetAllRecipes;

import android.database.Observable;

import co.za.bakingapp.Data.models.RecipeDatumList;

public interface GetAllRecipesContract {

    public interface LoadRecipeCallBack{
        void onSuccess(String success);
        void onFailure(String error);
    }

    Observable<String> getRecipes();
}
