package com.example.recipeai;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import static org.junit.Assert.*;

import androidx.lifecycle.MutableLiveData;

import com.example.recipeai.model.Recipe;
import com.example.recipeai.ui.cooking.CookingViewModel;
import java.util.ArrayList;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import java.util.List;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void RecipeModelPreviousStepOnFirstStep() {
        List<String> list = new ArrayList<>();
        list.add("1. Toast bread.");
        list.add("2. Spread peanut butter.");
        list.add("3. Spread jam.");
        list.add("4. Assemble sandwich and enjoy.");
        Recipe recipe = new Recipe("Fried Rice", list);

        recipe.previousStep();
        String currentStep = recipe.getCurrentStep();
        assertEquals("1. Toast bread.", currentStep);
    }

    @Test
    public void RecipeModelNextStep() {
        List<String> list = new ArrayList<>();
        list.add("1. Toast bread.");
        list.add("2. Spread peanut butter.");
        list.add("3. Spread jam.");
        list.add("4. Assemble sandwich and enjoy.");
        Recipe recipe = new Recipe("Fried Rice", list);

        recipe.nextStep();
        String currentStep = recipe.getCurrentStep();
        assertEquals("2. Spread peanut butter.", currentStep);
    }

    @Test
    public void CookingViewModel() {

        List<String> list = new ArrayList<>();
        list.add("1. Toast bread.");
        list.add("2. Spread peanut butter.");
        list.add("3. Spread jam.");
        list.add("4. Assemble sandwich and enjoy.");
        Recipe recipe = new Recipe("Fried Rice", list);
        CookingViewModel myViewModel = new CookingViewModel();
        myViewModel.getCookRecipe().setValue(recipe);

        MutableLiveData<Recipe> test = myViewModel.getCookRecipe();
        Recipe testRecipe = test.getValue();
        assertEquals(recipe, testRecipe);
    }

}