package com.nicster34.mealplan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nicster34.mealplan.data.DayPlan;
import com.nicster34.mealplan.data.Ingredient;
import com.nicster34.mealplan.data.Meal;
import com.nicster34.mealplan.data.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseFirestore mDatabase;

    private Date today;
    private DateFormat formatter;
    private TextView dateDisplay;


    public static final int MEALREQUEST = 1025;
    public static final int DATEREQUEST = 1026;

    private TextView breakfastDisplay;
    private ImageButton breakChangeButton;
    private ImageButton breakViewButton;

    private TextView lunchDisplay;
    private ImageButton lunchChangeButton;
    private ImageButton lunchViewButton;

    private TextView dinnerDisplay;
    private ImageButton dinChangeButton;
    private ImageButton dinViewButton;


    Intent myIntent;
    private ImageView calendarButton;
    private User currentUser;
    private List<Ingredient> allIngredients;



    private DocumentReference currentPlanRef;
    private DayPlan currentPlan;

    private DocumentReference currentBreakfastRef;
    private Meal currentBreakfast;

    private DocumentReference currentLunchRef;
    private Meal currentLunch;

    private DocumentReference currentDinnerRef;
    private Meal currentDinner;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        today = new Date();
        formatter = new SimpleDateFormat("MMMM d yyyy");
        dateDisplay = findViewById(R.id.text_date);
        dateDisplay.setText(formatter.format(today));

        breakfastDisplay = findViewById(R.id.break_name);
        lunchDisplay = findViewById(R.id.lunch_name);
        dinnerDisplay = findViewById(R.id.din_name);

        breakChangeButton = findViewById(R.id.break_change);
        lunchChangeButton = findViewById(R.id.lunch_change);
        dinChangeButton = findViewById(R.id.din_change);


        breakViewButton = findViewById(R.id.break_info);
        lunchViewButton = findViewById(R.id.lunch_info);
        dinViewButton = findViewById(R.id.din_info);

        calendarButton = findViewById(R.id.calendarButton);

        View.OnClickListener buttonlistener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onChangeMeal(v);
            }
        };
        View.OnClickListener viewButtonlistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewMeal(v);
            }
        };

        breakChangeButton.setOnClickListener(buttonlistener);
        lunchChangeButton.setOnClickListener(buttonlistener);
        dinChangeButton.setOnClickListener(buttonlistener);

        breakViewButton.setOnClickListener(viewButtonlistener);
        lunchViewButton.setOnClickListener(viewButtonlistener);
        dinViewButton.setOnClickListener(viewButtonlistener);

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
        updateDayPlan();
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), CalendarActivity.class), DATEREQUEST);
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
        startActivityForResult(mealIntent, MEALREQUEST);
    }

    public void onViewMeal(View v) {
        Intent mealIntent = new Intent(getApplicationContext(), RecipeActivity.class);
        switch (v.getId()) {
            case R.id.break_info:
                mealIntent.putExtra("meal", currentBreakfast.getName());
                break;
            case R.id.lunch_info:
                mealIntent.putExtra("meal", currentLunch.getName());
                break;
            case R.id.din_info:
                mealIntent.putExtra("meal", currentDinner.getName());
                break;
        }
        startActivity(mealIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == DATEREQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                String selection = data.getStringExtra("newDate");
                onNewDate(selection);
            }
        }
        if (requestCode == MEALREQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                String selection = data.getStringExtra("mealSelection");
                switch (data.getStringExtra("mealType")) {
                    case "Breakfast":
                        currentBreakfastRef = mDatabase.collection("meals").document(selection);
                        currentPlan.setBreakfast(currentBreakfastRef);
                        currentPlanRef.set(currentPlan).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("d", "DocumentSnapshot successfully written!");
                                updateDayPlan();
                            }
                        });

                        break;
                    case "Lunch":
                        currentLunchRef = mDatabase.collection("meals").document(selection);
                        currentPlan.setLunch(currentLunchRef);
                        currentPlanRef.set(currentPlan).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("d", "DocumentSnapshot successfully written!");
                                updateDayPlan();
                            }
                        });

                        break;
                    case "Dinner":
                        currentDinnerRef = mDatabase.collection("meals").document(selection);
                        currentPlan.setDinner(currentDinnerRef);
                        currentPlanRef.set(currentPlan).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("d", "DocumentSnapshot successfully written!");
                                updateDayPlan();
                            }
                        });
                        break;
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    protected void onNewDate(String newDate) {
        dateDisplay.setText(newDate);
        try {
            today = formatter.parse(newDate);
            updateDayPlan();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void updateDayPlan() {
        currentPlanRef = mDatabase.collection("dayplans").document(mFirebaseUser.getUid() + formatter.format(today));
        currentPlanRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("help", "DocumentSnapshot data: " + document.getData());
                        currentPlan = document.toObject(DayPlan.class);
                        //Log.d("help", "DocumentSnapshot data: " + currentUser.getMealsPlanned());
                    } else {
                        Log.d("help", "No such document");
                        currentPlan = new DayPlan();
                        currentPlan.setDate(today);
                        currentPlanRef.set(currentPlan);
                    }
                } else {
                    Log.d("help", "get failed with ", task.getException());
                }
                updateMeals();
            }
        });
    }

    private void updateMeals() {
        currentBreakfastRef = currentPlan.getBreakfast();
        loadBreakfast();
        currentLunchRef = currentPlan.getLunch();
        loadLunch();
        currentDinnerRef = currentPlan.getDinner();
        loadDinner();
    }

    private void loadBreakfast() {
        if (currentBreakfastRef != null) {
            currentBreakfastRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("help", "DocumentSnapshot data: " + document.getData());
                            currentBreakfast = document.toObject(Meal.class);
                            updateBreakfastUI();
                            //Log.d("help", "DocumentSnapshot data: " + currentUser.getMealsPlanned());
                        } else {
                            Log.d("help", "No such document");
                            currentBreakfast = null;
                        }

                    } else {
                        Log.d("help", "get failed with ", task.getException());
                    }

                }
            });

        }
        updateBreakfastUI();

    }


    protected void updateBreakfastUI() {
        if (currentBreakfast != null) {
            breakfastDisplay.setText(currentBreakfast.getName());
            breakViewButton.setVisibility(View.VISIBLE);
        } else {
            breakfastDisplay.setText("Pick a Meal");
            breakViewButton.setVisibility(View.INVISIBLE);
        }
    }

    private void loadLunch() {
        if (currentLunchRef != null) {
            currentLunchRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("help", "DocumentSnapshot data: " + document.getData());
                            currentLunch = document.toObject(Meal.class);
                            updateLunchUI();
                            //Log.d("help", "DocumentSnapshot data: " + currentUser.getMealsPlanned());
                        } else {
                            Log.d("help", "No such document");
                            currentLunch = null;
                        }

                    } else {
                        Log.d("help", "get failed with ", task.getException());
                    }
                }
            });
        }
        updateLunchUI();
    }


    protected void updateLunchUI() {
        if (currentLunch != null) {
            lunchDisplay.setText(currentLunch.getName());
            lunchViewButton.setVisibility(View.VISIBLE);
        } else {
            lunchDisplay.setText("Pick a Meal");
            lunchViewButton.setVisibility(View.INVISIBLE);
        }
    }

    private void loadDinner() {
        if (currentDinnerRef != null) {
            currentDinnerRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("help", "DocumentSnapshot data: " + document.getData());
                            currentDinner = document.toObject(Meal.class);
                            updateDinnerUI();
                            //Log.d("help", "DocumentSnapshot data: " + currentUser.getMealsPlanned());
                        } else {
                            Log.d("help", "No such document");
                            currentDinner = null;
                        }

                    } else {
                        Log.d("help", "get failed with ", task.getException());
                    }
                }
            });
        }
        updateDinnerUI();
    }


    protected void updateDinnerUI() {
        if (currentDinner != null) {
            dinnerDisplay.setText(currentDinner.getName());
            dinViewButton.setVisibility(View.VISIBLE);

        } else {
            dinnerDisplay.setText("Pick a Meal");
            dinViewButton.setVisibility(View.INVISIBLE);
        }
    }

}
