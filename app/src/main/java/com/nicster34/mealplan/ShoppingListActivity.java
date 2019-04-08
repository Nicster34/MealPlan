package com.nicster34.mealplan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nicster34.mealplan.data.Ingredient;
import com.nicster34.mealplan.data.IngredientRef;
import com.nicster34.mealplan.data.Meal;

import java.util.LinkedList;

public class ShoppingListActivity extends AppCompatActivity {

    TextView Items = null;
    Meal meal = new Meal();
    Ingredient eggs = new Ingredient();
    Ingredient cheese = new Ingredient();
    IngredientRef eggsRef = new IngredientRef();
    IngredientRef cheeseRef = new IngredientRef();
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseFirestore mDatabase;
    private LinkedList<IngredientRef> recipe = new LinkedList<>();
    private int listItemCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        Items = (TextView) findViewById(R.id.ShoppingListContainer);

        Items.setText("");

        eggs.setName("Eggs");
        cheese.setName("Cheese");

        eggsRef.setRef(null);
        cheeseRef.setRef(null);
        eggsRef.setQuantity(2);
        cheeseRef.setQuantity(1);

        recipe.add(eggsRef);
        recipe.add(cheeseRef);

        meal.setIngredients(recipe);

//        This is going to have to go in a for loop that knows how many meals there are, so that every meal's
//        ingredients are actually shown
        for (int i = 0; i < recipe.size(); i++) {
            Items.append("[Name of food here]" + " " + "[quantity here]" + " " + "\n");
        }
    }
}
