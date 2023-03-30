package com.example.recipeai.ui.library;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipeai.model.Recipe;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecipeViewModel extends ViewModel {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference recipesRef;

    private final MutableLiveData<List<Recipe>> recipesLiveData = new MutableLiveData<>();;

    public RecipeViewModel() {
        // Set up recipes live data
        // TODO: Add filter for data only for specific user
        recipesRef = db.collection("recipes");
        recipesRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("load recipes error", "Failed to update recipe live data", error);
                    return;
                }

                // Recreate the recipes list when recipes collection in firestore changes
                List<Recipe> recipes = new ArrayList<>();
                for (DocumentSnapshot recipeDoc : querySnapshot.getDocuments()) {
                    Recipe recipe = recipeDoc.toObject(Recipe.class);
                    recipe.setDocId(recipeDoc.getId());
                    Log.w("Recipe Test", recipe.getName());
                    recipes.add(recipe);
                }
                recipesLiveData.setValue(recipes);
            }
        });
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipesLiveData;
    }
}