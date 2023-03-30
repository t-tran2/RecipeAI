package com.example.recipeai.model;

import androidx.annotation.Nullable;

public class Recipe {
    private String docId;

    private String name;
    public void setDocId(String recipeDocId) {
        docId = recipeDocId;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return docId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}
