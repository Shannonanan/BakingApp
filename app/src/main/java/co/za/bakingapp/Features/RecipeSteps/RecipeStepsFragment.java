package co.za.bakingapp.Features.RecipeSteps;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.za.bakingapp.Data.models.Ingredient;
import co.za.bakingapp.Data.models.RecipeDatum;
import co.za.bakingapp.Data.models.Step;
import co.za.bakingapp.Features.RecipeDetails.RecipeDetailsActivity;
import co.za.bakingapp.R;

public class RecipeStepsFragment extends Fragment {

    private static final String RECIPE_DATA = "recipes";
    RecipeDatum recipeDatum;
    RecyclerView recyclerView;
    RecipeStepsAdapter recipeStepsAdapter;
    List<Step> steps;
    ListView listview;
    View view;
    LayoutInflater inflaterThis;
    ViewGroup containerThis;
    @BindView(R.id.btn_ingredients) Button btn_ingredients;

    public RecipeStepsFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        recipeDatum = new RecipeDatum();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflaterThis = inflater;
        containerThis = container;
         view = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        ButterKnife.bind(this, view);
        steps = new ArrayList<>();
        //load the saved state of imageid's and listindex if there is any
        if (savedInstanceState != null) {
            recipeDatum = (RecipeDatum) savedInstanceState.getSerializable(RECIPE_DATA);

        } else {
            try {
                recipeDatum = (RecipeDatum) getArguments().getSerializable("entity");
            } catch (NullPointerException ex) {
                String message = ex.getMessage();
            }
        }
        setupViews(view);
        return view;
    }

    @OnClick(R.id.btn_ingredients)
    public void onIngredientsClick(){

        final ConstraintLayout ingredients_display = view.findViewById(R.id.ingredients_list_view);
        final ConstraintLayout  ingredients = view.findViewById(R.id.cl_listview);
        listview  = (ListView) view.findViewById(R.id.lv_ingredients);
        Button btn_close =view.findViewById(R.id.btn_close);

        if(ingredients_display.getVisibility() == View.VISIBLE){
            ingredients_display.setVisibility(View.GONE);
        }else{
            btn_ingredients.setVisibility(View.GONE);
            ingredients_display.setVisibility(View.VISIBLE);
            ingredients.setVisibility(View.VISIBLE);
        }

       btn_close.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(ingredients_display.getVisibility() == View.VISIBLE){
                   btn_ingredients.setVisibility(View.VISIBLE);
                   ingredients_display.setVisibility(View.GONE);
                   ingredients.setVisibility(View.GONE);
               }else{
                   btn_ingredients.setVisibility(View.GONE);
                   ingredients_display.setVisibility(View.VISIBLE);
                   ingredients.setVisibility(View.VISIBLE);
               }
           }
       });


        List<Ingredient> ingredientsList = new ArrayList<>();
        ingredientsList = recipeDatum.getIngredients();

        final MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getContext(),
                ingredientsList);
        listview.setAdapter(adapter);
    }


    private void setupViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerview_RecipeSteps);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recipeStepsAdapter = new RecipeStepsAdapter(getContext());
        recyclerView.setAdapter(recipeStepsAdapter);

        recipeStepsAdapter.setInfoCollection(getSteps(recipeDatum));

        RecipeStepsAdapter.onStepClickedListener recipeStepClicked = new RecipeStepsAdapter.onStepClickedListener() {
            @Override
            public void onStepClicked(int position) {
                startActivity(RecipeDetailsActivity.getCallingIntent(getContext(), position, recipeDatum));
            }
        };

        recipeStepsAdapter.setClickListener(recipeStepClicked);
    }


    public List<Step> getSteps(RecipeDatum recipeDatumm) {
        steps.addAll(recipeDatumm.getSteps());
        return steps;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putSerializable(RECIPE_DATA, recipeDatum);
    }

//
//    private class StableArrayAdapter extends ArrayAdapter<String> {
//
//        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
//
//        public StableArrayAdapter(Context context, int textViewResourceId,
//                                  List<String> objects) {
//            super(context, textViewResourceId, objects);
//            for (int i = 0; i < objects.size(); ++i) {
//                mIdMap.put(objects.get(i), i);
//            }
//        }
//
//        @Override
//        public long getItemId(int position) {
//            String item = getItem(position);
//            return mIdMap.get(item);
//        }
//
//        @Override
//        public boolean hasStableIds() {
//            return true;
//        }
//
//    }

    public class MySimpleArrayAdapter extends ArrayAdapter<Ingredient> {
        private final Context context;
        private final List<Ingredient> values;




        public MySimpleArrayAdapter(Context context, List<Ingredient> values) {
            super(context, -1, values);
            this.context = context;
            this.values = values;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.cell_ingredients, parent, false);
            TextView tv_ingredient =  rowView.findViewById(R.id.tv_ingredient);
            TextView tv_quantity =  rowView.findViewById(R.id.tv_quantity);
            TextView tv_measure = rowView.findViewById(R.id.tv_measure);

            tv_ingredient.setText(values.get(position).getIngredient());
            tv_quantity.setText(values.get(position).getQuantity().toString());
            tv_measure.setText(values.get(position).getMeasure());

            return rowView;
        }
    }
}
