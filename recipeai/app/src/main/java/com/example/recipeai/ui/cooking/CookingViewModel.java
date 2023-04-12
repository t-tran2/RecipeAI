package com.example.recipeai.ui.cooking;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipeai.model.Recipe;


public class CookingViewModel extends ViewModel {

    private MutableLiveData<Recipe> recipeLiveData;
    public MutableLiveData<Recipe> getCookRecipe() {
        if (recipeLiveData == null) {
            recipeLiveData = new MutableLiveData<>();
        }
        return recipeLiveData;
    }


}
