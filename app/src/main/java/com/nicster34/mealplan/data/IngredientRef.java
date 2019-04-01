package com.nicster34.mealplan.data;

import com.google.firebase.firestore.DocumentReference;

public class IngredientRef {

    private int quantity;
    private DocumentReference ref;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DocumentReference getRef() {
        return ref;
    }

    public void setRef(DocumentReference ref) {
        this.ref = ref;
    }


}
