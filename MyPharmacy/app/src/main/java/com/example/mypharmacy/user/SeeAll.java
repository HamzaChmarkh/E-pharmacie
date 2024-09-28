package com.example.mypharmacy.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypharmacy.Adapter.MyAdapter;
import com.example.mypharmacy.DataClass;
import com.example.mypharmacy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SeeAll extends AppCompatActivity {

    List<DataClass> dataList;
    MyAdapter adapter;
    RecyclerView recyclerView;

    SearchView searchView;
    ImageView retourButton;



    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);

        AlertDialog.Builder builder = new AlertDialog.Builder(SeeAll.this);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        Intent intent = getIntent();

        dataList = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("Android Tutorials").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                    String categorie = intent.getStringExtra("catego");

                    if (dataClass.getDataCate().equals(categorie) ) {
                        dataList.add(dataClass);
                    }

                    dataClass.setKey(itemSnapshot.getKey());


                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();

        retourButton = findViewById(R.id.retour1);

        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous activity
                onBackPressed();
            }
        });

        recyclerView=findViewById(R.id.zzzz);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SeeAll.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);


        builder.setView(R.layout.progress_layout);

        dialog.show();

        dataList = new ArrayList<>();

        adapter = new MyAdapter(SeeAll.this, dataList);
        recyclerView.setAdapter(adapter);


      searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            searchList(newText);
            return true;
        }
    });




}
    public void searchList(String text) {
        ArrayList<DataClass> searchList = new ArrayList<>();
        String searchTextLowerCase = text.toLowerCase();
        for (DataClass dataClass : dataList) {
            String titleLowerCase = dataClass.getDataTitle().toLowerCase();
            if (titleLowerCase.contains(searchTextLowerCase) ) {
                searchList.add(dataClass);
            }
        }
        adapter.searchDataList(searchList);
    }
}