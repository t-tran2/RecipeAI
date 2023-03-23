package com.example.recipeai.model;

import com.google.firebase.firestore.DocumentReference;

public class Ingredient {
        String name;
        String category;


        public Ingredient() {}

        public Ingredient(String name){
            this.name = name;
        }

    public String getName() {
        return name;
    }


}
