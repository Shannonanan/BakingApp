package co.za.bakingapp.Features.GetAllRecipes;


import android.arch.lifecycle.ViewModel;
import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.za.bakingapp.Data.Repository;
import co.za.bakingapp.Data.models.RecipeDatum;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class GetAllRecipesViewModel extends ViewModel {

    private final Repository mRepository;
     SetCollection setCollection;


    public interface SetCollection {
        void setInfoCollection(List<RecipeDatum> newList);
    }

    GetAllRecipesViewModel(Repository mRepository, Context context) {
        this.mRepository = mRepository;
    }

    public void setOnItemClickListener (GetAllRecipesViewModel.SetCollection setCollection) {
        this.setCollection = setCollection;
    }


    public void doSomeWork() {
        mRepository.getRecipes2().subscribeOn(Schedulers.newThread())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecipeDatum[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RecipeDatum[] list) {
                        List<RecipeDatum> newList = new ArrayList<>(Arrays.asList(list));
                        setCollection.setInfoCollection(newList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
