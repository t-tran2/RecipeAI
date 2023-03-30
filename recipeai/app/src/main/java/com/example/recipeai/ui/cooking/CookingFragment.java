package com.example.recipeai.ui.cooking;

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
import androidx.lifecycle.ViewModelProvider;

import com.example.recipeai.R;
import com.example.recipeai.databinding.FragmentCookingBinding;
import com.example.recipeai.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class CookingFragment extends Fragment implements SensorEventListener, View.OnClickListener {

    private FragmentCookingBinding binding;
    private SensorManager sensorManager;
    private Sensor mLight;
    private TextView sensorText, stepText;
    private Button nextButton, previousButton;
    private Recipe myCookingViewModel;
    private float timestamp;
    private boolean covered, lastEventCovered;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCookingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//      cookingViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorText = root.findViewById(R.id.text_cooking);
        stepText = root.findViewById(R.id.text_step);
        nextButton = root.findViewById(R.id.next_step);
        previousButton = root.findViewById(R.id.previous_step);
        nextButton.setOnClickListener(this);
        previousButton.setOnClickListener(this);
        List<String> steps = new ArrayList<>();
        steps.add("hi");
        steps.add("bye");
        steps.add("hello");
        myCookingViewModel = new Recipe("recipe", steps);
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
                    myCookingViewModel.previousStep();

                } else if ((event.timestamp - timestamp) / 1000000 > 500) {
                    myCookingViewModel.nextStep();

                }
            }
            timestamp = event.timestamp;
        }
        lastEventCovered = covered;
        stepText.setText(myCookingViewModel.getCurrentStep());
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
           myCookingViewModel.nextStep();
           stepText.setText(myCookingViewModel.getCurrentStep());
       } else if (view.getId() == R.id.previous_step){
           myCookingViewModel.previousStep();
           stepText.setText(myCookingViewModel.getCurrentStep());
       }
    }

}