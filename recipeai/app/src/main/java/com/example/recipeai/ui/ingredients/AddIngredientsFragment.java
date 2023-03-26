package com.example.recipeai.ui.ingredients;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.recipeai.MainActivity;
import com.example.recipeai.R;
import com.example.recipeai.databinding.FragmentAddIngredientsBinding;
import com.example.recipeai.model.Ingredient;
import com.example.recipeai.model.IngredientInventory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


public class AddIngredientsFragment extends Fragment{
    TextView inputIngredient;
    private FirebaseFirestore firestoreDb;
    private Query query;
    public AddIngredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_add_ingredients, container, false);
        com.example.recipeai.databinding.FragmentAddIngredientsBinding binding = FragmentAddIngredientsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button addIngredientButton = root.findViewById(R.id.addIngredientButton);
        Button returnButton = root.findViewById(R.id.addIngredientsBackButton);
        inputIngredient = root.findViewById(R.id.editTextAddIngredient);

        FirebaseFirestore.setLoggingEnabled(true);
        firestoreDb = FirebaseFirestore.getInstance();

        addIngredientButton.setOnClickListener(view -> {
            String ingredientName = inputIngredient.getText().toString();
            DocumentReference johnDocRef = firestoreDb.collection("users").document("VmpfS4tyaSUn64ucP203");
            DocumentReference ingredientRef = firestoreDb.collection("users").document("VmpfS4tyaSUn64ucP203");

            CollectionReference dbIngredients = firestoreDb.collection("ingredients");
            CollectionReference dbIngredientsInventory = firestoreDb.collection("ingredients_inventory");
            IngredientInventory ingredientInventory = new IngredientInventory(johnDocRef,ingredientName,ingredientRef);
            Ingredient ingredient = new Ingredient(ingredientName);
            // below method is use to add data to Firebase Firestore.

            Task<QuerySnapshot> query = dbIngredients.whereEqualTo("name", ingredientName).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (task.getResult().size() == 0) {
                        dbIngredients.add(ingredient);
                    }
                }
                ((MainActivity) getActivity()).showBottomAppBar();
                getParentFragmentManager().popBackStack();
            });
            dbIngredientsInventory.add(ingredientInventory);
        });

        returnButton.setOnClickListener(v -> {
            ((MainActivity) getActivity()).showBottomAppBar();
            getParentFragmentManager().popBackStack();
        });




        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
