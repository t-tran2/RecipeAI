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


public class AddIngredientsFragment extends Fragment{

    private FragmentAddIngredientsBinding binding;
    private Button button;
    TextView inputIngredient;
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ingredientName = inputIngredient.getText().toString();
                Ingredient ingredient = new Ingredient(ingredientName);


            }
        });


        return root;
    }
}