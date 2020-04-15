package com.example.pizzaorder;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ComputableLiveData;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDefaultSelection();
        ingredientChipHandler();
    }

    public void setDefaultSelection() {
        MaterialButton roundPizzaType = findViewById(R.id.roundPizzaToggleButton);
        roundPizzaType.performClick();

        MaterialRadioButton noCheeseRadioButton = findViewById(R.id.noCheeseRadioButton);
        noCheeseRadioButton.performClick();

    }

    public void pizzaTypeHandler(View view) {
        MaterialButton roundPizzaType = findViewById(R.id.roundPizzaToggleButton);
        MaterialButton squarePizzaType = findViewById(R.id.squarePizzaToggleButton);

        ImageView pizzaImageView = findViewById(R.id.pizzaImageView);

        if (roundPizzaType.isChecked()) {
            pizzaImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_round_pizza, null));
        } else if (squarePizzaType.isChecked()){
            pizzaImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_squared_pizza, null));
        } else {
            pizzaImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_not_selected_pizza, null));
        }

    }

    public void ingredientChipHandler() {
        CheckBox pepperoniCheckBox = findViewById(R.id.pepperoniCheckBox);
        CheckBox veggiesCheckBox = findViewById(R.id.veggieCheckBox);
        CheckBox mushroomsCheckBox = findViewById(R.id.mushroomCheckBox);
        CheckBox anchoviesCheckBox = findViewById(R.id.anchovyCheckBox);

        RadioGroup cheeseRadioGroup = findViewById(R.id.cheeseRadioGroup);

        final ChipGroup chosenIngredients = findViewById(R.id.chosenIngredientsChipGroup);
        final Chip pepperoniChip = new Chip(chosenIngredients.getContext(), null, R.attr.chipStyle);
        pepperoniChip.setText(R.string.pepperoni);
        pepperoniChip.setChipBackgroundColorResource(R.color.primaryColor);

        final Chip veggieChip = new Chip(chosenIngredients.getContext(), null, R.attr.chipStyle);
        veggieChip.setText(R.string.veggies);
        veggieChip.setChipBackgroundColorResource(R.color.primaryColor);

        final Chip mushroomChip = new Chip(chosenIngredients.getContext(), null, R.attr.chipStyle);
        mushroomChip.setText(R.string.mushrooms);
        mushroomChip.setChipBackgroundColorResource(R.color.primaryColor);

        final Chip anchovyChip = new Chip(chosenIngredients.getContext(), null, R.attr.chipStyle);
        anchovyChip.setText(R.string.anchovies);
        anchovyChip.setChipBackgroundColorResource(R.color.primaryColor);

        pepperoniCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean pepperoniChecked) {
                chosenIngredients.removeView(pepperoniChip);
                if (pepperoniChecked) {
                    chosenIngredients.addView(pepperoniChip);
                }
            }
        } );

        veggiesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean pepperoniChecked) {
                chosenIngredients.removeView(veggieChip);
                if (pepperoniChecked) {
                    chosenIngredients.addView(veggieChip);
                }
            }
        } );

        mushroomsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean pepperoniChecked) {
                chosenIngredients.removeView(mushroomChip);
                if (pepperoniChecked) {
                    chosenIngredients.addView(mushroomChip);
                }
            }
        } );

        anchoviesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean pepperoniChecked) {
                chosenIngredients.removeView(anchovyChip);
                if (pepperoniChecked) {
                    chosenIngredients.addView(anchovyChip);
                }
            }
        } );


        final Chip cheeseChip = new Chip(chosenIngredients.getContext(), null, R.attr.chipStyle);
        cheeseRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup cheeseRadioGroup, int checkedId) {
                if (cheeseRadioGroup.getCheckedRadioButtonId() == R.id.regularCheeseRadioButton) {
                    cheeseChip.setText(getResources().getString(R.string.cheese));
                    cheeseChip.setChipBackgroundColorResource(R.color.primaryColor);
                    chosenIngredients.removeView(cheeseChip);
                    chosenIngredients.addView(cheeseChip);
                } else if (cheeseRadioGroup.getCheckedRadioButtonId() == R.id.doubleCheeseRadioButton) {
                    cheeseChip.setText((getResources().getString(R.string.double_cheese)));
                    chosenIngredients.removeView(cheeseChip);
                    chosenIngredients.addView(cheeseChip);
                } else {
                    chosenIngredients.removeView(cheeseChip);
                }
            }
        });
    }

    public void placeOrderButtonHandler(View view) {
        TextInputLayout nameLayout = findViewById(R.id.nameTextInputLayout);
        TextInputLayout phoneLayout = findViewById(R.id.phoneTextInputLayout);

        TextInputEditText name = findViewById(R.id.nameEditText);
        TextInputEditText phone = findViewById(R.id.phoneEditText);

        if (name.getText().toString().length() == 0) {
            nameLayout.setError("Required field");
        } else {
            nameLayout.setError("");
        }

        if (phone.getText().toString().length() != 10 ) {
            phoneLayout.setError("Phone number not valid");
        }
    }



    }
