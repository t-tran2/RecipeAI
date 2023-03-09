package com.example.recipeai.ui.generate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GenerateViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GenerateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Generate fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}