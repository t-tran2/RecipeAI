package com.example.recipeai.ui.ingredients;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


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
                String userId = "users/VmpfS4tyaSUn64ucP203";
                String ingredientId = "";

                CollectionReference dbIngredients = firestoreDb.collection("ingredients");
                CollectionReference dbIngredientsInventory = firestoreDb.collection("ingredients_inventory");
                Ingredient ingredient = new Ingredient(ingredientName);
                // below method is use to add data to Firebase Firestore.

                Task<QuerySnapshot> query = dbIngredients.whereEqualTo("name", ingredientName).get().addOnCompleteListener(
                    new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() == 0) {
                                dbIngredients.add(ingredient);
                            }
                        }
                    }
                });


            }
        });
        return root;

    }
}
