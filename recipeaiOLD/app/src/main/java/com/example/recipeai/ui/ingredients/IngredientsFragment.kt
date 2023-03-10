package com.example.recipeai.ui.ingredients

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.recipeai.databinding.FragmentIngredientsBinding

class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("onCreateViewFragment", "onCreateView Fragment lifecycle triggered")
        val ingredientsViewModel =
            ViewModelProvider(this).get(IngredientsViewModel::class.java)

        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("onDestroyViewFragment", "onDestroyView Fragment lifecycle triggered")
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        Log.d("onStartFragment", "onStart Fragment lifecycle triggered")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroyFragment", "onDestroy Fragment lifecycle triggered")
        _binding = null
    }

}