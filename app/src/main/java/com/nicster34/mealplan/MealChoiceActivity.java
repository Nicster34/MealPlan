package com.nicster34.mealplan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.AlteredCharSequence;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nicster34.mealplan.data.Ingredient;
import com.nicster34.mealplan.data.IngredientRef;
import com.nicster34.mealplan.data.Meal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MealChoiceActivity extends AppCompatActivity {

    private final LinkedList<String> mWordList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;

    //firebase variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseFirestore mDatabase;

    private List<Ingredient> allMeals;
    private ProgressBar prog;
    private String typeOfMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_choice);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prog = findViewById(R.id.progressBar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //intent handling (variables passed through different activities)
        Intent tempInt = getIntent();
        setTitle("Chose a Meal (" + tempInt.getStringExtra("mealType") + ")");

        //firebase authentication and setup
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mDatabase = FirebaseFirestore.getInstance();
        }

        //grabbing the information from the databases

        mDatabase.collection("meals").get();
        allMeals = new ArrayList<Ingredient>();
        CollectionReference colRef = mDatabase.collection("ingredients");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        allMeals.add(document.toObject(Ingredient.class));
                    }
                    updateUI(allMeals);
                } else {
                    Log.d("Dunking", "Error getting documents: ", task.getException());
                }
            }
        });
        prog.setVisibility(View.VISIBLE);

//        Ingredient apple = new Ingredient();
//        apple.setName("apple");
//        apple.setPrice(3.2);
//        Meal applesalad = new Meal();
//        applesalad.setInstructions("Cut apples man");
//        applesalad.setName("applesalad");
//        IngredientRef apl = new IngredientRef();
//        apl.setQuantity(2);
//        apl.setRef(mDatabase.collection("ingredients").document(apple.getName()));
//        List<IngredientRef> abs = new ArrayList<IngredientRef>();
//        abs.add(apl);
//        applesalad.setIngredients(abs);
//        mDatabase.collection("meals").document(applesalad.getName()).set(applesalad);

        // Put initial data into the word list.


    }

    private void updateUI(List<Ingredient> meals) {


        for (Ingredient meal : meals) {
            mWordList.addLast(meal.getName());
            Log.d("Dunking", meal.getName());
        }

        prog.setVisibility(View.INVISIBLE);

        mRecyclerView = findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new WordListAdapter(this, mWordList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
