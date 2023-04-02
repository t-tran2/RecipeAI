package com.example.recipeai.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeai.R;
import com.example.recipeai.databinding.RecipeViewholderBinding;
import com.example.recipeai.model.Recipe;
import com.example.recipeai.ui.cooking.CookingViewModel;
import com.example.recipeai.ui.library.RecipeViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends ListAdapter<Recipe, RecipeAdapter.ViewHolder> {

    public RecipeAdapter() {
        super(new DiffUtil.ItemCallback<Recipe>() {
            @Override
            public boolean areItemsTheSame(Recipe oldItem, Recipe newItem) {
                return oldItem.getDocId() == newItem.getDocId();
                // TODO: IMPLEMENT
//                 return false;
            }

            @Override
            public boolean areContentsTheSame(Recipe oldItem, Recipe newItem) {
                return oldItem.equals(newItem);
                // TODO: IMPLEMENT
            }
        });
    }

    public RecipeAdapter(@NonNull DiffUtil.ItemCallback<Recipe> diffCallback) {
        super(diffCallback);
    }


    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecipeViewholderBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false), parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
        Log.w("ONBIND HERE", "HERE!!!");
        Recipe recipe = getItem(position);
        // TODO: bind data to viewholder
        holder.setRecipe(recipe);
        // Example: holder.textView.setText(.....)
        Log.w("onBind Recipe", recipe.getName());
        holder.getRecipeName().setText(recipe.getName());
    }

//    @Override
//    public int getItemCount() {
//        return 0;
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RecipeViewholderBinding binding;
        private Recipe recipe;
        private CookingViewModel cookingViewModel;

        public ViewHolder(RecipeViewholderBinding binding, Context context) {
            super(binding.getRoot());
            this.binding = binding;
            cookingViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(CookingViewModel.class);


            // TODO: set on click event listeners
            binding.cookBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cookingViewModel.getCookRecipe().setValue(recipe);
                    Log.w("COOKING VIEW MODEL", cookingViewModel.getCookRecipe().getValue().toString());
                }
            });

            // Remove recipe
            binding.removeRecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recipe.removeFromFirestore();
                }
            });
        }

        public TextView getRecipeName() {
            return this.binding.recipeName;
        }
        public void setRecipe(Recipe recipe) {
            this.recipe = recipe;
        }
    }




}
