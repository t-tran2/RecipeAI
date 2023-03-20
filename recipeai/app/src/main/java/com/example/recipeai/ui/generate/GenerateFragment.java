package com.example.recipeai.ui.generate;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipeai.GPT3Api.GPT3Api;
import com.example.recipeai.GPT3Api.GPT3Request;
import com.example.recipeai.GPT3Api.GPT3Response;
import com.example.recipeai.R;
import com.example.recipeai.databinding.FragmentGenerateBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class GenerateFragment extends Fragment {

    private FragmentGenerateBinding binding;
    private FirebaseFirestore firestoreDb;
    private Query query;

    private static final String BASE_URL = "https://api.openai.com/v1/";

    private GPT3Api gpt3Api;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        com.example.recipeai.ui.generate.GenerateViewModel generateViewModel =
                new ViewModelProvider(this).get(com.example.recipeai.ui.generate.GenerateViewModel.class);

        binding = FragmentGenerateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGenerate;
//        generateViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void makeApiCall(int maxTokens) {
        // create a Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // create an instance of the API interface
        GPT3Api api = retrofit.create(GPT3Api.class);

        // generate a prompt by getting the ingredients from DB
        String prompt = generatePrompt();

        // create a GPT3Request object with the prompt, maxTokens, temperature, and apiKey properties set
        GPT3Request request = new GPT3Request(prompt, maxTokens);

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
                    // avoid null pointer exception
                    assert apiResponse != null;
                    String generatedText = apiResponse.getText();
                    // format and display the generated text
                    String formattedText = formatText(generatedText);
                    // Do not do anything with response just yet
                    // textView.setText(formattedText);
                } else {
                    // handle API error
                    // avoid null pointer exception
                    assert response.errorBody() != null;
                    Log.e(TAG, "API call failed: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GPT3Response> call, @NonNull Throwable t) {
                this.call = call;
                // handle API error
                Log.e(TAG, "API call failed: " + t.getMessage());
            }
        });
    }

    private String generatePrompt() {
        FirebaseFirestore firestoreDb = FirebaseFirestore.getInstance();

        // Fetch ingredients collection from Firebase Firestore
        DocumentReference userDocRef = firestoreDb.collection("users").document("VmpfS4tyaSUn64ucP203");
        CollectionReference inventoryRef = firestoreDb.collection("ingredients_inventory");
        Query query = inventoryRef.whereEqualTo("name", userDocRef);

        // Execute the query and get the results
        Task<QuerySnapshot> querySnapshotTask = query.get();
        while (!querySnapshotTask.isComplete()) {
            // Wait for the query to complete
        }
        QuerySnapshot querySnapshot = querySnapshotTask.getResult();

        List<String> results = new ArrayList<>();
        for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
            Map<String, Object> data = documentSnapshot.getData();
            if (data.containsKey("user_id")) {
                String idCode = (String) data.get("user_id");
                if ("VmpfS4tyaSUn64ucP203".equals(idCode)) {
                    results.add(idCode);
                }
            }
        }

        // Join the results into a single string separated by commas
        String ingredients = String.join(",", results);

        // Combine ingredients into template, return prompt
        return String.format(getString(R.string.PROMPT_TEMPLATE), ingredients);
    }

    @SuppressLint("DefaultLocale")
    private String formatText(String text) {
        // split the text into separate lines
        String[] lines = text.split("\n");

        // add a bullet point to each line
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            builder.append(String.format("%d. %s\n", i + 1, lines[i]));
        }

        return builder.toString();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}