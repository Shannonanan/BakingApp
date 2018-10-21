package co.za.bakingapp.Data;

import android.content.Context;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import co.za.bakingapp.Data.models.RecipeDatum;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class Repository {
    Context context;
    RecipeDatum recipeDatum;

    public Repository(Context context) {
        this.context = context;
    }


    public Observable<RecipeDatum[]> getRecipes2() {

            return io.reactivex.Observable.create(new ObservableOnSubscribe<RecipeDatum[]>() {
                @Override
                public void subscribe(ObservableEmitter<RecipeDatum[]> emitter) throws Exception {
                    try {
                        RecipeDatum [] response = new ObjectMapper().readValue(new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json").openStream(), RecipeDatum[].class);
                        emitter.onNext(response);
                        emitter.onComplete();
                    } catch (MalformedURLException ex) {
                        String temp = ex.getMessage();
                    } catch (IOException ed) {
                        String temp = ed.getMessage();
                    }
                    }
                });

    }


}
