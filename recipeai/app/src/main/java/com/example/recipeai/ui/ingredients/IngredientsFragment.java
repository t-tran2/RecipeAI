package com.example.recipeai.ui.ingredients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.recipeai.adapter.IngredientsAdapter;
import com.example.recipeai.databinding.FragmentIngredientsBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.ktx.Firebase;

public class IngredientsFragment extends Fragment {

    private FirebaseFirestore firestoreDb;
    private Query query;

    private FragmentIngredientsBinding binding;
    private IngredientsAdapter ingredientsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        IngredientsViewModel ingredientsViewModel =
                new ViewModelProvider(this).get(IngredientsViewModel.class);

        binding = FragmentIngredientsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);

        // Setup firestore
        firestoreDb = FirebaseFirestore.getInstance();


        // TODO: Uncomment for TESTING. Delete for PROD.
//        firestoreDb.useEmulator("10.0.2.2", 8080);
//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setPersistenceEnabled(false)
//                .build();
//        firestoreDb.setFirestoreSettings(settings);

        // Fetch ingredients collection from firebase db
        query = firestoreDb.collection("ingredients");

        // RecyclerView of ingredients
        if (query != null) {
            ingredientsAdapter = new IngredientsAdapter(query);
            ingredientsAdapter.setOnDataChangedListener(new IngredientsAdapter.OnDataChangedListener() {
                @Override
                public void onDataChanged() {
                    // Only display if query not empty
                    if (ingredientsAdapter.getItemCount() > 0) {
                        binding.ingredientsRecyclerview.setVisibility(View.VISIBLE);
                    } else {
                        binding.ingredientsRecyclerview.setVisibility(View.GONE);
                    }
                }
            });

            // Set adapter for recycler view ingredients
            binding.ingredientsRecyclerview.setAdapter(ingredientsAdapter);
        }
        binding.ingredientsRecyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    @Override
    public void onStart() {
        super.onStart();
        ingredientsAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        ingredientsAdapter.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}