package com.nicster34.mealplan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nicster34.mealplan.data.Ingredient;
import com.nicster34.mealplan.data.IngredientRef;
import com.nicster34.mealplan.data.Meal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    Meal meal = new Meal();
    Ingredient eggs = new Ingredient();
    Ingredient cheese = new Ingredient();
    IngredientRef eggsRef = new IngredientRef();
    IngredientRef cheeseRef = new IngredientRef();
    TextView Ingredients = null;
    TextView Recipe = null;
    TextView MealName = null;
    FirebaseFirestore mDatabase;
    private List<Meal> allMeals;
    private LinkedList<IngredientRef> recipe = new LinkedList<>();
    private String recipeFromIntent;
    private List<Ingredient> allIngredients;

    //recycler view
    private RecyclerView mRecyclerView;
    private IngListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_recipe);
        recipeFromIntent = getIntent().getStringExtra("meal");
        Intent my = getIntent();

        //find the views
        //Ingredients = (TextView) findViewById(R.id.IngredientHolder);
        Recipe = (TextView) findViewById(R.id.RecipeHolder);
        MealName = (TextView) findViewById(R.id.RecipeTitle);

//        eggs.setName("Eggs");
//        cheese.setName("Cheese");
//
//        eggsRef.setRef(null);
//        cheeseRef.setRef(null);
//        eggsRef.setQuantity(2);
//        cheeseRef.setQuantity(1);
//
//        recipe.add(eggsRef);
//        recipe.add(cheeseRef);
//
//        meal.setIngredients(recipe);
//
//        meal.setInstructions("Put the eggs and the cheese in a pan and just make an omlette.");
//
//        meal.setName("Omlette");
//
//        Ingredients.setText("");

//        Recipe.setText(meal.getInstructions());
//        MealName.setText(meal.getName());
//        mDatabase.collection("meals").get();



        allMeals = new ArrayList<Meal>();
        CollectionReference colRef = mDatabase.collection("meals");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        allMeals.add(document.toObject(Meal.class));
                    }
                    setMeal();
                } else {
                    Log.d("Dunking", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void setMeal() {
        if (allMeals != null) {
            for (Meal meal : allMeals) {
                String mealname = meal.getName();
                if (meal.getName().equals(recipeFromIntent)) {
                    this.meal = meal;
                    getIngredientFromMeal();
                }
            }
        }
    }

    protected void getIngredientFromMeal() {
        allIngredients = new ArrayList<Ingredient>();
        final List<IngredientRef> mealIngred = meal.getIngredients();
        for (IngredientRef ref : mealIngred) {
            ref.getRef().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        Ingredient newIng = document.toObject(Ingredient.class);
                        allIngredients.add(newIng);
                        if (allIngredients.size() == mealIngred.size()) {
                            updateUI();
                        }
                    } else {
                        Log.d("Dunking", "Error getting documents: ", task.getException());
                    }
                }
            });
        }
    }

    private void updateUI() {
        Recipe.setText(meal.getInstructions());
        int counter = 0;
        LinkedList<String> names = new LinkedList<>();
        LinkedList<Number> nums = new LinkedList<>();
        for (Ingredient ing : allIngredients) {
            names.add(ing.getName());
            nums.add(meal.getIngredients().get(counter).getQuantity());
            //Ingredients.append(ing.getName() + " " + meal.getIngredients().get(counter).getQuantity() + "\n");
            counter++;
        }
        MealName.setText(meal.getName());

        mRecyclerView = findViewById(R.id.IngredientsHolder);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new IngListAdapter(this, names, nums, getIntent());
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    ;
}
