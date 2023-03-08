package com.example.recipeai.ui.ingredients

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.recipeai.R
import com.example.recipeai.databinding.IngredientWithRemoveButtonBinding

class IngredientWithRemoveButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var binding: IngredientWithRemoveButtonBinding

    init {
        // Layout inflation
        binding = IngredientWithRemoveButtonBinding.inflate(LayoutInflater.from(context), this, true)

        // remove this custom view from parent if remove button clicked
        binding.removeButton.setOnClickListener {
            val parent = parent as ViewGroup
            parent.removeView(this@IngredientWithRemoveButton)
        }
    }

    fun setIngredientText(text: String) {
        binding.ingredientName.text = text
    }
}
