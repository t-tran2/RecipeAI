package com.example.recipeai.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.icu.text.BreakIterator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeai.R;
import com.example.recipeai.databinding.FragmentAddIngredientsBinding;
import com.example.recipeai.databinding.IngredientWithRemoveButtonBinding;
import com.example.recipeai.model.Ingredient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class IngredientsAdapter extends FirestoreAdapter<IngredientsAdapter.ViewHolder> {

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
        viewHolder.ingredient = getSnapshot(position).toObject(Ingredient.class);

        if (viewHolder.ingredient == null) {
            return;
        }

        // Bind ingredient document values to view
        viewHolder.getNameTextView().setText(viewHolder.ingredient.getName());
        viewHolder.ingredientDocRef = getSnapshot(position).getReference();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public IngredientWithRemoveButtonBinding binding;
        Ingredient ingredient;
        Button button;
        TextView ingredientText;
        private FirebaseFirestore firestoreDb;
        DocumentReference ingredientDocRef;

        public ViewHolder(IngredientWithRemoveButtonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // TODO: Move event listeners here (onClick and onLongClick) defined in the ingredientWithRemoveButton class
            View root = binding.getRoot();
            button = root.findViewById(R.id.remove_button);
            ingredientText = binding.ingredientName;

            FirebaseFirestore.setLoggingEnabled(true);
            firestoreDb = FirebaseFirestore.getInstance();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ingredientDocRef.delete();
                }
            });

            // Rename ingredient on long click
            root.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // Pop up to rename ingredient
                    AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                    builder.setTitle("Rename Ingredient");

                    // Edit ingredient name field
                    final EditText ingredientEditText = new EditText(root.getContext());
                    ingredientEditText.setText(ingredientText.getText());
                    builder.setView(ingredientEditText);


                    // Complete rename
                    builder.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // set ingredient name in text view to new name
//                            ingredientText.setText(ingredientEditText.getText().toString());

                            ingredientDocRef.update("name", ingredientEditText.getText().toString());
                        }
                    });

                    // Cancel rename
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    builder.show();
                    return true;
                }
            });
        }

        public TextView getNameTextView() {
            return this.binding.ingredientName;
        }
    }
}
