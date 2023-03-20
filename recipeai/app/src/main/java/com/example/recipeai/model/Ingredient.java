package com.example.recipeai.model;

import com.google.firebase.firestore.DocumentReference;

public class Ingredient {
        String name;
        String category;
        DocumentReference ingredient_id;
        DocumentReference user_id;


        public Ingredient() {}

        public Ingredient(String name){
            this.name = name;
        }

        public Ingredient(DocumentReference id, String name, DocumentReference userId){
            this.name = name;
            this.ingredient_id = id;
            this.user_id = userId;
        }


    public String getName() {
        return name;
    }
    public DocumentReference getUserId(){return user_id;
    }
    public DocumentReference getIngredientId(){return ingredient_id;
    }

}
