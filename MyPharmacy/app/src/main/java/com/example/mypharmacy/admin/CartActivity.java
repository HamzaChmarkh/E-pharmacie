package com.example.mypharmacy.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypharmacy.CartAdapter;
import com.example.mypharmacy.CartItem;
import com.example.mypharmacy.LoginActivity;
import com.example.mypharmacy.PaymentActivity;
import com.example.mypharmacy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = "CartActivity";
    private static List<CartItem> cartItems = new ArrayList<>();

    private RecyclerView recyclerView;
    private ImageView retourButton;
    private CartAdapter cartAdapter;
    private SharedPreferences sharedPreferences;
    private AppCompatButton button4;

    public static double totalAllProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcart);

        sharedPreferences = getSharedPreferences("MyPharmacyPrefs", MODE_PRIVATE);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve and set up cart items
        retrieveCartItems();

        retourButton = findViewById(R.id.imageView5);
        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double totalAllProducts = calculateTotalAllProducts();

                // Save total amount in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("TOTAL_AMOUNT", (float) totalAllProducts);
                editor.apply();

                ArrayList<String> itemDetails = new ArrayList<>();
                for (CartItem item : cartItems) {
                    String details = item.getDataTitle() + ": " + item.getQuantity();
                    itemDetails.add(details);
                }

                Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                intent.putStringArrayListExtra("ITEM_DETAILS", itemDetails);
                startActivity(intent);
            }
        });
    }

    private void retrieveCartItems() {
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("add to cart");
        String username = sharedPreferences.getString("username", "");

        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartItems.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String title = snapshot.child("dataTitle").getValue(String.class);
                    String desc = snapshot.child("dataDesc").getValue(String.class);
                    String lang = snapshot.child("dataLang").getValue(String.class);
                    String image = snapshot.child("dataImage").getValue(String.class);
                    String key = snapshot.child("key").getValue(String.class);
                    String date = snapshot.child("date").getValue(String.class);
                    Integer quantityInteger = snapshot.child("quantity").getValue(Integer.class);
                    int quantity = (quantityInteger != null) ? quantityInteger : 0;

                    CartItem cartItem = new CartItem(title, desc, lang, image, key, date, username, quantity);
                    cartItems.add(cartItem);
                }
                cartAdapter = new CartAdapter(CartActivity.this, cartItems);
                recyclerView.setAdapter(cartAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to retrieve cart items: " + databaseError.getMessage());
            }
        });
    }

    public static double calculateTotalAllProducts() {
        double totalAllProducts = 0;
        for (CartItem item : cartItems) {
            totalAllProducts += item.getTotalPrice();
        }
        return totalAllProducts;
    }
}