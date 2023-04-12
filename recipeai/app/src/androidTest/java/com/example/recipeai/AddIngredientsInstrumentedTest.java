package com.example.recipeai;

import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.example.recipeai.ui.ingredients.IngredientsFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AddIngredientsInstrumentedTest {

    private FragmentScenario<IngredientsFragment> scenario;

    @Before
    public void setUp() {
        // Launch the IngredientsFragment
        scenario = FragmentScenario.launchInContainer(IngredientsFragment.class);
    }

    @Test
    @LargeTest
    public void testButton1Initialization() {
        // Set up test data
        String expectedText = "Add Ingredient";

        // Verify Button was initialized with the correct text
        scenario.onFragment(fragment -> {
            Button button = (Button) fragment.getView().findViewById(R.id.addIngredientButton);
            assertEquals(expectedText, button.getText().toString());
        });
    }


    @Test
    @LargeTest
    public void testEditTextInitialization() {
        // Set up test data
        String expectedText = "Type ingredient here …";

        // Verify EditText was initialized with the correct text
        scenario.onFragment(fragment -> {
            EditText editText = (EditText) fragment.getView().findViewById(R.id.editTextAddIngredient);
            assertEquals(expectedText, editText.getText().toString());
        });
    }

    @Test
    @LargeTest
    public void testButton2Initialization() {
        // Set up test data
        String expectedText = "Add Ingredient";

        // Verify Button was initialized with the correct text
        scenario.onFragment(fragment -> {
            Button button = (Button) fragment.getView().findViewById(R.id.addIngredientsBackButton);
            assertEquals(expectedText, button.getText().toString());
        });
    }

}
