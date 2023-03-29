package com.example.recipeai.ui.cooking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CookingViewModel extends ViewModel {

    private List<String> steps;
    private int currentStepIndex;

    public CookingViewModel() {
        steps = new ArrayList<>();
        currentStepIndex= 0;
        steps.add("hi");
        steps.add("bye");
        steps.add("hello");
    }

    public void addStep(String step){
        steps.add(step);
    }

    public void nextStep(){
        if(currentStepIndex<steps.size()-1) {
            currentStepIndex++;
        }
    }

    public void previousStep(){
        if(currentStepIndex>0) {
            currentStepIndex--;
        }
    }

    public String getCurrentStep(){
        return steps.get(currentStepIndex);
    }


}