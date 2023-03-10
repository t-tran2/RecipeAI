package com.example.recipeai.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeai.databinding.IngredientWithRemoveButtonBinding;
import com.example.recipeai.model.Ingredient;
import com.google.firebase.firestore.Query;

public class IngredientsAdapter extends FirestoreAdapter<IngredientsAdapter.ViewHolder>{

    public IngredientsAdapter(Query query) {
        super(query);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(IngredientWithRemoveButtonBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Ingredient ingredient = getSnapshot(position).toObject(Ingredient.class);
        if (ingredient == null) {
            return;
        }

        // Bind ingredient document values to view
        viewHolder.getNameTextView().setText(ingredient.getName());
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final IngredientWithRemoveButtonBinding binding;
        public ViewHolder(IngredientWithRemoveButtonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // TODO: Move event listeners here (onClick and onLongClick) defined in the ingredientWithRemoveButton class
        }

        public TextView getNameTextView() {
            return this.binding.ingredientName;
        }
    }
}
