package com.example.recipeai;

import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.example.recipeai.ui.generate.GenerateFragment;


@RunWith(AndroidJUnit4.class)
public class GenerateTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Mock
    GenerateFragment generateFragment;

    private IdlingResource idlingResource;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }

    @Test
    public void testTextView() {
        // Launch the fragment
        FragmentScenario.launchInContainer(GenerateFragment.class);

        // Check that the textView contains the correct text
        onView(withId(R.id.prompt_display))
                .check(matches(withText("")));
    }

    @Test
    public void testButtonText() {
        // Launch the fragment
        FragmentScenario.launchInContainer(GenerateFragment.class);

        // Check that the button contains the correct text
        onView(withId(R.id.generate_btn))
                .check(matches(withText("Generate Recipe")));
    }

    @Test
    public void testButtonClick() {
        // Launch the fragment
        FragmentScenario.launchInContainer(GenerateFragment.class);

        // Press button
        onView(withId(R.id.generate_btn)).perform(click());

        // Check that textView changed
        onView(withId(R.id.prompt_display))
                .check(matches(not(withText(""))));
    }



}
