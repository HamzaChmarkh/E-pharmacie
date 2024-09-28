package com.example.mypharmacy.admin;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mypharmacy.DataClass;
import com.example.mypharmacy.LoginActivity;
import com.example.mypharmacy.R;
import com.example.mypharmacy.user.ProfileActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class AdminDetailActivity extends AppCompatActivity {

    TextView detailDesc, detailTitle, detailLang , detailCate;
    ImageView detailImage, imageView2 , imageView;
    String key = "";
    String imageUrl = "";

    Button button;

    Button logoutButton;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_admin);

        detailDesc = findViewById(R.id.detailDesc);
        detailImage = findViewById(R.id.detailImage);
        detailTitle = findViewById(R.id.detailTitle);
        button = findViewById(R.id.button);
        detailLang = findViewById(R.id.detailLang);
        imageView2 = findViewById(R.id.imageView2);
        detailCate = findViewById(R.id.detailCate);

        //logoutButton = findViewById(R.id.logoutButton);




        //Button profileButton = findViewById(R.id.profileButton);


        //Button homeButton = findViewById(R.id.homeButton);
        //Button cartButton = findViewById(R.id.cartButton);

        /*homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDetailActivity.this, HomeAdminActivity.class));
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDetailActivity.this, CartActivity.class));
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

         */

        imageView = findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                onBackPressed();
            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailDesc.setText(bundle.getString("Description"));
            detailTitle.setText(bundle.getString("Title"));
            detailLang.setText(bundle.getString("Language"));
            detailCate.setText(bundle.getString("Categorie"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }


        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDetailActivity.this, CartActivity.class));
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from TextViews and ImageView
                String title = detailTitle.getText().toString();
                String lang = detailLang.getText().toString();
                String cate = detailCate.getText().toString();
                String image = imageUrl; // Assuming imageUrl is already initialized
                String currentDate = Calendar.getInstance().getTime().toString(); // Get current date

                // Get authenticated user's username (replace this with your authentication method)
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String username = user != null ? user.getDisplayName() : "Anonymous";

                // Create a new data object
                DataClass data = new DataClass(title, "", lang, image, currentDate, username,cate);

                // Get a reference to the Firebase Realtime Database
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

                // Check if "add to cart" collection exists
                databaseRef.child("add to cart").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            // "add to cart" collection doesn't exist, create it
                            databaseRef.child("add to cart").setValue(true);
                        }

                        // Push data to "add to cart" collection
                        DatabaseReference cartRef = databaseRef.child("add to cart").push();
                        cartRef.setValue(data)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AdminDetailActivity.this, "Item added to cart successfully", Toast.LENGTH_SHORT).show();


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AdminDetailActivity.this, "Failed to add item to cart", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle error
                    }
                });
            }


        });




    }

    private void logout() {
        Intent intent = new Intent(AdminDetailActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
