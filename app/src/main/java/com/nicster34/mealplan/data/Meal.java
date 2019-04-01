package com.nicster34.mealplan.data;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Meal {

    private String name;
    private List<IngredientRef> ingredients;
    private String instructions;

    public List<IngredientRef> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientRef> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
