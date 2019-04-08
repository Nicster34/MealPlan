package com.nicster34.mealplan.data;

import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class DayPlan {
    private Date date;
    private DocumentReference breakfast;
    private DocumentReference lunch;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DocumentReference getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(DocumentReference breakfast) {
        this.breakfast = breakfast;
    }

    public DocumentReference getLunch() {
        return lunch;
    }

    public void setLunch(DocumentReference lunch) {
        this.lunch = lunch;
    }

    public DocumentReference getDinner() {
        return dinner;
    }

    public void setDinner(DocumentReference dinner) {
        this.dinner = dinner;
    }

    private DocumentReference dinner;
}
