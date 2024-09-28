package com.example.mypharmacy.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mypharmacy.LoginActivity;
import com.example.mypharmacy.R;
import com.example.mypharmacy.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class UserNavActivity extends AppCompatActivity {

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
                // replaceFragment(new LibraryFragmentUser());
                logout();
            }


            return true;

        });

    }

    private void logout() {
        // Perform logout actions here, such as signing out the user
        // For example, if you're using Firebase Authentication:
        FirebaseAuth.getInstance().signOut();

        // After logout, navigate to the login screen or any other desired screen
        Intent intent = new Intent(UserNavActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Optional, to finish the current activity
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}