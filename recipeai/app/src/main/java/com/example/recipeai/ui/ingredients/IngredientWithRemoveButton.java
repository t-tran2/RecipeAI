package com.example.recipeai.ui.ingredients;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.recipeai.R;

/**
 * TODO: document your custom view class.
 */
public class IngredientWithRemoveButton extends ConstraintLayout {

    private Button removeButton;
    private TextView ingredientText;

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
        View ingredientWithRemoveButton = inflater.inflate(R.layout.ingredient_with_remove_button, this);


        removeButton = findViewById(R.id.remove_button);
        ingredientText = findViewById(R.id.ingredient_name);
        removeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ViewGroup) getParent()).removeView(IngredientWithRemoveButton.this);
            }
        });

        ingredientWithRemoveButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // Pop up to rename ingredient
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Rename Ingredient");

                // Edit ingredient name field
                final EditText ingredientEditText = new EditText(getContext());
                ingredientEditText.setText(ingredientText.getText());
                builder.setView(ingredientEditText);

                // Complete rename
                builder.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // set ingredient name in text view to new name
                        ingredientText.setText(ingredientEditText.getText().toString());
                    }
                });

                // Cancel rename
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();
                return true;
            }
        });

    }
}