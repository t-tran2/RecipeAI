package com.example.recipeai.ui.library;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeai.R;
import com.example.recipeai.adapter.RecipeAdapter;
import com.example.recipeai.databinding.FragmentLibraryBinding;
import com.example.recipeai.model.Recipe;

import java.util.List;

public class LibraryFragment extends Fragment {

    private FragmentLibraryBinding binding;
//    private RecyclerView recipeRecyclerView;
    private RecipeAdapter recipeAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.recipeRecyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));


//        libraryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Connect recyclerview to viewModel and RecipeAdapter
        RecipeViewModel recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        recipeAdapter = new RecipeAdapter();
        binding.recipeRecyclerview.setAdapter(recipeAdapter);
        binding.recipeRecyclerview.setNestedScrollingEnabled(true);

        // Observe Recipes liveData
        recipeViewModel.loadRecipes();
        recipeViewModel.getRecipes().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                recipeAdapter.submitList(recipes);
                recipeAdapter.notifyDataSetChanged();
            }
        });

        binding.recipeRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    recipeViewModel.fetchMoreRecipes();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}