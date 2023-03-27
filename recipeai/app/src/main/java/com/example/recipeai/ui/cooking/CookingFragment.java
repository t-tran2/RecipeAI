package com.example.recipeai.ui.cooking;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipeai.R;
import com.example.recipeai.databinding.FragmentCookingBinding;

public class CookingFragment extends Fragment implements SensorEventListener {

    private FragmentCookingBinding binding;
    private SensorManager sensorManager;
    private Sensor mLight;
    private TextView sensorText;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        com.example.recipeai.ui.cooking.CookingViewModel cookingViewModel =
                new ViewModelProvider(this).get(com.example.recipeai.ui.cooking.CookingViewModel.class);

        binding = FragmentCookingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCooking;
//        cookingViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorText = root.findViewById(R.id.text_cooking);
        return root;
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        float lux = event.values[0];
        sensorText.setText(Float.toString(lux));

        // Do something with this sensor value.
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
}