package com.example.recipeai;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.example.recipeai.ui.ingredients.IngredientsFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import androidx.test.core.app.ApplicationProvider;

@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class AddIngredientsUnitTests {
    private Fragment fragment;

    @Before
    public void setUp() {
        // Set up mock Fragment
        fragment = new IngredientsFragment(); // Create a real instance of the fragment
        LayoutInflater inflater = LayoutInflater.from(ApplicationProvider.getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_add_ingredients, null); // Inflate the layout
        fragment.onViewCreated(view, null); // Attach the view to the fragment

        // Set up mock Button
        Button button1 = new Button(ApplicationProvider.getApplicationContext());
        when(fragment.getView().findViewById(R.id.addIngredientButton)).thenReturn(button1);

        // Set up mock editText
        EditText editText = new EditText(ApplicationProvider.getApplicationContext());
        when(fragment.getView().findViewById(R.id.editTextAddIngredient)).thenReturn(editText);

        // Set up mock Button
        Button button2 = new Button(ApplicationProvider.getApplicationContext());
        when(fragment.getView().findViewById(R.id.addIngredientsBackButton)).thenReturn(button2);
    }


    @Test
    public void testButton1Initialization() {
        // Set up test data
        String expectedText = "Add Ingredient";

        // Verify Button was initialized with the correct text
        assertEquals(expectedText, ((Button)fragment.getView().findViewById(R.id.addIngredientButton)).getText().toString());
    }

    @Test
    public void testEditTextInitialization() {
        // Set up test data
        String expectedText = "Type ingredient here …";

        // Verify Button was initialized with the correct text
        assertEquals(expectedText, ((EditText)fragment.getView().findViewById(R.id.editTextAddIngredient)).getText().toString());
    }

    @Test
    public void testButton2Initialization() {
        // Set up test data
        String expectedText = "Back to Ingredients";

        // Verify Button was initialized with the correct text
        assertEquals(expectedText, ((Button)fragment.getView().findViewById(R.id.addIngredientsBackButton)).getText().toString());
    }

}
