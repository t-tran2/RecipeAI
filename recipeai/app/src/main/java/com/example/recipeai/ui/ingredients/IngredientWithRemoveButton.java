package com.example.recipeai.ui.ingredients;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.recipeai.R;

/**
 * TODO: document your custom view class.
 */
public class IngredientWithRemoveButton extends ConstraintLayout {

    private Button removeButton;

    public IngredientWithRemoveButton(Context context) {
        super(context);
        init(context, null, 0);
    }

    public IngredientWithRemoveButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public IngredientWithRemoveButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ingredient_with_remove_button, this);

        removeButton = findViewById(R.id.remove_button);
        removeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ViewGroup) getParent()).removeView(IngredientWithRemoveButton.this);
            }
        });
    }
}