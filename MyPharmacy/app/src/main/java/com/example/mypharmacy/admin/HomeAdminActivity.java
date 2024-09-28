package com.example.mypharmacy.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mypharmacy.Adapter.MyAdapter;
import com.example.mypharmacy.MainActivity;
import com.example.mypharmacy.SeeAllCat;
import com.example.mypharmacy.admin.CartActivity;
import com.example.mypharmacy.DataClass;
import com.example.mypharmacy.ImageSliderAdapter;
import com.example.mypharmacy.LoginActivity;
import com.example.mypharmacy.R;
import com.example.mypharmacy.SeeAllActivity;
import com.example.mypharmacy.user.ProfileActivity;
import com.example.mypharmacy.user.SeeAll;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeAdminActivity extends AppCompatActivity {
    FloatingActionButton fab;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<DataClass> dataList;
    MyAdapter adapter;
    SearchView searchView;

    Button logoutButton;
    TextView seeall , imageView2;

    TextView seeallcate;
    Button profileButton;

    Button cartButton;
    ViewPager2 imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        logoutButton = findViewById(R.id.logoutButton);
        seeall = findViewById(R.id.seeall);
        seeallcate=findViewById(R.id.seeallcate);
        profileButton = findViewById(R.id.profileButton);
        cartButton = findViewById(R.id.cartButton);
        imageView2 = findViewById(R.id.imageView2);


        findViewById(R.id.lin).setOnClickListener(createClickListener("Ears"));
        findViewById(R.id.lin1).setOnClickListener(createClickListener("Nose"));
        findViewById(R.id.lin2).setOnClickListener(createClickListener("Eyes"));
        findViewById(R.id.lin3).setOnClickListener(createClickListener("Muscle"));




        GridLayoutManager gridLayoutManager = new GridLayoutManager(HomeAdminActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        dataList = new ArrayList<>();
        adapter = new MyAdapter(HomeAdminActivity.this, dataList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Android Tutorials");

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeAdminActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                // Limiter le nombre d'éléments à afficher à 2
                int count = 0;
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    if (count < 4) {
                        DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                        dataClass.setKey(itemSnapshot.getKey());
                        dataList.add(dataClass);
                        count++;
                    } else {
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeAdminActivity.this, ProfileActivity.class));
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeAdminActivity.this , MainActivity.class));
            }
        });




        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the activity or fragment where the RecyclerView displaying cart items is defined
                startActivity(new Intent(HomeAdminActivity.this, CartActivity.class));
            }
        });

        seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeAdminActivity.this, SeeAllActivity.class);
                startActivity(intent);
            }
        });

        seeallcate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeAdminActivity.this, SeeAllCat.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        imageSlider = findViewById(R.id.imageSlider);

        // Set up the image slider adapter
        ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(this, imageSlider);
        imageSlider.setAdapter(imageSliderAdapter);
    }


    // ----------------CODE POUR CATEGORIE-------------------//
    private View.OnClickListener createClickListener(final String category) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeAdminActivity.this, SeeAll.class);
                intent.putExtra("catego", category);
                startActivity(intent);
            }
        };
    }

    public void logout(){
        SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Supprime tout le contenu des SharedPreferences "userData"
        editor.apply();
        Intent intent = new Intent(HomeAdminActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
