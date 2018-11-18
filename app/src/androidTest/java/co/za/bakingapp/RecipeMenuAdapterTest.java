package co.za.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.za.bakingapp.Features.GetAllRecipes.GetAllRecipesActivity;
import co.za.bakingapp.Features.GetAllRecipes.GetAllRecipesAdapter;
import co.za.bakingapp.Features.GetAllRecipes.GetAllRecipesFragment;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;


import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;


@RunWith(AndroidJUnit4.class)
public class RecipeMenuAdapterTest {
    /**
     * The ActivityTestRule is a rule provided by Android used for functional testing of a single
     * activity. The activity that will be tested will be launched before each test that's annotated
     * with @Test and before methods annotated with @Before. The activity will be terminated after
     * the test and methods annotated with @After are complete. This rule allows you to directly
     * access the activity during the test.
     */
    @Rule
    public ActivityTestRule<GetAllRecipesActivity> mActivityTestRule =
            new ActivityTestRule<>(GetAllRecipesActivity.class);


    @Before
    public void init(){
        GetAllRecipesFragment fragment = new GetAllRecipesFragment();
      //  mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction().add(R.id.recyclerview_container_allrecipes, fragment).commit();
        mActivityTestRule.getActivity().addFragment(fragment);
    }

    @Test
    public void checkText_RecipeActivity() {
        onView(withIndex(withId(R.id.recyclerview_AllRecipes), 1))
                .perform(RecyclerViewActions.scrollToHolder(
                        withHolderTitleView("Nutella Pie"))
                );

//        onView(org.hamcrest.core.AllOf.allOf(withId(R.id.recyclerview_AllRecipes)))
//                .perform(RecyclerViewActions.scrollToHolder(
//                        withHolderTitleView("Nutella Pie"))
//                );

//        onView(allOf(withId(R.id.recyclerview_AllRecipes),
//                withText(is("Nutella Pie"))));


//        onView(allOf(isDisplayed(), withId(R.id.recyclerview_AllRecipes)))
//                .perform(RecyclerViewActions.scrollToPosition(0));
//        onView(withText("Brownies")).check(matches(isDisplayed()));
    }

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }

    public Matcher<RecyclerView.ViewHolder> withHolderTitleView(final String text){
        return new BoundedMatcher<RecyclerView.ViewHolder, GetAllRecipesAdapter.GetAllRecipesViewHolder>(GetAllRecipesAdapter.GetAllRecipesViewHolder.class) {
            @Override
            protected boolean matchesSafely(GetAllRecipesAdapter.GetAllRecipesViewHolder item) {
                TextView timeViewText = item.itemView.findViewById(R.id.tv_recipe_title);
                if (timeViewText == null) {
                    return false;
                }
                return timeViewText.getText().toString().contains(text);
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }


    /**
     * Clicks on a GridView item and checks it opens up the OrderActivity with the correct details.
     */
    @Test
    public void clickGridViewItem_OpensOrderActivity() {

        // Uses {@link Espresso#onData(org.hamcrest.Matcher)} to get a reference to a specific
        // gridview item and clicks it.
        onData(anything()).inAdapterView(withId(R.id.recyclerview_AllRecipes)).atPosition(1).perform(click());

        // Checks that the OrderActivity opens with the correct tea name displayed
        onView(withId(R.id.recyclerview_ingredients)).check(matches(withId(R.id.recyclerview_ingredients)));


    }


}
