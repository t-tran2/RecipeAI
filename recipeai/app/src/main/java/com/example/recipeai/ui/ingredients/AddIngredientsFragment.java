package com.example.recipeai.ui.ingredients;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.recipeai.R;
import com.example.recipeai.databinding.FragmentAddIngredientsBinding;
import com.example.recipeai.databinding.FragmentIngredientsBinding;
import com.example.recipeai.model.Ingredient;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class AddIngredientsFragment extends Fragment{
    private FragmentAddIngredientsBinding binding;
    private Button button;
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
        binding = FragmentAddIngredientsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        button = root.findViewById(R.id.addIngredientsButton);
        inputIngredient = root.findViewById(R.id.editTextAddIngredient);

        FirebaseFirestore.setLoggingEnabled(true);
        firestoreDb = FirebaseFirestore.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ingredientName = inputIngredient.getText().toString();

                CollectionReference dbIngredients =  firestoreDb.collection("ingredients");
                Ingredient ingredient = new Ingredient(ingredientName);
                // below method is use to add data to Firebase Firestore.
                dbIngredients =  firestoreDb.collection("ingredients_inventory");
                dbIngredients.add(ingredient);


            }
        });


        return root;
    }
}