package com.example.mypharmacy;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.mypharmacy.user.SeeAll;

import java.util.ArrayList;
import java.util.List;
import android.widget.LinearLayout;



public class SeeAllCat extends AppCompatActivity {

    private ImageView retourButton;
    private SearchView searchView;
    private LinearLayout lin, lin1, lin2, lin3, lin4, lin5, lin6, lin8,lin9;
    private List<DataClass> dataList; // Assuming you have a list of DataClass objects
    private List<String> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_test2);

        // Initialize views
        retourButton = findViewById(R.id.retour1);
        searchView = findViewById(R.id.searchView);
        lin = findViewById(R.id.lin);
        //lin1 = findViewById(R.id.lin1);
        lin2 = findViewById(R.id.lin2);
        lin3 = findViewById(R.id.lin3);
        lin4 = findViewById(R.id.lin4);
        lin5 = findViewById(R.id.lin5);
        lin6 = findViewById(R.id.lin6);
        //lin8 = findViewById(R.id.lin8);
        lin9 = findViewById(R.id.lin9);

        // Set click listeners for each category
        lin.setOnClickListener(createClickListener("Heart"));
        //lin1.setOnClickListener(createClickListener("Liver"));
        lin2.setOnClickListener(createClickListener("Eyes"));
        lin3.setOnClickListener(createClickListener("Muscle"));
        lin4.setOnClickListener(createClickListener("Nose"));
        lin5.setOnClickListener(createClickListener("Ears"));
        lin6.setOnClickListener(createClickListener("Lungs"));
        //lin8.setOnClickListener(createClickListener("Throat"));
        lin9.setOnClickListener(createClickListener("Brain"));


        categoryList = new ArrayList<>();
        categoryList.add("Heart");
        categoryList.add("Liver");
        categoryList.add("Eyes");
        categoryList.add("Muscle");
        categoryList.add("Nose");
        categoryList.add("Ears");
        categoryList.add("Lungs");
        categoryList.add("Throat");
        categoryList.add("Brain");



        // Set click listener for retourButton
        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Set up search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchCategories(newText);
                return true;
            }
        });
    }

    // Method to create a click listener for category LinearLayouts
    private View.OnClickListener createClickListener(final String category) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeeAllCat.this, SeeAll.class);
                intent.putExtra("catego", category);
                startActivity(intent);
            }
        };
    }

    private String normalizeQuery(String query) {
        // Replace accented characters with their base characters
        return query
                .replaceAll("[àáâãäå]", "a")
                .replaceAll("[èéêë]", "e")
                .replaceAll("[ìíîï]", "i")
                .replaceAll("[òóôõö]", "o")
                .replaceAll("[ùúûü]", "u")
                .replaceAll("[ç]", "c");
    }

    // Method to filter categories based on search query
    private void searchCategories(String query) {
        // Filtered list to hold categories that match the search query
        List<String> filteredList = new ArrayList<>();
        String normalizedQuery = normalizeQuery(query.toLowerCase());

        for (String category : categoryList) {
            String normalizedCategory = normalizeQuery(category.toLowerCase());

            // Check if the category name contains the search query (case insensitive)
            if (normalizedCategory.contains(normalizedQuery)) {
                filteredList.add(category);
            }
        }

        // Show or hide category LinearLayouts based on search result
        lin.setVisibility(filteredList.contains("Heart") ? View.VISIBLE : View.GONE);
        //lin1.setVisibility(filteredList.contains("Liver") ? View.VISIBLE : View.GONE);
        lin2.setVisibility(filteredList.contains("Eyes") ? View.VISIBLE : View.GONE);
        lin3.setVisibility(filteredList.contains("Muscle") ? View.VISIBLE : View.GONE);
        lin4.setVisibility(filteredList.contains("Nose") ? View.VISIBLE : View.GONE);
        lin5.setVisibility(filteredList.contains("Ears") ? View.VISIBLE : View.GONE);
        lin6.setVisibility(filteredList.contains("Lungs") ? View.VISIBLE : View.GONE);
        //lin8.setVisibility(filteredList.contains("Throat") ? View.VISIBLE : View.GONE);
        lin9.setVisibility(filteredList.contains("Brain") ? View.VISIBLE : View.GONE);
    }


    // Helper method to check if the filteredList contains a specific category
    private boolean filteredListContains(String category, List<DataClass> filteredList) {
        for (DataClass data : filteredList) {
            if (data.getDataCate().equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
    }





    /*public void searchList(String text) {
        ArrayList<DataClass> searchList = new ArrayList<>();
        String searchTextLowerCase = text.toLowerCase();
        for (DataClass dataClass : dataList) {
            String titleLowerCase = dataClass.getDataCate().toLowerCase();
            if (titleLowerCase.contains(searchTextLowerCase) ) {
                searchList.add(dataClass);
            }
        }
        adapter.searchDataList(searchList);
    }*/



}

