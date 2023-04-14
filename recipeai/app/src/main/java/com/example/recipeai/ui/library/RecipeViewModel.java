package com.example.recipeai.ui.library;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipeai.model.Recipe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private DocumentSnapshot lastVisibleRecipe;
    private final MutableLiveData<List<Recipe>> recipesLiveData = new MutableLiveData<>();
    private static final int LIMIT = 10;

    public RecipeViewModel() {
//        // Set up recipes live data
//        // TODO: Add filter for data only for specific user
//        recipesRef = db.collection("recipes");
//        recipesRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException error) {
//                if (error != null) {
//                    Log.w("load recipes error", "Failed to update recipe live data", error);
//                    return;
//                }
//
//                // Recreate the recipes list when recipes collection in firestore changes
//                List<Recipe> recipes = new ArrayList<>();
//                for (DocumentSnapshot recipeDoc : querySnapshot.getDocuments()) {
//                    Recipe recipe = recipeDoc.toObject(Recipe.class);
//                    recipe.setDocId(recipeDoc.getId());
//                    Log.w("Recipe Test", recipe.getName());
//                    recipes.add(recipe);
//                }
//                recipesLiveData.setValue(recipes);
//            }
//        });

        recipesRef = db.collection("recipes");
    }

    // Load initial recipes when launching app
    public void loadRecipes() {
        recipesRef.orderBy("name").limit(LIMIT).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        lastVisibleRecipe = queryDocumentSnapshots.getDocuments()
                                .get(queryDocumentSnapshots.size() - 1);
                        List<Recipe> recipes = new ArrayList<>();
                        for (DocumentSnapshot recipeDoc : queryDocumentSnapshots) {
                            Recipe recipe = recipeDoc.toObject(Recipe.class);
                            recipe.setDocId(recipeDoc.getId());
                            Log.w("Recipe Test", recipe.getName());
                            recipes.add(recipe);
                        }
                        recipesLiveData.setValue(recipes);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("load recipes error", "Failed to load recipes from Firestore", e);
                    }
                });
    }

    // Fetch more recipes when user scrolls to end of current list
    public void fetchMoreRecipes() {
        if (lastVisibleRecipe == null) {
            return;
        }
        // Query starting from last recipe that is visible in recycler view
        recipesRef.orderBy("name").startAfter(lastVisibleRecipe).limit(LIMIT).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            return;
                        }
                        lastVisibleRecipe = queryDocumentSnapshots.getDocuments()
                                .get(queryDocumentSnapshots.size() - 1);
                        List<Recipe> recipes = recipesLiveData.getValue();
                        // Convert each recipe document to recipe object and add to recipe live data
                        for (DocumentSnapshot recipeDoc : queryDocumentSnapshots) {
                            Recipe recipe = recipeDoc.toObject(Recipe.class);
                            recipe.setDocId(recipeDoc.getId());
                            recipes.add(recipe);
                        }
                        recipesLiveData.setValue(recipes);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("load recipes error", "Failed to load more recipes from Firestore", e);
                    }
                });
    }

    public MutableLiveData<List<Recipe>> getRecipes() {
        return recipesLiveData;
    }
}