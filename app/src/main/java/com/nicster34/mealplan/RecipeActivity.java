package com.nicster34.mealplan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.nicster34.mealplan.data.Ingredient;
import com.nicster34.mealplan.data.IngredientRef;
import com.nicster34.mealplan.data.Meal;

import java.util.LinkedList;

public class RecipeActivity extends AppCompatActivity {

    Meal meal = new Meal();
    Ingredient eggs = new Ingredient();
    Ingredient cheese = new Ingredient();
    IngredientRef eggsRef = new IngredientRef();
    IngredientRef cheeseRef = new IngredientRef();
    TextView Ingredients = null;
    TextView Recipe = null;
    TextView MealName = null;
    private LinkedList<IngredientRef> recipe = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Ingredients = (TextView) findViewById(R.id.IngredientHolder);
        Recipe = (TextView) findViewById(R.id.RecipeHolder);
        MealName = (TextView) findViewById(R.id.RecipeTitle);

        eggs.setName("Eggs");
        cheese.setName("Cheese");

        eggsRef.setRef(null);
        cheeseRef.setRef(null);
        eggsRef.setQuantity(2);
        cheeseRef.setQuantity(1);

        recipe.add(eggsRef);
        recipe.add(cheeseRef);

        meal.setIngredients(recipe);

        meal.setInstructions("Put the eggs and the cheese in a pan and just make an omlette.");

        meal.setName("Omlette");

        Ingredients.setText("");

        for (int i = 0; i < recipe.size(); i++) {
            Ingredients.append("[Name of food here]" + " " + "[quantity here]" + "\n");
        }

        Recipe.setText(meal.getInstructions());
        MealName.setText(meal.getName());
    }
}
