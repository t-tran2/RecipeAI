package com.example.recipeai.ui.generate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipeai.model.Recipe;

public class GenerateViewModel extends ViewModel {

    private MutableLiveData<String> genRecipeLiveData;

    public MutableLiveData<String> getGeneratedRecipe() {
        if (genRecipeLiveData == null) {
            genRecipeLiveData = new MutableLiveData<>();
        }
        return genRecipeLiveData;
    }
}