package com.example.recipeai.model;

import java.util.List;

public class Recipe {

    private List<String> steps;
    private int currentStepIndex;
    private String name;

    public Recipe(String name, List<String> steps) {
        this.steps = steps;
        this.name = name;
        currentStepIndex= 0;

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