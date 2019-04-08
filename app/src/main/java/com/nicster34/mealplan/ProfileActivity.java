package com.nicster34.mealplan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {


    private final int PICK_IMAGE_REQUEST = 71;
    private ImageView imageView;
    private CheckBox box1;
    private CheckBox box2;
    private CheckBox box3;
    private CheckBox box4;
    private Button btnChoose;
    private Uri filePath;
    private FirebaseAuth mFirebaseAuth;
    Intent myIntent;
    private FirebaseUser mFirebaseUser;
    private TextView userEmailDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        myIntent = getIntent();


        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();


        imageView = (ImageView) findViewById(R.id.imgView);
        btnChoose = (Button) findViewById(R.id.button1);
        box1 = (CheckBox) findViewById(R.id.checkBox);
        box2 = (CheckBox) findViewById(R.id.checkBox3);
        box3 = (CheckBox) findViewById(R.id.checkBox4);
        box4 = (CheckBox) findViewById(R.id.checkBox5);
        if (myIntent.getExtras() == null) {
            box1.setVisibility(View.INVISIBLE);
            box2.setVisibility(View.INVISIBLE);
            box3.setVisibility(View.INVISIBLE);
            box4.setVisibility(View.INVISIBLE);
        } else {
            box1.setChecked((Boolean) myIntent.getExtras().get("vegan"));
            box2.setChecked((Boolean) myIntent.getExtras().get("gluten"));
            box3.setChecked((Boolean) myIntent.getExtras().get("nut"));
            box4.setChecked((Boolean) myIntent.getExtras().get("fish"));

        }
        userEmailDisplay = (TextView) findViewById(R.id.userEmail);
        userEmailDisplay.setText(mFirebaseUser.getEmail());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
                //m
            }
        });
        box1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (box1.isChecked()) {
                    Toast.makeText(getBaseContext(), "Vegan", Toast.LENGTH_SHORT).show();//add banned vegan ingredients
                }
            }
        });
        box2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (box2.isChecked()) {
                    Toast.makeText(getBaseContext(), "Gluten-Free", Toast.LENGTH_SHORT).show();//add banned gluten free ingredients
                }
            }
        });
        box3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (box1.isChecked()) {
                    //ban all nuts
                }
            }
        });
        box4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (box1.isChecked()) {
                    //ban all fish
                }
            }
        });
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                RoundedBitmapDrawable mDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                mDrawable.setCircular(true);

                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void logout() {
        mFirebaseAuth.signOut();
        startActivity(new Intent(getApplicationContext(), SignInActivity.class));
    }

    private void updateUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            //String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            userEmailDisplay.setText(email);
            imageView.setImageURI(photoUrl);

            // Check if user's email is verified
            // boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            //mFirebaseAuth.signOut();
        }
    }
}
