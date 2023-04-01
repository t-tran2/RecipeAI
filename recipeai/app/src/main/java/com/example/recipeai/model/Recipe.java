package com.example.recipeai.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class Recipe {

    private List<String> steps;
    private int currentStepIndex;
    private String name;
    private DocumentReference userId;



    public Recipe(String name, List<String> steps, DocumentReference userId) {
        this.steps = steps;
        this.name = name;
        this.userId = userId;
        this.currentStepIndex= 0;

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
    public List<String> getSteps(){
        return this.steps;
    }

    public DocumentReference getUserId(){
        return this.userId;
    }
    public String getName(){
        return this.name;
    }



}