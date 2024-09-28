package com.example.mypharmacy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class PaymentActivity extends AppCompatActivity {

    private EditText editTextCardNumber, textadresse, textrue;
    private TextView totalTextView,textViewItemDetails;
    private Button buttonConfirmPayment;
    private ImageView retourButton,payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentui);

        editTextCardNumber = findViewById(R.id.editTextCardNumber);
        textadresse = findViewById(R.id.textadresse);
        textrue = findViewById(R.id.textrue);
        totalTextView = findViewById(R.id.total);
        textViewItemDetails = findViewById(R.id.textViewItemDetails);
        buttonConfirmPayment = findViewById(R.id.buttonConfirmPayment);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPharmacyPrefs", Context.MODE_PRIVATE);
        float totalAmount = sharedPreferences.getFloat("TOTAL_AMOUNT", 0.0f);
        totalTextView.setText(String.valueOf(totalAmount));

        Intent intent = getIntent();
        ArrayList<String> itemDetails = intent.getStringArrayListExtra("ITEM_DETAILS");
        if (itemDetails != null) {
            StringBuilder detailsBuilder = new StringBuilder();
            for (String detail : itemDetails) {
                detailsBuilder.append(detail).append("\n");
            }
            textViewItemDetails.setText(detailsBuilder.toString());
        }


        buttonConfirmPayment.setEnabled(false);

        editTextCardNumber.addTextChangedListener(confirmButtonWatcher);
        textadresse.addTextChangedListener(confirmButtonWatcher);
        textrue.addTextChangedListener(confirmButtonWatcher);

        buttonConfirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmPayment();
            }
        });

        retourButton = findViewById(R.id.imageView);
        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        payment = findViewById(R.id.payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentActivity.this,ActivityPaymentList.class));
            }
        });
    }

    private void confirmPayment() {
        try {
            String cardNumber = editTextCardNumber.getText().toString().trim();
            String ville = textadresse.getText().toString().trim();
            String rue = textrue.getText().toString().trim();
            String date = Calendar.getInstance().getTime().toString();
            String total = totalTextView.getText().toString().trim();
            SharedPreferences sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);
            String username = sharedPreferences.getString("username", "");

            ArrayList<String> selectedItems = getIntent().getStringArrayListExtra("ITEM_DETAILS");


            Payment data = new Payment(cardNumber, ville, rue, date, total,username,selectedItems);
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();




            databaseRef.child("payment").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        databaseRef.child("payment").setValue(true);
                    }

                    DatabaseReference paymentRef = databaseRef.child("payment").push();
                    paymentRef.setValue(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(PaymentActivity.this, "Payment confirmed!", Toast.LENGTH_SHORT).show();
                                    // Pass total amount to ActivityPaymentList
                                    try {
                                        Intent intent = new Intent(PaymentActivity.this, ActivityPaymentList.class);
                                        // intent.putExtra("TOTAL_AMOUNT", Float.parseFloat(total));
                                        startActivity(intent);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(PaymentActivity.this, "Error starting ActivityPaymentList", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(PaymentActivity.this, "Failed Payment", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle error
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error confirming payment", Toast.LENGTH_SHORT).show();
        }
    }


    private final TextWatcher confirmButtonWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            boolean cardNumberFilled = !editTextCardNumber.getText().toString().trim().isEmpty();
            boolean addressFilled = !textadresse.getText().toString().trim().isEmpty();
            boolean rueFilled = !textrue.getText().toString().trim().isEmpty();
            buttonConfirmPayment.setEnabled(cardNumberFilled && addressFilled && rueFilled);
        }
    };
}