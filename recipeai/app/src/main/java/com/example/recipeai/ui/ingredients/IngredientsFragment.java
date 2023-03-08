package com.example.recipeai.ui.ingredients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipeai.databinding.FragmentIngredientsBinding;

public class IngredientsFragment extends Fragment {

    private FragmentIngredientsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        IngredientsViewModel ingredientsViewModel =
                new ViewModelProvider(this).get(IngredientsViewModel.class);

        binding = FragmentIngredientsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.titleIngredients;
//        ingredientsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}