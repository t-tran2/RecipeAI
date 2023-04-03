package com.example.recipeai.ui.generate;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static java.lang.Thread.*;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipeai.GPT3Api.GPT3Api;
import com.example.recipeai.GPT3Api.GPT3Request;
import com.example.recipeai.GPT3Api.GPT3Response;
import com.example.recipeai.MainActivity;
import com.example.recipeai.R;
import com.example.recipeai.databinding.FragmentGenerateBinding;
import com.example.recipeai.model.Recipe;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class GenerateFragment extends Fragment {

    private FragmentGenerateBinding binding;
    private FirebaseFirestore firestoreDb;
    private Recipe myRecipe;
    private Button saveRecipe;
    private ProgressBar spinner;
    private static final String BASE_URL = "https://api.openai.com/v1/";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        com.example.recipeai.ui.generate.GenerateViewModel generateViewModel =
                        new ViewModelProvider(this).get(com.example.recipeai.ui.generate.GenerateViewModel.class);

                binding = FragmentGenerateBinding.inflate(inflater, container, false);
                View root = binding.getRoot();

                FirebaseFirestore.setLoggingEnabled(true);
                firestoreDb = FirebaseFirestore.getInstance();

                // Get a reference to the button view
                Button generateRecipe = root.findViewById(R.id.generate_btn);
                saveRecipe = root.findViewById(R.id.save_recipe_button);
                saveRecipe.setVisibility(View.GONE);
                spinner = root.findViewById(R.id.progressBar);
                spinner.setVisibility(View.GONE);

                // Get a reference to the text view
                TextView promptDisplay = root.findViewById(R.id.prompt_display);

                // Set an OnClickListener to the button view
                generateRecipe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //System.out.println(generatePrompt());
                        ((MainActivity) getActivity()).hideBottomAppBar();
                        promptDisplay.setText("");
                        saveRecipe.setVisibility(View.GONE);
                        spinner.setVisibility(View.VISIBLE);
                        makeApiCall(400, generatePrompt());
                    }

                });

                saveRecipe.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        saveRecipe();
                    }

                });
        return root;
    }

    String recipeText;

    public void makeApiCall(int max_tokens, String recipePrompt) {
        // create a Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // create an instance of the API interface
        GPT3Api api = retrofit.create(GPT3Api.class);

        // create a GPT3Request object with the prompt, maxTokens, temperature, and apiKey properties set
        GPT3Request request = new GPT3Request(recipePrompt, max_tokens, 1, "text-davinci-003");

        // create a Call object for the API request
        Call<GPT3Response> call = api.generateText(request);

        // execute the API request asynchronously
        call.enqueue(new Callback<GPT3Response>() {
            private Call<GPT3Response> call;

            @Override
            public void onResponse(@NonNull Call<GPT3Response> call, @NonNull Response<GPT3Response> response) {
                this.call = call;
                if (response.isSuccessful()) {
                    // handle the API response
                    GPT3Response apiResponse = response.body();
                    String generatedText = apiResponse.getGeneratedText();
                    myRecipe = convertResponseToRecipe(generatedText);

                    View root = binding.getRoot();

                    // Get a reference to the text view
                    TextView promptDisplay = root.findViewById(R.id.prompt_display);
                    promptDisplay.setMovementMethod(new ScrollingMovementMethod());
                    promptDisplay.setText(generatedText);
                    recipeText = generatedText;
                    saveRecipe.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.GONE);

                    ((MainActivity) getActivity()).showBottomAppBar();

                } else {
                    // handle API error
                    // avoid null pointer exception
                    try {
                        String errorResponse = response.errorBody().string();
                        Log.e(TAG, "API call failed (responseFail): " + errorResponse);
                    } catch (IOException e) {
                        Log.e(TAG, "Failed to parse error response: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GPT3Response> call, @NonNull Throwable t) {
                this.call = call;
                // handle API error
                Log.e(TAG, "API call failed (generalFail): " + t.getMessage());
            }
        });
    }

    private String generatePrompt() {
        FirebaseFirestore firestoreDb = FirebaseFirestore.getInstance();

        // Fetch ingredients collection from Firebase Firestore
        DocumentReference userDocRef = firestoreDb.collection("users").document("VmpfS4tyaSUn64ucP203");
        CollectionReference inventoryRef = firestoreDb.collection("ingredients_inventory");
        Query query = inventoryRef.whereEqualTo("userId", userDocRef);

        // Execute the query and get the results
        Task<QuerySnapshot> querySnapshotTask = query.get();
        while (!querySnapshotTask.isComplete()) {

        }

//        Debug
          QuerySnapshot querySnapshot = querySnapshotTask.getResult();
//        List<DocumentSnapshot> documents = querySnapshot.getDocuments();
//        for (DocumentSnapshot document : documents) {
//            String name = document.getString("name");
//            System.out.println("Name: " + name);
//        }

        List<String> results = new ArrayList<>();
        for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
            Map<String, Object> data = documentSnapshot.getData();
                String ingredientName = (String) data.get("name");
                    // System.out.println("Ingredient:" + ingredientName);
                    results.add(ingredientName);
        }

        // Join the results into a single string separated by commas
        String ingredients = String.join(",", results);

        // Combine ingredients into template, return prompt
        return String.format(getString(R.string.PROMPT_TEMPLATE), ingredients);
    }

    private Recipe convertResponseToRecipe(String text) {
        // split the text into separate lines
        String[] lines = text.split("\n");
        List<String> steps = Arrays.asList(lines);

        List<String> finalSteps = new ArrayList<>();
        for(int i = 0; i<steps.size();i++){
            if(!steps.get(i).isEmpty()){
                finalSteps.add(steps.get(i));
            }
        }
        String name = finalSteps.get(0);
        finalSteps.remove(0);

        Log.i("name", name);
        Log.i("steps", finalSteps.toString());
        DocumentReference userDocRef = firestoreDb.collection("users").document("VmpfS4tyaSUn64ucP203");
        Recipe recipe = new Recipe(name, finalSteps, userDocRef);
        return recipe;
    }

    private void saveRecipe(){
        CollectionReference db = firestoreDb.collection("recipes");
        Map<String, Object> docData = new HashMap<>();
        docData.put("steps", myRecipe.getSteps());
        docData.put("name", myRecipe.getName());
        docData.put("userId", myRecipe.getUserId());
        db.add(docData);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}