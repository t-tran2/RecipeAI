package com.example.recipeai.ui.ingredients;

public class Ingredient {
        int id;
        String name;
        String category;

        public Ingredient(String name){
            this.name = name;
        }

        public Ingredient(int id, String name, String category){
            this.name = name;
            this.id = id;
            this.category = category;
        }

        public void setID(int id){
            this.id = id;
        }

        public void setCategory(String category){
            this.category = category;
        }
}