package com.example.recipeai.ui.ingredients;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.recipeai.MainActivity;
import com.example.recipeai.R;
import com.example.recipeai.adapter.FirestoreAdapter;
import com.example.recipeai.adapter.IngredientsAdapter;
import com.example.recipeai.databinding.FragmentIngredientsBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.ktx.Firebase;

public class IngredientsFragment extends Fragment {

    private FragmentIngredientsBinding binding;
    private IngredientsAdapter ingredientsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        IngredientsViewModel ingredientsViewModel =
                new ViewModelProvider(this).get(IngredientsViewModel.class);

        binding = FragmentIngredientsBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        // Get a reference to the button view
        Button addIngredients = root.findViewById(R.id.add_ingredients_btn);

        // Set an OnClickListener to the button view
        addIngredients.setOnClickListener(v -> {

            // Create an instance of the destination fragment
            AddIngredientsFragment addIngredientsFragment = new AddIngredientsFragment();

            // Get a reference to the fragment manager
            FragmentManager fragmentManager = getParentFragmentManager();

            // Start a fragment transaction
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            ((MainActivity) getActivity()).hideBottomAppBar();

            // Replace the current fragment with the destination fragment
            fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, addIngredientsFragment);

            // Add the transaction to the back stack
            fragmentTransaction.addToBackStack(null);

            // Commit the transaction
            fragmentTransaction.commit();
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);
        // Setup firestore
        FirebaseFirestore firestoreDb = FirebaseFirestore.getInstance();


        // TODO: Uncomment for TESTING. Delete for PROD.
//        firestoreDb.useEmulator("10.0.2.2", 8080);
//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setPersistenceEnabled(false)
//                .build();
//        firestoreDb.setFirestoreSettings(settings);

        // Fetch ingredients collection from firebase db
        // TODO: SPECIFIC USER SELECTED
        DocumentReference johnDocRef = firestoreDb.collection("users").document("VmpfS4tyaSUn64ucP203");
        Query query = firestoreDb.collection("ingredients_inventory").whereEqualTo("userId", johnDocRef);


        // RecyclerView of ingredients
        ingredientsAdapter = new IngredientsAdapter(query);
        ingredientsAdapter.setOnDataChangedListener(() -> {
            // Only display if query not empty
            if (ingredientsAdapter.getItemCount() > 0) {
                binding.ingredientsRecyclerview.setVisibility(View.VISIBLE);
            } else {
                binding.ingredientsRecyclerview.setVisibility(View.GONE);
            }
        });

        // Set adapter for recycler view ingredients
        binding.ingredientsRecyclerview.setAdapter(ingredientsAdapter);
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