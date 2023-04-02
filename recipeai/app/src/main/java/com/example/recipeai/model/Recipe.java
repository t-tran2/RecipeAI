package com.example.recipeai.model;

import androidx.annotation.Nullable;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;


public class Recipe {
    private String docId;


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

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
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

    public String getDocId() {
        return docId;
    }

    public void setDocId(String recipeDocId) {
        docId = recipeDocId;
    }
}