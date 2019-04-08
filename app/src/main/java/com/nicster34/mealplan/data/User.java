package com.nicster34.mealplan.data;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DocumentReference> getBannedMeals() {
        return bannedMeals;
    }

    public void setBannedMeals(List<DocumentReference> bannedMeals) {
        this.bannedMeals = bannedMeals;
    }

    public List<DayPlan> getMealsPlanned() {
        return mealsPlanned;
    }

    public void setMealsPlanned(List<DayPlan> mealsPlanned) {
        this.mealsPlanned = mealsPlanned;
    }

    private List<DocumentReference> bannedMeals;
    private List<DayPlan> mealsPlanned;
}
