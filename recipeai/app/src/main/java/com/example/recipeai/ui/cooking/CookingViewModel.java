package com.example.recipeai.ui.cooking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CookingViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public CookingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Cooking fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}