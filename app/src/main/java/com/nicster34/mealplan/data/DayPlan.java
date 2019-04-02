package com.nicster34.mealplan.data;

import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

class DayPlan {
    private Date date;
    private DocumentReference breakfast;
    private DocumentReference lunch;
    private DocumentReference dinner;
}
