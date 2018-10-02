package co.za.bakingapp.Data;

import android.content.Context;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class Repository {
    Context context;


    public Repository(Context context) {
        this.context = context;
    }



    public void getAllRecipeData() {
       // JSONObject obj = new JSONObject(readJSONFromAsset());
    }



    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("baking.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
