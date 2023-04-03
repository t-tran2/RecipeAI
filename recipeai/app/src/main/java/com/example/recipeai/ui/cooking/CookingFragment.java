package com.example.recipeai.ui.cooking;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.recipeai.MainActivity;
import com.example.recipeai.R;
import com.example.recipeai.databinding.FragmentCookingBinding;
import com.example.recipeai.model.Recipe;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CookingFragment extends Fragment implements SensorEventListener, View.OnClickListener {

    private FragmentCookingBinding binding;
    private SensorManager sensorManager;
    private Sensor mLight;
    private TextView sensorText, stepText, recipeNameText;
    private Button nextButton, previousButton, returnToLibraryButton;
    private Recipe myRecipe;
    private float timestamp;
    private boolean covered, lastEventCovered;
    private FirebaseFirestore firestoreDb;
    private LiveData<Recipe> myLiveData;
    private CookingViewModel myCookingViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCookingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FirebaseFirestore.setLoggingEnabled(true);
        firestoreDb = FirebaseFirestore.getInstance();

        myCookingViewModel = new ViewModelProvider(requireActivity()).get(CookingViewModel.class);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorText = root.findViewById(R.id.text_cooking);
        stepText = root.findViewById(R.id.text_step);
        recipeNameText = root.findViewById(R.id.recipeNameText);
        nextButton = root.findViewById(R.id.next_step);
        previousButton = root.findViewById(R.id.previous_step);
        returnToLibraryButton = root.findViewById(R.id.cooking_back_button);

        nextButton.setOnClickListener(this);
        previousButton.setOnClickListener(this);
        returnToLibraryButton.setOnClickListener(this);

        myLiveData = myCookingViewModel.getCookRecipe();
        myRecipe = myLiveData.getValue();
        stepText.setText(myRecipe.getCurrentStep());
        recipeNameText.setText(myRecipe.getName());
        lastEventCovered = false;
        return root;
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {

        float lux = event.values[0];
        sensorText.setText(Float.toString(lux));
        covered = event.values[0]<15;
        if (!covered) {
            if(lastEventCovered) {
                if ((event.timestamp - timestamp) / 1000000 > 1500) {
                    myRecipe.previousStep();
                } else if ((event.timestamp - timestamp) / 1000000 > 500) {
                    myRecipe.nextStep();
                }
            }
            timestamp = event.timestamp;
        }
        lastEventCovered = covered;
        stepText.setText(myRecipe.getCurrentStep());
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        Log.d("hi", "bruh");
       if (view.getId() == R.id.next_step){
           myRecipe.nextStep();
           stepText.setText(myRecipe.getCurrentStep());
       } else if (view.getId() == R.id.previous_step){
           myRecipe.previousStep();
           stepText.setText(myRecipe.getCurrentStep());
       } else if (view.getId() == R.id.cooking_back_button){
           Activity MainActivity = this.getActivity();
           NavController navController = Navigation.findNavController(MainActivity, R.id.nav_host_fragment_activity_main);
           navController.navigate(R.id.action_navigation_cooking_to_navigation_library);
       }
    }

}