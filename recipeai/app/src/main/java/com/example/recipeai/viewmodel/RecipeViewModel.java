package com.example.recipeai.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipeai.model.Recipe;

public class RecipeViewModel extends ViewModel {
    private MutableLiveData<Recipe> recipe;

    public MutableLiveData<Recipe> getRecipe() {
        if (recipe == null) {
            recipe = new MutableLiveData<Recipe>();
        }
        return recipe;
    }
}
