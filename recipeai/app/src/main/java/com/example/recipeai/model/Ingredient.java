package com.example.recipeai.model;

import com.google.firebase.firestore.DocumentReference;

public class Ingredient {
        String name;
        String category;
        DocumentReference ingredientId;
        DocumentReference userId;


        public Ingredient() {}

        public Ingredient(String name){
            this.name = name;
        }

        public Ingredient(DocumentReference id, String name, DocumentReference userId){
            this.name = name;
            this.ingredientId = id;
            this.userId = userId;
        }


    public String getName() {
        return name;
    }
    public DocumentReference getUserId(){return userId;
    }
    public DocumentReference getIngredientId(){return ingredientId;
    }

}
