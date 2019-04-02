package com.nicster34.mealplan;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nicster34.mealplan.data.Ingredient;
import com.nicster34.mealplan.data.IngredientRef;
import com.nicster34.mealplan.data.Meal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseFirestore mDatabase;
    private Date today;

    private TextView dateDisplay;
    private TextView breakfastDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        today = new Date();
        dateDisplay = findViewById(R.id.textView);
        breakfastDisplay = findViewById(R.id.breakfastName);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        String mUsername = null;
        String mPhotoUrl = null;
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mDatabase = FirebaseFirestore.getInstance();
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), Profile.class));
                Ingredient apple = new Ingredient();
                apple.setName("apple");
                apple.setPrice(3.2);
                Meal applesalad = new Meal();
                applesalad.setInstructions("Cut apples man");
                applesalad.setName("applesalad");
                IngredientRef apl = new IngredientRef();
                apl.setQuantity(2);
                apl.setRef(mDatabase.collection("ingredients").document(apple.getName()));
                List<IngredientRef> abs = new ArrayList<IngredientRef>();
                abs.add(apl);
                applesalad.setIngredients(abs);
                mDatabase.collection("meals").document(applesalad.getName()).set(applesalad);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        }

    }
}
