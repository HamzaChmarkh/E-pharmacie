package com.example.mypharmacy;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mypharmacy.databinding.ActivityMainBinding;
import com.example.mypharmacy.user.HomeFragmentUser;
import com.example.mypharmacy.user.LibraryFragmentUser;
import com.example.mypharmacy.user.ShortsFragmentUser;
import com.example.mypharmacy.user.SubscriptionFragmentUser;

public class AdminNavActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragmentUser());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragment(new HomeFragmentUser());
            } else if (itemId == R.id.shorts) {
                replaceFragment(new ShortsFragmentUser());
            } else if (itemId == R.id.subscriptions) {
                replaceFragment(new SubscriptionFragmentUser());
            } else if (itemId == R.id.library) {
                replaceFragment(new LibraryFragmentUser());
            }


            return true;

        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}