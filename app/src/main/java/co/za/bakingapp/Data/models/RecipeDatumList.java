package co.za.bakingapp.Data.models;

import java.io.Serializable;
import java.util.List;

public class RecipeDatumList implements Serializable {

    private List<RecipeDatum> list;

    public List<RecipeDatum> getList() {
        return list;
    }

    public void setList(List<RecipeDatum> list) {
        this.list = list;
    }
}
