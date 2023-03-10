package com.example.recipeai.ui.generate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipeai.databinding.FragmentGenerateBinding;

public class GenerateFragment extends Fragment {

    private FragmentGenerateBinding binding;

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}