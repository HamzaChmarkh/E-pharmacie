package com.example.mypharmacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityPaymentList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PaymentAdapter paymentAdapter;
    private ImageView retourButton;
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        retourButton = findViewById(R.id.retour1);
        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                onBackPressed();
            }
        });



        // Fetch payment data from Firebase
        fetchPaymentData();


    }

    private void fetchPaymentData() {
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("payment");
        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Payment> payments = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String cardNumber = snapshot.child("cardNumber").getValue(String.class);
                    String ville = snapshot.child("ville").getValue(String.class);
                    String paymentType = snapshot.child("rue").getValue(String.class);
                    String date = snapshot.child("date").getValue(String.class);
                    String total = snapshot.child("total").getValue(String.class);
                    String username = snapshot.child("username").getValue(String.class);

                    ArrayList<String> items = new ArrayList<>();
                    for (DataSnapshot itemSnapshot : snapshot.child("items").getChildren()) {
                        String item = itemSnapshot.getValue(String.class);
                        items.add(item);
                    }


                    // Create Payment object with all arguments
                    Payment payment = new Payment(cardNumber, ville, paymentType, date, total ,username,items);
                    payments.add(payment);
                }

                paymentAdapter = new PaymentAdapter(ActivityPaymentList.this, payments);
                recyclerView.setAdapter(paymentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled event
                Toast.makeText(ActivityPaymentList.this, "Failed to fetch payment data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}