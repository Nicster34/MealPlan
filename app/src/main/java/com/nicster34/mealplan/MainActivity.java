package com.nicster34.mealplan;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nicster34.mealplan.data.Ingredient;
import com.nicster34.mealplan.data.User;

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
    private ImageButton breakChangeButton;
    private ImageButton lunchChangeButton;
    private ImageButton dinChangeButton;
    Intent myIntent;
    private ImageView calendarButton;
    private User currentUser;
    private List<Ingredient> allIngredients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        today = new Date();
        dateDisplay = findViewById(R.id.text_date);
        breakfastDisplay = findViewById(R.id.break_name);

        breakChangeButton = findViewById(R.id.break_change);
        lunchChangeButton = findViewById(R.id.lunch_change);
        dinChangeButton = findViewById(R.id.din_change);
        calendarButton = findViewById(R.id.calendarButton);

        View.OnClickListener buttonlistener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onChangeMeal(v);
            }
        };

        breakChangeButton.setOnClickListener(buttonlistener);
        lunchChangeButton.setOnClickListener(buttonlistener);
        dinChangeButton.setOnClickListener(buttonlistener);

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
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
            }
        });


        FloatingActionButton Profile = findViewById(R.id.Profile);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        FloatingActionButton ShoppingList = findViewById(R.id.ShoppingList);
        ShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShoppingListActivity.class));
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainActivity.this, SignInActivity.class));
                startActivity(new Intent(getApplicationContext(), RecipeActivity.class));
                //Ingredient apple = new Ingredient();
                //apple.setName("apple");
                //apple.setPrice(3.2);
                //Meal applesalad = new Meal();
                // applesalad.setInstructions("Cut apples man");
                //applesalad.setName("applesalad");
                //IngredientRef apl = new IngredientRef();
                //apl.setQuantity(2);
                // apl.setRef(mDatabase.collection("ingredients").document(apple.getName()));
                // List<IngredientRef> abs = new ArrayList<IngredientRef>();
                // abs.add(apl);
                //applesalad.setIngredients(abs);
                //mDatabase.collection("meals").document(applesalad.getName()).set(applesalad);


//                DocumentReference docRef = mDatabase.collection("users").document(mFirebaseUser.getUid());
//                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
//                                Log.d("help", "DocumentSnapshot data: " + document.getData());
//                                currentUser = document.toObject(User.class);
//                                Log.d("help", "DocumentSnapshot data: " + currentUser.getMealsPlanned());
//                                loadUser(currentUser);
//                            } else {
//                                Log.d("help", "No such document");
//                            }
//                        } else {
//                            Log.d("help", "get failed with ", task.getException());
//                        }
//                    }
//                });
                //allIngredients = new ArrayList<Ingredient>();
//                Log.d("Dunking", "onClick: FUCK");
//                CollectionReference colRef = mDatabase.collection("users").document(mFirebaseUser.getUid()).collection("mealsplanned");
//                if(colRef==null){
//                    Log.d("Dunking", "onClick: NULL");
//                }
//                colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            Log.d("Dunking", " => " + task.getResult().getDocuments().size());
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("Dunking", document.getId() + " => " + document.getData());
//                                //sssallIngredients.add(document.toObject(Ingredient.class));
//                            }
//                        } else {
//                            Log.d("Dunking", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });

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
        myIntent = getIntent();
        /*
        myInten
        if(myIntent!=null){
            if(myIntent.getExtras().get("date")!=null) {
                Log.d("Fee", (String) myIntent.getExtras().get("date"));
            }
        }*/

    }

    //
//    private void loadUser(User u){
//        DocumentReference day = u.getMealsPlanned().get(dateDisplay);
//
//    }
//    private void loadMeals(DayPlan day){
//        Meal breakfast;
//        Meal lunch;
//        Meal dinner;
//        day.getBreakfast().get()
//
//
//
//    }
    public void onChangeMeal(View v) {
        Intent mealIntent = new Intent(getApplicationContext(), MealChoiceActivity.class);
        switch (v.getId()) {
            case R.id.break_change:
                mealIntent.putExtra("mealType", "Breakfast");
                break;
            case R.id.lunch_change:
                mealIntent.putExtra("mealType", "Lunch");
                break;
            case R.id.din_change:
                mealIntent.putExtra("mealType", "Dinner");
                break;
        }
        startActivity(mealIntent);
    }

}
